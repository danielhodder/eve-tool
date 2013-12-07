/**
 * 
 */
package nz.net.dnh.eve.spring;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 
 * @author Daniel Hodder (danielh)
 *
 */
@Component
public class BoundMethodInvoker {
	private static final Logger logger = LoggerFactory.getLogger(BoundMethodInvoker.class);

	@Autowired
	private WebMethodDataBinder methodArgumentResolver;

	public void invokeMethodOnBean(final Object bean, final Method methodToExecute, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		try {
			final HandlerMethod handlerForTheMethodWeAreInvoking = new HandlerMethod(bean, methodToExecute);

			methodToExecute.invoke(bean, this.methodArgumentResolver.getMethodArgumentValues(new ServletWebRequest(request, response),
																								new ModelAndViewContainer(),
																								handlerForTheMethodWeAreInvoking));
		} catch (final Exception e) {
			logger.error("Could not execute handler method: {}", methodToExecute, e);
			throw e;
		}
	}
}
