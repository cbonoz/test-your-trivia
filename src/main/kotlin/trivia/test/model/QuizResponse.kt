package trivia.test.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

data class QuizResponse(
    val response_code: Int,
    val results: List<Question>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Question(
    val correct_answer: String,
    val question: String
)
