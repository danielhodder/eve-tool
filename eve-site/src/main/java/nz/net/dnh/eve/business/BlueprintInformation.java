package nz.net.dnh.eve.business;


public interface BlueprintInformation extends BlueprintReference {
	/**
	 * @return The name of the blueprint
	 */
	public String getName();

	/**
	 * Get the ID of the thing this blueprint produces
	 *
	 * @return
	 */
	public int getProducedTypeID();

	/**
	 * @return The number of things produced by this blueprint
	 */
	public int getProducedQuantity();
}
