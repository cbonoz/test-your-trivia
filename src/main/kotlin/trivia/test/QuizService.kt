package trivia.test

import org.eclipse.microprofile.rest.client.inject.RestClient
import trivia.test.model.Category
import trivia.test.model.Difficulty
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizService {

    @Inject
    @RestClient
    lateinit var repository: QuizRepository

    fun getQuiz(difficulty: Difficulty, category: Category, amount: Int = 10) =
        repository.getQuiz(difficulty.apiName, category.apiCode.toString(), amount)
}
