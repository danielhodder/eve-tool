package nz.net.dnh.eve.spring;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Provides a fix for one of the most annoying components of Spring MVC. It has no before/after request hooks in the
 * controller. Here we implelement this using the {@link BeforeRequest @BeforeRequest} and \@AfterRequest annotations.
 * 
 * @author Daniel Hodder (danielh)
 * 
 */
public class BeforeAfterRequestExecutionIntercepter implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(BeforeAfterRequestExecutionIntercepter.class);

	@Autowired
	private BoundMethodInvoker methodInvoker;

	/**
	 * Parses the handler if it is a {@link HandlerMethod} and uses it to get the bean the code is executing on. Then it
	 * finds every method annotated with {@link BeforeRequest @BeforeRequest} and executes them in the order specified
	 * by the {@link BeforeRequest#priority()} field. Methods with the same priority execute in an unspecified order
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
		logger.trace("Running before request hooks for {}", request.getRequestURL());

		if (!(handler instanceof HandlerMethod)) {
			logger.info("Handler was not of type HandlerMethod, skipping pre-handle. Was: {}", handler);
			return true;
		}

		final HandlerMethod handlerMethod = (HandlerMethod) handler;

		for (final Method methodToExecute : getMethodsToExecute(handlerMethod, BeforeRequest.class)) {
			this.methodInvoker.invokeMethodOnBean(handlerMethod.getBean(), methodToExecute, request, response);
		}

		return true;
	}

	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
			final ModelAndView modelAndView)
			throws Exception {
		logger.trace("Running after request hooks for {}", request.getRequestURL());

		if (!(handler instanceof HandlerMethod)) {
			logger.info("Handler was not of type HandlerMethod, skipping pre-handle. Was: {}", handler);
			return;
		}

		final HandlerMethod handlerMethod = (HandlerMethod) handler;

		for (final Method methodToExecute : getMethodsToExecute(handlerMethod, AfterRequest.class)) {
			this.methodInvoker.invokeMethodOnBean(handlerMethod.getBean(), methodToExecute, request, response);
		}
	}

	@Override
	public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
			final Exception ex) throws Exception {
		// No-op
	}

	/**
	 * 
	 * 
	 * @param handlerMethod
	 * @param annotation
	 *            The annotation we are looking for. Must be one of {@link BeforeRequest} or AfterRequest
	 * @return
	 */
	private SortedSet<Method> getMethodsToExecute(final HandlerMethod handlerMethod, final Class<? extends Annotation> annotation) {
		Comparator<Method> comparater;

		if (annotation == BeforeRequest.class) {
			comparater = new Comparator<Method>() {
				@Override
				public int compare(final Method o1, final Method o2) {
					return Integer.compare(o1.getAnnotation(BeforeRequest.class).priority(),
											o2.getAnnotation(BeforeRequest.class).priority());
				}
			};
		} else if (annotation == AfterRequest.class) {
			comparater = new Comparator<Method>() {
				@Override
				public int compare(final Method o1, final Method o2) {
					return Integer.compare(o1.getAnnotation(AfterRequest.class).priority(),
											o2.getAnnotation(AfterRequest.class).priority());
				}
			};
		} else
			throw new IllegalArgumentException("May only be a before or after requst annotation");

		final TreeSet<Method> methodsToExecute = new TreeSet<>(comparater);

		for (final Method method : handlerMethod.getBeanType().getMethods()) {
			if (method.isAnnotationPresent(annotation)) {
				methodsToExecute.add(method);
			}
		}

		return methodsToExecute;
	}
}
