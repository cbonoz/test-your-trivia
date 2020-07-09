package trivia.test.model

enum class Category(val textKey: String, val apiCode: Int) {
    SPORTS("sports", 1),
    GENERAL_KNOWLEDGE("general knowledge", 9);
    // TODO: all the other ones

    companion object {
        fun fromText(text: String): Category? =
            values().firstOrNull { it.textKey == text.toLowerCase() }
    }
}
