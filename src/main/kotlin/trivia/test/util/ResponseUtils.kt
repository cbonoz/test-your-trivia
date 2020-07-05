package trivia.test.util

object ResponseUtils {

    fun getStartQuizMessage(category: String): String {
        val categoryString = if (category.isEmpty()) "" else " about $category"
        return "OK.  I will ask you 10 questions$categoryString."
    }
}