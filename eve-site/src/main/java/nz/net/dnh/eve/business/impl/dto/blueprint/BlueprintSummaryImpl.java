package nz.net.dnh.eve.business.impl.dto.blueprint;


import java.math.BigDecimal;

import nz.net.dnh.eve.business.RequiredBlueprint;
import nz.net.dnh.eve.business.RequiredTypes;
import nz.net.dnh.eve.business.impl.BlueprintRequiredTypesService;
import nz.net.dnh.eve.model.domain.Blueprint;

public class BlueprintSummaryImpl extends AbstractBlueprintSummary {

	private RequiredTypes requiredTypes;
	private final BlueprintRequiredTypesService requiredTypesService;

	public BlueprintSummaryImpl(final Blueprint blueprint, final BlueprintRequiredTypesService requiredTypesGenerator) {
		super(blueprint);
		this.requiredTypesService = requiredTypesGenerator;
	}

	@Override
	public RequiredTypes getRequiredTypes() {
		// Lazily evaluated for the times when the list of required types is not needed
		if (this.requiredTypes == null) {
			this.requiredTypes = this.requiredTypesService.getRequiredTypes(this);
		}
		return this.requiredTypes;
	}

	@Override
	public BigDecimal getRunningCost() {
		BigDecimal runningCost = BigDecimal.ZERO;
		for (final RequiredBlueprint requiredBlueprint : getRequiredTypes().getRequiredBlueprints()) {
			runningCost = runningCost.add(requiredBlueprint.getProductionCost());
		}
		return runningCost;
	}

	@Override
	public int getNumberPerRun() {
		return this.blueprint.getNumberPerRun();
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof BlueprintSummaryImpl && this.blueprint.equals(((BlueprintSummaryImpl) obj).blueprint);
	}

	@Override
	public int hashCode() {
		return this.blueprint.hashCode();
	}
}