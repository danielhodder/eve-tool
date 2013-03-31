package nz.net.dnh.eve.model.raw;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "invCategories")
public class InventoryCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	private int categoryID;

	@Max(100)
	private String categoryName;

	@Max(3000)
	private String description;

	private Integer iconID;

	// The columnDefinition attribute is required here or hibernate thinks the
	// column should be a 'bit'
	@Column(columnDefinition = "INT")
	private boolean published;

	public int getCategoryID() {
		return this.categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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

	public boolean getPublished() {
		return this.published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	@Override
	public String toString() {
		return "InventoryCategory [categoryID=" + this.categoryID
				+ ", categoryName=" + this.categoryName + ", description="
				+ this.description + ", iconID=" + this.iconID + ", published="
				+ this.published + "]";
	}

	@Override
	public int hashCode() {
		return this.categoryID;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof InventoryCategory
				&& this.categoryID == ((InventoryCategory) obj).getCategoryID();
	}
}
