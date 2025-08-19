//package ru.quizHub.quiz.data.datasource
//
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.combine
//import ru.quizHub.quiz.data.models.CityDTO
//import ru.quizHub.quiz.data.models.QuizDTO
//import ru.quizHub.quizApi.api.QuizPleaseApi
//import ru.quizHub.quizApi.api.ShakerQuizApi
//import ru.quizHub.quizApi.api.SquizApi
//import ru.quizHub.quizApi.model.quizplease.QuizPleaseDTO
//import ru.quizHub.quizApi.model.shakerquiz.ShakerQuizItemDTO
//import ru.quizHub.quizApi.model.squiz.SquizDTO
//
//class QuizListRemoteDataSource(
//    private val squizApi: SquizApi,
//    private val shakerApi: ShakerQuizApi,
//    private val quizPleaseApi: QuizPleaseApi,
//) {
//     fun fetchAllQuizList(dto: CityDTO): Flow<List<QuizDTO>> {
//        return combine(
//            squizApi.getQuizzes(dto.squizId),
//            quizPleaseApi.getQuizzes(
//                cityId = dto.quizPleaseId,
//                pageNumber = 1,
//                pageSize = 100
//            ),
//            shakerApi.getQuizzes(dto.shakerQuizId),
//        ) { squizList, quizPleaseList, shakerQuizList ->
//            listOf(
//                quizMapper.squizMapper.map(squizList),
//                quizMapper.quizPleaseMapper.mapToQuiz(quizPleaseList),
//                quizMapper.shakerMapper.mapToShakerQuiz(shakerQuizList)
//            ).flatten()
//        }
//    }
//
//    fun fetchSquizList(dto: CityDTO): Flow<List<SquizDTO>> {
//        return squizApi.getQuizzes(dto.squizId)
//    }
//
//    fun fetchQuizPleaseList(dto: CityDTO): Flow<List<QuizPleaseDTO>> {
//        return quizPleaseApi.getQuizzes(dto.quizPleaseId)
//    }
//
//    fun fetchShakerQuizList(dto: CityDTO): Flow<List<ShakerQuizItemDTO>> {
//        return shakerApi.getQuizzes(dto.shakerQuizId)
//    }
//}