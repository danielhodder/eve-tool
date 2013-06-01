package nz.net.dnh.eve.model.repository;

import nz.net.dnh.eve.model.domain.Blueprint;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Retrieves {@link Blueprint}s 
 */
public interface BlueprintRepository extends JpaRepository<Blueprint, Integer>, BlueprintRepositoryCustom {
}
