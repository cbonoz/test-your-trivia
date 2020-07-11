package trivia.test.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.LaunchRequest
import com.amazon.ask.model.Response
import trivia.test.model.Constants
import trivia.test.model.QuizState
import trivia.test.model.SessionAttributes
import trivia.test.util.requestOfType
import java.util.Optional

object LaunchRequestHandler : RequestHandler {
    override fun canHandle(input: HandlerInput): Boolean =
        input.matches(requestOfType<LaunchRequest>())

    override fun handle(input: HandlerInput): Optional<Response> {
        val sessionAttributes = SessionAttributes(input.attributesManager.sessionAttributes)
        sessionAttributes.setState(QuizState.START)

        return input.responseBuilder
                .withSpeech(Constants.WELCOME_MESSAGE)
                .withReprompt(Constants.HELP_MESSAGE)
                .withShouldEndSession(false)
                .build()
    }
}
