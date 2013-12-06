/**
 * 
 */
package nz.net.dnh.eve.business;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

/**
 * This is a collection of blueprints and other information requires to use the {@link ShoppingCartService}
 * 
 * @author Daniel Hodder (danielh)
 * 
 */
public class BlueprintCollection {
	private final Map<BlueprintReference, Integer> blueprintsAndQuantities = new HashMap<>();

	/**
	 * No arg constructor that initilizes an empty collection
	 */
	public BlueprintCollection() {
	}

	/**
	 * Creates a blueprint collection with one blueprint populated.
	 * 
	 * @param blueprint
	 *            The blueprint to make
	 * @param runs
	 *            How many times to run the blueprint
	 * @see #addBlueprint(BlueprintReference, int)
	 */
	public BlueprintCollection(final BlueprintReference blueprint, final int runs) {
		Assert.isTrue(runs > 0, "Must make at least one run of a blueprint");

	}

	/**
	 * Add a blueprint to the collection
	 * 
	 * @param blueprint
	 *            The blueprint to make
	 * @param runs
	 *            The number of times to run the blueprint
	 * @return The collection for method chaining
	 */
	public BlueprintCollection addBlueprint(final BlueprintReference blueprint, final int runs) {
		this.blueprintsAndQuantities.put(blueprint, runs);

		return this;
	}
}
