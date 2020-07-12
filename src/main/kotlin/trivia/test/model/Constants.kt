package trivia.test.model

object Constants {
    private const val APP_NAME = "Test your Trivia"

    const val NUM_QUESTIONS = 5

    val EXAMPLE_MESSAGE = "For example, say 'Start an easy sports quiz'. "
    val INFO_MESSAGE = "You can ask me for a quiz from one of my categories, or say 'help' to hear a list. "
    val QUIZ_ERROR_MESSAGE = "Sorry I couldn't find a quiz for that. $INFO_MESSAGE $EXAMPLE_MESSAGE"
    val WELCOME_MESSAGE = "Welcome to the $APP_NAME game! $INFO_MESSAGE"
    // This is the message a user will hear when they try to cancel or stop the
// skill, or when they finish a quiz.
    val EXIT_SKILL_MESSAGE = "Thank you for playing $APP_NAME!  Let's play again soon!"
    val HELP_MESSAGE = "You can ask me for an easy, medium, or hard quiz in different categories. ${Category.allCategoriesText()}. $EXAMPLE_MESSAGE"
    val CORRECT_RESPONSES = listOf("Booya", "All righty", "Bam", "Bazinga", "Bingo", "Boom", "Bravo", "Cha Ching", "Cheers", "Dynomite",
            "Hip hip hooray", "Hurrah", "Hurray", "Huzzah", "Oh dear.  Just kidding.  Hurray", "Kaboom", "Kaching", "Oh snap", "Phew",
            "Righto", "Way to go", "Well done", "Whee", "Woo hoo", "Yay", "Wowza", "Yowsa")
    val INCORRECT_RESPONSES = listOf("Argh", "Aw man", "Blarg", "Blast", "Boo", "Bummer", "Darn", "D'oh", "Dun dun dun", "Eek", "Honk", "Le sigh",
            "Mamma mia", "Oh boy", "Oh dear", "Oof", "Ouch", "Ruh roh", "Shucks", "Uh oh", "Wah wah", "Whoops a daisy", "Yikes")
}
