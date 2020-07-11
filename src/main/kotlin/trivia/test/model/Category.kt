package trivia.test.model

enum class Category(val text: String, val apiCode: Int) {
    GENERAL_KNOWLEDGE("general knowledge", 9),
    BOOKS("books", 10),
    FILM("film", 11),
    MUSIC("music", 12),
    MUSICALS("musicals", 13),
    TELEVISION("television", 14),
    VIDEO_GAMES("video games", 15),
    BOARD_GAMES("board games", 16),
    SCIENCE("science", 17),
    COMPUTERS("computers", 18),
    MATH("math", 19),
    MYTHOLOGY("mythology", 20),
    SPORTS("sports", 21),
    GEOGRAPHY("geography", 22),
    HISTORY("history", 23),
    POLITICS("politics", 24),
    ART("art", 25),
    CELEBRITIES("celebrities", 26),
    ANIMALS("animals", 27),
    VEHICLES("vehicles", 28),
    COMICS("comics", 29),
    GADGETS("gadgets", 30),
    ANIME("anime", 31),
    CARTOONS("cartoons", 32);

    companion object {
        fun fromText(text: String): Category =
            values().first { it.text == text.toLowerCase() }

        fun allCategoriesText(): String =
            values().joinToString { it.text }
    }
}
