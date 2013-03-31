package nz.net.dnh.eve.model.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

	@Id
	@ManyToOne
	@JoinColumn(name = "materialTypeID")
	private Type type;

	private int units;

	public Blueprint getBlueprint() {
		return this.blueprint;
	}

	public void setBlueprint(Blueprint blueprint) {
		this.blueprint = blueprint;
	}

	public Type getType() {
		return this.type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getUnits() {
		return this.units;
	}

	public void setUnits(int units) {
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
	public boolean equals(Object obj) {
		if (!(obj instanceof BlueprintRequiredType))
			return false;
		BlueprintRequiredType other = (BlueprintRequiredType) obj;
		return this.blueprint.equals(other.getBlueprint())
				&& this.type.equals(other.getType());
	}
}
