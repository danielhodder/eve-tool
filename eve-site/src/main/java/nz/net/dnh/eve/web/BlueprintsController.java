package nz.net.dnh.eve.web;

import java.math.BigDecimal;

import nz.net.dnh.eve.business.BlueprintIdReference;
import nz.net.dnh.eve.business.BlueprintService;
import nz.net.dnh.eve.business.BlueprintSummary;
import nz.net.dnh.eve.business.RequiredTypes;
import nz.net.dnh.eve.business.TypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public final class BlueprintsController {
	@Autowired private BlueprintService blueprintService;
	@Autowired private TypeService typeService;
	
	@RequestMapping(value="/blueprints/{id}", method=RequestMethod.GET)
	public ModelAndView showBlueprint(@PathVariable("id") final int id) {
		BlueprintSummary blueprintInformation = this.blueprintService.getBlueprint(new BlueprintIdReference(id));
		
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
	
	public final class BlueprintView {
		private final BlueprintSummary blueprint;
		private final RequiredTypes requiredTypes;
		private final BlueprintForm form;
		
		private BlueprintView(BlueprintSummary blueprint, RequiredTypes requiredTypes, BlueprintForm form) {
			this.blueprint = blueprint;
			this.requiredTypes = requiredTypes;
			this.form = form;
		}

		public BlueprintSummary getBlueprint() {
			return blueprint;
		}

		public RequiredTypes getRequiredTypes() {
			return requiredTypes;
		}

		public BlueprintForm getForm() {
			return form;
		}
	}
	
	public static final class BlueprintForm {
		private BigDecimal saleValue;
		private int numberPerRun;
		private int materialEfficency;
		private int productionEffiecincy;
		
		public BlueprintForm() {}
		public BlueprintForm(BlueprintSummary summary) {
			this.setSaleValue(summary.getSaleValue());
			this.setNumberPerRun(summary.getNumberPerRun());
			this.setMaterialEfficency(summary.getMaterialEfficiency());
			this.setProductionEffiecincy(summary.getProductionEfficiency());
		}
		public BigDecimal getSaleValue() {
			return saleValue;
		}
		public void setSaleValue(BigDecimal saleValue) {
			this.saleValue = saleValue;
		}
		public int getNumberPerRun() {
			return numberPerRun;
		}
		public void setNumberPerRun(int numberPerRun) {
			this.numberPerRun = numberPerRun;
		}
		public int getMaterialEfficency() {
			return materialEfficency;
		}
		public void setMaterialEfficency(int materialEfficency) {
			this.materialEfficency = materialEfficency;
		}
		public int getProductionEffiecincy() {
			return productionEffiecincy;
		}
		public void setProductionEffiecincy(int productionEffiecincy) {
			this.productionEffiecincy = productionEffiecincy;
		}
	}
}
