package trivia.test.util

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.Request
import com.amazon.ask.request.Predicates
import trivia.test.model.Attributes
import java.util.function.Predicate

fun Predicate<HandlerInput>.inQuiz(): Predicate<HandlerInput> =
    and(Predicates.sessionAttribute(Attributes.STATE_KEY, Attributes.QUIZ_STATE))

fun Predicate<HandlerInput>.notInQuiz(): Predicate<HandlerInput> =
    and(Predicates.sessionAttribute(Attributes.STATE_KEY, Attributes.QUIZ_STATE).negate())

inline fun <reified T : Request> requestOfType(): Predicate<HandlerInput> =
    Predicates.requestType(T::class.java)
