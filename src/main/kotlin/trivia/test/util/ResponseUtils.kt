package trivia.test.util

import trivia.test.model.Category
import trivia.test.model.Constants.NUM_QUESTIONS
import trivia.test.model.Difficulty
import kotlin.random.Random

object ResponseUtils {

    fun getStartQuizMessage(category: Category): String =
        "OK.  I will ask you $NUM_QUESTIONS questions about ${category.text}. "

    fun getExampleQuizMessage(): String {
        val difficulty = Difficulty.values()[Random.nextInt(0, Difficulty.values().size)]
        val category = Category.values()[Random.nextInt(0, Category.values().size)]
        return "For example, say 'Start a ${difficulty.apiName} ${category.text} quiz. "
    }

}
