//package ru.dansh1nv.quiz.data.datasource
//
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.combine
//import ru.dansh1nv.quiz.data.models.CityDTO
//import ru.dansh1nv.quiz.data.models.QuizDTO
//import ru.dansh1nv.quizapi.api.QuizPleaseApi
//import ru.dansh1nv.quizapi.api.ShakerQuizApi
//import ru.dansh1nv.quizapi.api.SquizApi
//import ru.dansh1nv.quizapi.model.quizplease.QuizPleaseDTO
//import ru.dansh1nv.quizapi.model.shakerquiz.ShakerQuizItemDTO
//import ru.dansh1nv.quizapi.model.squiz.SquizDTO
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