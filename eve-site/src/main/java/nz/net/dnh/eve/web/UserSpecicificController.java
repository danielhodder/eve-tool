package nz.net.dnh.eve.web;

import nz.net.dnh.eve.authorization.AuthorizationException;
import nz.net.dnh.eve.authorization.ControllerAdditions;
import nz.net.dnh.eve.spring.BeforeRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.view.RedirectView;

/**
 * A superclass that indicated that the subclass presents different information for different users.
 * 
 * @author Daniel Hodder (danielh)
 *
 */
public class UserSpecicificController {
	@BeforeRequest
	public final void ensureUserIsLoggedIn() {
		ControllerAdditions.ensureUserIsLoggedIn();
	}

	@ExceptionHandler(AuthorizationException.class)
	public RedirectView redirectWhenAuthorizationErrorOccours() {
		return new RedirectView("/login");
	}
}
