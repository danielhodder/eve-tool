package nz.net.dnh.eve.business.impl;

import static nz.net.dnh.eve.HelpingMatchers.contains;
import static nz.net.dnh.eve.HelpingMatchers.containsInAnyOrder;
import static nz.net.dnh.eve.HelpingMatchers.hasEntry;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import nz.net.dnh.eve.business.AbstractType;
import nz.net.dnh.eve.business.BlueprintIdReference;
import nz.net.dnh.eve.business.BlueprintReference;
import nz.net.dnh.eve.business.Component;
import nz.net.dnh.eve.business.Mineral;
import nz.net.dnh.eve.business.RequiredTypes;
import nz.net.dnh.eve.model.domain.Blueprint;
import nz.net.dnh.eve.model.domain.BlueprintRequiredType;
import nz.net.dnh.eve.model.domain.Type;
import nz.net.dnh.eve.model.raw.InventoryType;
import nz.net.dnh.eve.model.repository.InventoryTypeRepository;
import nz.net.dnh.eve.model.repository.TypeRepository;

import org.hamcrest.Matcher;
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
	private static final InventoryType COMPONENT_2 = mock(InventoryType.class);
	private static final InventoryType MINERAL_1 = mock(InventoryType.class);
	private static final InventoryType MINERAL_2 = mock(InventoryType.class);

	static {
		when(TYPE_1.getTypeID()).thenReturn(1);
		when(TYPE_2.getTypeID()).thenReturn(2);
		when(TYPE_1.getTypeName()).thenReturn("Type 1");
		when(TYPE_2.getTypeName()).thenReturn("Type 2");
		when(TYPE_1.getCost()).thenReturn(COST_1);
		when(TYPE_2.getCost()).thenReturn(COST_2);
		when(TYPE_1.getLastUpdated()).thenReturn(LAST_UPDATED_1);
		when(TYPE_2.getLastUpdated()).thenReturn(LAST_UPDATED_2);
		when(COMPONENT_1.getTypeID()).thenReturn(11);
		when(COMPONENT_1.getTypeName()).thenReturn("Component 1");
		when(COMPONENT_1.isMineral()).thenReturn(false);
		when(COMPONENT_2.getTypeID()).thenReturn(13);
		when(COMPONENT_2.getTypeName()).thenReturn("Component 2");
		when(COMPONENT_2.isMineral()).thenReturn(false);
		when(MINERAL_1.getTypeID()).thenReturn(12);
		when(MINERAL_1.getTypeName()).thenReturn("Mineral 1");
		when(MINERAL_1.isMineral()).thenReturn(true);
		when(MINERAL_2.getTypeID()).thenReturn(14);
		when(MINERAL_2.getTypeName()).thenReturn("Mineral 2");
		when(MINERAL_2.isMineral()).thenReturn(true);
	}

	@InjectMocks
	private final TypeServiceImpl typeService = new TypeServiceImpl();

	@Mock
	private TypeRepository typeRepository;

	@Mock
	private InventoryTypeRepository inventoryTypeRepository;

	@Mock
	private BlueprintResolverService blueprintResolverService;

	private static Matcher<AbstractType> component(final String name, final BigDecimal cost, final Date lastUpdated,
			final boolean missing,
			final int id) {
		return allOf(instanceOf(Component.class), type(name, cost, lastUpdated, missing, id));
	}

	private static Matcher<AbstractType> mineral(final String name, final BigDecimal cost, final Date lastUpdated, final boolean missing,
			final int id) {
		return allOf(instanceOf(Mineral.class), type(name, cost, lastUpdated, missing, id));
	}

	private static Matcher<AbstractType> type(final String name, final BigDecimal cost, final Date lastUpdated, final boolean missing,
			final int id) {
		return allOf(hasProperty("id", equalTo(id)), hasProperty("name", equalTo(name)), hasProperty("cost", equalTo(cost)),
				hasProperty("costLastUpdated", equalTo(lastUpdated)), hasProperty("missing", equalTo(missing)));
	}

	private static BlueprintRequiredType createRequiredType(final Blueprint b, final Type type, final InventoryType inventoryType,
			final int units) {
		final BlueprintRequiredType requiredType = new BlueprintRequiredType() {
			private static final long serialVersionUID = 1L;

			@Override
			public InventoryType getInventoryType() {
				return inventoryType;
			}
		};
		requiredType.setBlueprint(b);
		requiredType.setType(type);
		requiredType.setUnits(units);
		return requiredType;
	}

	@Test
	public void listComponents() {
		when(this.typeRepository.findAllComponents()).thenReturn(
				Arrays.asList(TYPE_1, TYPE_2));
		when(this.inventoryTypeRepository.findUnknownComponents()).thenReturn(Arrays.asList(COMPONENT_1));

		List<Component> components = this.typeService.listComponents(false);

		assertThat(components,
				contains(component("Type 1", COST_1, LAST_UPDATED_1, false, 1), component("Type 2", COST_2, LAST_UPDATED_2, false, 2)));

		components = this.typeService.listComponents(true);

		assertThat(
				components,
				contains(component("Type 1", COST_1, LAST_UPDATED_1, false, 1), component("Type 2", COST_2, LAST_UPDATED_2, false, 2),
						component("Component 1", null, null, true, 11)));
	}

	@Test
	public void listMinerals() {
		when(this.typeRepository.findAllMinerals()).thenReturn(
				Arrays.asList(TYPE_2, TYPE_1));
		when(this.inventoryTypeRepository.findUnknownMinerals()).thenReturn(Arrays.asList(MINERAL_1));

		List<Mineral> minerals = this.typeService.listMinerals(false);

		assertThat(minerals,
				contains(mineral("Type 2", COST_2, LAST_UPDATED_2, false, 2), mineral("Type 1", COST_1, LAST_UPDATED_1, false, 1)));

		minerals = this.typeService.listMinerals(true);

		assertThat(
				minerals,
				contains(mineral("Type 2", COST_2, LAST_UPDATED_2, false, 2), mineral("Type 1", COST_1, LAST_UPDATED_1, false, 1),
						mineral("Mineral 1", null, null, true, 12)));
	}

	@Test
	public void listMissingTypes() {
		when(this.inventoryTypeRepository.findUnknownComponents()).thenReturn(Arrays.asList(COMPONENT_1));
		when(this.inventoryTypeRepository.findUnknownMinerals()).thenReturn(Arrays.asList(MINERAL_1));

		final List<? extends AbstractType> types = this.typeService.listMissingTypes();

		assertThat(types, containsInAnyOrder(component("Component 1", null, null, true, 11), mineral("Mineral 1", null, null, true, 12)));
	}

	@Test
	public void listMissingTypesForBlueprint() {
		final BlueprintReference blueprintReference = new BlueprintIdReference(4);
		final Blueprint b = mock(Blueprint.class);
		when(this.blueprintResolverService.toBlueprint(blueprintReference)).thenReturn(b);
		when(this.inventoryTypeRepository.findUnknownComponentsForBlueprint(b)).thenReturn(Arrays.asList(COMPONENT_1));
		when(this.inventoryTypeRepository.findUnknownMineralsForBlueprint(b)).thenReturn(Arrays.asList(MINERAL_1));

		final List<? extends AbstractType> types = this.typeService.listMissingTypes(blueprintReference);

		assertThat(types, containsInAnyOrder(component("Component 1", null, null, true, 11), mineral("Mineral 1", null, null, true, 12)));
	}

	@Test
	public void getRequiredComponents() {
		final BlueprintReference ref = new BlueprintIdReference(6);
		final Blueprint b = mock(Blueprint.class);
		final BlueprintRequiredType requiredComponent = createRequiredType(b, TYPE_1, COMPONENT_1, 5);
		final BlueprintRequiredType requiredMineral = createRequiredType(b, TYPE_2, MINERAL_1, 14);
		final BlueprintRequiredType requiredMissingComponent = createRequiredType(b, null, COMPONENT_2, 1);
		final BlueprintRequiredType requiredMissingMineral = createRequiredType(b, null, MINERAL_2, 3);
		when(b.getRequiredTypes()).thenReturn(
				Arrays.asList(requiredComponent, requiredMineral, requiredMissingComponent, requiredMissingMineral));
		when(this.blueprintResolverService.toBlueprint(ref)).thenReturn(b);

		final RequiredTypes requiredTypes = this.typeService.getRequiredTypes(ref);

		final Map<Component, Integer> requiredComponents = requiredTypes.getRequiredComponents();
		assertEquals(2, requiredComponents.size());
		assertThat(requiredComponents, hasEntry(component("Type 1", COST_1, LAST_UPDATED_1, false, 1), 5));
		assertThat(requiredComponents, hasEntry(component("Component 2", null, null, true, 13), 1));

		final Map<Mineral, Integer> requiredMinerals = requiredTypes.getRequiredMinerals();
		assertEquals(2, requiredMinerals.size());
		assertThat(requiredMinerals, hasEntry(mineral("Type 2", COST_2, LAST_UPDATED_2, false, 2), 14));
		assertThat(requiredMinerals, hasEntry(mineral("Mineral 2", null, null, true, 14), 3));
	}
}
