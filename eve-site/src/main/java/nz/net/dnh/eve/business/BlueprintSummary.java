package nz.net.dnh.eve.business;

import java.math.BigDecimal;
import java.util.Date;

public interface BlueprintSummary extends BlueprintReference {
	/**
	 * @return The name of the blueprint
	 */
	public String getName();

	/**
	 * @return The cost of the materials used to build the blueprint. May be null if the cost is unknown
	 */
	public BigDecimal getMaterialCost();

	/**
	 * @return The running cost of building the blueprint, in Isk. Never null
	 */
	public BigDecimal getRunningCost();

	/**
	 * Convenience method which returns the sum of material and running costs
	 * 
	 * @return The total cost of building the blueprint, in Isk. May be null if the cost is unknown
	 */
	public BigDecimal getTotalCost();

	/**
	 * @return The sale value of the blueprint, never null
	 */
	public BigDecimal getSaleValue();

	/**
	 * @return The date and time the sale value was last updated
	 */
	public Date getSaleValueLastUpdated();

	/**
	 * @return The profit from selling the blueprint, in Isk. May be null if the cost is unknown
	 */
	public BigDecimal getProfit();

	/**
	 * @return The profit from selling the blueprint, as a percentage of the sale value. May be null if the cost is unknown
	 */
	public BigDecimal getProfitPercentage();
}
