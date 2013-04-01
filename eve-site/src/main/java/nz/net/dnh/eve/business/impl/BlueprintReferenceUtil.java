package nz.net.dnh.eve.business.impl;

import nz.net.dnh.eve.business.BlueprintReference;
import nz.net.dnh.eve.business.impl.dto.blueprint.BlueprintSummaryImpl;
import nz.net.dnh.eve.model.domain.Blueprint;

class BlueprintReferenceUtil {
	private BlueprintReferenceUtil() {
	}

	/**
	 * Resolve a BlueprintReference back to the Blueprint object it represents
	 * 
	 * @param blueprintReference
	 *            The BlueprintReference to resolve
	 * @return The Blueprint model object
	 */
	public static Blueprint toBlueprint(BlueprintReference blueprintReference) {
		if (blueprintReference instanceof BlueprintSummaryImpl) {
			return ((BlueprintSummaryImpl) blueprintReference).toBlueprint();
		}
		throw new IllegalArgumentException(
				"BlueprintReference must be a type returned from a business service");
	}
}
