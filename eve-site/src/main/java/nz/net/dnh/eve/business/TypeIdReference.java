package nz.net.dnh.eve.business;

/**
 * A simple reference that can be used to lookup types by their ID.
 * <p>
 * Note that this should only be used if you haven't got another object you
 * could use to lookup the type, such as a {@link Mineral} or {@link Component}.
 */
public class TypeIdReference implements TypeReference {
	private final int id;

	/**
	 * Create a new simple reference to a type with the given id
	 * 
	 * @param id
	 *            The ID of the type
	 */
	public TypeIdReference(final int id) {
		this.id = id;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Type ID (" + this.id + ")";
	}

}
