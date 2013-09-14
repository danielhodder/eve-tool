package nz.net.dnh.eve.business;

import java.util.List;

/** TODO javadoc*/
public class RequiredType<T extends AbstractType> implements Comparable<RequiredType<T>> {
	public static enum DecompositionState {
		/** The blueprint exists, and this required type should be replaced with it */
		DECOMPOSED,
		/** The blueprint exists, but this required type should <b>not</b> be replaced with it */
		NOT_DECOMPOSED,
		/**
		 * A blueprint for this required type exists in EVE Online, but has not been added to the list of blueprints this tool knows about.
		 * <p>
		 * If the blueprint was added to the system, it would be possible to replace this required type with a blueprint
		 */
		NOT_CONFIGURED,
		/** It is never possible to replace this required type with a blueprint, because no blueprint for this type exists in EVE Online */
		NEVER_DECOMPOSED
	}

	private final T type;
	private final int units;
	private final BlueprintSummary typeBlueprint;
	private final DecompositionState decompositionState;
	private final List<RequiredType<? extends AbstractType>> typeBlueprintRequiredTypes;

	public RequiredType(final T type, final int units, final BlueprintSummary typeBlueprint, final DecompositionState decompositionState,
			final List<RequiredType<? extends AbstractType>> typeBlueprintRequiredTypes) {
		this.type = type;
		this.units = units;
		this.typeBlueprint = typeBlueprint;
		this.decompositionState = decompositionState;
		this.typeBlueprintRequiredTypes = typeBlueprintRequiredTypes;
	}

	public T getType() {
		return this.type;
	}

	// TODO doc - this is multiplied by the units of the parent required type
	public int getUnits() {
		return this.units;
	}

	public DecompositionState getDecompositionState() {
		return this.decompositionState;
	}

	public List<RequiredType<? extends AbstractType>> getTypeBlueprintRequiredTypes() {
		return this.typeBlueprintRequiredTypes;
	}

	/**
	 * Get the blueprint which should be used to create this required type if {@link #isDecomposed()} is true.
	 * 
	 * @return The blueprint which can create this required type, or null if no blueprint creates the required type
	 */
	public BlueprintSummary getTypeBlueprint() {
		return this.typeBlueprint;
	}

	@Override
	public int compareTo(final RequiredType<T> o) {
		return this.type.compareTo(o.type);
	}
}