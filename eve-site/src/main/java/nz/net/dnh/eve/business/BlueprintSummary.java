package nz.net.dnh.eve.business;

import java.math.BigDecimal;
import java.util.Date;


public interface BlueprintSummary extends BlueprintInformation {
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

	/**
	 * @return The number produced in a single run
	 */
	public int getNumberPerRun();

	/**
	 * @return The time for a single run (i.e. producing
	 *         {@link #getNumberPerRun()} products), in hours
	 */
	public int getHours();

	/**
	 * @return The material efficiency magical value
	 */
	public int getMaterialEfficiency();

	/**
	 * @return The production efficiency magical value
	 */
	public int getProductionEfficiency();

	/**
	 * @return Is the price for this blueprint being updated automatically
	 * @return
	 */
	public boolean isAutomaticallyUpdateSalePrice();
}
