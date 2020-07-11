package trivia.test.util

import trivia.test.model.Category
import trivia.test.model.Constants.NUM_QUESTIONS

object ResponseUtils {

    fun getStartQuizMessage(category: Category): String =
        "OK.  I will ask you $NUM_QUESTIONS questions about ${category.text}. "
}
