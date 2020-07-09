package trivia.test.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import com.amazon.ask.model.ui.PlainTextOutputSpeech
import com.amazon.ask.request.Predicates
import trivia.test.QuizService
import trivia.test.model.Attributes
import trivia.test.model.Category
import trivia.test.model.Difficulty
import trivia.test.util.notInQuiz
import java.util.Optional

private const val QUIZ_INTENT = "QuizIntent"
private const val START_OVER_INTENT = "AMAZON.StartOverIntent"

class QuizAndStartOverIntentHandler(
    private val quizService: QuizService
) : RequestHandler {

    override fun canHandle(input: HandlerInput): Boolean =
        input.matches(Predicates.intentName(QUIZ_INTENT).notInQuiz())
                || input.matches(Predicates.intentName(START_OVER_INTENT))

    override fun handle(input: HandlerInput): Optional<Response> {
        val sessionAttributes = input.attributesManager.sessionAttributes
        val intentRequest = input.requestEnvelope.request as IntentRequest
        sessionAttributes[Attributes.STATE_KEY] = Attributes.QUIZ_STATE

        val difficulty = intentRequest.intent.slots["difficulty"]?.also {
            sessionAttributes[Attributes.QUIZ_DIFFICULTY_KEY] = it
        }

        val category = intentRequest.intent.slots["category"]?.also {
            sessionAttributes[Attributes.QUIZ_CATEGORY_KEY] = it
        }

        sessionAttributes[Attributes.RESPONSE_KEY] = ""
        sessionAttributes[Attributes.COUNTER_KEY] = 0
        sessionAttributes[Attributes.QUIZ_SCORE_KEY] = 0

        val apiDifficulty = when (difficulty?.value) {
            "easy" -> Difficulty.EASY
            "medium" -> Difficulty.MEDIUM
            "hard" -> Difficulty.HARD
            else -> Difficulty.EASY
        }
        val apiCategory = category?.let { Category.fromText(it.value) } ?: Category.GENERAL_KNOWLEDGE

        val question = quizService.getQuiz(
            difficulty = apiDifficulty,
            category = apiCategory
        ).results.firstOrNull()?.question ?: "No questions found"

        return Optional.of(
            Response.builder()
                .withOutputSpeech(
                    PlainTextOutputSpeech.builder()
                        .withText(question)
                        .build()
                )
                .build()
        )
    }
}
