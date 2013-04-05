package nz.net.dnh.eve.model.repository;

import java.util.List;

import nz.net.dnh.eve.model.domain.Blueprint;
import nz.net.dnh.eve.model.raw.InventoryType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface InventoryTypeRepository extends
		JpaRepository<InventoryType, Integer> {
	/**
	 * List the minerals that are required for the given blueprint but not present
	 * in our system.
	 * 
	 * @param blueprint
	 *            The blueprint to retrieve unknown minerals for
	 * @return The list of raw minerals required by the blueprint that are not
	 *         known to our system
	 */
	public List<InventoryType> findUnknownMineralsForBlueprint(
			@Param("blueprint") Blueprint blueprint);

	/**
	 * List the minerals that are required by any blueprint but not present in our
	 * system.
	 * 
	 * @return The list of raw minerals required by any blueprint that are not
	 *         known to our system
	 */
	public List<InventoryType> findUnknownMinerals();
	/**
	 * List the components that are required for the given blueprint but not present
	 * in our system.
	 * 
	 * @param blueprint
	 *            The blueprint to retrieve unknown components for
	 * @return The list of raw components required by the blueprint that are not
	 *         known to our system
	 */
	public List<InventoryType> findUnknownComponentsForBlueprint(
			@Param("blueprint") Blueprint blueprint);

	/**
	 * List the components that are required by any blueprint but not present in our
	 * system.
	 * 
	 * @return The list of raw components required by any blueprint that are not
	 *         known to our system
	 */
	public List<InventoryType> findUnknownComponents();
}
