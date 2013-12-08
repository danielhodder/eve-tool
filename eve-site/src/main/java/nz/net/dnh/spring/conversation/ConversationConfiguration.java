/**
 * 
 */
package nz.net.dnh.spring.conversation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

/**
 * Configuration for the conversation listener and scope. the {@link #customScopeConfigurer()} is not static in
 * convention of the spring java-config settings. This is required because the {@link #customScopeConfigurer()} bean
 * requires the {@link #conversationScope()} bean to work and since it has to be injected it can't be static.
 * 
 * @author Daniel Hodder (danielh)
 * 
 */
@Configuration
public class ConversationConfiguration {
	@Bean
	@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public ConversationScope conversationScope() {
		return new ConversationScope();
	}

	@Bean
	public CustomScopeConfigurer customScopeConfigurer() {
		final CustomScopeConfigurer customScopeConfigurer = new CustomScopeConfigurer();
		final Map<String, Object> customScopes = new HashMap<>();
		customScopes.put(ConversationScope.SCOPE_CONVERSATION, conversationScope());

		customScopeConfigurer.setScopes(customScopes);

		return customScopeConfigurer;
	}
}
