package nz.net.dnh.eve.business.impl;

import static nz.net.dnh.eve.HelpingMatchers.contains;
import static nz.net.dnh.eve.HelpingMatchers.hasEntry;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.SortedMap;

import nz.net.dnh.eve.business.AbstractType;
import nz.net.dnh.eve.business.BlueprintSummary;
import nz.net.dnh.eve.business.RequiredBlueprint;
import nz.net.dnh.eve.business.RequiredType;
import nz.net.dnh.eve.business.RequiredType.DecompositionState;
import nz.net.dnh.eve.business.RequiredTypes;
import nz.net.dnh.eve.business.impl.dto.blueprint.BlueprintSummaryImpl;
import nz.net.dnh.eve.business.impl.dto.type.AbstractMissingTypeImpl.MissingComponentImpl;
import nz.net.dnh.eve.business.impl.dto.type.AbstractMissingTypeImpl.MissingMineralImpl;
import nz.net.dnh.eve.business.impl.dto.type.AbstractTypeImpl.ComponentImpl;
import nz.net.dnh.eve.business.impl.dto.type.AbstractTypeImpl.MineralImpl;
import nz.net.dnh.eve.model.domain.Blueprint;
import nz.net.dnh.eve.model.domain.BlueprintRequiredType;
import nz.net.dnh.eve.model.domain.Type;
import nz.net.dnh.eve.model.raw.InventoryBlueprintType;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Test;

public class BlueprintRequiredTypesServiceTest extends AbstractTypesTest {

	@SafeVarargs
	private static Matcher<RequiredType<? extends AbstractType>> requiredType(final AbstractType type, final int units,
			final Blueprint blueprint, final DecompositionState decompositionState, final Matcher<RequiredType<?>>... requiredTypes) {
		final Matcher<?> requiredTypesMatcher;
		if (requiredTypes == null)
			requiredTypesMatcher = nullValue();
		else
			requiredTypesMatcher = contains(requiredTypes);
		final BlueprintSummary blueprintSummary = blueprint == null ? null : new BlueprintSummaryImpl(blueprint);
		return new TypeSafeDiagnosingMatcher<RequiredType<?>>() {

			@Override
			public void describeTo(final Description description) {
				description.appendText("Required type with type ").appendValue(type).appendText(" and units ").appendValue(units)
						.appendText(" and blueprint ").appendValue(blueprint).appendText(" and decomposition state ")
						.appendValue(decompositionState);
				if (requiredTypes == null) {
					description.appendText(" and no required types");
				} else {
					description.appendText(" and required types ").appendList("[", ",", "]", Arrays.asList(requiredTypes));
				}
			}

			@Override
			protected boolean matchesSafely(final RequiredType<?> item, final Description mismatchDescription) {
				if (!type.equals(item.getType())) {
					mismatchDescription.appendText("Type was ").appendValue(item.getType());
					return false;
				}
				if (units != item.getUnits()) {
					mismatchDescription.appendText("Units was ").appendValue(item.getUnits());
					return false;
				}
				if (!Objects.equals(blueprintSummary, item.getTypeBlueprint())) {
					mismatchDescription.appendValue("Blueprint summary was ").appendValue(item.getTypeBlueprint());
					return false;
				}
				if (decompositionState != item.getDecompositionState()) {
					mismatchDescription.appendValue("Decomposition state was ").appendValue(item.getDecompositionState());
					return false;
				}
				if (!requiredTypesMatcher.matches(item.getTypeBlueprintRequiredTypes())) {
					mismatchDescription.appendText("required types: ");
					requiredTypesMatcher.describeMismatch(item.getTypeBlueprintRequiredTypes(), mismatchDescription);
					return false;
				}
				return true;
			}
		};
	}

	private static Matcher<RequiredType<?>> requiredType(final AbstractType type, final int units, final Blueprint blueprint,
			final DecompositionState decompositionState) {
		return requiredType(type, units, blueprint, decompositionState, (Matcher<RequiredType<?>>[]) null);
	}

	private Matcher<RequiredBlueprint> requiredBlueprint(final int requiredUnits, final int producedUnits, final int runs,
			final Blueprint blueprint) {
		final BlueprintSummaryImpl blueprintSummary = blueprint == null ? null : new BlueprintSummaryImpl(blueprint);
		return new TypeSafeDiagnosingMatcher<RequiredBlueprint>() {
			@Override
			public void describeTo(final Description description) {
				description.appendText("Required blueprint ").appendValue(blueprint).appendText(" with required units ")
						.appendValue(requiredUnits).appendText(" and produced units ").appendValue(producedUnits)
						.appendText(" and runs ").appendValue(runs);
			}

			@Override
			protected boolean matchesSafely(final RequiredBlueprint item, final Description mismatchDescription) {
				if (!blueprintSummary.equals(item.getTypeBlueprint())) {
					mismatchDescription.appendText("Invalid blueprint ").appendValue(item.getTypeBlueprint());
					return false;
				}
				if (requiredUnits != item.getRequiredUnits()) {
					mismatchDescription.appendText("Invalid required units ").appendValue(item.getRequiredUnits());
					return false;
				}
				if (producedUnits != item.getProducedUnits()) {
					mismatchDescription.appendText("Invalid required units ").appendValue(item.getProducedUnits());
					return false;
				}
				if (runs != item.getRuns()) {
					mismatchDescription.appendText("Invalid runs ").appendValue(item.getRuns());
					return false;
				}
				return true;
			}

		};
	}

	private final BlueprintRequiredTypesServiceImpl blueprintRequiredTypesService = new BlueprintRequiredTypesServiceImpl();

	@Test
	public void getRequiredComponentsWithoutDecomposition() {
		// Setup 4 required types, one of which has an InventoryBlueprintType but no configured Blueprint
		final Blueprint b = mock(Blueprint.class);
		final BlueprintRequiredType requiredComponent = createRequiredType(b, this.type1, this.component1, 5, false,
				mock(InventoryBlueprintType.class), null);
		final BlueprintRequiredType requiredMineral = createRequiredType(b, this.type2, this.mineral1, 14, false, null, null);
		final BlueprintRequiredType requiredMissingComponent = createRequiredType(b, null, this.component2, 1, false, null, null);
		final BlueprintRequiredType requiredMissingMineral = createRequiredType(b, null, this.mineral2, 3, false, null, null);
		when(b.getRequiredTypes()).thenReturn(
				Arrays.asList(requiredComponent, requiredMineral, requiredMissingComponent, requiredMissingMineral));

		final AbstractType type1Component = new ComponentImpl(this.type1);
		final AbstractType component2Component = new MissingComponentImpl(this.component2);
		final AbstractType type2Mineral = new MineralImpl(this.type2);
		final AbstractType mineral2Mineral = new MissingMineralImpl(this.mineral2);

		final RequiredTypes requiredTypes = this.blueprintRequiredTypesService.getRequiredTypes(b);
		assertThat(requiredTypes.getRequiredBlueprints(), is(empty()));

		final SortedMap<? extends AbstractType, Integer> resolvedRequiredTypes = requiredTypes.getResolvedRequiredTypes();
		// Sorted by name
		assertThat(resolvedRequiredTypes.keySet(), contains(component2Component, mineral2Mineral, type1Component, type2Mineral));
		assertThat(resolvedRequiredTypes, hasEntry(type1Component, 5));
		assertThat(resolvedRequiredTypes, hasEntry(component2Component, 1));
		assertThat(resolvedRequiredTypes, hasEntry(type2Mineral, 14));
		assertThat(resolvedRequiredTypes, hasEntry(mineral2Mineral, 3));

		final List<RequiredType<? extends AbstractType>> requiredTypesTree = requiredTypes.getRequiredTypesTree();
		assertThat(
				requiredTypesTree,
				contains(requiredType(component2Component, 1, null, DecompositionState.NEVER_DECOMPOSED),
						requiredType(mineral2Mineral, 3, null, DecompositionState.NEVER_DECOMPOSED),
						requiredType(type1Component, 5, null, DecompositionState.NOT_CONFIGURED),
						requiredType(type2Mineral, 14, null, DecompositionState.NEVER_DECOMPOSED)));
	}

	@Test
	public void getRequiredComponentsWithDecomposition() {
		// Setup 4 required types, where 1 has an InventoryBlueprintType and no Blueprint, and another has a fully-configured Blueprint
		// which requires a previously-referenced type
		final Blueprint b = mock(Blueprint.class);
		final Blueprint requiredComponentBlueprint = mock(Blueprint.class);
		when(requiredComponentBlueprint.getProducedQuantity()).thenReturn(2);
		final BlueprintRequiredType requiredComponent = createRequiredType(b, this.type1, this.component1, 5, true,
				mock(InventoryBlueprintType.class), requiredComponentBlueprint);
		final BlueprintRequiredType requiredMineral = createRequiredType(b, this.type2, this.mineral1, 14, false, null, null);
		final BlueprintRequiredType requiredMissingComponent = createRequiredType(b, null, this.component2, 1, false,
				mock(InventoryBlueprintType.class), null);
		final BlueprintRequiredType requiredMissingMineral = createRequiredType(b, null, this.mineral2, 3, false, null, null);
		when(b.getRequiredTypes()).thenReturn(
				Arrays.asList(requiredComponent, requiredMineral, requiredMissingComponent, requiredMissingMineral));

		final BlueprintRequiredType requiredComponentRequiredMineral = createRequiredType(requiredComponentBlueprint, this.type2,
				this.mineral1, 7, false, null, null);
		when(requiredComponentBlueprint.getRequiredTypes()).thenReturn(Collections.singletonList(requiredComponentRequiredMineral));

		final AbstractType type1Component = new ComponentImpl(this.type1);
		final AbstractType component2Component = new MissingComponentImpl(this.component2);
		final AbstractType type2Mineral = new MineralImpl(this.type2);
		final AbstractType mineral2Mineral = new MissingMineralImpl(this.mineral2);

		final RequiredTypes requiredTypes = this.blueprintRequiredTypesService.getRequiredTypes(b);

		assertThat(requiredTypes.getRequiredBlueprints(), contains(requiredBlueprint(5, 6, 3, requiredComponentBlueprint)));

		final SortedMap<? extends AbstractType, Integer> resolvedRequiredTypes = requiredTypes.getResolvedRequiredTypes();
		// Sorted by name
		assertThat(resolvedRequiredTypes.keySet(), contains(component2Component, mineral2Mineral, type2Mineral));
		assertThat(resolvedRequiredTypes, hasEntry(component2Component, 1));
		// 14 from the original required type, 3*7 from 3x required component runs (producing 2x components each)
		assertThat(resolvedRequiredTypes, hasEntry(type2Mineral, 35));
		assertThat(resolvedRequiredTypes, hasEntry(mineral2Mineral, 3));

		final List<RequiredType<? extends AbstractType>> requiredTypesTree = requiredTypes.getRequiredTypesTree();
		assertThat(
				requiredTypesTree,
				contains(
						requiredType(component2Component, 1, null, DecompositionState.NOT_CONFIGURED),
						requiredType(mineral2Mineral, 3, null, DecompositionState.NEVER_DECOMPOSED),
						requiredType(type1Component, 5, requiredComponentBlueprint, DecompositionState.DECOMPOSED,
								requiredType(type2Mineral, 7, null, DecompositionState.NEVER_DECOMPOSED)),
						requiredType(type2Mineral, 14, null, DecompositionState.NEVER_DECOMPOSED)));
	}

	@Test
	public void getRequiredComponentsWithDeepDecomposition() {
		// Setup 3 required types A/B/C where A and B are configured blueprints, mainBlueprint->A,B,C, A->B,C and B->C
		// The blueprint for A produces 2 items every time it is run, the blueprint for B produces 4 items every time it is run
		final Blueprint b = mock(Blueprint.class);
		final Blueprint requiredBlueprint1 = mock(Blueprint.class);
		when(requiredBlueprint1.getProducedQuantity()).thenReturn(2);
		final Blueprint requiredBlueprint2 = mock(Blueprint.class);
		when(requiredBlueprint2.getProducedQuantity()).thenReturn(7);
		final BlueprintRequiredType requiredComponent1 = createRequiredType(b, this.type1, this.component1, 5, true,
				mock(InventoryBlueprintType.class), requiredBlueprint1);
		final Type component2Type = mock(Type.class);
		when(component2Type.getTypeName()).thenReturn("Type 1.5");
		final BlueprintRequiredType requiredComponent2 = createRequiredType(b, component2Type, this.component2, 6, true,
				mock(InventoryBlueprintType.class), requiredBlueprint2);

		final BlueprintRequiredType requiredMineral = createRequiredType(b, this.type2, this.mineral1, 14, false, null, null);
		when(b.getRequiredTypes()).thenReturn(Arrays.asList(requiredComponent1, requiredComponent2, requiredMineral));

		final BlueprintRequiredType requiredComponent1Mineral = createRequiredType(requiredBlueprint1, this.type2, this.mineral1, 7, false,
				null, null);
		final BlueprintRequiredType requiredComponent1Component2 = createRequiredType(requiredBlueprint1, component2Type, this.component2,
				8, true, mock(InventoryBlueprintType.class), requiredBlueprint2);
		when(requiredBlueprint1.getRequiredTypes()).thenReturn(Arrays.asList(requiredComponent1Mineral, requiredComponent1Component2));

		final BlueprintRequiredType requiredComponent2Mineral = createRequiredType(requiredBlueprint2, this.type2, this.mineral1, 9, false,
				null, null);
		when(requiredBlueprint2.getRequiredTypes()).thenReturn(Arrays.asList(requiredComponent2Mineral));

		final AbstractType requiredComponent1Type = new ComponentImpl(this.type1);
		final AbstractType requiredComponent2Type = new ComponentImpl(component2Type);
		final AbstractType requiredMineralType = new MineralImpl(this.type2);

		final RequiredTypes requiredTypes = this.blueprintRequiredTypesService.getRequiredTypes(b);

		final List<RequiredBlueprint> requiredBlueprints = requiredTypes.getRequiredBlueprints();
		// 5 requiredComponent1 from A, requiredComponent1 produces 2 per run, so 6 should be produced from 3 runs
		// 6 requiredComponent2 from A + 3*8 requiredComponent2 from 3 runs*requiredComponent1 = 30, requiredComponent2 produces 7 per run,
		// so 35 should be produced from 5 runs
		assertThat(requiredBlueprints,
				contains(requiredBlueprint(5, 6, 3, requiredBlueprint1), requiredBlueprint(30, 35, 5, requiredBlueprint2)));

		final SortedMap<? extends AbstractType, Integer> resolvedRequiredTypes = requiredTypes.getResolvedRequiredTypes();
		assertEquals(Collections.singleton(requiredMineralType), resolvedRequiredTypes.keySet());
		// 14 from the original required type, 3*7 from 3x required component 1, 5*9 from 5x required component 2
		assertThat(resolvedRequiredTypes, hasEntry(requiredMineralType, 80));

		final List<RequiredType<? extends AbstractType>> requiredTypesTree = requiredTypes.getRequiredTypesTree();
		assertThat(
				requiredTypesTree,
				contains(
						requiredType(
								requiredComponent1Type,
								5,
								requiredBlueprint1,
								DecompositionState.DECOMPOSED,
								requiredType(requiredComponent2Type, 8, requiredBlueprint2, DecompositionState.DECOMPOSED,
										requiredType(requiredMineralType, 9, null, DecompositionState.NEVER_DECOMPOSED)),
								requiredType(requiredMineralType, 7, null, DecompositionState.NEVER_DECOMPOSED)),
						requiredType(requiredComponent2Type, 6, requiredBlueprint2, DecompositionState.DECOMPOSED,
								requiredType(requiredMineralType, 9, null, DecompositionState.NEVER_DECOMPOSED)),
						requiredType(requiredMineralType, 14, null, DecompositionState.NEVER_DECOMPOSED)));
	}

	@Test
	public void getRequiredComponentsWithDecompositionDisabled() {
		// Setup 3 required types A/B/C where A and B are configured blueprints, mainBlueprint->A,B,C, A->B,C and B->C, and B has been
		// configured to not be decomposed in either place
		final Blueprint b = mock(Blueprint.class);
		final Blueprint requiredBlueprint1 = mock(Blueprint.class);
		final Blueprint requiredBlueprint2 = mock(Blueprint.class);
		when(requiredBlueprint1.getProducedQuantity()).thenReturn(1);
		when(requiredBlueprint2.getProducedQuantity()).thenReturn(1);
		final BlueprintRequiredType requiredComponent1 = createRequiredType(b, this.type1, this.component1, 5, true,
				mock(InventoryBlueprintType.class), requiredBlueprint1);
		final Type component2Type = mock(Type.class);
		when(component2Type.getTypeName()).thenReturn("Type 1.5");
		final BlueprintRequiredType requiredComponent2 = createRequiredType(b, component2Type, this.component2, 6, false,
				mock(InventoryBlueprintType.class), requiredBlueprint2);

		final BlueprintRequiredType requiredMineral = createRequiredType(b, this.type2, this.mineral1, 14, false, null, null);
		when(b.getRequiredTypes()).thenReturn(Arrays.asList(requiredComponent1, requiredComponent2, requiredMineral));

		final BlueprintRequiredType requiredComponent1Mineral = createRequiredType(requiredBlueprint1, this.type2, this.mineral1, 7, false,
				null, null);
		final BlueprintRequiredType requiredComponent1Component2 = createRequiredType(requiredBlueprint1, component2Type, this.component2,
				8, false, mock(InventoryBlueprintType.class), requiredBlueprint2);
		when(requiredBlueprint1.getRequiredTypes()).thenReturn(Arrays.asList(requiredComponent1Mineral, requiredComponent1Component2));

		final BlueprintRequiredType requiredComponent2Mineral = createRequiredType(requiredBlueprint2, this.type2, this.mineral1, 9, false,
				null, null);
		when(requiredBlueprint2.getRequiredTypes()).thenReturn(Arrays.asList(requiredComponent2Mineral));

		final AbstractType requiredComponent1Type = new ComponentImpl(this.type1);
		final AbstractType requiredComponent2Type = new ComponentImpl(component2Type);
		final AbstractType requiredMineralType = new MineralImpl(this.type2);

		final RequiredTypes requiredTypes = this.blueprintRequiredTypesService.getRequiredTypes(b);
		assertThat(requiredTypes.getRequiredBlueprints(), contains(requiredBlueprint(5, 5, 5, requiredBlueprint1)));

		final SortedMap<? extends AbstractType, Integer> resolvedRequiredTypes = requiredTypes.getResolvedRequiredTypes();
		assertThat(resolvedRequiredTypes.keySet(), contains(requiredComponent2Type, requiredMineralType));
		// 14 from the original required type, 5*7 from 5x required component 1
		assertThat(resolvedRequiredTypes, hasEntry(requiredMineralType, 49));
		// 6 from the original required type, 5*8 from 5x required component 1
		assertThat(resolvedRequiredTypes, hasEntry(requiredComponent2Type, 46));

		final List<RequiredType<? extends AbstractType>> requiredTypesTree = requiredTypes.getRequiredTypesTree();
		assertThat(
				requiredTypesTree,
				contains(
						requiredType(
								requiredComponent1Type,
								5,
								requiredBlueprint1,
								DecompositionState.DECOMPOSED,
								requiredType(requiredComponent2Type, 8, requiredBlueprint2, DecompositionState.NOT_DECOMPOSED,
										requiredType(requiredMineralType, 9, null, DecompositionState.NEVER_DECOMPOSED)),
								requiredType(requiredMineralType, 7, null, DecompositionState.NEVER_DECOMPOSED)),
						requiredType(requiredComponent2Type, 6, requiredBlueprint2, DecompositionState.NOT_DECOMPOSED,
								requiredType(requiredMineralType, 9, null, DecompositionState.NEVER_DECOMPOSED)),
						requiredType(requiredMineralType, 14, null, DecompositionState.NEVER_DECOMPOSED)));
	}

	// TODO test case where A->B and B->C where A->B is marked NOT_DECOMPOSED and B->C is marked DECOMPOSED
}
