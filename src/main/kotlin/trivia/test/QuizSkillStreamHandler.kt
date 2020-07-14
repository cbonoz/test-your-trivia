package trivia.test

import com.amazon.ask.SkillStreamHandler
import com.amazon.ask.Skills
import trivia.test.handlers.*
import javax.inject.Inject
import javax.inject.Named

@Named("QuizHandler")
class QuizSkillStreamHandler @Inject constructor(
        attributesProvider: SessionAttributesProvider,
        quizService: QuizService
) : SkillStreamHandler(
        Skills.standard()
                .addRequestHandlers(
                        LaunchRequestHandler(),
                        SkipQuestionIntentHandler(attributesProvider),
                        QuizIntentHandler(attributesProvider, quizService),
                        QuestionRepeatIntentHandler(attributesProvider),
                        ExitSkillHandler(),
                        HelpIntentHandler(),
                        AnswerIntentHandler(attributesProvider),
                        ErrorIntentHandler(),
                        SessionEndedHandler()
                )
                .build()
)
