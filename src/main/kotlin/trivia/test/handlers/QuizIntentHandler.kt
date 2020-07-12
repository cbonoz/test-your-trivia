package trivia.test.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import trivia.test.SessionAttributesProvider
import trivia.test.QuizService
import trivia.test.model.Category
import trivia.test.model.Constants.NUM_QUESTIONS
import trivia.test.model.Difficulty
import trivia.test.model.QuizState
import trivia.test.model.SessionAttributes
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
        val sessionAttributes = SessionAttributes(attributesProvider.get(input))
        val intentRequest = input.requestEnvelope.request as IntentRequest

        sessionAttributes.setState(QuizState.IN_QUIZ)

        val intentDifficulty = intentRequest.intent.slots["difficulty"]?.value ?: Difficulty.EASY.name
        val intentCategory = intentRequest.intent.slots["category"]?.value ?: Category.GENERAL_KNOWLEDGE.text

        sessionAttributes.setDifficulty(intentDifficulty)
        sessionAttributes.setCategory(intentCategory)

        val apiDifficulty = when (intentDifficulty) {
            "easy" -> Difficulty.EASY
            "medium" -> Difficulty.MEDIUM
            "hard" -> Difficulty.HARD
            else -> Difficulty.EASY
        }
        val apiCategory = Category.fromText(intentCategory)

        val questionsResponse = quizService.getQuiz(
            difficulty = apiDifficulty,
            category = apiCategory,
            amount = NUM_QUESTIONS
        )

        sessionAttributes.setQuizItems(questionsResponse.results)

        return questionFactory.generateQuestion(input)
    }
}
