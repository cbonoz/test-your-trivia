package trivia.test.model

import java.util.*

object Constants {
    val STATES = Arrays.asList(
            State("Alabama", "AL", "Montgomery", "1819", "22"),
            State("Alaska", "AK", "Juneau", "1959", "49"),
            State("Arizona", "AZ", "Phoenix", "1912", "48"),
            State("Arkansas", "AR", "Little Rock", "1836", "25"),
            State("California", "CA", "Sacramento", "1850", "31"),
            State("Colorado", "CO", "Denver", "1876", "38"),
            State("Connecticut", "CT", "Hartford", "1788", "5"),
            State("Delaware", "DE", "Dover", "1787", "1"),
            State("Florida", "FL", "Tallahassee", "1845", "27"),
            State("Georgia", "GA", "Atlanta", "1788", "4"),
            State("Hawaii", "HI", "Honolulu", "1959", "50"),
            State("Idaho", "ID", "Boise", "1890", "43"),
            State("Illinois", "IL", "Springfield", "1818", "21"),
            State("Indiana", "IN", "Indianapolis", "1816", "19"),
            State("Iowa", "IA", "Des Moines", "1846", "29"),
            State("Kansas", "KS", "Topeka", "1861", "34"),
            State("Kentucky", "KY", "Frankfort", "1792", "15"),
            State("Louisiana", "LA", "Baton Rouge", "1812", "18"),
            State("Maine", "ME", "Augusta", "1820", "23"),
            State("Maryland", "MD", "Annapolis", "1788", "7"),
            State("Massachusetts", "MA", "Boston", "1788", "6"),
            State("Michigan", "MI", "Lansing", "1837", "26"),
            State("Minnesota", "MN", "St. Paul", "1858", "32"),
            State("Mississippi", "MS", "Jackson", "1817", "20"),
            State("Missouri", "MO", "Jefferson City", "1821", "24"),
            State("Montana", "MT", "Helena", "1889", "41"),
            State("Nebraska", "NE", "Lincoln", "1867", "37"),
            State("Nevada", "NV", "Carson City", "1864", "36"),
            State("New Hampshire", "NH", "Concord", "1788", "9"),
            State("New Jersey", "NJ", "Trenton", "1787", "3"),
            State("New Mexico", "NM", "Santa Fe", "1912", "47"),
            State("New York", "NY", "Albany", "1788", "11"),
            State("North Carolina", "NC", "Raleigh", "1789", "12"),
            State("North Dakota", "ND", "Bismarck", "1889", "39"),
            State("Ohio", "OH", "Columbus", "1803", "17"),
            State("Oklahoma", "OK", "Oklahoma City", "1907", "46"),
            State("Oregon", "OR", "Salem", "1859", "33"),
            State("Pennsylvania", "PA", "Harrisburg", "1787", "2"),
            State("Rhode Island", "RI", "Providence", "1790", "13"),
            State("South Carolina", "SC", "Columbia", "1788", "8"),
            State("South Dakota", "SD", "Pierre", "1889", "40"),
            State("Tennessee", "TN", "Nashville", "1796", "16"),
            State("Texas", "TX", "Austin", "1845", "28"),
            State("Utah", "UT", "Salt Lake City", "1896", "45"),
            State("Vermont", "VT", "Montpelier", "1791", "14"),
            State("Virginia", "VA", "Richmond", "1788", "10"),
            State("Washington", "WA", "Olympia", "1889", "42"),
            State("West Virginia", "WV", "Charleston", "1863", "35"),
            State("Wisconsin", "WI", "Madison", "1848", "30"),
            State("Wyoming", "WY", "Cheyenne", "1890", "44")
    )
    const val APP_NAME = "Test your Trivia"

    var USE_CARDS_FLAG = true
    var WELCOME_MESSAGE = "Welcome to the $APP_NAME game!  You can ask me for a random quiz on one of my categories. Example, say 'I want an easy quiz on sports'."
    // This is the message a user will hear when they try to cancel or stop the
// skill, or when they finish a quiz.
    var EXIT_SKILL_MESSAGE = "Thank you for playing $APP_NAME!  Let's play again soon!"
    // This is the message a user will hear after they ask (and hear) about a
// specific data element.
    var REPROMPT_MESSAGE = "Which other state or capital would you like to know about?"
    // This is the message a user will hear when they ask Alexa for help in your
// skill.
    var HELP_MESSAGE = "I know lots of random trivia. You can ask me for a quiz on one of my categories. Example, say 'I want an easy quiz on sports'."
    var CORRECT_RESPONSES = Arrays.asList("Booya", "All righty", "Bam", "Bazinga", "Bingo", "Boom", "Bravo", "Cha Ching", "Cheers", "Dynomite",
            "Hip hip hooray", "Hurrah", "Hurray", "Huzzah", "Oh dear.  Just kidding.  Hurray", "Kaboom", "Kaching", "Oh snap", "Phew",
            "Righto", "Way to go", "Well done", "Whee", "Woo hoo", "Yay", "Wowza", "Yowsa")
    var INCORRECT_RESPONSES = Arrays.asList("Argh", "Aw man", "Blarg", "Blast", "Boo", "Bummer", "Darn", "D'oh", "Dun dun dun", "Eek", "Honk", "Le sigh",
            "Mamma mia", "Oh boy", "Oh dear", "Oof", "Ouch", "Ruh roh", "Shucks", "Uh oh", "Wah wah", "Whoops a daisy", "Yikes")
}