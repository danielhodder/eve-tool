/**
 * 
 */
package nz.net.dnh.eve.spring.conversation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

/**
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
		customScopes.put("conversation", conversationScope());

		customScopeConfigurer.setScopes(customScopes);

		return customScopeConfigurer;
	}

	@Bean
	public ConversationInterceptor conversationInterceptor() {
		return new ConversationInterceptor();
	}
}
