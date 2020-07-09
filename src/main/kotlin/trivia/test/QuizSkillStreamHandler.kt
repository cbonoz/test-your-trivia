package trivia.test

import com.amazon.ask.SkillStreamHandler
import com.amazon.ask.Skills
import trivia.test.handlers.*
import javax.inject.Inject
import javax.inject.Named

@Named("QuizHandler")
class QuizSkillStreamHandler @Inject constructor(
        quizService: QuizService
) : SkillStreamHandler(
        Skills.standard()
                .addRequestHandlers(
                        LaunchRequestHandler(),
                        QuizAndStartOverIntentHandler(quizService),
                        NoAnswerIntentHandler(),
                        AnswerIntentHandler(),
                        RepeatIntentHandler(),
                        HelpIntentHandler(),
                        ExitSkillHandler(),
                        SessionEndedHandler()
                )
        .build()
)
