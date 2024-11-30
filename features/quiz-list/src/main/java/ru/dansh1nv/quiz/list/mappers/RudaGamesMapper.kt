package ru.dansh1nv.quiz.list.mappers

import ru.dansh1nv.core.resourceManager.IResourceManager
import ru.dansh1nv.quiz.list.models.item.QuizUI
import ru.dansh1nv.quiz_list_domain.models.Quiz

class RudaGamesMapper(
    private val resourceManager: IResourceManager,
    private val commonMapper: CommonMapper,
) {

    fun mapToQuizUI(entities: List<Quiz>): List<QuizUI> {
        return entities.map(::mapToQuizUI)
    }

    fun mapToQuizUI(entity: Quiz): QuizUI {
        return QuizUI(
            //TODO:Сделать маппинг
        )
    }
}