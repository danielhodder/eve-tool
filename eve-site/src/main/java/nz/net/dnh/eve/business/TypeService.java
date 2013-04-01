package nz.net.dnh.eve.business;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * Provides access to Types
 */
@Service
public interface TypeService {
	public List<Mineral> listMinerals();

	public List<Component> listComponents();

	public List<? extends AbstractType> listMissingTypes();

	public List<? extends AbstractType> listMissingTypes(BlueprintReference blueprint);
}
