package trivia.test.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import trivia.test.SessionAttributesProvider
import trivia.test.model.SessionAttributes
import trivia.test.util.QuestionFactory
import trivia.test.util.inQuiz
import java.util.Optional

class QuestionRepeatIntentHandler(
    private val attributesProvider: SessionAttributesProvider
) : RequestHandler {

    private val questionFactory = QuestionFactory(attributesProvider)

    override fun canHandle(input: HandlerInput): Boolean =
        input.matches(Predicates.intentName("AMAZON.RepeatIntent").inQuiz())

    override fun handle(input: HandlerInput): Optional<Response> {
        val sessionAttributes = SessionAttributes(attributesProvider.get(input))
        val counter = sessionAttributes.counter
        val item = sessionAttributes.quizItems[counter - 1]
        val question = questionFactory.getQuestionText(counter, item)
        return input.responseBuilder
                .withSpeech(question)
                .withReprompt(question)
                .withShouldEndSession(false)
                .build()
    }
}
