package nz.net.dnh.eve.business;


/**
 * Marker interface for objects which can be used to look up a {@link Mineral}
 * or {@link Component}.
 * 
 * @see Mineral
 * @see Component
 * @see TypeIdReference
 */
public interface TypeReference {

	/**
	 * @return The id of the type
	 */
	public int getId();

}
