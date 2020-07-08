package trivia.test

import com.amazon.ask.SkillStreamHandler
import com.amazon.ask.Skills
import trivia.test.handlers.*
import javax.inject.Named

@Named("QuizHandler")
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
