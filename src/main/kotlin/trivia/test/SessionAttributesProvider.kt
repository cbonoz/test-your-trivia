package trivia.test

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces

interface SessionAttributesProvider {
    fun get(input: HandlerInput): MutableMap<String, Any>
}

object DefaultSessionAttributesProvider : SessionAttributesProvider {
    override fun get(input: HandlerInput): MutableMap<String, Any> =
        input.attributesManager.sessionAttributes
}

@ApplicationScoped
class Provider {

    @Produces
    fun provideSessionAttributesProvider(): SessionAttributesProvider =
        DefaultSessionAttributesProvider
}
