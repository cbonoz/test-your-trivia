package trivia.test.model

object Constants {
    private const val APP_NAME = "Test your Trivia"
    private const val EXAMPLE_MESSAGE = "For example, say 'Start an easy sports quiz'."

    const val NUM_QUESTIONS = 5
    const val WELCOME_MESSAGE = "Welcome to the $APP_NAME game!  You can ask me for a random quiz in any of my categories."
    const val EXIT_SKILL_MESSAGE = "Thank you for playing $APP_NAME!  Let's play again soon!"

    val HELP_MESSAGE = "You can ask me for an easy, medium, or hard quiz in different categories. ${Category.allCategoriesText()}. $EXAMPLE_MESSAGE"
    val CORRECT_RESPONSES = listOf("Booya", "All righty", "Bam", "Bazinga", "Bingo", "Boom", "Bravo", "Cha Ching", "Cheers", "Dynomite",
            "Hip hip hooray", "Hurrah", "Hurray", "Huzzah", "Oh dear.  Just kidding.  Hurray", "Kaboom", "Kaching", "Oh snap", "Phew",
            "Righto", "Way to go", "Well done", "Whee", "Woo hoo", "Yay", "Wowza", "Yowsa")
    val INCORRECT_RESPONSES = listOf("Argh", "Aw man", "Blarg", "Blast", "Boo", "Bummer", "Darn", "D'oh", "Dun dun dun", "Eek", "Honk", "Le sigh",
            "Mamma mia", "Oh boy", "Oh dear", "Oof", "Ouch", "Ruh roh", "Shucks", "Uh oh", "Wah wah", "Whoops a daisy", "Yikes")
}
