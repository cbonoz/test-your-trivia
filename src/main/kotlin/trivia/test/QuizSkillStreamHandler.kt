package trivia.test

import com.amazon.ask.SkillStreamHandler
import com.amazon.ask.Skills
import trivia.test.handlers.AnswerIntentHandler
import trivia.test.handlers.ExitSkillHandler
import trivia.test.handlers.HelpIntentHandler
import trivia.test.handlers.LaunchRequestHandler
import trivia.test.handlers.QuizIntentHandler
import trivia.test.handlers.QuestionRepeatIntentHandler
import trivia.test.handlers.SessionEndedHandler
import trivia.test.handlers.SkipQuestionIntentHandler
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
                        SessionEndedHandler()
                )
                .build()
)
