package nz.net.dnh.eve.model.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import nz.net.dnh.eve.model.raw.InventoryType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.mysema.query.annotations.QueryInit;

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
	@QueryInit("*")
	private InventoryType inventoryType;

	private int units;

	public Blueprint getBlueprint() {
		return this.blueprint;
	}

	public void setBlueprint(final Blueprint blueprint) {
		this.blueprint = blueprint;
	}

	public Type getType() {
		return this.type;
	}

	public void setType(final Type type) {
		this.type = type;
	}

	public InventoryType getInventoryType() {
		return this.inventoryType;
	}

	public int getUnits() {
		return this.units;
	}

	public void setUnits(final int units) {
		this.units = units;
	}

	@Override
	public String toString() {
		return "BlueprintType [blueprint="
				+ this.blueprint.getBlueprintTypeID() + ", type="
				+ this.type.getTypeID() + ", units=" + this.units + "]";
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
