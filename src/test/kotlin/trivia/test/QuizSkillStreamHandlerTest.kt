package trivia.test

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.*
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.common.truth.Truth.assertThat
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Test
import trivia.test.handlers.LaunchRequestHandler
import trivia.test.handlers.QuizIntentHandler
import trivia.test.model.*
import trivia.test.model.Constants.NUM_QUESTIONS
import java.util.*
import javax.inject.Inject


@QuarkusTest
class QuizSkillStreamHandlerTest {

    private val TEST_QUIZ_RESPONSE = QuizResponse(response_code = 200, results = listOf(Question(correct_answer = "Cheetah", incorrect_answers = listOf("Lion", "Thomson&rsquo;s Gazelle", "Pronghorn Antelope"), question = "What is the fastest  land animal?")))
    private val attributesMap = mutableMapOf<String, Any>()
    private val sessionAttributes = SessionAttributes(attributesMap)
    private val mapper = jacksonObjectMapper()

    @Inject
    lateinit var quizService: QuizService

    private val quizAndStartOverIntentHandler by lazy {
        QuizIntentHandler(
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
        assertThat(sessionAttributes.quizItems).hasSize(NUM_QUESTIONS)
        assertThat(sessionAttributes.counter).isEqualTo(1)
    }


    @Test
    fun `generate category intent list`() {
        val categoryValues = Category.values().map { mapOf("name" to mapOf("value" to it.text)) }
        val categoryString = mapper.writeValueAsString(categoryValues)
        print(categoryString)
    }

    @Test
    fun `parse handles html encoded characters in response`() {
        sessionAttributes.setQuizItems(TEST_QUIZ_RESPONSE.results)
        val quizItems = sessionAttributes.quizItems
        assertThat(quizItems[0].incorrect_answers[1]).isEqualTo("Thomsons Gazelle")

    }
}
