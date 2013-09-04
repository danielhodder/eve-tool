package nz.net.dnh.eve.business;

import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * The types required for a blueprint
 */
public class RequiredTypes {
	private final SortedMap<Component, Integer> requiredComponents;
	private final SortedMap<Mineral, Integer> requiredMinerals;

	public RequiredTypes(final SortedMap<Component, Integer> requiredComponents, final SortedMap<Mineral, Integer> requiredMinerals) {
		this.requiredComponents = Collections.unmodifiableSortedMap(requiredComponents);
		this.requiredMinerals = Collections.unmodifiableSortedMap(requiredMinerals);
	}

	/**
	 * Get the components required to build the blueprint, and their quantities.
	 * <p>
	 * Returns a map where the key is the required component, and the value is the number required
	 * 
	 * @return A map from required component to required units, ordered by name
	 */
	public SortedMap<Component, Integer> getRequiredComponents() {
		return this.requiredComponents;
	}

	/**
	 * Get the minerals required to build the blueprint, and their quantities.
	 * <p>
	 * Returns a map where the key is the required mineral, and the value is the number required
	 * 
	 * @return A map from required mineral to required units, ordered by name
	 */
	public SortedMap<Mineral, Integer> getRequiredMinerals() {
		return this.requiredMinerals;
	}

	/**
	 * Gets the required minerals and components required to build a blueprint, and their quantities
	 * <p>
	 * Returns a map where the key is the required type, and the value if the number required
	 * 
	 * @return A map from required types to required units, ordered by name
	 */
	public SortedMap<? extends AbstractType, Integer> getAllRequiredTypes() {
		final SortedMap<AbstractType, Integer> allTypes = new TreeMap<>();
		
		allTypes.putAll(this.requiredComponents);
		allTypes.putAll(this.requiredMinerals);
		
		return allTypes;
	}
}
