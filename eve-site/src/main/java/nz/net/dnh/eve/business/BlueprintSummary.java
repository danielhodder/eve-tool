package nz.net.dnh.eve.business;

import java.math.BigDecimal;
import java.util.Date;

public interface BlueprintSummary extends BlueprintReference {
	public String getName();

	public BigDecimal getMaterialCost();

	public BigDecimal getRunningCost();

	/**
	 * Convenience method which returns the sum of material and running costs
	 * 
	 * @return The total cost of building the blueprint
	 */
	public BigDecimal getTotalCost();

	public BigDecimal getSaleValue();

	public Date getSaleValueLastUpdated();

	public BigDecimal getProfit();

	public BigDecimal getProfitPercentage();
}
