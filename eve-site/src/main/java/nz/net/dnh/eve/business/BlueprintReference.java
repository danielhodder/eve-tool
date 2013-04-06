package nz.net.dnh.eve.business;


/**
 * Marker interface for objects which can be used to look up a Blueprint.
 * 
 * @see BlueprintSummary
 * @see BlueprintIdReference
 */
public interface BlueprintReference {
	/**
	 * @return The ID of the blueprint
	 */
	public int getId();
}
