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

import nz.net.dnh.eve.model.raw.InventoryType;

@Entity
public class Type implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	private int typeID;

	@OneToOne
	@JoinColumn(name = "typeID", updatable = false, insertable = false)
	private InventoryType type;

	@NotNull
	private BigDecimal cost;

	@OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
	private Collection<BlueprintRequiredType> requiredBy;

	public int getTypeID() {
		return this.typeID;
	}

	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}

	public BigDecimal getCost() {
		return this.cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public InventoryType getType() {
		return this.type;
	}

	public void setRequiredBy(Collection<BlueprintRequiredType> requiredBy) {
		this.requiredBy = requiredBy;
	}

	public Collection<BlueprintRequiredType> getRequiredBy() {
		return this.requiredBy;
	}

	/**
	 * Convenience method which returns the name of this type.
	 * 
	 * @return The blueprint's type name
	 */
	public String getTypeName() {
		return getType().getTypeName();
	}

	@Override
	public String toString() {
		return "Type [typeID=" + this.typeID + ", type=" + this.type
				+ ", cost=" + this.cost + "]";
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Type && this.typeID == ((Type) obj).getTypeID();
	}

	@Override
	public int hashCode() {
		return this.typeID;
	}
}
