package nz.net.dnh.eve.model.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Read-only view of the costs of making a blueprint
 */
@Entity
@Table(name = "BlueprintSummary")
public class BlueprintCostSummary implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	private int blueprintTypeID;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "blueprintTypeID", updatable = false, insertable = false)
	private Blueprint blueprint;

	private BigDecimal materialCost;

	private BigDecimal otherCost;

	@Column(name = "cost")
	private BigDecimal totalCost;

	private BigDecimal saleValue;

	private BigDecimal profit;

	private BigDecimal profitPercentage;

	private int hours;

	public int getBlueprintTypeID() {
		return this.blueprintTypeID;
	}

	/**
	 * You probably don't want to call this method - if you are, consider adding
	 * the data you're retrieving to the BlueprintSummary view instead.
	 * 
	 * @return The blueprint this summary is for
	 */
	public Blueprint getBlueprint() {
		return this.blueprint;
	}

	public BigDecimal getMaterialCost() {
		return this.materialCost;
	}

	public BigDecimal getOtherCost() {
		return this.otherCost;
	}

	public BigDecimal getTotalCost() {
		return this.totalCost;
	}

	public BigDecimal getSaleValue() {
		return this.saleValue;
	}

	public BigDecimal getProfit() {
		return this.profit;
	}

	public BigDecimal getProfitPercentage() {
		return this.profitPercentage;
	}

	public int getHours() {
		return this.hours;
	}

	@Override
	public String toString() {
		return "BlueprintCosts [blueprintTypeID=" + this.blueprintTypeID
				+ ", materialCost=" + this.materialCost + ", otherCost="
				+ this.otherCost + ", totalCost=" + this.totalCost
				+ ", saleValue=" + this.saleValue + ", profit=" + this.profit
				+ ", profitPercentage=" + this.profitPercentage + ", hours=" + this.hours + "]";
	}

	@Override
	public int hashCode() {
		return this.blueprintTypeID;
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof BlueprintCostSummary
				&& this.blueprintTypeID == ((BlueprintCostSummary) obj).blueprintTypeID;
	}

}
