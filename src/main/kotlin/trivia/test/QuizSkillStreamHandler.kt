package trivia.test

import com.amazon.ask.SkillStreamHandler
import com.amazon.ask.Skills
import trivia.test.handlers.*

@Suppress("UNUSED")
class QuizSkillStreamHandler : SkillStreamHandler(
        Skills.standard()
                .addRequestHandlers(
                        LaunchRequestHandler(),
                        QuizAndStartOverIntentHandler(),
                        NoAnswerIntentHandler(),
                        AnswerIntentHandler(),
                        RepeatIntentHandler(),
                        HelpIntentHandler(),
                        ExitSkillHandler(),
                        SessionEndedHandler()
                )
        .build()
)
