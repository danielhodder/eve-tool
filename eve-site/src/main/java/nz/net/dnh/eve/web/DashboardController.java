package nz.net.dnh.eve.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public final class DashboardController {
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String dashboard() {
		return "dashboard";
	}
}
