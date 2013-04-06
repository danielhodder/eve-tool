package nz.net.dnh.eve.business;

/**
 * A simple reference that can be used to lookup blueprints by their ID.
 * <p>
 * Note that this should only be used if you haven't got another object you
 * could use to lookup the blueprint, such as a BlueprintSummary.
 */
public class BlueprintIdReference implements BlueprintReference {
	private final int id;

	/**
	 * Create a new simple reference to a blueprint with the given id
	 * 
	 * @param id
	 *            The ID of the blueprint
	 */
	public BlueprintIdReference(int id) {
		this.id = id;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Blueprint ID (" + this.id + ")";
	}

}
