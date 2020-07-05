package trivia.test.util

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.Response
import com.amazon.ask.model.Slot
import trivia.test.model.Attributes
import trivia.test.model.Constants
import trivia.test.model.State
import trivia.test.model.StateProperty
import java.util.Optional
import java.util.Random

object QuestionUtils {
    private val RANDOM = Random()
    fun generateQuestion(input: HandlerInput): Optional<Response> {
        val sessionAttributes = input.attributesManager.sessionAttributes
        var counter = sessionAttributes[Attributes.COUNTER_KEY] as Int
        if (counter == 0) {
            val category = sessionAttributes.getOrDefault(Attributes.QUIZ_CATEGORY_KEY, "").toString()
            sessionAttributes[Attributes.RESPONSE_KEY] = ResponseUtils.getStartQuizMessage(category)
        }
        counter++
        val state = randomState
        val stateProperty = randomProperty
        sessionAttributes[Attributes.QUIZ_ITEM_KEY] = state
        sessionAttributes[Attributes.QUIZ_PROPERTY_KEY] = stateProperty
        sessionAttributes[Attributes.COUNTER_KEY] = counter
        val question = getQuestionText(counter, stateProperty, state)
        val speech = sessionAttributes[Attributes.RESPONSE_KEY].toString() + question
        return input.responseBuilder
                .withSpeech(speech)
                .withReprompt(question)
                .withShouldEndSession(false)
                .build()
    }

    fun getQuestionText(counter: Int, stateProperty: StateProperty, state: State?): String {
        return "Here is your " + counter + "th question.  What is the " + stateProperty.value + " of " + stateProperty.name + "?"
    }

    fun getState(slots: Map<String?, Slot>): Optional<State> {
        for (slot in slots.values) {
            val value = slot.value
            for (stateProperty in StateProperty.values()) {
                val state = Constants.STATES.stream()
                        .filter { s: State? -> getPropertyOfState(stateProperty, s!!) == value }
                        .findAny()
                if (state.isPresent) {
                    return state
                }
            }
        }
        return Optional.empty()
    }

    fun getPropertyOfState(stateProperty: StateProperty, state: State): String? {
        return when (stateProperty) {
            StateProperty.NAME -> state.name
            StateProperty.ABBREVIATION -> state.abbreviation
            StateProperty.CAPITAL -> state.capital
            StateProperty.STATEHOOD_YEAR -> state.statehoodYear
            StateProperty.STATEHOOD_ORDER -> state.statehoodOrder
        }
    }

    private val randomState: State?
        private get() = Constants.STATES[RANDOM.nextInt(Constants.STATES.size)]

    private val randomProperty: StateProperty
        private get() = StateProperty.values()[RANDOM.nextInt(StateProperty.values().size - 1) + 1]
}
