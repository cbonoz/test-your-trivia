package trivia.test.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import trivia.test.SessionAttributesProvider
import trivia.test.model.Constants
import trivia.test.model.Constants.NUM_QUESTIONS
import trivia.test.model.SessionAttributes
import trivia.test.util.QuestionFactory
import trivia.test.util.inQuiz
import java.util.Optional

class SkipQuestionIntentHandler(
    private val attributesProvider: SessionAttributesProvider
) : RequestHandler {

    private val questionFactory = QuestionFactory(attributesProvider)

    override fun canHandle(input: HandlerInput): Boolean =
        input.matches(Predicates.intentName("SkipQuestionIntent").inQuiz())

    override fun handle(input: HandlerInput): Optional<Response> {
        val sessionAttributes = SessionAttributes(attributesProvider.get(input))
        val speechOutput: String
        val counter = sessionAttributes.counter
        val quizScore = sessionAttributes.score

        return if (counter < NUM_QUESTIONS) {
            val responseText = "OK. I will skip the current question. "
            sessionAttributes.setResponse(responseText)
            questionFactory.generateQuestion(input)
        } else {
            val responseText = "That was the last question. Your final score is $quizScore out of $counter. "
            speechOutput = "$responseText ${Constants.EXIT_SKILL_MESSAGE}"
            input.responseBuilder
                    .withSpeech(speechOutput)
                    .withShouldEndSession(true)
                    .build()
        }
    }
}
