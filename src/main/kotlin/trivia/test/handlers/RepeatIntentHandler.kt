package trivia.test.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import trivia.test.model.SessionAttributes
import trivia.test.util.QuestionUtils
import trivia.test.util.inQuiz
import java.util.Optional

class RepeatIntentHandler : RequestHandler {
    override fun canHandle(input: HandlerInput): Boolean {
        return input.matches(Predicates.intentName("AMAZON.RepeatIntent").inQuiz())
    }

    override fun handle(input: HandlerInput): Optional<Response> {
        val sessionAttributes = SessionAttributes(input.attributesManager.sessionAttributes)
        val counter = sessionAttributes.counter
        val item = sessionAttributes.quizItems[counter - 1]
        val question = QuestionUtils.getQuestionText(counter, item)
        return input.responseBuilder
                .withSpeech(question)
                .withReprompt(question)
                .withShouldEndSession(false)
                .build()
    }
}
