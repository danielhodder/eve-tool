package nz.net.dnh.eve.web;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;

import nz.net.dnh.eve.business.BlueprintService;
import nz.net.dnh.eve.business.BlueprintSummary;
import nz.net.dnh.eve.model.domain.Type;

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
	private NumberFormat currencyFormatter;

	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView dashboard() {
		return new ModelAndView("dashboard", "view", new DashboardView(
				this.blueprintService.listSummaries(),
				Collections.<Type> emptyList(),
				Collections.<Type> emptyList()));
	}

	public class DashboardView {
		private final List<BlueprintSummary> blueprints;
		private final List<Type> minerals;
		private final List<Type> components;

		public DashboardView(final List<BlueprintSummary> blueprints,
				             final List<Type> minerals,
				             final List<Type> components) {
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
		public List<Type> getMinerals() {
			return this.minerals;
		}

		/**
		 * @return the components
		 */
		public List<Type> getComponents() {
			return this.components;
		}
	}
}
