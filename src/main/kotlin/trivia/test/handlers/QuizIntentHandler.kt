package trivia.test.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import trivia.test.SessionAttributesProvider
import trivia.test.QuizService
import trivia.test.model.*
import trivia.test.model.Constants.NUM_QUESTIONS
import trivia.test.model.Constants.QUIZ_ERROR_MESSAGE
import trivia.test.util.QuestionFactory
import trivia.test.util.notInQuiz
import java.util.Optional

private const val QUIZ_INTENT = "QuizIntent"

class QuizIntentHandler(
    private val attributesProvider: SessionAttributesProvider,
    private val quizService: QuizService
) : RequestHandler {

    private val questionFactory = QuestionFactory(attributesProvider)

    override fun canHandle(input: HandlerInput): Boolean = input.matches(Predicates.intentName(QUIZ_INTENT).notInQuiz())

    override fun handle(input: HandlerInput): Optional<Response> {
        val intentRequest = input.requestEnvelope.request as IntentRequest

        val intentDifficulty = intentRequest.intent.slots["difficulty"]?.value ?: Difficulty.EASY.name
        val intentCategory = intentRequest.intent.slots["category"]?.value ?: Category.GENERAL_KNOWLEDGE.text


        val apiDifficulty = when (intentDifficulty) {
            "easy" -> Difficulty.EASY
            "medium" -> Difficulty.MEDIUM
            "hard" -> Difficulty.HARD
            else -> Difficulty.EASY
        }
        val apiCategory = Category.fromText(intentCategory)
        if (apiCategory == null) {
            val speech = QUIZ_ERROR_MESSAGE
            return input.responseBuilder
                    .withSpeech(speech)
                    .withReprompt(speech)
                    .withShouldEndSession(false)
                    .build()
        }

        val sessionAttributes = SessionAttributes(attributesProvider.get(input))
        sessionAttributes.setDifficulty(intentDifficulty)
        sessionAttributes.setCategory(intentCategory)
        sessionAttributes.setState(QuizState.IN_QUIZ)

        val questionsResponse = quizService.getQuiz(
            difficulty = apiDifficulty,
            category = apiCategory,
            amount = NUM_QUESTIONS
        )

        sessionAttributes.setQuizItems(questionsResponse.results)

        return questionFactory.generateQuestion(input)
    }
}
