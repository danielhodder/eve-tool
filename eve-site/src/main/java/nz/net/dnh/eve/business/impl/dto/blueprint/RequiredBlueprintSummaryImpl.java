package nz.net.dnh.eve.business.impl.dto.blueprint;

import java.math.BigDecimal;

import nz.net.dnh.eve.business.RequiredBlueprint;
import nz.net.dnh.eve.business.RequiredType;
import nz.net.dnh.eve.model.domain.Blueprint;

/** Blueprint summary used when returning a {@link RequiredBlueprint} or {@link RequiredType} */
public class RequiredBlueprintSummaryImpl extends AbstractBlueprintSummary {

	private final int runs;

	public RequiredBlueprintSummaryImpl(final Blueprint blueprint, final int runs) {
		super(blueprint);
		this.runs = runs;
	}

	@Override
	public int getNumberPerRun() {
		return this.runs;
	}

	@Override
	public BigDecimal getMaterialCost() {
		// This also makes getTotalCost(), getProfit() and getProfitPercentage() unavailable
		throw new UnsupportedOperationException("Material cost is not available for required blueprints");
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof RequiredBlueprintSummaryImpl && this.blueprint.equals(((RequiredBlueprintSummaryImpl) obj).blueprint);
	}

	@Override
	public int hashCode() {
		return this.blueprint.hashCode();
	}
}
