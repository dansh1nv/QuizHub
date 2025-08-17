package ru.quizHub.quizList.presentation

import android.content.ActivityNotFoundException
import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.kizitonwose.calendar.core.CalendarDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.quizHub.common.addOrAppend
import ru.quizHub.core.presentation.ActionEventsListener
import ru.quizHub.core.presentation.IntentErrorMapper
import ru.quizHub.core.presentation.ScreenState
import ru.quizHub.core.presentation.SnackbarListener
import ru.quizHub.core.presentation.model.ActionEvents
import ru.quizHub.core.presentation.model.IntentError
import ru.quizHub.core.presentation.model.SnackbarEvents
import ru.quizHub.core.presentation.model.UIStatus
import ru.quizHub.core.presentation.viewModel.BaseMviViewModel
import ru.quizHub.core.resourceManager.IResourceManager
import ru.quizHub.designsystem.theme.bottomsheet.controller.BottomSheetController
import ru.quizHub.designsystem.theme.bottomsheet.model.QuizBottomSheetModel.Toolbar
import ru.quizHub.designsystem.theme.bottomsheet.model.QuizBottomSheetModel.Toolbar.IconModel
import ru.quizHub.designsystem.theme.utils.`typealias`.UIDrawable
import ru.quizHub.quizList.R
import ru.quizHub.quizList.mappers.ActionEventsMapper
import ru.quizHub.quizList.mappers.CommonMapper
import ru.quizHub.quizList.mappers.EventPieChartMapper
import ru.quizHub.quizList.mappers.QuizPleaseMapper
import ru.quizHub.quizList.mappers.ShakerQuizMapper
import ru.quizHub.quizList.mappers.SquizMapper
import ru.quizHub.quizList.models.CityModel
import ru.quizHub.quizList.models.bottomsheet.BottomSheetModels
import ru.quizHub.quizList.models.filters.Filters
import ru.quizHub.quizList.models.item.Organization
import ru.quizHub.quizList.models.item.QuizUI
import ru.quizHub.quizList.models.sorting.Sort
import ru.quizHub.quizlist.interactors.CommonInteractor
import ru.quizHub.quizlist.interactors.GeoInfoInteractor
import ru.quizHub.quizlist.interactors.QuizListInteractor
import ru.quizHub.quizlist.models.Quiz
import ru.quizHub.quizlist.models.QuizPlease
import ru.quizHub.quizlist.models.SQuiz
import ru.quizHub.quizlist.models.ShakerQuiz
import timber.log.Timber

internal class QuizListViewModel(
    private val interactor: QuizListInteractor,
    private val geoInfoInteractor: GeoInfoInteractor,
    private val commonInteractor: CommonInteractor,
    private val commonMapper: CommonMapper,
    private val squizMapper: SquizMapper,
    private val quizPleaseMapper: QuizPleaseMapper,
    private val shakerQuizMapper: ShakerQuizMapper,
    private val actionEventsMapper: ActionEventsMapper,
    private val intentErrorMapper: IntentErrorMapper,
    private val resourceManager: IResourceManager,
    private val bottomSheetController: BottomSheetController,
    private val actionEventsListener: ActionEventsListener,
    private val snackbarListener: SnackbarListener
) : BaseMviViewModel<QuizListState, QuizListSideEffect, QuizListEvent>(
    initialState = QuizListState()
), BottomSheetController by bottomSheetController {

    private val quizMap = mutableMapOf<Organization, MutableList<QuizUI>>()

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

    private fun observeCurrentCity() {
        commonInteractor.observeCurrentCity()
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
        viewModelScope.launch {
            quizMap.clear()
            updateState {
                copy(uiStatus = UIStatus.Loading)
            }
            val selectedCity = commonMapper.mapToCity(container.stateFlow.value.currentCity)
            interactor.getAllQuizList(selectedCity)
                .map { quizList ->
                    updateQuizCache(quizList)
                    getQuizzesFromCache()
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
                            uiStatus = if (quizMap.values.isNotEmpty() || currentCity != CityModel.UNKNOWN) {
                                UIStatus.Loaded()
                            } else {
                                UIStatus.Empty
                            }
                        )
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    private fun updateCurrentTab(index: Int) = updateState { copy(selectedTabIndex = index) }

    private fun onScreenEvent(event: ScreenEvent) {
        when (event) {
            is ScreenEvent.OnSortButtonClick -> showSorting()
            is ScreenEvent.OnLocationClick -> handleLocationClick()
            is ScreenEvent.OnFiltersButtonClick -> showFilters()
            is ScreenEvent.OnTabClick -> updateCurrentTab(event.index)
            is ScreenEvent.OnRefresh -> fetchQuizList()
            is ScreenEvent.OnCalendarClick -> handleCalendarClick()
            is ScreenEvent.OnCardItemClicked -> {
                //Добавить экран детализации квиза
                //navigateToQuizDetails(event.id)
            }

            is ScreenEvent.OnShareEventClick -> handleShareEventClick(event.id)
            is ScreenEvent.OnShowLocationEventClick -> handleShowLocationEventClick(event.quiz)
            is ScreenEvent.ResetFilters -> {
                resetFilters()
                showQuizList()
            }

            is ScreenEvent.OnSearch -> search(event.query)
            is ScreenEvent.OnCityClick -> updateCurrentCity(event.city)
        }
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
            is BottomSheetEvent.OnFilterClick -> applyFilters(event.filters)
            is BottomSheetEvent.OnSortClick -> applySorting(event.sort)
            is BottomSheetEvent.OnCalendarDayClick -> handleCalendarDayClick(event.day)
        }
    }

    private fun handleShowLocationEventClick(quiz: QuizUI) {
        val query = actionEventsMapper.buildGeoQuery(quiz)
        geoInfoInteractor.getGeoInfoByQuery(query)
            .map { geoInfo ->
                val location = commonMapper.mapGeoLocation(
                    latitude = geoInfo.latitude.toString(),
                    longitude = geoInfo.longitude.toString(),
                )
                val updatedQuiz = quiz.copy(location = quiz.location?.copy(geolocation = location))
                val quizList = quizMap[quiz.organization]?.map { model ->
                    if (quiz.id == model.id) updatedQuiz else model
                }.orEmpty()
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

    private fun handleShareEventClick(id: String) {
        val quiz = container.stateFlow.value.quizList.firstOrNull { it.id == id } ?: return
        val shareText = actionEventsMapper.mapToShareText(quiz)
        actionEventsListener.onActionEvent(
            ActionEvents.ShareEvent(shareText)
        )
    }

    private fun handleCalendarClick() {
        val quizList = quizMap.getOrDefault(
            key = container.stateFlow.value.filtersState.filters?.organization,
            defaultValue = quizMap.values.flatten()
        )
        val calendarEvents = EventPieChartMapper.mapToCalendarEventsUI(quizList)

        bottomSheetController.show(
            BottomSheetModels.CalendarBottomSheetModel(
                toolbar = Toolbar(
                    title = resourceManager.getStringById(R.string.calendar_title),
                    trailIcon = IconModel(
                        UIDrawable.ic_clear,
                        onClick = { dismiss() }
                    )
                ),
                events = calendarEvents
            )
        )
    }

    private fun handleLocationClick() {
        bottomSheetController.show(
            BottomSheetModels.CityBottomSheetModel(
                toolbar = Toolbar(
                    title = resourceManager.getStringById(R.string.city_bottomsheet_title),
                    trailIcon = IconModel(
                        UIDrawable.ic_clear,
                        onClick = { dismiss() }
                    ),
                ),
            )
        )
    }

    //Ну это тоже какой-то пиздец, надо подумать над улучшением фильтров
    private fun handleCalendarDayClick(day: CalendarDay) {
        updateState {
            val quizList = quizMap.getOrDefault(
                key = this.filtersState.filters?.organization,
                defaultValue = quizMap.values.flatten()
            )
            copy(
                quizList = quizList.filter { quiz ->
                    quiz.formattedDate?.date?.date == day.date
                },
                filtersState = filtersState.copy(
                    filterByDay = day,
                    isApplied = true,
                )
            )
        }
        bottomSheetController.dismiss()
    }

    private fun showFilters() {
        bottomSheetController.show(
            BottomSheetModels.FilterBottomSheetModel(
                toolbar = Toolbar(
                    title = resourceManager.getStringById(R.string.filter_title),
                    trailIcon = IconModel(
                        iconRes = UIDrawable.ic_clear,
                        onClick = { dismiss() }
                    )
                )
            )
        )
    }

    private fun showSorting() {
        bottomSheetController.show(
            BottomSheetModels.SortingBottomSheetModel(
                toolbar = Toolbar(
                    title = resourceManager.getStringById(R.string.sorting_title),
                    trailIcon = IconModel(
                        iconRes = UIDrawable.ic_clear,
                        onClick = { dismiss() }
                    )
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

    private fun applyFilters(filters: Filters) {
        //Нужно придумать чет с фильтрами, а то это не дело подпирать их костылями
        updateState {
            copy(
                filtersState = filtersState.copy(
                    filters = filters,
                    isApplied = true,
                )
            )
        }
        completeAction {
            showQuizList()
            bottomSheetController.dismiss()
        }
    }

    private fun showQuizList() = updateState {
        if (uiStatus !is UIStatus.Loaded) return@updateState this

        //Мда, треш
        val quizList = quizMap.getOrDefault(
            key = this.filtersState.filters?.organization,
            defaultValue = quizMap.values.flatten()
        )

        val sortedList = when (this.sort) {
            Sort.ASC_DATE -> quizList.sortedBy { it.formattedDate?.date }
            Sort.DESC_DATE -> quizList.sortedByDescending { it.formattedDate?.date }
        }
        copy(quizList = sortedList)
    }

    private fun resetFilters() {
        updateState {
            copy(
                filtersState = filtersState.copy(
                    filters = null,
                    filterByDay = null,
                    isApplied = false,
                ),
                sort = Sort.ASC_DATE,
            )
        }
    }

    private fun updateQuizCache(quizList: List<Quiz>) {
        //Ну тут надо бы маппинг поправить, чтобы не работать в domain моделькой в presentation слое
        quizList.map { quiz ->
            when (quiz) {
                is QuizPlease -> quizMap.addOrAppend(
                    key = Organization.QUIZ_PLEASE,
                    value = quizPleaseMapper.mapToQuizUI(quiz)
                )

                is SQuiz -> quizMap.addOrAppend(
                    key = Organization.SQUIZ,
                    value = squizMapper.mapToQuizUI(quiz)
                )

                is ShakerQuiz -> quizMap.addOrAppend(
                    key = Organization.SHAKER_QUIZ,
                    value = shakerQuizMapper.mapToQuizUI(quiz)
                )
            }
        }
    }

    private fun getQuizzesFromCache(): List<QuizUI> =
        quizMap.values
            .flatten()
            .sortedBy { it.formattedDate?.date }

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
) : ScreenState

internal data class FeatureToggle(
    val isFavouriteFeatureEnabled: Boolean = false,
    val isFiltersFeatureEnabled: Boolean = true,
    val isSortFeatureEnabled: Boolean = true,
    val isCalendarFeatureEnable: Boolean = true
)