package nz.net.dnh.eve.business.impl.dto.type;

import nz.net.dnh.eve.business.AbstractType;

public abstract class AbstractSortableTypeImpl implements AbstractType {
	@Override
	public int compareTo(final AbstractType o) {
		return getName().compareTo(o.getName());
	}
}
