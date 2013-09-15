package nz.net.dnh.eve.business.impl.dependencies;

import nz.net.dnh.eve.model.domain.Blueprint;

//TODO refactor this to be above BlueprintNode?
public class RootBlueprintNode extends BlueprintNode {

	public RootBlueprintNode(final Blueprint blueprint) {
		super(blueprint, null, null);
	}

	@Override
	protected int getBlueprintCount(final BlueprintDependencyState state) {
		return 1;
	}
}
