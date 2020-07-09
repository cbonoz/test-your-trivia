package trivia.test

import org.eclipse.microprofile.rest.client.inject.RestClient
import trivia.test.model.Category
import trivia.test.model.Difficulty
import trivia.test.model.QuizResponse
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class QuizService {

    @Inject
    @RestClient
    lateinit var repository: QuizRepository

    fun getQuiz(
        difficulty: Difficulty,
        category: Category,
        amount: Int = 10,
        type: String = "multiple"
    ): QuizResponse =
        repository.getQuiz(
            category = category.apiCode.toString(),
            difficulty = difficulty.apiName,
            amount = amount,
            type = type
        )
}
