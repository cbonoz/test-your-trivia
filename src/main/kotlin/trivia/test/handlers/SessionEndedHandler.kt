package trivia.test.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.Response
import com.amazon.ask.model.SessionEndedRequest
import com.amazon.ask.request.Predicates
import org.slf4j.LoggerFactory
import java.util.*

class SessionEndedHandler : RequestHandler {
    override fun canHandle(input: HandlerInput): Boolean {
        return input.matches(Predicates.requestType(SessionEndedRequest::class.java))
    }

    override fun handle(input: HandlerInput): Optional<Response> {
        val sessionEndedRequest = input.requestEnvelope.request as SessionEndedRequest
        LOG.debug("Session ended with reason: " + sessionEndedRequest.reason.toString())
        return Optional.empty()
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(SessionEndedHandler::class.java)
    }
}