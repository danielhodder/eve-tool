package nz.net.dnh.eve.business;

import java.util.List;
import java.util.SortedMap;

public class RequiredTypes {
	private final List<RequiredType<? extends AbstractType>> requiredTypesTree;
	private final SortedMap<? extends AbstractType, Integer> resolvedRequiredTypes;

	public RequiredTypes(final List<RequiredType<? extends AbstractType>> requiredTypesTree,
			final SortedMap<? extends AbstractType, Integer> resolvedRequiredTypes) {
		this.requiredTypesTree = requiredTypesTree;
		this.resolvedRequiredTypes = resolvedRequiredTypes;
	}

	public List<RequiredType<? extends AbstractType>> getRequiredTypesTree() {
		return this.requiredTypesTree;
	}

	// TODO javadoc and type??
	public SortedMap<? extends AbstractType, Integer> getResolvedRequiredTypes() {
		return this.resolvedRequiredTypes;
	}
}
