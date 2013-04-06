package nz.net.dnh.eve.business;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * Provides access to Types
 */
@Service
public interface TypeService {
	/**
	 * List the minerals in the system, optionally including minerals which are referenced by blueprints but missing from the database.
	 * 
	 * @param includeMissing Whether to include minerals that are missing from the database
	 * @return The list of minerals
	 * @see #listMissingTypes()
	 */
	public List<Mineral> listMinerals(boolean includeMissing);

	/**
	 * List the components in the system, optionally including components which are referenced by blueprints but missing from the database.
	 * 
	 * @param includeMissing Whether to include components that are missing from the database
	 * @return The list of components
	 * @see #listMissingTypes()
	 */
	public List<Component> listComponents(boolean includeMissing);

	/**
	 * List minerals and components which are referenced by blueprints but misisng from the database
	 * 
	 * @return The list of minerals and components referenced by blueprints but missing from the database
	 */
	public List<? extends AbstractType> listMissingTypes();

	/**
	 * List minerals and components which are referenced by the given blueprint but misisng from the database
	 * 
	 * @param The blueprint to check for missing references
	 * @return The list of minerals and components referenced by the given blueprint but missing from the database
	 */
	public List<? extends AbstractType> listMissingTypes(BlueprintReference blueprint);

	/**
	 * Get the minerals and components required to build the given blueprint,
	 * and their quantities.
	 * 
	 * @param blueprint
	 *            The blueprint to retrieve required types for
	 * @return The required minerals and components
	 */
	public RequiredTypes getRequiredTypes(BlueprintReference blueprint);

}
