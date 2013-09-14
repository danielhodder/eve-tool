package nz.net.dnh.eve.model.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import nz.net.dnh.eve.model.raw.InventoryBlueprintType;
import nz.net.dnh.eve.model.raw.InventoryType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * Represents a type required to build a blueprint
 */
@Entity
@Table(name = "BlueprintTypes")
public class BlueprintRequiredType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	@JoinColumn(name = "blueprintTypeID")
	private Blueprint blueprint;

	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "materialTypeID")
	private Type type;

	@Id
	@ManyToOne
	@JoinColumn(name = "materialTypeID", insertable = false, updatable = false)
	private InventoryType inventoryType;

	private int units;

	@OneToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "materialBlueprintTypeID")
	private Blueprint materialBlueprint;

	@OneToOne
	@JoinColumn(name = "materialBlueprintTypeID", insertable = false, updatable = false)
	private InventoryBlueprintType materialBlueprintType;

	@Column(columnDefinition = "bigint")
	private boolean decomposed;

	/** @return the blueprint which requires this type */
	public Blueprint getBlueprint() {
		return this.blueprint;
	}

	public void setBlueprint(final Blueprint blueprint) {
		this.blueprint = blueprint;
	}

	/** @return the type required by the blueprint, may be null if the type is not in the system */
	public Type getType() {
		return this.type;
	}

	public void setType(final Type type) {
		this.type = type;
	}

	/** @return the type required by the blueprint in eve's inventory, never null */
	public InventoryType getInventoryType() {
		return this.inventoryType;
	}

	public void setInventoryType(final InventoryType inventoryType) {
		this.inventoryType = inventoryType;
	}

	/** @return the number of the given {@link #getType() type} required by this {@link #getBlueprint() blueprint} */
	public int getUnits() {
		return this.units;
	}

	public void setUnits(final int units) {
		this.units = units;
	}

	/**
	 * @return the blueprint which could manufacture the {@link #getType() required type}, may be null if no blueprint can manufacutre the
	 *         type or the blueprint is not in the system
	 */
	public Blueprint getMaterialBlueprint() {
		return this.materialBlueprint;
	}

	public void setMaterialBlueprint(final Blueprint materialBlueprint) {
		this.materialBlueprint = materialBlueprint;
	}

	/**
	 * @return the blueprint which could manufacture the {@link #getType() required type} in eve's inventory, may be null if no blueprint
	 *         can manufacture the type
	 */
	public InventoryBlueprintType getMaterialBlueprintType() {
		return this.materialBlueprintType;
	}

	public void setMaterialBlueprintType(final InventoryBlueprintType materialBlueprintType) {
		this.materialBlueprintType = materialBlueprintType;
	}

	public boolean isDecomposed() {
		return this.decomposed;
	}

	public void setDecomposed(final boolean decomposed) {
		this.decomposed = decomposed;
	}

	@Override
	public String toString() {
		return "BlueprintType [blueprint="
				+ this.blueprint.getBlueprintTypeID() + ", type="
				+ this.type.getTypeID() + ", units=" + this.units +", decomposed="+this.decomposed+"]";
	}

	@Override
	public int hashCode() {
		return this.blueprint.getBlueprintTypeID() + this.type.getTypeID();
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof BlueprintRequiredType))
			return false;
		final BlueprintRequiredType other = (BlueprintRequiredType) obj;
		return this.blueprint.equals(other.getBlueprint())
				&& this.type.equals(other.getType());
	}
}
