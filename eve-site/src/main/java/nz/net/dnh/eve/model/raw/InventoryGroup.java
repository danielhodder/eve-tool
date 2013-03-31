package nz.net.dnh.eve.model.raw;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "invGroups")
public class InventoryGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	private int groupID;

	@ManyToOne
	@JoinColumn(name = "categoryID", updatable = false, insertable = false)
	private InventoryCategory category;
	@Max(100)
	private String groupName;
	@Max(3000)
	private String description;
	private Integer iconID;
	private Integer useBasePrice;
	private Integer allowManufacture;
	private Integer allowRecycler;
	private Integer anchored;
	private Integer anchorable;
	private Integer fittableNonSingleton;
	private Integer published;

	public int getGroupID() {
		return this.groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	public InventoryCategory getCategory() {
		return this.category;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIconID() {
		return this.iconID;
	}

	public void setIconID(Integer iconID) {
		this.iconID = iconID;
	}

	public Integer getUseBasePrice() {
		return this.useBasePrice;
	}

	public void setUseBasePrice(Integer useBasePrice) {
		this.useBasePrice = useBasePrice;
	}

	public Integer getAllowManufacture() {
		return this.allowManufacture;
	}

	public void setAllowManufacture(Integer allowManufacture) {
		this.allowManufacture = allowManufacture;
	}

	public Integer getAllowRecycler() {
		return this.allowRecycler;
	}

	public void setAllowRecycler(Integer allowRecycler) {
		this.allowRecycler = allowRecycler;
	}

	public Integer getAnchored() {
		return this.anchored;
	}

	public void setAnchored(Integer anchored) {
		this.anchored = anchored;
	}

	public Integer getAnchorable() {
		return this.anchorable;
	}

	public void setAnchorable(Integer anchorable) {
		this.anchorable = anchorable;
	}

	public Integer getFittableNonSingleton() {
		return this.fittableNonSingleton;
	}

	public void setFittableNonSingleton(Integer fittableNonSingleton) {
		this.fittableNonSingleton = fittableNonSingleton;
	}

	public Integer getPublished() {
		return this.published;
	}

	public void setPublished(Integer published) {
		this.published = published;
	}

	@Override
	public String toString() {
		return "InventoryGroup [groupID=" + this.groupID + ", category="
				+ this.category + ", groupName=" + this.groupName
				+ ", description=" + this.description + ", iconID="
				+ this.iconID + ", useBasePrice=" + this.useBasePrice
				+ ", allowManufacture=" + this.allowManufacture
				+ ", allowRecycler=" + this.allowRecycler + ", anchored="
				+ this.anchored + ", anchorable=" + this.anchorable
				+ ", fittableNonSingleton=" + this.fittableNonSingleton
				+ ", published=" + this.published + "]";
	}

	@Override
	public int hashCode() {
		return this.groupID;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof InventoryGroup
				&& this.groupID == ((InventoryGroup) obj).getGroupID();
	}
}
