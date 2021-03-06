package trivia.test.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.LaunchRequest
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import trivia.test.model.Constants
import trivia.test.model.SessionAttributes
import trivia.test.util.ResponseUtils
import trivia.test.util.notInQuiz
import trivia.test.util.requestOfType
import java.util.*

private const val START_OVER_INTENT = "AMAZON.StartOverIntent"

class LaunchRequestHandler : RequestHandler {
    override fun canHandle(input: HandlerInput): Boolean =
            input.matches(requestOfType<LaunchRequest>()) ||
                    input.matches(Predicates.intentName(START_OVER_INTENT)) ||
                    input.matches(Predicates.intentName("AMAZON.RepeatIntent").notInQuiz())


    override fun handle(input: HandlerInput): Optional<Response> {
        val sessionAttributes = SessionAttributes(input.attributesManager.sessionAttributes)
        sessionAttributes.reset()

        val exampleQuiz = ResponseUtils.getExampleQuizMessage()
        val welcomeMessage = "${Constants.WELCOME_MESSAGE} $exampleQuiz"

        return input.responseBuilder
                .withSpeech(welcomeMessage)
                .withReprompt(Constants.HELP_MESSAGE)
                .withShouldEndSession(false)
                .build()
    }
}
