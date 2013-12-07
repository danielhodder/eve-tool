/**
 * 
 */
package nz.net.dnh.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * @author  Daniel Hodder (danielh)
 */
@Component
public class WebMethodDataBinder {
	private static final Logger logger = LoggerFactory.getLogger(WebMethodDataBinder.class);

	/**
	 * 
	 */
	@Autowired
	public RequestMappingHandlerAdapter containerForMethodParamaterBinder;

	// All these are shamelessly stolen from the InvokableHandlerMethod

	/**
	 * Get the method argument values for the current request.
	 */
	public Object[] getMethodArgumentValues(
			final NativeWebRequest request, final ModelAndViewContainer mavContainer,
			final HandlerMethod handlerMethod) throws Exception {

		final MethodParameter[] parameters = handlerMethod.getMethodParameters();
		final Object[] args = new Object[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			final MethodParameter parameter = parameters[i];
			// parameter.initParameterNameDiscovery(parameterNameDiscoverer);
			// GenericTypeResolver.resolveParameterType(parameter, handlerMethod.getBean().getClass());
			//
			// args[i] = resolveProvidedArgument(parameter, providedArgs);
			// if (args[i] != null) {
			// continue;
			// }

			if (this.containerForMethodParamaterBinder.getArgumentResolvers().supportsParameter(parameter)) {
				try {
					args[i] = this.containerForMethodParamaterBinder.getArgumentResolvers().resolveArgument(parameter, mavContainer,
																													request,
																													null);
					args[i] = new ServletRequestDataBinder(null).convertIfNecessary(args[i], parameter.getParameterType(), parameter);

					continue;
				} catch (final Exception ex) {
					logger.warn(getArgumentResolutionErrorMessage("Error resolving argument", i, handlerMethod), ex);
					throw ex;
				}
			}

			if (args[i] == null) {
				final String msg = getArgumentResolutionErrorMessage("No suitable resolver for argument", i, handlerMethod);
				logger.error(msg);

				throw new IllegalStateException(msg);
			}
		}

		logger.trace("The arguments for the method {} are {}", handlerMethod.getMethod(), args);

		return args;
	}

	private String getArgumentResolutionErrorMessage(String message, final int index, final HandlerMethod handlerMethod) {
		final MethodParameter param = handlerMethod.getMethodParameters()[index];
		message += " [" + index + "] [type=" + param.getParameterType().getName() + "]";
		return getDetailedErrorMessage(message, handlerMethod);
	}

	/**
	 * Adds HandlerMethod details such as the controller type and method signature to the given error message.
	 * 
	 * @param message
	 *            error message to append the HandlerMethod details to
	 */
	protected String getDetailedErrorMessage(final String message, final HandlerMethod handlerMethod) {
		final StringBuilder sb = new StringBuilder(message).append("\n");
		sb.append("HandlerMethod details: \n");
		sb.append("Controller [").append(handlerMethod.getBeanType().getName()).append("]\n");
		return sb.toString();
	}
}