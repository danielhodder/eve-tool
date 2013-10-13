package nz.net.dnh.eve.authorization;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Provides convenience methods for controllers
 * 
 * @author Daniel Hodder (danielh)
 * 
 */
public class ControllerAdditions {
	/**
	 * A method that checks if the user is logged in.
	 * 
	 * @return True if the user is logged in, false otherwise
	 */
	public static boolean isUserLoggedIn() {
		return SecurityContextHolder.getContext().getAuthentication() != null;
	}

	/**
	 * Checks if a user is logged in and throws a {@link UserNotLoggedInException} if they are not.
	 * 
	 * @throws UserNotLoggedInException
	 *             Thrown if there is no logged in user
	 * @see #isUserLoggedIn()
	 */
	public static void ensureUserIsLoggedIn() {
		if (!isUserLoggedIn())
			throw new UserNotLoggedInException();
	}
}
