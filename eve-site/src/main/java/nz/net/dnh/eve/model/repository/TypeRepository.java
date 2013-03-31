package nz.net.dnh.eve.model.repository;

import nz.net.dnh.eve.model.domain.Type;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Retrieves {@link Type}s
 */
public interface TypeRepository extends JpaRepository<Type, Integer> {

}