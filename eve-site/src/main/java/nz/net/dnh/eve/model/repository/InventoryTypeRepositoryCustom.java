package nz.net.dnh.eve.model.repository;

import java.util.List;

import nz.net.dnh.eve.model.raw.InventoryType;

public interface InventoryTypeRepositoryCustom {
	/**
	 * List the minerals that are required by any blueprint but not present in our
	 * system.
	 * 
	 * @return The list of raw minerals required by any blueprint that are not
	 *         known to our system
	 */
	public List<InventoryType> findUnknownMinerals();
}
