package nz.net.dnh.eve.business.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;

import nz.net.dnh.eve.model.domain.Blueprint;
import nz.net.dnh.eve.model.domain.BlueprintRequiredType;
import nz.net.dnh.eve.model.domain.BlueprintRequiredType.BlueprintRequiredTypeId;
import nz.net.dnh.eve.model.domain.Type;
import nz.net.dnh.eve.model.raw.InventoryBlueprintType;
import nz.net.dnh.eve.model.raw.InventoryType;

import org.junit.Before;

/** Helper methods and fields for dealing with types */
public abstract class AbstractTypesTest {

	protected static final Timestamp LAST_UPDATED_2 = new Timestamp(11111111);
	protected static final Timestamp LAST_UPDATED_1 = new Timestamp(1000);
	protected static final BigDecimal COST_2 = new BigDecimal(40);
	protected static final BigDecimal COST_1 = new BigDecimal(15);

	protected static BlueprintRequiredType createRequiredType(final Blueprint b, final Type type, final InventoryType inventoryType,
			final int units, final boolean decomposed, final InventoryBlueprintType materialBlueprintType, final Blueprint materialBlueprint) {
		final BlueprintRequiredType requiredType = new BlueprintRequiredType();
		requiredType.setId(new BlueprintRequiredTypeId(b, inventoryType));
		requiredType.setType(type);
		requiredType.setUnits(units);
		requiredType.setDecomposed(decomposed);
		requiredType.setMaterialBlueprintType(materialBlueprintType);
		requiredType.setMaterialBlueprint(materialBlueprint);
		return requiredType;
	}

	protected final Type type1 = mock(Type.class);
	protected final Type type2 = mock(Type.class);
	protected final InventoryType component1 = mock(InventoryType.class);
	protected final InventoryType component2 = mock(InventoryType.class);
	protected final InventoryType mineral1 = mock(InventoryType.class);
	protected final InventoryType mineral2 = mock(InventoryType.class);

	@Before
	public void setup() {
		when(this.type1.getTypeID()).thenReturn(1);
		when(this.type2.getTypeID()).thenReturn(2);
		when(this.type1.getTypeName()).thenReturn("Type 1");
		when(this.type2.getTypeName()).thenReturn("Type 2");
		when(this.type1.getCost()).thenReturn(COST_1);
		when(this.type2.getCost()).thenReturn(COST_2);
		when(this.type1.getLastUpdated()).thenReturn(LAST_UPDATED_1);
		when(this.type2.getLastUpdated()).thenReturn(LAST_UPDATED_2);
		when(this.component1.getTypeID()).thenReturn(11);
		when(this.component1.getTypeName()).thenReturn("Component 1");
		when(this.component1.isMineral()).thenReturn(false);
		when(this.component2.getTypeID()).thenReturn(13);
		when(this.component2.getTypeName()).thenReturn("Component 2");
		when(this.component2.isMineral()).thenReturn(false);
		when(this.mineral1.getTypeID()).thenReturn(12);
		when(this.mineral1.getTypeName()).thenReturn("Mineral 1");
		when(this.mineral1.isMineral()).thenReturn(true);
		when(this.mineral2.getTypeID()).thenReturn(14);
		when(this.mineral2.getTypeName()).thenReturn("Mineral 2");
		when(this.mineral2.isMineral()).thenReturn(true);
	}

}
