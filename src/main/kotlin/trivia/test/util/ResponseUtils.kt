package trivia.test.util

import trivia.test.model.Category

object ResponseUtils {

    fun getStartQuizMessage(category: Category): String =
        "OK.  I will ask you 10 questions about ${category.text}."
}
