package nz.net.dnh.eve.model.repository;

import nz.net.dnh.eve.model.domain.Blueprint;

public interface BlueprintRepositoryCustom {
	/**
	 * Associates the given blueprint object with the current session so lazy
	 * collections work
	 * 
	 * @param blueprint
	 *            The blueprint potentially associated with another session
	 * @return The blueprint object passed in
	 */
	Blueprint refresh(Blueprint blueprint);
}
