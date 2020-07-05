package trivia.test.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import trivia.test.model.Attributes
import trivia.test.model.State
import trivia.test.model.StateProperty
import trivia.test.util.QuestionUtils
import java.util.*

class RepeatIntentHandler : RequestHandler {
    override fun canHandle(input: HandlerInput): Boolean {
        return input.matches(Predicates.intentName("AMAZON.RepeatIntent").and(Predicates.sessionAttribute(Attributes.STATE_KEY, Attributes.QUIZ_STATE)))
    }

    override fun handle(input: HandlerInput): Optional<Response> {
        val sessionAttributes = input.attributesManager.sessionAttributes
        val counter = sessionAttributes[Attributes.COUNTER_KEY] as Int
        val stateProperty = sessionAttributes[Attributes.QUIZ_PROPERTY_KEY] as StateProperty?
        val state = sessionAttributes[Attributes.QUIZ_ITEM_KEY] as State?
        val question = QuestionUtils.getQuestionText(counter, stateProperty!!, state)
        return input.responseBuilder
                .withSpeech(question)
                .withReprompt(question)
                .withShouldEndSession(false)
                .build()
    }
}