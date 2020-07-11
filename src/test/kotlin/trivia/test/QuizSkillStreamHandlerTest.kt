package trivia.test

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.Intent
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.LaunchRequest
import com.amazon.ask.model.RequestEnvelope
import com.amazon.ask.model.Response
import com.google.common.truth.Truth.assertThat
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Test
import trivia.test.handlers.LaunchRequestHandler
import trivia.test.handlers.QuizAndStartOverIntentHandler
import trivia.test.model.Category
import trivia.test.model.QuizState
import trivia.test.model.SessionAttributes
import java.util.Optional
import javax.inject.Inject

@QuarkusTest
class QuizSkillStreamHandlerTest {

    private val attributesMap = mutableMapOf<String, Any>()
    private val sessionAttributes = SessionAttributes(attributesMap)

    @Inject
    lateinit var quizService: QuizService

    private val quizAndStartOverIntentHandler by lazy {
        QuizAndStartOverIntentHandler(
            attributesProvider = object : SessionAttributesProvider {
                override fun get(input: HandlerInput): MutableMap<String, Any> = attributesMap
            },
            quizService = quizService
        )
    }

    @Test
    fun `launch can handle LaunchRequests`() {
        assertThat(
            LaunchRequestHandler().canHandle(
                HandlerInput.builder()
                    .withRequestEnvelope(
                        RequestEnvelope.builder()
                            .withRequest(LaunchRequest.builder().build())
                            .build()
                    )
                    .build()
            )
        ).isTrue()
    }

    @Test
    fun `quiz and start over handles QuizIntent request`() {
        assertThat(
            quizAndStartOverIntentHandler.handle(
                HandlerInput.builder()
                    .withRequestEnvelope(
                        RequestEnvelope.builder()
                            .withRequest(
                                IntentRequest.builder()
                                    .withIntent(
                                        Intent.builder()
                                            .withName("QuizIntent")
                                            .build()
                                    )
                                    .build()
                            )
                            .build()
                    )
                    .build()
            )
        ).isNotEqualTo(Optional.empty<Response>())

        assertThat(sessionAttributes.state).isEqualTo(QuizState.IN_QUIZ)
        assertThat(sessionAttributes.category).isEqualTo(Category.GENERAL_KNOWLEDGE)
        assertThat(sessionAttributes.quizItems).hasSize(10)
        assertThat(sessionAttributes.counter).isEqualTo(1)
    }
}
