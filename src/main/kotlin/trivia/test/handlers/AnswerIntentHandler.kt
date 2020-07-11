package trivia.test.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import com.amazon.ask.model.Slot
import com.amazon.ask.request.Predicates
import trivia.test.model.Constants
import trivia.test.model.Question
import trivia.test.model.SessionAttributes
import trivia.test.util.QuestionUtils
import trivia.test.util.inQuiz
import java.util.Optional

class AnswerIntentHandler : RequestHandler {
    override fun canHandle(input: HandlerInput): Boolean {
        return input.matches(Predicates.intentName("AnswerIntent").inQuiz())
    }

    override fun handle(input: HandlerInput): Optional<Response> {
        val sessionAttributes = SessionAttributes(input.attributesManager.sessionAttributes)
        var responseText: String
        val speechOutput: String
        val counter = sessionAttributes.counter
        val quizItem = sessionAttributes.quizItems[counter - 1]
        val intentRequest = input.requestEnvelope.request as IntentRequest
        val correct = compareSlots(intentRequest.intent.slots, quizItem.correct_answer)
        if (correct) {
            responseText = getSpeechCon(correct = true)
            sessionAttributes.incrementScore()
        } else {
            responseText = getSpeechCon(correct = false)
        }
        responseText += getAnswerText(quizItem)

        val quizScore = sessionAttributes.score

        return if (counter < 9) {
            responseText += "Your current score is $quizScore out of $counter. "
            sessionAttributes.setResponse(responseText)
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

    private fun getAnswerText(question: Question): String =
        "The correct answer is ${question.correct_answer}"

    private fun getSpeechCon(correct: Boolean): String {
        return if (correct) {
            "<say-as interpret-as='interjection'>" + Constants.CORRECT_RESPONSES.random() + "! </say-as><break strength='strong'/>"
        } else {
            "<say-as interpret-as='interjection'>" + Constants.INCORRECT_RESPONSES.random() + " </say-as><break strength='strong'/>"
        }
    }

    private fun compareSlots(slots: Map<String, Slot>, correctAnswer: String): Boolean =
        slots.values.mapNotNull { it.value }
            .any { it.toLowerCase() == correctAnswer.toLowerCase() }
}
