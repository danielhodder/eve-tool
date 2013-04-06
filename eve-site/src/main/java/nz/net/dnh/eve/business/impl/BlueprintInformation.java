package nz.net.dnh.eve.business.impl;


public interface BlueprintInformation {
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
}
