package nz.net.dnh.eve.business;

/**
 * Represents a blueprint that does not currently exist in the database
 */
public interface CandidateBlueprint extends BlueprintReference {
	/**
	 * @return The name of the blueprint
	 */
	public String getName();
}
