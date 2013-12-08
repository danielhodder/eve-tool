/**
 * 
 */
package nz.net.dnh.spring.conversation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * A spring web intercepter that is used for conversation scoping. This bean intercepts requests before they hit the MVC
 * controllers and other filters. This MUST be called before any 'conversation' scoped beans are used otherwise the
 * {@link ConversationScope} will throw null pointers.
 * <p>
 * It is worth noting that this intercepter consumes the request to extract the conversation ID from any form post data
 * that is supplied.
 * 
 * @author Daniel Hodder (danielh)
 * 
 */
@Component
public class ConversationInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(ConversationInterceptor.class);

	private String conversationParamaterName = "conversationID";

	@Autowired
	private CurrentConversationIDHolder currentConversationIDHolder;

	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
		logger.trace("Running conversation ID interceptor for {}", request.getRequestURL());

		this.currentConversationIDHolder.setConversationID(request.getParameter(this.conversationParamaterName));

		return true;
	}

	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
			final ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
			final Exception ex) throws Exception {
	}

	/**
	 * Get the name of the attribute that will be used to determine the current conversation.
	 * 
	 * @return The name of the attribute used to store the conversation ID.
	 * @see #preHandle(HttpServletRequest, HttpServletResponse, Object)
	 */
	public String getConversationParamaterName() {
		return this.conversationParamaterName;
	}

	/**
	 * Set the conversation parameter name.
	 * 
	 * @param conversationParamaterName
	 *            The name of the attribute holding the current conversation ID.
	 */
	public void setConversationParamaterName(final String conversationParamaterName) {
		this.conversationParamaterName = conversationParamaterName;
	}
}
