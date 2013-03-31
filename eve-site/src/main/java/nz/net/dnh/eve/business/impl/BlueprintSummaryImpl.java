package nz.net.dnh.eve.business.impl;

import java.math.BigDecimal;
import java.util.Date;

import nz.net.dnh.eve.business.BlueprintSummary;
import nz.net.dnh.eve.model.domain.Blueprint;

public class BlueprintSummaryImpl implements BlueprintSummary {

	private final Blueprint blueprint;

	public BlueprintSummaryImpl(Blueprint blueprint) {
		this.blueprint = blueprint;
	}

	@Override
	public String getName() {
		return this.blueprint.getTypeName();
	}

	@Override
	public BigDecimal getTotalCost() {
		return this.blueprint.getCostSummary().getTotalCost();
	}
	
	@Override
	public BigDecimal getMaterialCost() {
		return this.blueprint.getCostSummary().getMaterialCost();
	}
	
	@Override
	public BigDecimal getRunningCost() {
		return this.blueprint.getCostSummary().getOtherCost();
	}

	@Override
	public BigDecimal getSaleValue() {
		return this.blueprint.getCostSummary().getSaleValue();
	}

	@Override
	public Date getSaleValueLastUpdated() {
		return this.blueprint.getLastUpdated();
	}

	@Override
	public BigDecimal getProfit() {
		return this.blueprint.getCostSummary().getProfit();
	}

	@Override
	public BigDecimal getProfitPercentage() {
		return this.blueprint.getCostSummary().getProfitPercentage();
	}

	@Override
	public String toString() {
		return "Blueprint Summary (" + this.blueprint + ")";
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof BlueprintSummaryImpl
				&& this.blueprint
						.equals(((BlueprintSummaryImpl) obj).blueprint);
	}

	@Override
	public int hashCode() {
		return this.blueprint.hashCode();
	}

}
