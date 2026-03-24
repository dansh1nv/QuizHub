package ru.quizHub.quizList.presentation

import android.content.ActivityNotFoundException
import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.quizHub.common.orTrue
import ru.quizHub.core.presentation.ActionEventsListener
import ru.quizHub.core.presentation.IntentErrorMapper
import ru.quizHub.core.presentation.ScreenState
import ru.quizHub.core.presentation.SnackbarListener
import ru.quizHub.core.presentation.calendar.DateSelection
import ru.quizHub.core.presentation.model.ActionEvents
import ru.quizHub.core.presentation.model.IntentError
import ru.quizHub.core.presentation.model.SnackbarEvents
import ru.quizHub.core.presentation.model.UIStatus
import ru.quizHub.core.presentation.viewModel.BaseMviViewModel
import ru.quizHub.core.resourceManager.IResourceManager
import ru.quizHub.designsystem.theme.bottomsheet.controller.BottomSheetController
import ru.quizHub.quizList.R
import ru.quizHub.quizList.mappers.ActionEventsMapper
import ru.quizHub.quizList.mappers.CommonMapper
import ru.quizHub.quizList.mappers.EventPieChartMapper
import ru.quizHub.quizList.mappers.QuizListUIMapper
import ru.quizHub.quizList.models.CityModel
import ru.quizHub.quizList.models.bottomsheet.BottomSheetModels
import ru.quizHub.quizList.models.item.Organization
import ru.quizHub.quizList.models.item.QuizUI
import ru.quizHub.quizList.models.sorting.Sort
import ru.quizHub.quizlist.interactors.CommonInteractor
import ru.quizHub.quizlist.interactors.GeoInfoInteractor
import ru.quizHub.quizlist.interactors.QuizListInteractor
import timber.log.Timber

internal class QuizListViewModel(
    private val interactor: QuizListInteractor,
    private val geoInfoInteractor: GeoInfoInteractor,
    private val commonInteractor: CommonInteractor,
    private val commonMapper: CommonMapper,
    private val quizListUIMapper: QuizListUIMapper,
    private val eventPieChartMapper: EventPieChartMapper,
    private val actionEventsMapper: ActionEventsMapper,
    private val intentErrorMapper: IntentErrorMapper,
    private val resourceManager: IResourceManager,
    private val bottomSheetController: BottomSheetController,
    private val actionEventsListener: ActionEventsListener,
    private val snackbarListener: SnackbarListener
) : BaseMviViewModel<QuizListState, QuizListSideEffect, QuizListEvent>(
    initialState = QuizListState()
), BottomSheetController by bottomSheetController {

    private var quizListFetchJob: Job? = null
    private var geoInfoFetchJob: Job? = null

    override suspend fun onLaunch() {
        fetchCities()
        observeCurrentCity()
    }

    override fun handleEvent(event: QuizListEvent) {
        when (event) {
            is ScreenEvent -> onScreenEvent(event)
            is BottomSheetEvent -> onBottomSheetEvent(event)
        }
    }

    private fun refresh() = viewModelScope.launch {
        fetchCities()
        fetchQuizList()
    }

    private fun observeCurrentCity() {
        commonInteractor.observeCurrentCity()
            .distinctUntilChanged()
            .onEach { cityName ->
                updateState {
                    copy(
                        currentCity = cities
                            .firstOrNull { it.name == cityName }
                            ?: CityModel.UNKNOWN
                    )
                }
                completeAction { fetchQuizList() }
            }.launchIn(viewModelScope)
    }

    private fun fetchQuizList() = completeAction {
        quizListFetchJob?.cancel()
        quizListFetchJob = viewModelScope.launch {
            updateState {
                copy(uiStatus = UIStatus.Loading)
            }
            val selectedCity = commonMapper.mapToCity(container.stateFlow.value.currentCity)
            try {
                interactor.getAllQuizList(selectedCity)
                    .map { quizList ->
                        quizList.map { quizListUIMapper.mapToQuizUI(it) }
                    }
                    .catch { ex ->
                        Timber.e(ex)
                        updateState {
                            copy(
                                uiStatus = UIStatus.Error(
                                    errorText =
                                        resourceManager.getStringById(R.string.quiz_list_fetch_data_error)
                                )
                            )
                        }
                    }
                    .flowOn(Dispatchers.Default)
                    .onEach { quizList ->
                        updateState {
                            copy(
                                quizList = quizList,
                                uiStatus = if (quizList.isNotEmpty() && currentCity != CityModel.UNKNOWN) {
                                    UIStatus.Loaded()
                                } else {
                                    UIStatus.Empty
                                }
                            )
                        }
                        completeAction {
                            showQuizList()
                        }
                    }
                    .flowOn(Dispatchers.Main)
                    .collect()
            } catch (e: CancellationException) {
                throw e
            }
        }
    }

    private fun updateCurrentTab(index: Int) = updateState { copy(selectedTabIndex = index) }

    private fun onScreenEvent(event: ScreenEvent) {
        when (event) {
            is ScreenEvent.OnSortButtonClick -> showSorting()
            is ScreenEvent.OnLocationClick -> handleLocationClick()
            is ScreenEvent.OnFiltersButtonClick -> showFilters()
            is ScreenEvent.OnTabClick -> updateCurrentTab(event.index)
            is ScreenEvent.OnRefresh -> refresh()
            is ScreenEvent.OnCalendarClick -> handleCalendarClick()
            is ScreenEvent.OnCardItemClicked -> navigateToQuizDetails(event.id)

            is ScreenEvent.OnScrollPositionChanged -> updateScrollUp(event.isScrollUpVisible)
            is ScreenEvent.OnShareEventClick -> handleShareEventClick(event.quiz)
            is ScreenEvent.OnShowLocationEventClick -> handleShowLocationEventClick(event.quiz)
            is ScreenEvent.ResetFilters -> {
                resetFilters()
                showQuizList()
            }

            is ScreenEvent.OnSearch -> search(event.query)
            is ScreenEvent.OnCityClick -> updateCurrentCity(event.city)
        }
    }

    private fun updateScrollUp(showScrollToTop: Boolean) = updateState {
        copy(isScrollUpVisible = showScrollToTop)
    }

    private fun updateCurrentCity(city: CityModel) {
        updateState {
            val cities = cities.map { city ->
                city.copy(isSearchVisible = true)
            }
            copy(
                currentCity = city,
                cities = cities,
            )
        }

        completeAction {
            fetchQuizList()
            bottomSheetController.dismiss()
            viewModelScope.launch {
                commonInteractor.updateCurrentCity(city.name)
            }
        }
    }

    private fun search(query: String) {
        updateState {
            val newCityList = if (query.isEmpty()) cities else cities.map { city ->
                val hasSearch = city.name.contains(query, true)
                if (hasSearch) {
                    city.copy(isSearchVisible = true)
                } else {
                    city.copy(isSearchVisible = false)
                }
            }
            copy(cities = newCityList)
        }
    }

    private fun onBottomSheetEvent(event: BottomSheetEvent) {
        when (event) {
            is BottomSheetEvent.OnApplyFiltersClick -> applyFilters(event.filters)
            is BottomSheetEvent.OnSortClick -> applySorting(event.sort)
            is BottomSheetEvent.OnCalendarDaySelected -> handleCalendarDaysSelected(event.days)
        }
    }

    private fun handleShowLocationEventClick(quiz: QuizUI) {
        val query = actionEventsMapper.buildGeoQuery(quiz)
        geoInfoFetchJob?.cancel()
        geoInfoFetchJob = geoInfoInteractor.getGeoInfoByQuery(query)
            .map { geoInfo ->
                val location = commonMapper.mapGeoLocation(
                    latitude = geoInfo.latitude.toString(),
                    longitude = geoInfo.longitude.toString(),
                )
                val updatedQuiz = quiz.copy(location = quiz.location?.copy(geolocation = location))
                val quizList = container.stateFlow.value.quizList.map { model ->
                    if (quiz.id == model.id) updatedQuiz else model
                }
                Pair(updatedQuiz, quizList)
            }
            .catch { ex ->
                Timber.e(ex)
                val locationText = actionEventsMapper.mapToLocationEventText(quiz)
                actionEventsListener.onActionEvent(ActionEvents.ShowLocationEvent(locationText))
                handleError(ex)
            }
            .flowOn(Dispatchers.Default)
            .onEach { (updatedQuiz, quizList) ->
                updateState { copy(quizList = quizList) }
                val locationText = actionEventsMapper.mapToLocationEventText(updatedQuiz)
                actionEventsListener.onActionEvent(ActionEvents.ShowLocationEvent(locationText))
            }
            .flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)
    }

    private fun handleError(exception: Throwable) {
        val error = when (exception) {
            is ActivityNotFoundException -> IntentError.ActivityNotFound
            is IllegalArgumentException -> IntentError.IllegalArgument
            is SecurityException -> IntentError.Security
            is NoSuchElementException -> IntentError.GeoLocationError
            else -> IntentError.Unknown(exception)
        }
        if (error !is IntentError.GeoLocationError) {
            val errorMessage = intentErrorMapper.mapErrorMessage(error)
            snackbarListener.showSnackbar(SnackbarEvents.ShowErrorSnackbar(errorMessage))
        }
    }

    private fun handleShareEventClick(quiz: QuizUI) {
        val shareText = actionEventsMapper.mapToShareText(quiz)
        actionEventsListener.onActionEvent(
            ActionEvents.ShareEvent(shareText)
        )
    }

    private fun handleCalendarClick() {
        completeAction {
            val quizList = container.stateFlow.value.quizList
            val calendarEvents = eventPieChartMapper.mapToCalendarEventsUI(quizList)
            bottomSheetController.show(
                BottomSheetModels.CalendarBottomSheetModel(
                    toolbar = QuizListBottomSheetToolbar.withCloseButton(
                        title = resourceManager.getStringById(R.string.calendar_title),
                        onCloseClick = { dismiss() },
                    ),
                    events = calendarEvents
                )
            )
        }
    }

    private fun handleLocationClick() {
        bottomSheetController.show(
            BottomSheetModels.CityBottomSheetModel(
                toolbar = QuizListBottomSheetToolbar.withCloseButton(
                    title = resourceManager.getStringById(R.string.city_bottomsheet_title),
                    onCloseClick = { dismiss() },
                ),
            )
        )
    }

    private fun handleCalendarDaysSelected(dateSelection: DateSelection) {
        updateState {
            copy(
                filtersState = filtersState.copy(
                    dateSelection = dateSelection,
                    isApplied = dateSelection.startDate != null
                )
            )
        }

        completeAction {
            showQuizList()
            bottomSheetController.dismiss()
        }
    }

    private fun showFilters() {
        bottomSheetController.show(
            BottomSheetModels.FilterBottomSheetModel(
                toolbar = QuizListBottomSheetToolbar.withCloseButton(
                    title = resourceManager.getStringById(R.string.filter_title),
                    onCloseClick = { dismiss() },
                )
            )
        )
    }

    private fun showSorting() {
        bottomSheetController.show(
            BottomSheetModels.SortingBottomSheetModel(
                toolbar = QuizListBottomSheetToolbar.withCloseButton(
                    title = resourceManager.getStringById(R.string.sorting_title),
                    onCloseClick = { dismiss() },
                )
            )
        )
    }

    private fun applySorting(sort: Sort) {
        updateState { copy(sort = sort) }
        completeAction {
            showQuizList()
            bottomSheetController.dismiss()
        }
    }

    private fun applyFilters(filters: List<Organization>) {
        updateState {
            copy(
                filtersState = filtersState.copy(
                    organizations = filters,
                    isApplied = filters.isNotEmpty(),
                )
            )
        }
        completeAction {
            showQuizList()
            bottomSheetController.dismiss()
        }
    }

    private fun showQuizList() = updateState {
        if (uiStatus is UIStatus.Loading) return@updateState this

        val quizList = quizList.map { quiz ->
            quiz.copy(
                isVisible = filtersState.organizations.contains(quiz.organization)
                    .takeIf { filtersState.organizations.isNotEmpty() }
                    .orTrue()
            )
        }

        val startDate = filtersState.dateSelection?.startDate
        val endDate = filtersState.dateSelection?.endDate

        val quizListByDate = when {
            startDate == null -> {
                quizList
            }

            endDate == null -> {
                quizList.map { quiz ->
                    quiz.copy(isVisible = quiz.formattedDate?.date?.date == startDate)
                }
            }

            else -> {
                quizList.map { quiz ->
                    val quizDate = quiz.formattedDate?.date?.date
                    quiz.copy(isVisible = quizDate != null && quizDate in startDate..endDate)
                }
            }
        }

        val sortedList = when (this.sort) {
            Sort.ASC_DATE -> quizListByDate.sortedBy { it.formattedDate?.date }
            Sort.DESC_DATE -> quizListByDate.sortedByDescending { it.formattedDate?.date }
        }

        copy(quizList = sortedList)
    }

    private fun resetFilters() {
        updateState {
            copy(
                filtersState = filtersState.copy(
                    organizations = emptyList(),
                    dateSelection = null,
                    isApplied = false,
                ),
                sort = Sort.ASC_DATE,
            )
        }
        completeAction { showQuizList() }
    }

    private fun navigateToQuizDetails(quizId: String) {
        postSideEffect(QuizListSideEffect.NavigateQuizDetails(quizId))
    }

    private suspend fun fetchCities() {
        commonInteractor.fetchCities()
            .map { commonMapper.mapCities(it) }
            .flowOn(Dispatchers.IO)
            .onEach {
                updateState {
                    copy(cities = it)
                }
            }
            .flowOn(Dispatchers.Main)
            .collect()
    }
}

@Immutable
internal data class QuizListState(
    val uiStatus: UIStatus = UIStatus.Loading,
    val selectedTabIndex: Int = 0,
    val quizList: List<QuizUI> = emptyList(),
    val featureToggle: FeatureToggle = FeatureToggle(),
    val filtersState: FiltersState = FiltersState(),
    val currentCity: CityModel = CityModel.UNKNOWN,
    val cities: List<CityModel> = emptyList(),
    val sort: Sort = Sort.ASC_DATE,
    val isScrollUpVisible: Boolean = false,
) : ScreenState

internal data class FeatureToggle(
    val isFavouriteFeatureEnabled: Boolean = false,
    val isFiltersFeatureEnabled: Boolean = true,
    val isSortFeatureEnabled: Boolean = true,
    val isCalendarFeatureEnable: Boolean = true
)