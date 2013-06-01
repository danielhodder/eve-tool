package nz.net.dnh.eve.business.impl;

import java.math.BigDecimal;

import nz.net.dnh.eve.model.domain.Blueprint;
import nz.net.dnh.eve.model.domain.BlueprintCostSummary;
import nz.net.dnh.eve.model.raw.InventoryBlueprintType;

public class MockBlueprint extends Blueprint {
	private static final long serialVersionUID = 1L;
	private final String typeName;
	private final BigDecimal materialCost;
	private final BigDecimal otherCost;
	private final BigDecimal profit;
	private final BigDecimal profitPercentage;
	private final BigDecimal totalCost;
	private final int producedTypeId;
	private final int hours;

	public MockBlueprint(final int blueprintTypeID, final int numberPerRun, final int hours, final int productionEfficiency,
			final BigDecimal saleValue, final int materialEfficiency, final String typeName, final BigDecimal materialCost,
			final BigDecimal otherCost, final BigDecimal profit, final BigDecimal profitPercentage, final BigDecimal totalCost,
			final int producedTypeId) {
		super(blueprintTypeID, numberPerRun, productionEfficiency, saleValue, materialEfficiency);
		this.typeName = typeName;
		this.hours = hours;
		this.materialCost = materialCost;
		this.otherCost = otherCost;
		this.profit = profit;
		this.profitPercentage = profitPercentage;
		this.totalCost = totalCost;
		this.producedTypeId = producedTypeId;
	}

	@Override
	public String getTypeName() {
		return this.typeName;
	}

	public BigDecimal getMaterialCost() {
		return this.materialCost;
	}

	public BigDecimal getOtherCost() {
		return this.otherCost;
	}

	public int getHours() {
		return this.hours;
	}

	public BigDecimal getProfit() {
		return this.profit;
	}

	public BigDecimal getProfitPercentage() {
		return this.profitPercentage;
	}

	public BigDecimal getTotalCost() {
		return this.totalCost;
	}

	public int getProducedTypeId() {
		return this.producedTypeId;
	}

	@Override
	public InventoryBlueprintType getBlueprintType() {
		return new InventoryBlueprintType() {
			private static final long serialVersionUID = 1L;

			@Override
			public Integer getProductTypeID() {
				return MockBlueprint.this.producedTypeId;
			}
		};
	}

	@Override
	public BlueprintCostSummary getCostSummary() {
		return new BlueprintCostSummary() {
			private static final long serialVersionUID = 1L;

			@Override
			public BigDecimal getMaterialCost() {
				return MockBlueprint.this.getMaterialCost();
			}

			@Override
			public BigDecimal getOtherCost() {
				return MockBlueprint.this.getOtherCost();
			}

			@Override
			public BigDecimal getProfit() {
				return MockBlueprint.this.getProfit();
			}

			@Override
			public BigDecimal getProfitPercentage() {
				return MockBlueprint.this.getProfitPercentage();
			}

			@Override
			public BigDecimal getSaleValue() {
				return MockBlueprint.this.getSaleValue();
			}

			@Override
			public BigDecimal getTotalCost() {
				return MockBlueprint.this.getTotalCost();
			}

			@Override
			public int getHours() {
				return MockBlueprint.this.getHours();
			}
		};
	}
}
