package nz.net.dnh.eve.model.repository;

import java.util.List;

import nz.net.dnh.eve.model.domain.Blueprint;
import nz.net.dnh.eve.model.raw.InventoryType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface InventoryTypeRepository extends
		JpaRepository<InventoryType, Integer> {
	/**
	 * List the types that are required for the given blueprint but not present
	 * in our system.
	 * 
	 * @param blueprint
	 *            The blueprint to retrieve unknown types for
	 * @return The list of raw types required by the blueprint that are not
	 *         known to our system
	 */
	public List<InventoryType> findUnknownTypesForBlueprint(
			@Param("blueprint") Blueprint blueprint);

	/**
	 * List the types that are required by any blueprint but not present in our
	 * system.
	 * 
	 * @return The list of raw types required by any blueprint that are not
	 *         known to our system
	 */
	public List<InventoryType> findUnknownTypes();
}
