package nz.net.dnh.eve.business.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import nz.net.dnh.eve.business.AbstractType;
import nz.net.dnh.eve.business.BlueprintIdReference;
import nz.net.dnh.eve.business.BlueprintReference;
import nz.net.dnh.eve.business.Component;
import nz.net.dnh.eve.business.Mineral;
import nz.net.dnh.eve.model.domain.Blueprint;
import nz.net.dnh.eve.model.domain.Type;
import nz.net.dnh.eve.model.raw.InventoryType;
import nz.net.dnh.eve.model.repository.InventoryTypeRepository;
import nz.net.dnh.eve.model.repository.TypeRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TypeServiceTest {
	private static final Timestamp LAST_UPDATED_2 = new Timestamp(11111111);
	private static final Timestamp LAST_UPDATED_1 = new Timestamp(1000);
	private static final BigDecimal COST_2 = new BigDecimal(40);
	private static final BigDecimal COST_1 = new BigDecimal(15);
	private static final Type TYPE_1 = mock(Type.class);
	private static final Type TYPE_2 = mock(Type.class);
	private static final InventoryType COMPONENT_1 = mock(InventoryType.class);
	private static final InventoryType MINERAL_1 = mock(InventoryType.class);

	static {
		when(TYPE_1.getTypeName()).thenReturn("Type 1");
		when(TYPE_2.getTypeName()).thenReturn("Type 2");
		when(TYPE_1.getCost()).thenReturn(COST_1);
		when(TYPE_2.getCost()).thenReturn(COST_2);
		when(TYPE_1.getLastUpdated()).thenReturn(LAST_UPDATED_1);
		when(TYPE_2.getLastUpdated()).thenReturn(LAST_UPDATED_2);
		when(COMPONENT_1.getTypeName()).thenReturn("Component 1");
		when(COMPONENT_1.isMineral()).thenReturn(false);
		when(MINERAL_1.getTypeName()).thenReturn("Mineral 1");
		when(MINERAL_1.isMineral()).thenReturn(true);
	}

	@InjectMocks
	private final TypeServiceImpl typeService = new TypeServiceImpl();

	@Mock
	private TypeRepository typeRepository;

	@Mock
	private InventoryTypeRepository inventoryTypeRepository;

	@Mock
	private BlueprintResolverService blueprintResolverService;

	private static void assertComponent(AbstractType type, String name, BigDecimal cost, Date lastUpdated, boolean missing) {
		assertTrue(type instanceof Component);
		assertType(type, name, cost, lastUpdated, missing);
	}

	private static void assertMineral(AbstractType type, String name, BigDecimal cost, Date lastUpdated, boolean missing) {
		assertTrue(type instanceof Mineral);
		assertType(type, name, cost, lastUpdated, missing);
	}

	private static void assertType(AbstractType type, String name, BigDecimal cost, Date lastUpdated, boolean missing) {
		assertEquals(name, type.getName());
		assertEquals(cost, type.getCost());
		assertEquals(lastUpdated, type.getCostLastUpdated());
		assertEquals(missing, type.isMissing());
	}

	@Test
	public void listComponents() {
		when(this.typeRepository.findAllComponents()).thenReturn(
				Arrays.asList(TYPE_1, TYPE_2));
		when(this.inventoryTypeRepository.findUnknownComponents()).thenReturn(Arrays.asList(COMPONENT_1));

		List<Component> components = this.typeService.listComponents(false);

		assertEquals(2, components.size());
		assertComponent(components.get(0), "Type 1", COST_1, LAST_UPDATED_1, false);
		assertComponent(components.get(1), "Type 2", COST_2, LAST_UPDATED_2, false);

		components = this.typeService.listComponents(true);

		assertEquals(3, components.size());
		assertComponent(components.get(0), "Type 1", COST_1, LAST_UPDATED_1, false);
		assertComponent(components.get(1), "Type 2", COST_2, LAST_UPDATED_2, false);
		assertComponent(components.get(2), "Component 1", null, null, true);
	}

	@Test
	public void listMinerals() {
		when(this.typeRepository.findAllMinerals()).thenReturn(
				Arrays.asList(TYPE_2, TYPE_1));
		when(this.inventoryTypeRepository.findUnknownMinerals()).thenReturn(Arrays.asList(MINERAL_1));

		List<Mineral> minerals = this.typeService.listMinerals(false);

		assertEquals(2, minerals.size());
		assertMineral(minerals.get(0), "Type 2", COST_2, LAST_UPDATED_2, false);
		assertMineral(minerals.get(1), "Type 1", COST_1, LAST_UPDATED_1, false);

		minerals = this.typeService.listMinerals(true);

		assertEquals(3, minerals.size());
		assertMineral(minerals.get(0), "Type 2", COST_2, LAST_UPDATED_2, false);
		assertMineral(minerals.get(1), "Type 1", COST_1, LAST_UPDATED_1, false);
		assertMineral(minerals.get(2), "Mineral 1", null, null, true);
	}

	@Test
	public void listMissingTypes() {
		when(this.inventoryTypeRepository.findUnknownComponents()).thenReturn(Arrays.asList(COMPONENT_1));
		when(this.inventoryTypeRepository.findUnknownMinerals()).thenReturn(Arrays.asList(MINERAL_1));

		List<? extends AbstractType> types = this.typeService.listMissingTypes();

		assertEquals(2, types.size());
		sortTypesByName(types);
		assertComponent(types.get(0), "Component 1", null, null, true);
		assertMineral(types.get(1), "Mineral 1", null, null, true);
	}

	private static void sortTypesByName(List<? extends AbstractType> types) {
		Collections.sort(types, new Comparator<AbstractType>() {

			@Override
			public int compare(AbstractType o1, AbstractType o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
	}

	@Test
	public void listMissingTypesForBlueprint() {
		BlueprintReference blueprintReference = new BlueprintIdReference(4);
		Blueprint b = mock(Blueprint.class);
		when(this.blueprintResolverService.toBlueprint(blueprintReference)).thenReturn(b);
		when(this.inventoryTypeRepository.findUnknownComponentsForBlueprint(b)).thenReturn(Arrays.asList(COMPONENT_1));
		when(this.inventoryTypeRepository.findUnknownMineralsForBlueprint(b)).thenReturn(Arrays.asList(MINERAL_1));

		List<? extends AbstractType> types = this.typeService.listMissingTypes(blueprintReference);

		assertEquals(2, types.size());
		sortTypesByName(types);
		assertComponent(types.get(0), "Component 1", null, null, true);
		assertMineral(types.get(1), "Mineral 1", null, null, true);
	}
}
