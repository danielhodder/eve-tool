package nz.net.dnh.eve.business.impl.dto.type;

import java.math.BigDecimal;
import java.util.Date;

import nz.net.dnh.eve.business.AbstractType;
import nz.net.dnh.eve.business.Component;
import nz.net.dnh.eve.business.Mineral;
import nz.net.dnh.eve.model.domain.Type;

public abstract class AbstractTypeImpl implements AbstractType {

	public static class MineralImpl extends AbstractTypeImpl implements Mineral {
		public MineralImpl(Type type) {
			super(type);
		}
	}

	public static class ComponentImpl extends AbstractTypeImpl implements
			Component {
		public ComponentImpl(Type type) {
			super(type);
		}
	}

	private final Type type;

	protected AbstractTypeImpl(Type type) {
		this.type = type;
	}

	@Override
	public String getName() {
		return this.type.getTypeName();
	}

	@Override
	public BigDecimal getCost() {
		return this.type.getCost();
	}

	@Override
	public Date getCostLastUpdated() {
		return this.type.getLastUpdated();
	}

}
