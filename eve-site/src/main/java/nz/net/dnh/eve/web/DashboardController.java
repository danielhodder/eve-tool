package nz.net.dnh.eve.web;

import java.util.Collections;
import java.util.List;

import nz.net.dnh.eve.model.domain.Blueprint;
import nz.net.dnh.eve.model.domain.Type;
import nz.net.dnh.eve.model.repository.BlueprintRepository;
import nz.net.dnh.eve.model.repository.TypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public final class DashboardController {
	@Autowired
	private BlueprintRepository blueprintRepository;

	@Autowired
	private TypeRepository typeRepository;

	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView dashboard() {
		return new ModelAndView("dashboard", "view", new DashboardView(
				this.blueprintRepository.findAll(),
				Collections.<Type> emptyList(),
				Collections.<Type> emptyList()));
	}

	public class DashboardView {
		public final List<Blueprint> blueprints;
		public final List<Type> minerals;
		public final List<Type> components;

		public DashboardView(final List<Blueprint> blueprints,
				             final List<Type> minerals,
				             final List<Type> components) {
			this.blueprints = blueprints;
			this.minerals = minerals;
			this.components = components;
		}
	}
}
