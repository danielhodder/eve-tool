package nz.net.dnh.eve.web;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import nz.net.dnh.eve.business.BlueprintIdReference;
import nz.net.dnh.eve.business.BlueprintService;
import nz.net.dnh.eve.business.BlueprintSummary;
import nz.net.dnh.eve.business.CandidateBlueprint;
import nz.net.dnh.eve.business.RequiredTypes;
import nz.net.dnh.eve.business.TypeService;
import nz.net.dnh.eve.web.view.ImageURILocater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public final class BlueprintsController {
	@Autowired
	private ImageURILocater imageURILocator;
	@Autowired private BlueprintService blueprintService;
	@Autowired private TypeService typeService;

	@RequestMapping(value="/blueprints/{id}", method=RequestMethod.GET)
	public ModelAndView showBlueprint(@PathVariable("id") final int id) {
		final BlueprintSummary blueprintInformation = this.blueprintService.getBlueprint(new BlueprintIdReference(id));

		return new ModelAndView("blueprints/show", "view", new BlueprintView(blueprintInformation,
				this.typeService.getRequiredTypes(blueprintInformation), new BlueprintForm(blueprintInformation)));
	}

	@RequestMapping(value="/blueprints/{id}", method=RequestMethod.POST)
	public String updateBlueprint(@PathVariable("id") final int id,
			@ModelAttribute final BlueprintForm form) {
		this.blueprintService.editBlueprint(new BlueprintIdReference(id),
											form.getSaleValue(),
											form.getNumberPerRun(),
											form.getProductionEffiecincy(),
											form.getMaterialEfficency());

		return "redirect:/blueprints/" + id;
	}

	@RequestMapping(value = "/blueprints/search")
	public @ResponseBody
	List<BlueprintSearchResult> searchBlueprint(
			@RequestParam("blueprint-name") final String blueprintName) {
		final Page<CandidateBlueprint> blueprints = this.blueprintService.findCandidateBlueprints(blueprintName,
				new PageRequest(0, 10));

		final List<BlueprintSearchResult> resultList = new LinkedList<>();

		for (final CandidateBlueprint blueprint : blueprints) {
			resultList.add(new BlueprintSearchResult(blueprint));
		}

		return resultList;
	}

	@RequestMapping(value = "/blueprints/new", method = RequestMethod.POST)
	public RedirectView addBluepring(
			@RequestParam("return") final String returnUri,
			@RequestParam("blueprint-id") final int blueprintId,
			@ModelAttribute final BlueprintForm form) {
		this.blueprintService.createBlueprint(new BlueprintIdReference(
				blueprintId), form.getSaleValue(), form.getNumberPerRun(), form
				.getProductionEffiecincy(), form.getMaterialEfficency());

		return new RedirectView(returnUri);
	}

	public final class BlueprintView {
		private final BlueprintSummary blueprint;
		private final RequiredTypes requiredTypes;
		private final BlueprintForm form;

		private BlueprintView(final BlueprintSummary blueprint, final RequiredTypes requiredTypes, final BlueprintForm form) {
			this.blueprint = blueprint;
			this.requiredTypes = requiredTypes;
			this.form = form;
		}

		public BlueprintSummary getBlueprint() {
			return this.blueprint;
		}

		public RequiredTypes getRequiredTypes() {
			return this.requiredTypes;
		}

		public BlueprintForm getForm() {
			return this.form;
		}
	}

	public static final class BlueprintForm {
		private BigDecimal saleValue;
		private int numberPerRun;
		private int materialEfficency;
		private int productionEffiecincy;

		public BlueprintForm() {}
		public BlueprintForm(final BlueprintSummary summary) {
			this.setSaleValue(summary.getSaleValue());
			this.setNumberPerRun(summary.getNumberPerRun());
			this.setMaterialEfficency(summary.getMaterialEfficiency());
			this.setProductionEffiecincy(summary.getProductionEfficiency());
		}
		public BigDecimal getSaleValue() {
			return this.saleValue;
		}
		public void setSaleValue(final BigDecimal saleValue) {
			this.saleValue = saleValue;
		}
		public int getNumberPerRun() {
			return this.numberPerRun;
		}
		public void setNumberPerRun(final int numberPerRun) {
			this.numberPerRun = numberPerRun;
		}
		public int getMaterialEfficency() {
			return this.materialEfficency;
		}
		public void setMaterialEfficency(final int materialEfficency) {
			this.materialEfficency = materialEfficency;
		}
		public int getProductionEffiecincy() {
			return this.productionEffiecincy;
		}
		public void setProductionEffiecincy(final int productionEffiecincy) {
			this.productionEffiecincy = productionEffiecincy;
		}
	}

	public final class BlueprintSearchResult {
		private final String name;
		private final String imageURI;
		private final int id;

		public BlueprintSearchResult(final CandidateBlueprint blueprint) {
			this.name = blueprint.getName();
			this.id = blueprint.getId();
			this.imageURI = BlueprintsController.this.imageURILocator
					.getUriForTypeID(blueprint.getProducedTypeID(), 32);
		}

		public String getName() {
			return this.name;
		}

		public int getId() {
			return this.id;
		}

		public String getImageURI() {
			return this.imageURI;
		}
	}
}
