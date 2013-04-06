package nz.net.dnh.eve.business.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nz.net.dnh.eve.business.BlueprintIdReference;
import nz.net.dnh.eve.business.BlueprintNotFoundException;
import nz.net.dnh.eve.business.BlueprintSummary;
import nz.net.dnh.eve.business.CandidateBlueprint;
import nz.net.dnh.eve.business.impl.dto.blueprint.BlueprintSummaryImpl;
import nz.net.dnh.eve.business.impl.dto.blueprint.CandidateBlueprintImpl;
import nz.net.dnh.eve.model.domain.Blueprint;
import nz.net.dnh.eve.model.raw.InventoryBlueprintType;
import nz.net.dnh.eve.model.repository.BlueprintRepository;
import nz.net.dnh.eve.model.repository.InventoryBlueprintTypeRepository;
import nz.net.dnh.eve.model.repository.TypeRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BlueprintServiceTest {
	private static final MockBlueprint BLUEPRINT_1 = new MockBlueprint(3, 4, 5, new BigDecimal(6), 7, "Blueprint 1", new BigDecimal(8),
			new BigDecimal(9), new BigDecimal(10), new BigDecimal(11), new BigDecimal(12));
	private static final MockBlueprint BLUEPRINT_2 = new MockBlueprint(13, 14, 15, new BigDecimal(16), 17, "Blueprint 2",
			new BigDecimal(18), new BigDecimal(19), new BigDecimal(20), new BigDecimal(21), new BigDecimal(22));
	private static final InventoryBlueprintType INVENTORY_BLUEPRINT_1 = mock(InventoryBlueprintType.class);

	static {

	}

	@InjectMocks
	private final BlueprintServiceImpl service = new BlueprintServiceImpl();

	@Mock
	private BlueprintRepository blueprintRepository;

	@Mock
	private TypeRepository typeRepository;

	@Mock
	private InventoryBlueprintTypeRepository inventoryBlueprintTypeRepository;

	private static void assertBlueprintSummary(BlueprintSummary summary, MockBlueprint blueprint) {
		assertBlueprintSummary(summary, blueprint.getTypeName(), blueprint.getBlueprintTypeID(), blueprint.getHours(),
				blueprint.getNumberPerRun(), blueprint.getMaterialEfficiency(), blueprint.getMaterialCost(), blueprint.getProfit(),
				blueprint.getProfitPercentage(), blueprint.getOtherCost(), blueprint.getTotalCost(), blueprint.getSaleValue(),
				blueprint.getLastUpdated());
	}

	private static void assertBlueprintSummary(BlueprintSummary summary, String name, int id, int hours, int numberPerRun,
			int materialEfficiency, BigDecimal materialCost, BigDecimal profit, BigDecimal profitPercentage, BigDecimal runningCost,
			BigDecimal totalCost, BigDecimal saleValue, Timestamp saleValueLastUpdated) {
		assertEquals(hours, summary.getHours());
		assertEquals(id, summary.getId());
		assertEquals(materialCost, summary.getMaterialCost());
		assertEquals(materialEfficiency, summary.getMaterialEfficiency());
		assertEquals(name, summary.getName());
		assertEquals(numberPerRun, summary.getNumberPerRun());
		assertEquals(profit, summary.getProfit());
		assertEquals(profitPercentage, summary.getProfitPercentage());
		assertEquals(runningCost, summary.getRunningCost());
		assertEquals(saleValue, summary.getSaleValue());
		assertEquals(saleValueLastUpdated, summary.getSaleValueLastUpdated());
		assertEquals(totalCost, summary.getTotalCost());
	}

	@Test
	public void toBlueprintWithSummary() {
		Blueprint b = mock(Blueprint.class);
		BlueprintSummary summary = new BlueprintSummaryImpl(b);

		Blueprint out = this.service.toBlueprint(summary);

		assertSame(b, out);
		verifyNoMoreInteractions(this.blueprintRepository);
	}

	@Test
	public void toBlueprintWithIdReference() {
		Blueprint b = mock(Blueprint.class);
		BlueprintIdReference ref = new BlueprintIdReference(5);
		when(this.blueprintRepository.findOne(5)).thenReturn(b);

		Blueprint out = this.service.toBlueprint(ref);

		assertSame(b, out);
	}

	@Test
	public void toBlueprintWithCandidate() {
		Blueprint b = mock(Blueprint.class);
		when(this.blueprintRepository.findOne(7)).thenReturn(b);
		InventoryBlueprintType inventoryBlueprint = mock(InventoryBlueprintType.class);
		when(inventoryBlueprint.getBlueprintTypeID()).thenReturn(7);
		CandidateBlueprint ref = new CandidateBlueprintImpl(inventoryBlueprint );

		Blueprint out = this.service.toBlueprint(ref);

		assertSame(b, out);
	}

	@Test(expected=BlueprintNotFoundException.class)
	public void toBlueprintWithUnknownIdReference() {
		BlueprintIdReference ref = new BlueprintIdReference(5);

		this.service.toBlueprint(ref);
	}

	@Test(expected=BlueprintNotFoundException.class)
	public void toBlueprintWithUnknownCandidate() {
		InventoryBlueprintType inventoryBlueprint = mock(InventoryBlueprintType.class);
		when(inventoryBlueprint.getBlueprintTypeID()).thenReturn(7);
		CandidateBlueprint ref = new CandidateBlueprintImpl(inventoryBlueprint );

		this.service.toBlueprint(ref);
	}

	@Test
	public void getBlueprintWithSummary() {
		BlueprintSummary summary = new BlueprintSummaryImpl(BLUEPRINT_1);

		BlueprintSummary out = this.service.getBlueprint(summary);

		assertBlueprintSummary(out, BLUEPRINT_1);
		verifyNoMoreInteractions(this.blueprintRepository);
	}

	@Test
	public void getBlueprintWithIdReference() {
		BlueprintIdReference ref = new BlueprintIdReference(5);
		when(this.blueprintRepository.findOne(5)).thenReturn(BLUEPRINT_1);

		BlueprintSummary out = this.service.getBlueprint(ref);

		assertBlueprintSummary(out, BLUEPRINT_1);
	}

	@Test
	public void getBlueprintWithCandidate() {
		when(this.blueprintRepository.findOne(7)).thenReturn(BLUEPRINT_1);
		InventoryBlueprintType inventoryBlueprint = mock(InventoryBlueprintType.class);
		when(inventoryBlueprint.getBlueprintTypeID()).thenReturn(7);
		CandidateBlueprint ref = new CandidateBlueprintImpl(inventoryBlueprint);

		BlueprintSummary out = this.service.getBlueprint(ref);

		assertBlueprintSummary(out, BLUEPRINT_1);
	}

	@Test(expected = BlueprintNotFoundException.class)
	public void getBlueprintWithUnknownIdReference() {
		BlueprintIdReference ref = new BlueprintIdReference(5);

		this.service.getBlueprint(ref);
	}

	@Test(expected = BlueprintNotFoundException.class)
	public void getBlueprintWithUnknownCandidate() {
		InventoryBlueprintType inventoryBlueprint = mock(InventoryBlueprintType.class);
		when(inventoryBlueprint.getBlueprintTypeID()).thenReturn(7);
		CandidateBlueprint ref = new CandidateBlueprintImpl(inventoryBlueprint);

		this.service.getBlueprint(ref);
	}

	@Test
	public void listNoSummaries() {
		when(this.blueprintRepository.findAll()).thenReturn(Collections.<Blueprint> emptyList());

		List<BlueprintSummary> out = this.service.listSummaries();

		assertTrue(out.isEmpty());
	}

	@Test
	public void listSingleSummary() {
		when(this.blueprintRepository.findAll()).thenReturn(Collections.<Blueprint> singletonList(BLUEPRINT_1));

		List<BlueprintSummary> out = this.service.listSummaries();

		assertEquals(1, out.size());
		assertBlueprintSummary(out.get(0), BLUEPRINT_1);
	}

	@Test
	public void listSummaries() {
		when(this.blueprintRepository.findAll()).thenReturn(Arrays.<Blueprint> asList(BLUEPRINT_1, BLUEPRINT_2));

		List<BlueprintSummary> out = this.service.listSummaries();

		assertEquals(2, out.size());
		assertBlueprintSummary(out.get(0), BLUEPRINT_1);
		assertBlueprintSummary(out.get(1), BLUEPRINT_2);
	}
}
