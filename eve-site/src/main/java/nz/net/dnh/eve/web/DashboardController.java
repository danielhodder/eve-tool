package nz.net.dnh.eve.web;

import java.util.List;

import nz.net.dnh.eve.business.BlueprintService;
import nz.net.dnh.eve.business.BlueprintSummary;
import nz.net.dnh.eve.business.Component;
import nz.net.dnh.eve.business.Mineral;
import nz.net.dnh.eve.business.TypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public final class DashboardController {
	@Autowired
	private BlueprintService blueprintService;

	@Autowired
	private TypeService typeService;

	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView dashboard() {
		return new ModelAndView("dashboard", "view", new DashboardView(
				this.blueprintService.listSummaries(),
				this.typeService.listMinerals(true),
				this.typeService.listComponents(true)));
	}

	public class DashboardView {
		private final List<BlueprintSummary> blueprints;
		private final List<Mineral> minerals;
		private final List<Component> components;

		public DashboardView(final List<BlueprintSummary> blueprints,
				             final List<Mineral> minerals,
				             final List<Component> components) {
			this.blueprints = blueprints;
			this.minerals = minerals;
			this.components = components;
		}

		public List<BlueprintSummary> getBlueprints() {
			return this.blueprints;
		}

		/**
		 * @return the minerals
		 */
		public List<Mineral> getMinerals() {
			return this.minerals;
		}

		/**
		 * @return the components
		 */
		public List<Component> getComponents() {
			return this.components;
		}
	}
}
