package nz.net.dnh.eve.business.impl.dto.blueprint;

import java.math.BigDecimal;

import nz.net.dnh.eve.model.domain.Blueprint;

public class BlueprintSummaryImpl extends AbstractBlueprintSummary {

	public BlueprintSummaryImpl(final Blueprint blueprint) {
		super(blueprint);
	}

	@Override
	public BigDecimal getMaterialCost() {// FIXME
		return this.blueprint.getCostSummary().getMaterialCost();
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