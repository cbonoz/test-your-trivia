package trivia.test.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import com.amazon.ask.model.ui.Image
import com.amazon.ask.request.Predicates
import trivia.test.model.Attributes
import trivia.test.model.Constants
import trivia.test.model.State
import trivia.test.util.QuestionUtils
import trivia.test.util.notInQuiz
import java.util.Optional

private const val INTENT_TYPE = "AnswerIntent"

class NoAnswerIntentHandler : RequestHandler {
    override fun canHandle(input: HandlerInput): Boolean =
        input.matches(Predicates.intentName(INTENT_TYPE).notInQuiz())

    override fun handle(input: HandlerInput): Optional<Response> {
        val sessionAttributes = input.attributesManager.sessionAttributes
        sessionAttributes[Attributes.STATE_KEY] = Attributes.START_STATE
        val intentRequest = input.requestEnvelope.request as IntentRequest
        val state = QuestionUtils.getState(intentRequest.intent.slots)
        return if (state!!.isPresent) {
            if (Constants.USE_CARDS_FLAG) {
                val image = Image.builder()
                        .withSmallImageUrl(getSmallCardImage())
                        .withLargeImageUrl(getLargeCardImage())
                        .build()
                input.responseBuilder
                        .withSpeech(getSpeechDescription(state.get()))
                        .withReprompt(Constants.REPROMPT_MESSAGE)
                        .withStandardCard(state.get().name, getTextDescription(state.get()), image)
                        .withShouldEndSession(false)
                        .build()
            } else {
                input.responseBuilder
                        .withSpeech(getSpeechDescription(state.get()))
                        .withReprompt(Constants.REPROMPT_MESSAGE)
                        .withShouldEndSession(false)
                        .build()
            }
        } else {
            val unknownAnswerText = "I'm sorry. That is not something I know very much about in this skill. " + Constants.HELP_MESSAGE
            input.responseBuilder
                    .withSpeech(unknownAnswerText)
                    .withReprompt(unknownAnswerText)
                    .withShouldEndSession(false)
                    .build()
        }
    }

    private fun getTextDescription(state: State): String {
        return ("Abbreviation: " + state.abbreviation + "\n"
                + "Capital: " + state.capital + "\n"
                + "Statehood Year: " + state.statehoodYear + "\n"
                + "Statehood Order: " + state.statehoodOrder)
    }

    private fun getSpeechDescription(state: State): String {
        return (state.name + " is the " + state.statehoodOrder + "th state, admitted to the Union in "
                + state.statehoodYear + ".  The capital of " + state.statehoodOrder + " is " + state.capital
                + ", and the abbreviation for " + state.name + " is <break strength='strong'/><say-as interpret-as='spell-out'>"
                + state.abbreviation + "</say-as>.  I've added " + state.name + " to your Alexa app.  Which other state or capital would you like to know about?")
    }

    private fun getSmallCardImage(): String {
        return "https://i.redd.it/3k7uvh8rz5k41.jpg"
    }

    private fun getLargeCardImage(): String {
        return "https://i.redd.it/3k7uvh8rz5k41.jpg"
    }
}
