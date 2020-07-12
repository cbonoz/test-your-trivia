package trivia.test.model

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import trivia.test.model.AttributeKeys.CATEGORY
import trivia.test.model.AttributeKeys.COUNTER
import trivia.test.model.AttributeKeys.DIFFICULTY
import trivia.test.model.AttributeKeys.QUIZ_ITEMS
import trivia.test.model.AttributeKeys.QUIZ_SCORE
import trivia.test.model.AttributeKeys.QUIZ_STATE
import trivia.test.model.AttributeKeys.RESPONSE
import trivia.test.model.AttributeKeys.START_STATE
import trivia.test.model.AttributeKeys.STATE

/**
 * Wraps session attributes and provides typesafe access to properties.
 */
class SessionAttributes(
    private val attributesMap: MutableMap<String, Any>
) {
    private val mapper = jacksonObjectMapper()

    val state: QuizState
        get() = when (attributesMap[STATE] as String) {
            START_STATE -> QuizState.START
            else -> QuizState.IN_QUIZ
        }

    val quizItems: List<Question>
        get() = mapper.readValue(attributesMap[QUIZ_ITEMS] as String)

    val score: Int
        get() = attributesMap[QUIZ_SCORE] as Int? ?: 0

    val counter: Int
        get() = attributesMap[COUNTER] as Int? ?: 0

    val response: String
        get() = attributesMap[RESPONSE] as String? ?: ""

    val category: Category
        get() = Category.fromText(attributesMap[CATEGORY] as String)!!

    val difficulty: Difficulty
        get() = Difficulty.valueOf(attributesMap[DIFFICULTY] as String)

    fun setState(state: QuizState) {
        attributesMap[STATE] = state.attrName
    }

    fun setQuizItems(items: List<Question>) {
        attributesMap[QUIZ_ITEMS] = mapper.writeValueAsString(items)
    }

    fun incrementScore() {
        attributesMap[QUIZ_SCORE] = score + 1
    }

    fun incrementCounter() {
        attributesMap[COUNTER] = counter + 1
    }

    fun setResponse(response: String) {
        attributesMap[RESPONSE] = response
    }

    fun setCategory(category: String) {
        attributesMap[CATEGORY] = category
    }

    fun setDifficulty(difficulty: String) {
        attributesMap[DIFFICULTY] = difficulty
    }

    fun reset() {
        setState(QuizState.START)
        attributesMap[QUIZ_SCORE] = 0
        attributesMap[COUNTER] = 0
    }
}

enum class QuizState(val attrName: String) {
    START(START_STATE),
    IN_QUIZ(QUIZ_STATE),
}

object AttributeKeys {
    const val STATE = "state"
    const val QUIZ_ITEMS = "quizitems"
    const val QUIZ_SCORE = "quizscore"
    const val COUNTER = "counter"
    const val RESPONSE = "response"
    const val CATEGORY = "category"
    const val DIFFICULTY = "difficulty"
    const val START_STATE = "_START"
    const val QUIZ_STATE = "_QUIZ"
}
