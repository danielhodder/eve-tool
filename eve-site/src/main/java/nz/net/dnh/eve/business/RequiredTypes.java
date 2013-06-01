package nz.net.dnh.eve.business;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The types required for a blueprint
 */
public class RequiredTypes {
	private final Map<Component, Integer> requiredComponents;
	private final Map<Mineral, Integer> requiredMinerals;

	public RequiredTypes(final Map<Component, Integer> requiredComponents, final Map<Mineral, Integer> requiredMinerals) {
		this.requiredComponents = Collections.unmodifiableMap(requiredComponents);
		this.requiredMinerals = Collections.unmodifiableMap(requiredMinerals);
	}

	/**
	 * Get the components required to build the blueprint, and their quantities.
	 * <p>
	 * Returns a map where the key is the required component, and the value is
	 * the number required
	 * 
	 * @return A map from required component to required units
	 */
	public Map<Component, Integer> getRequiredComponents() {
		return this.requiredComponents;
	}

	/**
	 * Get the minerals required to build the blueprint, and their quantities.
	 * <p>
	 * Returns a map where the key is the required mineral, and the value is the
	 * number required
	 * 
	 * @return A map from required mineral to required units
	 */
	public Map<Mineral, Integer> getRequiredMinerals() {
		return this.requiredMinerals;
	}
	
	/**
	 * Gets the required minerals and components required to build a blueprint, and their
	 * quantities
	 * <p>
	 * Returns a map where the key is the required type, and the value if the number required
	 * 
	 *  @return A map from required types to required units
	 * 
	 * @return
	 */
	public Map<? extends AbstractType, Integer> getAllRequiredTypes() {
		Map<AbstractType, Integer> allTypes = new HashMap<>();
		
		allTypes.putAll(requiredComponents);
		allTypes.putAll(requiredMinerals);
		
		return allTypes;
	}
}
