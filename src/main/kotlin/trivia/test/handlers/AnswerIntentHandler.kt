package trivia.test.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import com.amazon.ask.model.Slot
import com.amazon.ask.request.Predicates
import com.fasterxml.jackson.databind.ObjectMapper
import trivia.test.model.Attributes
import trivia.test.model.Constants
import trivia.test.model.State
import trivia.test.model.StateProperty
import trivia.test.util.QuestionUtils
import java.util.*

class AnswerIntentHandler : RequestHandler {
    override fun canHandle(input: HandlerInput): Boolean {
        return input.matches(Predicates.intentName("AnswerIntent").and(Predicates.sessionAttribute(Attributes.STATE_KEY, Attributes.QUIZ_STATE)))
    }

    override fun handle(input: HandlerInput): Optional<Response> {
        val sessionAttributes = input.attributesManager.sessionAttributes
        var responseText: String
        val speechOutput: String
        val quizItem: Map<String, String>? = sessionAttributes[Attributes.QUIZ_ITEM_KEY] as LinkedHashMap<String, String>?
        val state = MAPPER.convertValue(quizItem, State::class.java)
        val stateProperty = StateProperty.valueOf((sessionAttributes[Attributes.QUIZ_PROPERTY_KEY] as String?)!!)
        val counter = sessionAttributes[Attributes.COUNTER_KEY] as Int
        var quizScore = sessionAttributes[Attributes.QUIZ_SCORE_KEY] as Int
        val intentRequest = input.requestEnvelope.request as IntentRequest
        val correct = compareSlots(intentRequest.intent.slots, QuestionUtils.getPropertyOfState(stateProperty, state))
        if (correct) {
            quizScore++
            responseText = getSpeechCon(true)
            sessionAttributes[Attributes.QUIZ_SCORE_KEY] = quizScore
        } else {
            responseText = getSpeechCon(false)
        }
        responseText += getAnswerText(stateProperty, state)
        return if (counter < 10) {
            responseText += "Your current score is $quizScore out of $counter. "
            sessionAttributes[Attributes.RESPONSE_KEY] = responseText
            QuestionUtils.generateQuestion(input)
        } else {
            responseText += "Your final score is $quizScore out of $counter. "
            speechOutput = responseText + " " + Constants.EXIT_SKILL_MESSAGE
            input.responseBuilder
                    .withSpeech(speechOutput)
                    .withShouldEndSession(true)
                    .build()
        }
    }

    private fun getAnswerText(stateProperty: StateProperty, state: State): String {
        // TODO: Instrument to return based on the multiple choice answer from the question API response.
//        return when (stateProperty) {
//            StateProperty.ABBREVIATION -> "The " + stateProperty.value + " of " + state.name + " is <say-as interpret-as='spell-out'>" + QuestionUtils.getPropertyOfState(stateProperty, state) + "</say-as>. "
//            else -> "The " + stateProperty.value + " of " + state.name + " is " + QuestionUtils.getPropertyOfState(stateProperty, state) + ". "
//        }
        return ""
    }

    private fun getSpeechCon(correct: Boolean): String {
        return if (correct) {
            "<say-as interpret-as='interjection'>" + getRandomItem(Constants.CORRECT_RESPONSES) + "! </say-as><break strength='strong'/>"
        } else {
            "<say-as interpret-as='interjection'>" + getRandomItem(Constants.INCORRECT_RESPONSES) + " </say-as><break strength='strong'/>"
        }
    }

    private fun <T> getRandomItem(list: List<T>?): T {
        return list!![RANDOM.nextInt(list.size)]
    }

    private fun compareSlots(slots: Map<String, Slot>, correctAnswer: String?): Boolean {
        for (slot in slots.values) {
            if (slot.value != null && slot.value.toLowerCase() == correctAnswer!!.toLowerCase()) {
                return true
            }
        }
        return false
    }

    companion object {
        private val RANDOM = Random()
        private val MAPPER = ObjectMapper()
    }
}