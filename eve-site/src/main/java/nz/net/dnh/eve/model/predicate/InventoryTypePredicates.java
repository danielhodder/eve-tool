package nz.net.dnh.eve.model.predicate;

import nz.net.dnh.eve.model.raw.InventoryGroup;
import nz.net.dnh.eve.model.raw.QInventoryGroup;

import com.mysema.query.types.Predicate;

public final class InventoryTypePredicates {
	public static Predicate isMineral() {
		QInventoryGroup inventoryGroup = QInventoryGroup.inventoryGroup;
		
		return inventoryGroup.groupName.eq(InventoryGroup.MINERAL_GROUP);
	}
	
	public static Predicate isComponent() {
		QInventoryGroup inventoryGroup = QInventoryGroup.inventoryGroup;
		
		return inventoryGroup.groupName.ne(InventoryGroup.MINERAL_GROUP);
	}
}
