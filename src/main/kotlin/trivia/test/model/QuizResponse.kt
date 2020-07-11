package trivia.test.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

data class QuizResponse(
    val response_code: Int,
    val results: List<Question>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Question(
    val correct_answer: String,
    val incorrect_answers: List<String>,
    val question: String
) {
    @JsonIgnore
    val allAnswers = (incorrect_answers + correct_answer).shuffled()
}
