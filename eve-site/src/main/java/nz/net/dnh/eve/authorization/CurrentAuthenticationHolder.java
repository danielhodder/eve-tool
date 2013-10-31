package nz.net.dnh.eve.authorization;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

/**
 * Allows injecting of the current authentication to other components.
 * 
 * @author danielh
 */
@Component
public class CurrentAuthenticationHolder {
	/**
	 * Gets the current authentication from the {@link SecurityContextHolder}.
	 * 
	 * @return 	The current authentication or null if there is none
	 * @see SecurityContext#getAuthentication()
	 */
	public Authentication getCurrentAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}
