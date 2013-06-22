package nz.net.dnh.eve.model.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import nz.net.dnh.eve.model.raw.InventoryBlueprintType;

@Entity
public class Blueprint extends AbstractLastUpdatedBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull
	@Id
	private int blueprintTypeID;

	@OneToOne
	@JoinColumn(name = "blueprintTypeID", updatable = false, insertable = false)
	private InventoryBlueprintType blueprintType;

	@NotNull
	private int numberPerRun;

	@NotNull
	private int productionEfficiency;

	@NotNull
	private BigDecimal saleValue;

	@NotNull
	private int materialEfficiency;

	@NotNull
	private boolean automaticallyUpdateSalePrice;

	@OneToOne
	@JoinColumn(name = "blueprintTypeID", updatable = false, insertable = false)
	private BlueprintCostSummary costSummary;

	@OneToMany(mappedBy = "blueprint", fetch = FetchType.LAZY)
	private Collection<BlueprintRequiredType> requiredTypes;

	public Blueprint() {
	}

	public Blueprint(final int blueprintTypeID, final int numberPerRun, final int productionEfficiency, final BigDecimal saleValue,
			final int materialEfficiency) {
		this.blueprintTypeID = blueprintTypeID;
		this.numberPerRun = numberPerRun;
		this.productionEfficiency = productionEfficiency;
		this.saleValue = saleValue;
		touchLastUpdated();
		this.materialEfficiency = materialEfficiency;
	}

	public void setBlueprintTypeID(final int blueprintTypeID) {
		this.blueprintTypeID = blueprintTypeID;
	}

	public int getBlueprintTypeID() {
		return this.blueprintTypeID;
	}

	public void setNumberPerRun(final int numberPerRun) {
		this.numberPerRun = numberPerRun;
	}

	public int getNumberPerRun() {
		return this.numberPerRun;
	}

	public void setProductionEfficiency(final int productionEfficiency) {
		this.productionEfficiency = productionEfficiency;
	}

	public int getProductionEfficiency() {
		return this.productionEfficiency;
	}

	public void setSaleValue(final BigDecimal saleValue) {
		this.saleValue = saleValue;
	}

	public BigDecimal getSaleValue() {
		return this.saleValue;
	}

	public void setMaterialEfficiency(final int materialEfficiency) {
		this.materialEfficiency = materialEfficiency;
	}

	public int getMaterialEfficiency() {
		return this.materialEfficiency;
	}

	public Collection<BlueprintRequiredType> getRequiredTypes() {
		return this.requiredTypes;
	}

	public void setRequiredTypes(final Collection<BlueprintRequiredType> requiredTypes) {
		this.requiredTypes = requiredTypes;
	}

	public BlueprintCostSummary getCostSummary() {
		return this.costSummary;
	}

	/**
	 * Convenience method which returns the name of the
	 * {@link #getBlueprintType() Blueprint}'s
	 * {@link InventoryBlueprintType#getProductType() Product Type}.
	 * 
	 * @return The blueprint's type name
	 */
	public String getTypeName() {
		return getBlueprintType().getProductType().getTypeName();
	}

	public InventoryBlueprintType getBlueprintType() {
		return this.blueprintType;
	}

	@Override
	public String toString() {
		return "Blueprint [blueprintTypeID=" + this.blueprintTypeID + ", blueprintType=" + this.blueprintType + ", numberPerRun="
				+ this.numberPerRun + ", productionEfficiency=" + this.productionEfficiency + ", saleValue=" + this.saleValue
				+ ", materialEfficiency=" + this.materialEfficiency + ", automaticallyUpdateSalePrice=" + this.automaticallyUpdateSalePrice
				+ ", costSummary=" + this.costSummary + ", requiredTypes=" + this.requiredTypes + "]";
	}

	@Override
	public boolean equals(final Object o) {
		return o instanceof Blueprint
				&& this.blueprintTypeID == ((Blueprint) o).getBlueprintTypeID();
	}

	@Override
	public int hashCode() {
		return this.blueprintTypeID;
	}

	public int getProducedQuantity() {
		return getBlueprintType().getProducedQuantity();
	}

	public boolean isAutomaticallyUpdateSalePrice() {
		return this.automaticallyUpdateSalePrice;
	}

	public void setAutomaticallyUpdateSalePrice(final boolean automaticallyUpdateSalePrice) {
		this.automaticallyUpdateSalePrice = automaticallyUpdateSalePrice;
	}
}
