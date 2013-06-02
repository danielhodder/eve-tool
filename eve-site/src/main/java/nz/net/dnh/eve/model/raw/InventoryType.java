package nz.net.dnh.eve.model.raw;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "invTypes")
@NamedQueries({
		@NamedQuery(name = "InventoryType.findUnknownMineralsForBlueprint", query = InventoryType.UNKNOWN_TYPES_SQL
				+ InventoryType.MINERAL_SQL + " AND brt.blueprint = :blueprint"),
		@NamedQuery(name = "InventoryType.findUnknownMinerals", query = InventoryType.UNKNOWN_TYPES_SQL + InventoryType.MINERAL_SQL),
		@NamedQuery(name = "InventoryType.findUnknownComponentsForBlueprint", query = InventoryType.UNKNOWN_TYPES_SQL
				+ InventoryType.COMPONENT_SQL + " AND brt.blueprint = :blueprint"),
		@NamedQuery(name = "InventoryType.findUnknownComponents", query = InventoryType.UNKNOWN_TYPES_SQL + InventoryType.COMPONENT_SQL) })
public class InventoryType implements Serializable {
	// t.cost is not a nullable column, so this really checks whether t is null
	public static final String UNKNOWN_TYPES_SQL = "SELECT DISTINCT brt.inventoryType FROM BlueprintRequiredType brt LEFT OUTER JOIN brt.type t WHERE t.cost IS NULL";
	public static final String MINERAL_SQL = " AND brt.inventoryType.group.groupName = '"
			+ InventoryGroup.MINERAL_GROUP + "'";
	public static final String COMPONENT_SQL = " AND brt.inventoryType.group.groupName != '"+InventoryGroup.MINERAL_GROUP+"'";

	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	private int typeID;

	@ManyToOne
	@JoinColumn(name = "groupID", updatable = false, insertable = false)
	private InventoryGroup group;

	@Max(100)
	private String typeName;

	@Max(3000)
	private String description;

	private Double mass;

	private Double volume;

	private Double capacity;

	private Integer portionSize;

	private Integer raceID;

	private BigDecimal basePrice;

	private Integer published;

	private Integer marketGroupID;

	private Double chanceOfDuplicating;

	private Integer iconID;

	public int getTypeID() {
		return this.typeID;
	}

	public void setTypeID(final int typeID) {
		this.typeID = typeID;
	}

	public InventoryGroup getGroup() {
		return this.group;
	}

	public boolean isMineral() {
		return this.group.isMineral();
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(final String typeName) {
		this.typeName = typeName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Double getMass() {
		return this.mass;
	}

	public void setMass(final Double mass) {
		this.mass = mass;
	}

	public Double getVolume() {
		return this.volume;
	}

	public void setVolume(final Double volume) {
		this.volume = volume;
	}

	public Double getCapacity() {
		return this.capacity;
	}

	public void setCapacity(final Double capacity) {
		this.capacity = capacity;
	}

	public Integer getPortionSize() {
		return this.portionSize;
	}

	public void setPortionSize(final Integer portionSize) {
		this.portionSize = portionSize;
	}

	public Integer getRaceID() {
		return this.raceID;
	}

	public void setRaceID(final Integer raceID) {
		this.raceID = raceID;
	}

	public BigDecimal getBasePrice() {
		return this.basePrice;
	}

	public void setBasePrice(final BigDecimal basePrice) {
		this.basePrice = basePrice;
	}

	public Integer getPublished() {
		return this.published;
	}

	public void setPublished(final Integer published) {
		this.published = published;
	}

	public Integer getMarketGroupID() {
		return this.marketGroupID;
	}

	public void setMarketGroupID(final Integer marketGroupID) {
		this.marketGroupID = marketGroupID;
	}

	public Double getChanceOfDuplicating() {
		return this.chanceOfDuplicating;
	}

	public void setChanceOfDuplicating(final Double chanceOfDuplicating) {
		this.chanceOfDuplicating = chanceOfDuplicating;
	}

	public Integer getIconID() {
		return this.iconID;
	}

	public void setIconID(final Integer iconID) {
		this.iconID = iconID;
	}

	@Override
	public String toString() {
		return "InventoryType [typeID=" + this.typeID + ", group=" + this.group
				+ ", typeName=" + this.typeName + ", description="
				+ this.description + ", mass=" + this.mass + ", volume="
				+ this.volume + ", capacity=" + this.capacity
				+ ", portionSize=" + this.portionSize + ", raceID="
				+ this.raceID + ", basePrice=" + this.basePrice
				+ ", published=" + this.published + ", marketGroupID="
				+ this.marketGroupID + ", chanceOfDuplicating="
				+ this.chanceOfDuplicating + ", iconID=" + this.iconID + "]";
	}

	@Override
	public int hashCode() {
		return this.typeID;
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof InventoryType
				&& this.typeID == ((InventoryType) obj).getTypeID();
	}

}
