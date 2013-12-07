/**
 * 
 */
package nz.net.dnh.spring.conversation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Daniel Hodder (danielh)
 *
 */
public class ConversationInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(ConversationInterceptor.class);

	@Autowired
	private CurrentConversationIDHolder currentConversationIDHolder;

	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
		logger.trace("Running conversation ID interceptor for {}", request.getRequestURL());

		this.currentConversationIDHolder.setConversationID(request.getParameter("conversationID"));

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
}
