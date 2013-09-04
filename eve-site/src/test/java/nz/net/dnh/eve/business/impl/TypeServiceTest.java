package nz.net.dnh.eve.business.impl;

import static nz.net.dnh.eve.HelpingMatchers.contains;
import static nz.net.dnh.eve.HelpingMatchers.containsInAnyOrder;
import static nz.net.dnh.eve.HelpingMatchers.hasEntry;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;

import nz.net.dnh.eve.business.AbstractType;
import nz.net.dnh.eve.business.BlueprintIdReference;
import nz.net.dnh.eve.business.BlueprintReference;
import nz.net.dnh.eve.business.Component;
import nz.net.dnh.eve.business.Mineral;
import nz.net.dnh.eve.business.RequiredTypes;
import nz.net.dnh.eve.business.TypeIdReference;
import nz.net.dnh.eve.business.impl.dto.type.AbstractMissingTypeImpl.MissingComponentImpl;
import nz.net.dnh.eve.business.impl.dto.type.AbstractMissingTypeImpl.MissingMineralImpl;
import nz.net.dnh.eve.business.impl.dto.type.AbstractTypeImpl.ComponentImpl;
import nz.net.dnh.eve.business.impl.dto.type.AbstractTypeImpl.MineralImpl;
import nz.net.dnh.eve.model.domain.Blueprint;
import nz.net.dnh.eve.model.domain.BlueprintRequiredType;
import nz.net.dnh.eve.model.domain.Type;
import nz.net.dnh.eve.model.raw.InventoryType;
import nz.net.dnh.eve.model.repository.InventoryTypeRepository;
import nz.net.dnh.eve.model.repository.TypeRepository;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
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
	private final Type type1 = mock(Type.class);
	private final Type type2 = mock(Type.class);
	private final InventoryType component1 = mock(InventoryType.class);
	private final InventoryType component2 = mock(InventoryType.class);
	private final InventoryType mineral1 = mock(InventoryType.class);
	private final InventoryType mineral2 = mock(InventoryType.class);

	@InjectMocks
	private final TypeServiceImpl typeService = new TypeServiceImpl();

	@Mock
	private TypeRepository typeRepository;

	@Mock
	private InventoryTypeRepository inventoryTypeRepository;

	@Mock
	private BlueprintResolverService blueprintResolverService;

	private static Matcher<AbstractType> component(final String name, final BigDecimal cost, final Date lastUpdated, final boolean missing,
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

	@Test
	public void listComponents() {
		when(this.typeRepository.findAllComponents()).thenReturn(Arrays.asList(this.type1, this.type2));
		when(this.inventoryTypeRepository.findUnknownComponents()).thenReturn(Arrays.asList(this.component1));

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
		when(this.typeRepository.findAllMinerals()).thenReturn(Arrays.asList(this.type2, this.type1));
		when(this.inventoryTypeRepository.findUnknownMinerals()).thenReturn(Arrays.asList(this.mineral1));

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
	public void getComponent() {
		when(this.type1.getType()).thenReturn(this.component1);
		when(this.typeRepository.findOne(42)).thenReturn(this.type1);

		final Component component = this.typeService.getComponent(new TypeIdReference(42));
		assertThat(component, is(component("Type 1", COST_1, LAST_UPDATED_1, false, 1)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void getComponentWithMineralId() {
		when(this.type1.getType()).thenReturn(this.mineral1);
		when(this.typeRepository.findOne(43)).thenReturn(this.type1);

		this.typeService.getComponent(new TypeIdReference(43));
	}

	@Test
	public void getMissingComponent() {
		final Component component = this.typeService.getComponent(new TypeIdReference(42));
		assertNull(component);
	}

	@Test
	public void getMineral() {
		when(this.type2.getType()).thenReturn(this.mineral1);
		when(this.typeRepository.findOne(42)).thenReturn(this.type2);

		final Mineral component = this.typeService.getMineral(new TypeIdReference(42));
		assertThat(component, is(mineral("Type 2", COST_2, LAST_UPDATED_2, false, 2)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void getMineralWithComponentId() {
		when(this.type1.getType()).thenReturn(this.component1);
		when(this.typeRepository.findOne(43)).thenReturn(this.type1);

		this.typeService.getMineral(new TypeIdReference(43));
	}

	@Test
	public void getMissingMineral() {
		final Mineral component = this.typeService.getMineral(new TypeIdReference(42));
		assertNull(component);
	}

	@Test
	public void createMissingComponent() {
		final Date startDate = new Date();
		when(this.typeRepository.save(any(Type.class))).thenReturn(this.type1);
		when(this.inventoryTypeRepository.findOne(45)).thenReturn(this.component1);

		final Component createdComponent = this.typeService.createMissingComponent(new TypeIdReference(45), COST_1, false);

		assertThat(createdComponent, is(component("Type 1", COST_1, LAST_UPDATED_1, false, 1)));
		verify(this.typeRepository).save(
				argThat(both(Matchers.<Type> hasProperty("typeID", equalTo(45))).and(hasProperty("cost", equalTo(COST_1))).and(
						hasProperty("lastUpdated", greaterThanOrEqualTo(startDate)))));
	}

	@Test
	public void createMissingMineral() {
		final Date startDate = new Date();
		when(this.typeRepository.save(any(Type.class))).thenReturn(this.type1);
		when(this.inventoryTypeRepository.findOne(45)).thenReturn(this.mineral1);

		final Mineral createdMineral = this.typeService.createMissingMineral(new TypeIdReference(45), COST_1, false);

		assertThat(createdMineral, is(mineral("Type 1", COST_1, LAST_UPDATED_1, false, 1)));
		verify(this.typeRepository).save(
				argThat(both(Matchers.<Type> hasProperty("typeID", equalTo(45))).and(hasProperty("cost", equalTo(COST_1))).and(
						hasProperty("lastUpdated", greaterThanOrEqualTo(startDate)))));
	}

	@Test(expected = IllegalArgumentException.class)
	public void createMissingComponentWithMineralId() {
		when(this.inventoryTypeRepository.findOne(45)).thenReturn(this.mineral1);

		this.typeService.createMissingComponent(new TypeIdReference(45), COST_2, false);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createMissingMineralWithComponentId() {
		when(this.inventoryTypeRepository.findOne(45)).thenReturn(this.component1);

		this.typeService.createMissingMineral(new TypeIdReference(45), COST_2, false);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createMissingComponentWithInvalidId() {
		this.typeService.createMissingComponent(new TypeIdReference(45), COST_2, false);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createMissingMineralWithInvalidId() {
		this.typeService.createMissingMineral(new TypeIdReference(45), COST_2, false);
	}

	@Test
	public void updateComponent() {
		when(this.type2.getType()).thenReturn(this.component1);
		when(this.typeRepository.save(any(Type.class))).thenReturn(this.type1);
		when(this.typeRepository.findOne(22)).thenReturn(this.type2);
		final Component component = mock(Component.class);
		when(component.getId()).thenReturn(22);

		final Component updatedComponent = this.typeService.updateComponent(component, COST_1, false);

		assertThat(updatedComponent, is(component("Type 1", COST_1, LAST_UPDATED_1, false, 1)));
		verify(this.type2).setCost(COST_1);
		verify(this.type2).touchLastUpdated();
		verify(this.typeRepository).save(this.type2);
	}

	@Test
	public void updateMineral() {
		when(this.type2.getType()).thenReturn(this.mineral1);
		when(this.typeRepository.save(any(Type.class))).thenReturn(this.type1);
		when(this.typeRepository.findOne(22)).thenReturn(this.type2);
		final Mineral mineral = mock(Mineral.class);
		when(mineral.getId()).thenReturn(22);

		final Mineral updatedComponent = this.typeService.updateMineral(mineral, COST_1, false);

		assertThat(updatedComponent, is(mineral("Type 1", COST_1, LAST_UPDATED_1, false, 1)));
		verify(this.type2).setCost(COST_1);
		verify(this.type2).touchLastUpdated();
		verify(this.typeRepository).save(this.type2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateComponentWithInvalidId() {
		final Component component = mock(Component.class);
		when(component.getId()).thenReturn(22);

		this.typeService.updateComponent(component, COST_1, false);
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateMineralWithInvalidId() {
		final Mineral mineral = mock(Mineral.class);
		when(mineral.getId()).thenReturn(22);

		this.typeService.updateMineral(mineral, COST_1, false);
	}

	@Test
	public void listMissingTypes() {
		when(this.inventoryTypeRepository.findUnknownComponents()).thenReturn(Arrays.asList(this.component1));
		when(this.inventoryTypeRepository.findUnknownMinerals()).thenReturn(Arrays.asList(this.mineral1));

		final List<? extends AbstractType> types = this.typeService.listMissingTypes();

		assertThat(types, containsInAnyOrder(component("Component 1", null, null, true, 11), mineral("Mineral 1", null, null, true, 12)));
	}

	@Test
	public void listMissingTypesForBlueprint() {
		final BlueprintReference blueprintReference = new BlueprintIdReference(4);
		final Blueprint b = mock(Blueprint.class);
		when(this.blueprintResolverService.toBlueprint(blueprintReference)).thenReturn(b);
		when(this.inventoryTypeRepository.findUnknownComponentsForBlueprint(b)).thenReturn(Arrays.asList(this.component1));
		when(this.inventoryTypeRepository.findUnknownMineralsForBlueprint(b)).thenReturn(Arrays.asList(this.mineral1));

		final List<? extends AbstractType> types = this.typeService.listMissingTypes(blueprintReference);

		assertThat(types, containsInAnyOrder(component("Component 1", null, null, true, 11), mineral("Mineral 1", null, null, true, 12)));
	}

	@Test
	public void getRequiredComponents() {
		final BlueprintReference ref = new BlueprintIdReference(6);
		final Blueprint b = mock(Blueprint.class);
		final BlueprintRequiredType requiredComponent = createRequiredType(b, this.type1, this.component1, 5);
		final BlueprintRequiredType requiredMineral = createRequiredType(b, this.type2, this.mineral1, 14);
		final BlueprintRequiredType requiredMissingComponent = createRequiredType(b, null, this.component2, 1);
		final BlueprintRequiredType requiredMissingMineral = createRequiredType(b, null, this.mineral2, 3);
		when(b.getRequiredTypes()).thenReturn(
				Arrays.asList(requiredComponent, requiredMineral, requiredMissingComponent, requiredMissingMineral));
		when(this.blueprintResolverService.toBlueprint(ref)).thenReturn(b);

		final RequiredTypes requiredTypes = this.typeService.getRequiredTypes(ref);

		final SortedMap<Component, Integer> requiredComponents = requiredTypes.getRequiredComponents();
		final Matcher<AbstractType> type1Component = component("Type 1", COST_1, LAST_UPDATED_1, false, 1);
		final Matcher<AbstractType> component2Component = component("Component 2", null, null, true, 13);
		// Sorted by name
		assertThat(requiredComponents.keySet(), contains(component2Component, type1Component));
		assertThat(requiredComponents, hasEntry(type1Component, 5));
		assertThat(requiredComponents, hasEntry(component2Component, 1));

		final SortedMap<Mineral, Integer> requiredMinerals = requiredTypes.getRequiredMinerals();
		final Matcher<AbstractType> type1Mineral = mineral("Type 2", COST_2, LAST_UPDATED_2, false, 2);
		final Matcher<AbstractType> mineral2Mineral = mineral("Mineral 2", null, null, true, 14);
		// Sorted by name
		assertThat(requiredMinerals.keySet(), contains(mineral2Mineral, type1Mineral));
		assertThat(requiredMinerals, hasEntry(type1Mineral, 14));
		assertThat(requiredMinerals, hasEntry(mineral2Mineral, 3));
	}

	@Test
	public void toTypeWithIdReference() {
		when(this.typeRepository.findOne(4)).thenReturn(this.type1);

		assertSame(this.type1, this.typeService.toType(new TypeIdReference(4)));
	}

	@Test
	public void toTypeWithInvalidIdReference() {
		assertNull(this.typeService.toType(new TypeIdReference(4)));
	}

	@Test
	public void toTypeWithMineralImpl() {
		final MineralImpl mineral = new MineralImpl(this.type1);
		assertSame(this.type1, this.typeService.toType(mineral));
	}

	@Test
	public void toTypeWithComponentImpl() {
		final ComponentImpl mineral = new ComponentImpl(this.type1);
		assertSame(this.type1, this.typeService.toType(mineral));
	}

	@Test
	public void toTypeWithMissingMineralImpl() {
		when(this.typeRepository.findOne(4)).thenReturn(this.type1);
		final MissingMineralImpl missing = mock(MissingMineralImpl.class);
		when(missing.getId()).thenReturn(4);
		assertSame(this.type1, this.typeService.toType(missing));
	}

	@Test
	public void toTypeWithMissingComponentImpl() {
		when(this.typeRepository.findOne(4)).thenReturn(this.type1);
		final MissingComponentImpl missing = mock(MissingComponentImpl.class);
		when(missing.getId()).thenReturn(4);
		assertSame(this.type1, this.typeService.toType(missing));
	}

	@Test
	public void toInventoryTypeWithIdReference() {
		when(this.inventoryTypeRepository.findOne(4)).thenReturn(this.mineral1);

		assertSame(this.mineral1, this.typeService.toInventoryType(new TypeIdReference(4)));
	}

	@Test
	public void toInventoryTypeWithInvalidIdReference() {
		assertNull(this.typeService.toInventoryType(new TypeIdReference(4)));
	}

	@Test
	public void toInventoryTypeWithMineralImpl() {
		when(this.type1.getType()).thenReturn(this.mineral1);
		final MineralImpl mineral = new MineralImpl(this.type1);
		assertSame(this.mineral1, this.typeService.toInventoryType(mineral));
	}

	@Test
	public void toInventoryTypeWithComponentImpl() {
		when(this.type1.getType()).thenReturn(this.component1);
		final ComponentImpl component = new ComponentImpl(this.type1);
		assertSame(this.component1, this.typeService.toInventoryType(component));
	}

	@Test
	public void toInventoryTypeWithMissingMineralImpl() {
		final MissingMineralImpl missing = new MissingMineralImpl(this.mineral1);
		assertSame(this.mineral1, this.typeService.toInventoryType(missing));
	}

	@Test
	public void toInventoryTypeWithMissingComponentImpl() {
		final MissingComponentImpl missing = new MissingComponentImpl(this.component1);
		assertSame(this.component1, this.typeService.toInventoryType(missing));
	}
}
