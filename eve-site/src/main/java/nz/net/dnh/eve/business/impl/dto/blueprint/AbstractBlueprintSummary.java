package nz.net.dnh.eve.business.impl.dto.blueprint;

import java.math.BigDecimal;
import java.util.Date;

import nz.net.dnh.eve.business.BlueprintSummary;
import nz.net.dnh.eve.model.domain.Blueprint;
import nz.net.dnh.eve.model.domain.BlueprintCostSummary;

public abstract class AbstractBlueprintSummary implements BlueprintSummary {

	protected final Blueprint blueprint;

	public AbstractBlueprintSummary(final Blueprint blueprint) {
		this.blueprint = blueprint;
	}

	public Blueprint toBlueprint() {
		return this.blueprint;
	}

	@Override
	public String getName() {
		return this.blueprint.getTypeName();
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
	public int getId() {
		return this.blueprint.getBlueprintTypeID();
	}

	@Override
	public int getHours() {
		return (int) Math.ceil(this.blueprint.getCostSummary().getHoursForSingleRun() * getNumberPerRun());
	}

	@Override
	public int getProductionEfficiency() {
		return this.blueprint.getProductionEfficiency();
	}

	@Override
	public int getMaterialEfficiency() {
		return this.blueprint.getMaterialEfficiency();
	}

	@Override
	public int getProducedTypeID() {
		return this.blueprint.getBlueprintType().getProductTypeID();
	}

	@Override
	public int getProducedQuantity() {
		return this.blueprint.getProducedQuantity();
	}

	@Override
	public boolean isAutomaticallyUpdateSalePrice() {
		return this.blueprint.isAutomaticallyUpdateSalePrice();
	}

	@Override
	public String toString() {
		return "Blueprint Summary (" + this.blueprint + ")";
	}

	@Override
	public BigDecimal getTotalCost() {
		final BigDecimal materialCost = getMaterialCost();
		if (materialCost == null) {
			return null;
		}
		return getRunningCost().add(materialCost);
	}

	@Override
	public BigDecimal getProfit() {
		final BigDecimal totalCost = getTotalCost();
		if (totalCost == null) {
			return null;
		}
		return getSaleValue().subtract(totalCost);
	}

	@Override
	public BigDecimal getProfitPercentage() {
		final BigDecimal profit = getProfit();
		if (profit == null) {
			return null;
		}
		return profit.divide(getSaleValue(), BigDecimal.ROUND_HALF_UP);
	}

	@Override
	public BigDecimal getRunningCost() {
		final BlueprintCostSummary costSummary = this.blueprint.getCostSummary();
		final BigDecimal costPerHour = costSummary.getCostPerHour();
		final BigDecimal installCost = costSummary.getInstallCost();
		final BigDecimal hours = new BigDecimal(getHours());
		final BigDecimal numberPerRun = new BigDecimal(getNumberPerRun());
		return costPerHour.multiply(hours).add(installCost).divide(numberPerRun);// TODO rounding
	}

}
