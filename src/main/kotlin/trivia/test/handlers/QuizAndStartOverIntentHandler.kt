package trivia.test.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import trivia.test.model.Attributes
import trivia.test.util.QuestionUtils
import java.util.*

class QuizAndStartOverIntentHandler : RequestHandler {
    override fun canHandle(input: HandlerInput): Boolean {
        return (input.matches(Predicates.intentName("QuizIntent").and(Predicates.sessionAttribute(Attributes.STATE_KEY, Attributes.QUIZ_STATE).negate()))
                || input.matches(Predicates.intentName("AMAZON.StartOverIntent")))
    }

    override fun handle(input: HandlerInput): Optional<Response> {
        val sessionAttributes = input.attributesManager.sessionAttributes
        sessionAttributes[Attributes.STATE_KEY] = Attributes.QUIZ_STATE
        sessionAttributes[Attributes.RESPONSE_KEY] = ""
        sessionAttributes[Attributes.COUNTER_KEY] = 0
        sessionAttributes[Attributes.QUIZ_SCORE_KEY] = 0
        return QuestionUtils.generateQuestion(input)
    }
}