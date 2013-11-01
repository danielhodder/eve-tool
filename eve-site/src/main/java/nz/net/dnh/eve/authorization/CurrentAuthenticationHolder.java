package nz.net.dnh.eve.authorization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

/**
 * Allows injecting of the current authentication to other components.
 * 
 * @author danielh
 */
@Component("currentAuthenticationHolder")
public class CurrentAuthenticationHolder {
	private static final Logger LOGGER = LoggerFactory.getLogger(CurrentAuthenticationHolder.class);
	
	/**
	 * Gets the current authentication from the {@link SecurityContextHolder}.
	 * 
	 * @return 	The current authentication or null if there is none
	 * @see SecurityContext#getAuthentication()
	 */
	public Authentication getCurrentAuthentication() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		LOGGER.trace("The current authentication is {}", authentication);

		return authentication;
	}
}
