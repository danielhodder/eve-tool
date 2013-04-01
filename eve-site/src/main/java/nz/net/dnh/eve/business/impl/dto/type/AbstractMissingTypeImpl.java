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
		public MissingMineralImpl(InventoryType inventoryType) {
			super(inventoryType);
		}
	}

	public static class MissingComponentImpl extends AbstractMissingTypeImpl
			implements Component {
		public MissingComponentImpl(InventoryType inventoryType) {
			super(inventoryType);
		}
	}

	private final InventoryType inventoryType;

	public AbstractMissingTypeImpl(InventoryType inventoryType) {
		this.inventoryType = inventoryType;
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

}
