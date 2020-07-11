package trivia.test.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import com.amazon.ask.model.Slot
import com.amazon.ask.request.Predicates
import trivia.test.SessionAttributesProvider
import trivia.test.model.Constants
import trivia.test.model.Constants.NUM_QUESTIONS
import trivia.test.model.Question
import trivia.test.model.SessionAttributes
import trivia.test.util.QuestionFactory
import trivia.test.util.inQuiz
import java.util.Optional

class AnswerIntentHandler(
        private val attributesProvider: SessionAttributesProvider
) : RequestHandler {

    private val questionFactory = QuestionFactory(attributesProvider)

    override fun canHandle(input: HandlerInput): Boolean =
            input.matches(Predicates.intentName("AnswerIntent").inQuiz())

    override fun handle(input: HandlerInput): Optional<Response> {
        val sessionAttributes = SessionAttributes(attributesProvider.get(input))
        var responseText: String
        val speechOutput: String
        val counter = sessionAttributes.counter
        val quizItem = sessionAttributes.quizItems[counter - 1]
        val intentRequest = input.requestEnvelope.request as IntentRequest
        val result = compareSlots(
                intentRequest.intent.slots,
                quizItem.allAnswers.map { it.toLowerCase() },
                quizItem.correct_answer)
        when (result) {
            AnswerResult.CORRECT -> {
                responseText = getSpeechCon(correct = true)
                sessionAttributes.incrementScore()
            }
            AnswerResult.INCORRECT -> {
                responseText = getSpeechCon(correct = false)
            }
            AnswerResult.REPROMPT -> {
                speechOutput = "That was not a valid answer. ${questionFactory.getQuestionText(counter, quizItem)}"
                return input.responseBuilder
                        .withSpeech(speechOutput)
                        .withShouldEndSession(false)
                        .build()
            }
        }
        responseText += getAnswerText(quizItem)

        val quizScore = sessionAttributes.score

        return if (counter < NUM_QUESTIONS) {
            responseText += "Your current score is $quizScore out of $counter. "
            sessionAttributes.setResponse(responseText)
            questionFactory.generateQuestion(input)
        } else {
            responseText += "Your final score is $quizScore out of $counter. "
            speechOutput = "$responseText ${Constants.EXIT_SKILL_MESSAGE}"
            input.responseBuilder
                    .withSpeech(speechOutput)
                    .withShouldEndSession(true)
                    .build()
        }
    }

    private fun getAnswerText(question: Question): String = "The correct answer is ${question.correct_answer}. "

    private fun getSpeechCon(correct: Boolean): String {
        return if (correct) {
            "<say-as interpret-as='interjection'>" + Constants.CORRECT_RESPONSES.random() + "! </say-as><break strength='strong'/>"
        } else {
            "<say-as interpret-as='interjection'>" + Constants.INCORRECT_RESPONSES.random() + " </say-as><break strength='strong'/>"
        }
    }

    private fun compareSlots(slots: Map<String, Slot>, allAnswers: List<String>, correctAnswer: String): AnswerResult {
        val slotValues = slots.values.mapNotNull { it.value.toLowerCase() }
        val match = slotValues.firstOrNull { it in allAnswers } ?: return AnswerResult.REPROMPT
        return if (match == correctAnswer) {
            AnswerResult.CORRECT
        } else {
            AnswerResult.INCORRECT
        }
    }
}

private enum class AnswerResult {
    CORRECT,
    INCORRECT,
    REPROMPT
}
