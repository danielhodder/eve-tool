package nz.net.dnh.eve.model.repository;

import nz.net.dnh.eve.model.raw.InventoryBlueprintType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

// TODO figure out what the right way to do paging and named queries is to
// remove the warnings
public interface InventoryBlueprintTypeRepository extends
		JpaRepository<InventoryBlueprintType, Integer> {

	public Page<InventoryBlueprintType> findUnknownBlueprints(final Pageable pageable);

	public Page<InventoryBlueprintType> findUnknownBlueprintsBySearch(@Param("search") final String search, final Pageable pageable);
}
