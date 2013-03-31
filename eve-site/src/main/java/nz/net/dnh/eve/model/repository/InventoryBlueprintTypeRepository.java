package nz.net.dnh.eve.model.repository;

import nz.net.dnh.eve.model.raw.InventoryBlueprintType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryBlueprintTypeRepository extends
		JpaRepository<InventoryBlueprintType, Integer> {

	// TODO figure out what the right way to do paging and named queries is to
	// remove the warning
	public Page<InventoryBlueprintType> findUnknownBlueprints(Pageable pageable);
}
