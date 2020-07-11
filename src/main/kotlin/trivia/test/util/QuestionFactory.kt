package trivia.test.util

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.Response
import trivia.test.SessionAttributesProvider
import trivia.test.model.Question
import trivia.test.model.SessionAttributes
import java.util.Optional

class QuestionFactory(
    private val attributesProvider: SessionAttributesProvider
) {
    fun generateQuestion(input: HandlerInput): Optional<Response> {
        val sessionAttributes = SessionAttributes(attributesProvider.get(input))
        val count = sessionAttributes.counter
        if (count == 0) {
            val category = sessionAttributes.category
            sessionAttributes.setResponse(ResponseUtils.getStartQuizMessage(category))
        }
        val items = sessionAttributes.quizItems

        val question = getQuestionText(count + 1, items[count])
        val speech = sessionAttributes.response + question

        sessionAttributes.incrementCounter() // TODO: probably not the place for this

        return input.responseBuilder
                .withSpeech(speech)
                .withReprompt(question)
                .withShouldEndSession(false)
                .build()
    }

    fun getQuestionText(counter: Int, item: Question): String =
        "Here is question number " + counter + ". ${item.question}"
}
