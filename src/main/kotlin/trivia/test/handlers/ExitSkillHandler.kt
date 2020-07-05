package trivia.test.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import trivia.test.model.Constants
import java.util.*

class ExitSkillHandler : RequestHandler {
    override fun canHandle(input: HandlerInput): Boolean {
        return input.matches(Predicates.intentName("AMAZON.StopIntent")
                .or(Predicates.intentName("AMAZON.PauseIntent")
                        .or(Predicates.intentName("AMAZON.CancelIntent"))))
    }

    override fun handle(input: HandlerInput): Optional<Response> {
        return input.responseBuilder.withSpeech(Constants.EXIT_SKILL_MESSAGE).build()
    }
}