package trivia.test

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import trivia.test.model.QuizResponse
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam

@Path("/")
@RegisterRestClient(configKey = "quiz-api")
interface QuizRepository {

    @GET
    @Produces("application/json")
    fun getQuiz(
        @QueryParam("category") category: String,
        @QueryParam("difficulty") difficulty: String,
        @QueryParam("amount") amount: Int,
        @QueryParam("type") type: String
    ): QuizResponse
}
