package nz.net.dnh.eve.business.impl.dto.type;

import java.math.BigDecimal;
import java.util.Date;

import nz.net.dnh.eve.business.AbstractType;
import nz.net.dnh.eve.business.Component;
import nz.net.dnh.eve.business.Mineral;
import nz.net.dnh.eve.model.raw.InventoryType;

public abstract class AbstractMissingTypeImpl implements AbstractType {
	public static class MissingMineralImpl extends AbstractMissingTypeImpl
			implements Mineral {
		public MissingMineralImpl(final InventoryType inventoryType) {
			super(inventoryType);
		}

		@Override
		public String toString() {
			return "Mineral " + super.toString();
		}
	}

	public static class MissingComponentImpl extends AbstractMissingTypeImpl
			implements Component {
		public MissingComponentImpl(final InventoryType inventoryType) {
			super(inventoryType);
		}

		@Override
		public String toString() {
			return "Component " + super.toString();
		}
	}

	private final InventoryType inventoryType;

	public AbstractMissingTypeImpl(final InventoryType inventoryType) {
		this.inventoryType = inventoryType;
	}

	public InventoryType toInventoryType() {
		return this.inventoryType;
	}

	@Override
	public int getId() {
		return this.inventoryType.getTypeID();
	}

	@Override
	public String getName() {
		return this.inventoryType.getTypeName();
	}

	@Override
	public BigDecimal getCost() {
		return null;
	}

	@Override
	public Date getCostLastUpdated() {
		return null;
	}

	@Override
	public boolean isMissing() {
		return true;
	}

	@Override
	public String toString() {
		return "(Missing) [name=" + getName() + "]";
	}

	@Override
	public int hashCode() {
		return this.inventoryType.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof AbstractMissingTypeImpl
				&& this.inventoryType
						.equals(((AbstractMissingTypeImpl) obj).inventoryType);
	}

}
