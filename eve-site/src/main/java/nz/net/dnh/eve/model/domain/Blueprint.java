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
public class Blueprint implements Serializable {
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
	private int hours;

	@NotNull
	private BigDecimal saleValue;

	@NotNull
	private int materialEfficiency;

	@OneToOne
	@JoinColumn(name = "blueprintTypeID", updatable = false, insertable = false)
	private BlueprintCostSummary costSummary;

	@OneToMany(mappedBy = "blueprint", fetch = FetchType.LAZY)
	private Collection<BlueprintRequiredType> requiredTypes;

	public void setBlueprintTypeID(int blueprintTypeID) {
		this.blueprintTypeID = blueprintTypeID;
	}

	public int getBlueprintTypeID() {
		return this.blueprintTypeID;
	}

	public void setNumberPerRun(int numberPerRun) {
		this.numberPerRun = numberPerRun;
	}

	public int getNumberPerRun() {
		return this.numberPerRun;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getHours() {
		return this.hours;
	}

	public void setSaleValue(BigDecimal saleValue) {
		this.saleValue = saleValue;
	}

	public BigDecimal getSaleValue() {
		return this.saleValue;
	}

	public void setMaterialEfficiency(int materialEfficiency) {
		this.materialEfficiency = materialEfficiency;
	}

	public int getMaterialEfficiency() {
		return this.materialEfficiency;
	}

	public Collection<BlueprintRequiredType> getRequiredTypes() {
		return this.requiredTypes;
	}

	public void setRequiredTypes(Collection<BlueprintRequiredType> requiredTypes) {
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
		return "Blueprint [blueprintTypeID=" + this.blueprintTypeID
				+ ", blueprintType=" + this.blueprintType + ", numberPerRun="
				+ this.numberPerRun + ", hours=" + this.hours + ", saleValue="
				+ this.saleValue + ", materialEfficiency="
				+ this.materialEfficiency + ", costSummary=" + this.costSummary
				+ "]";
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Blueprint
				&& this.blueprintTypeID == ((Blueprint) o).getBlueprintTypeID();
	}

	@Override
	public int hashCode() {
		return this.blueprintTypeID;
	}

}
