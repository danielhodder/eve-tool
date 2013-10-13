/**
 * 
 */
package nz.net.dnh.eve.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * A controller that enables us to use our own standard login template
 * 
 * @author Daniel Hodder (danielh)
 * 
 */
@Controller
public class AccountController {
	@RequestMapping(value = "/login")
	public String showLoginPage(@RequestParam(value = "error", required = false) final String failed,
			final Model model) {
		if (failed != null) {
			model.addAttribute("failed", true);
		}

		return "login";
	}
}
