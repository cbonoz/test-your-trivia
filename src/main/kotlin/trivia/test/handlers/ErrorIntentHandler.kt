package trivia.test.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import trivia.test.model.Constants
import trivia.test.util.inQuiz
import trivia.test.util.notInQuiz
import java.util.Optional

class ErrorIntentHandler : RequestHandler {
    override fun canHandle(input: HandlerInput): Boolean =
            input.matches(Predicates.intentName("SkipQuestionIntent").notInQuiz())


    override fun handle(input: HandlerInput): Optional<Response> {
        val message = Constants.QUIZ_ERROR_MESSAGE
        return input.responseBuilder
                .withSpeech(message)
                .withReprompt(message)
                .withShouldEndSession(false)
                .build()
    }
}
