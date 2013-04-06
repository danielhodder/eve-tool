package nz.net.dnh.eve.business.impl;

import nz.net.dnh.eve.business.BlueprintReference;
import nz.net.dnh.eve.model.domain.Blueprint;

/**
 * Internal interface to share the implementation of resolving blueprint
 * references
 */
interface BlueprintResolverService {

	/**
	 * Resolve a BlueprintReference back to the Blueprint object it represents
	 * 
	 * @param blueprintReference
	 *            The BlueprintReference to resolve
	 * @return The Blueprint model object
	 */
	public Blueprint toBlueprint(BlueprintReference blueprintReference);
}
