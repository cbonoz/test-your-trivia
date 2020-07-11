package trivia.test.util

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.Request
import com.amazon.ask.request.Predicates
import trivia.test.model.AttributeKeys
import java.util.function.Predicate

fun Predicate<HandlerInput>.inQuiz(): Predicate<HandlerInput> =
    and(Predicates.sessionAttribute(AttributeKeys.STATE, AttributeKeys.QUIZ_STATE))

fun Predicate<HandlerInput>.notInQuiz(): Predicate<HandlerInput> =
    and(Predicates.sessionAttribute(AttributeKeys.STATE, AttributeKeys.QUIZ_STATE).negate())

inline fun <reified T : Request> requestOfType(): Predicate<HandlerInput> =
    Predicates.requestType(T::class.java)
