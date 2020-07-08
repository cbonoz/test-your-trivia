package trivia.test.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import com.amazon.ask.model.ui.PlainTextOutputSpeech
import com.amazon.ask.request.Predicates
import mu.KotlinLogging
import trivia.test.QuizService
import trivia.test.model.Attributes
import trivia.test.model.Category
import trivia.test.model.Difficulty
import java.util.Optional
import javax.inject.Inject

class QuizAndStartOverIntentHandler : RequestHandler {

    @Inject
    lateinit var quizService: QuizService

    override fun canHandle(input: HandlerInput): Boolean {
        return (input.matches(Predicates.intentName("QuizIntent").and(Predicates.sessionAttribute(Attributes.STATE_KEY, Attributes.QUIZ_STATE).negate()))
                || input.matches(Predicates.intentName("AMAZON.StartOverIntent")))
    }

    override fun handle(input: HandlerInput): Optional<Response> {
        val sessionAttributes = input.attributesManager.sessionAttributes
        val intentRequest = input.requestEnvelope.request as IntentRequest
        sessionAttributes[Attributes.STATE_KEY] = Attributes.QUIZ_STATE

        val difficulty = intentRequest.intent.slots.getOrDefault("difficulty", null)
        if (difficulty != null) {
            sessionAttributes[Attributes.QUIZ_DIFFICULTY_KEY] = difficulty
        }

        val category = intentRequest.intent.slots.getOrDefault("category", null)
        if (category != null) {
            sessionAttributes[Attributes.QUIZ_CATEGORY_KEY] = category
        }

        sessionAttributes[Attributes.RESPONSE_KEY] = ""
        sessionAttributes[Attributes.COUNTER_KEY] = 0
        sessionAttributes[Attributes.QUIZ_SCORE_KEY] = 0

        val question = quizService.getQuiz(
            difficulty = when (difficulty?.value) {
                "easy" -> Difficulty.EASY
                "medium" -> Difficulty.MEDIUM
                "hard" -> Difficulty.HARD
                else -> Difficulty.EASY
            },
            category = Category.SPORTS
        ).results.first().question

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
