package trivia.test.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.LaunchRequest
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import trivia.test.model.Attributes
import trivia.test.model.Constants
import java.util.*

class LaunchRequestHandler : RequestHandler {
    override fun canHandle(input: HandlerInput): Boolean {
        return input.matches(Predicates.requestType(LaunchRequest::class.java))
    }

    override fun handle(input: HandlerInput): Optional<Response> {
        val sessionAttributes = input.attributesManager.sessionAttributes
        sessionAttributes[Attributes.STATE_KEY] = Attributes.START_STATE
        return input.responseBuilder
                .withSpeech(Constants.WELCOME_MESSAGE)
                .withReprompt(Constants.HELP_MESSAGE)
                .withShouldEndSession(false)
                .build()
    }
}