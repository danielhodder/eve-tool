package nz.net.dnh.eve.business.impl.dto.type;

import java.math.BigDecimal;
import java.util.Date;

import nz.net.dnh.eve.business.AbstractType;
import nz.net.dnh.eve.business.Component;
import nz.net.dnh.eve.business.Mineral;
import nz.net.dnh.eve.model.domain.Type;

public abstract class AbstractTypeImpl implements AbstractType {

	public static class MineralImpl extends AbstractTypeImpl implements Mineral {
		public MineralImpl(final Type type) {
			super(type);
		}

		@Override
		public String toString() {
			return "Mineral " + super.toString();
		}
	}

	public static class ComponentImpl extends AbstractTypeImpl implements
			Component {
		public ComponentImpl(final Type type) {
			super(type);
		}

		@Override
		public String toString() {
			return "Component " + super.toString();
		}
	}

	private final Type type;

	protected AbstractTypeImpl(final Type type) {
		this.type = type;
	}

	@Override
	public long getId() {
		return this.type.getTypeID();
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

	@Override
	public boolean isMissing() {
		return false;
	}

	@Override
	public String toString() {
		return "[name=" + getName() + ", cost=" + getCost()
				+ ", costLastUpdated=" + getCostLastUpdated() + "]";
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof AbstractTypeImpl
				&& this.type.equals(((AbstractTypeImpl) obj).type);
	}

	@Override
	public int hashCode() {
		return this.type.hashCode();
	}
}
