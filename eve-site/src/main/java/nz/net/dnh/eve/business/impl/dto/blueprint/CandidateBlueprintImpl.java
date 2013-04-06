package nz.net.dnh.eve.business.impl.dto.blueprint;

import nz.net.dnh.eve.business.CandidateBlueprint;
import nz.net.dnh.eve.model.raw.InventoryBlueprintType;

public class CandidateBlueprintImpl implements CandidateBlueprint {

	private final InventoryBlueprintType inventoryBlueprint;

	public CandidateBlueprintImpl(InventoryBlueprintType inventoryBlueprint) {
		this.inventoryBlueprint = inventoryBlueprint;
	}

	@Override
	public int getId() {
		return this.inventoryBlueprint.getBlueprintTypeID();
	}

	@Override
	public String getName() {
		return this.inventoryBlueprint.getProductType().getTypeName();
	}

	@Override
	public String toString() {
		return "Candidate Blueprint (" + this.inventoryBlueprint + ")";
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof CandidateBlueprintImpl && this.inventoryBlueprint.equals(((CandidateBlueprintImpl) obj).inventoryBlueprint);
	}

	@Override
	public int hashCode() {
		return this.inventoryBlueprint.hashCode();
	}

}
