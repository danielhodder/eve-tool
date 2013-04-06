package nz.net.dnh.eve.business.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
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
import nz.net.dnh.eve.model.raw.InventoryType;
import nz.net.dnh.eve.model.repository.BlueprintRepository;
import nz.net.dnh.eve.model.repository.InventoryBlueprintTypeRepository;
import nz.net.dnh.eve.model.repository.TypeRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

@RunWith(MockitoJUnitRunner.class)
public class BlueprintServiceTest {
	private static final MockBlueprint BLUEPRINT_1 = new MockBlueprint(3, 4, 5, new BigDecimal(6), 7, "Blueprint 1", new BigDecimal(8),
			new BigDecimal(9), new BigDecimal(10), new BigDecimal(11), new BigDecimal(12), 99);
	private static final MockBlueprint BLUEPRINT_2 = new MockBlueprint(13, 14, 15, new BigDecimal(16), 17, "Blueprint 2",
			new BigDecimal(18), new BigDecimal(19), new BigDecimal(20), new BigDecimal(21), new BigDecimal(22), 98);
	private static final InventoryBlueprintType INVENTORY_BLUEPRINT_1 = mock(InventoryBlueprintType.class);
	private static final InventoryBlueprintType INVENTORY_BLUEPRINT_2 = mock(InventoryBlueprintType.class);

	static {
		InventoryType productType = mock(InventoryType.class);
		when(productType.getTypeName()).thenReturn("Inventory blueprint type");
		when(INVENTORY_BLUEPRINT_1.getBlueprintTypeID()).thenReturn(985);
		when(INVENTORY_BLUEPRINT_1.getProductType()).thenReturn(productType);
		when(INVENTORY_BLUEPRINT_1.getProductTypeID()).thenReturn(17);

		InventoryType productType2 = mock(InventoryType.class);
		when(productType2.getTypeName()).thenReturn("Inventory blueprint type 2");
		when(INVENTORY_BLUEPRINT_2.getBlueprintTypeID()).thenReturn(985);
		when(INVENTORY_BLUEPRINT_2.getProductType()).thenReturn(productType2);
		when(INVENTORY_BLUEPRINT_2.getProductTypeID()).thenReturn(18);
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
		assertBlueprintSummary(summary, blueprint.getTypeName(), blueprint.getBlueprintTypeID(), blueprint.getProducedTypeId(),
				blueprint.getHours(), blueprint.getNumberPerRun(), blueprint.getMaterialEfficiency(), blueprint.getMaterialCost(),
				blueprint.getProfit(), blueprint.getProfitPercentage(), blueprint.getOtherCost(), blueprint.getTotalCost(),
				blueprint.getSaleValue(), blueprint.getLastUpdated());
	}

	private static void assertBlueprintSummary(BlueprintSummary summary, String name, int id, int producedTypeId, int hours, int numberPerRun,
			int materialEfficiency, BigDecimal materialCost, BigDecimal profit, BigDecimal profitPercentage, BigDecimal runningCost,
			BigDecimal totalCost, BigDecimal saleValue, Timestamp saleValueLastUpdated) {
		assertEquals(hours, summary.getHours());
		assertEquals(id, summary.getId());
		assertEquals(producedTypeId, summary.getProducedTypeID());
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

	private static void assertCandidateBlueprint(CandidateBlueprint blueprint, InventoryBlueprintType inventoryBlueprint) {
		assertCandidateBlueprint(blueprint, inventoryBlueprint.getBlueprintTypeID(), inventoryBlueprint.getProductTypeID(),
				inventoryBlueprint.getProductType().getTypeName());
	}

	private static void assertCandidateBlueprint(CandidateBlueprint blueprint, int id, int producedTypeId, String name) {
		assertEquals(id, blueprint.getId());
		assertEquals(producedTypeId, blueprint.getProducedTypeID());
		assertEquals(name, blueprint.getName());
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

	@Test(expected = IllegalArgumentException.class)
	public void listCandidateBlueprintsSortNotAllowed() {
		this.service.listCandidateBlueprints(new PageRequest(0, 5, Direction.ASC, "totalCost"));
	}

	@Test
	public void listNoCandidateBlueprints() {
		PageRequest page = new PageRequest(0, 2);
		when(this.inventoryBlueprintTypeRepository.findUnknownBlueprints(page)).thenReturn(
				new PageImpl<>(Collections.<InventoryBlueprintType> emptyList()));
		
		Page<CandidateBlueprint> out = this.service.listCandidateBlueprints(page);
		assertTrue(out.getContent().isEmpty());
	}

	@Test
	public void listSingleCandidateBlueprint() {
		PageRequest page = new PageRequest(0, 2);
		when(this.inventoryBlueprintTypeRepository.findUnknownBlueprints(page)).thenReturn(
				new PageImpl<>(Collections.singletonList(INVENTORY_BLUEPRINT_1)));

		Page<CandidateBlueprint> out = this.service.listCandidateBlueprints(page);
		assertEquals(1, out.getNumberOfElements());
		assertEquals(0, out.getNumber());
		assertEquals(1, out.getTotalPages());
		assertEquals(1, out.getTotalElements());
		assertCandidateBlueprint(out.getContent().get(0), INVENTORY_BLUEPRINT_1);
	}

	@Test
	public void listCandidateBlueprints() {
		PageRequest page = new PageRequest(0, 2);
		when(this.inventoryBlueprintTypeRepository.findUnknownBlueprints(page)).thenReturn(
				new PageImpl<>(Arrays.asList(INVENTORY_BLUEPRINT_2, INVENTORY_BLUEPRINT_1)));

		Page<CandidateBlueprint> out = this.service.listCandidateBlueprints(page);
		assertEquals(2, out.getNumberOfElements());
		assertEquals(0, out.getNumber());
		assertEquals(1, out.getTotalPages());
		assertEquals(2, out.getTotalElements());
		assertCandidateBlueprint(out.getContent().get(0), INVENTORY_BLUEPRINT_2);
		assertCandidateBlueprint(out.getContent().get(1), INVENTORY_BLUEPRINT_1);
	}

	@Test
	public void listCandidateBlueprintsPaged() {
		PageRequest page = new PageRequest(0, 2);
		when(this.inventoryBlueprintTypeRepository.findUnknownBlueprints(page)).thenReturn(
				new PageImpl<>(Arrays.asList(INVENTORY_BLUEPRINT_1, INVENTORY_BLUEPRINT_2), page, 34));

		Page<CandidateBlueprint> out = this.service.listCandidateBlueprints(page);
		assertEquals(2, out.getNumberOfElements());
		assertEquals(0, out.getNumber());
		assertEquals(17, out.getTotalPages());
		assertEquals(34, out.getTotalElements());
		assertCandidateBlueprint(out.getContent().get(0), INVENTORY_BLUEPRINT_1);
		assertCandidateBlueprint(out.getContent().get(1), INVENTORY_BLUEPRINT_2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void findCandidateBlueprintsSortNotAllowed() {
		this.service.findCandidateBlueprints("a", new PageRequest(0, 5, Direction.ASC, "totalCost"));
	}

	@Test
	public void findNoCandidateBlueprints() {
		PageRequest page = new PageRequest(0, 2);
		when(this.inventoryBlueprintTypeRepository.findUnknownBlueprintsBySearch("s", page)).thenReturn(
				new PageImpl<>(Collections.<InventoryBlueprintType> emptyList()));

		Page<CandidateBlueprint> out = this.service.findCandidateBlueprints("s", page);
		assertTrue(out.getContent().isEmpty());
	}

	@Test
	public void findSingleCandidateBlueprint() {
		PageRequest page = new PageRequest(0, 2);
		when(this.inventoryBlueprintTypeRepository.findUnknownBlueprintsBySearch("q", page)).thenReturn(
				new PageImpl<>(Collections.singletonList(INVENTORY_BLUEPRINT_1)));

		Page<CandidateBlueprint> out = this.service.findCandidateBlueprints("q", page);
		assertEquals(1, out.getNumberOfElements());
		assertEquals(0, out.getNumber());
		assertEquals(1, out.getTotalPages());
		assertEquals(1, out.getTotalElements());
		assertCandidateBlueprint(out.getContent().get(0), INVENTORY_BLUEPRINT_1);
	}

	@Test
	public void findCandidateBlueprints() {
		PageRequest page = new PageRequest(0, 2);
		when(this.inventoryBlueprintTypeRepository.findUnknownBlueprintsBySearch("z", page)).thenReturn(
				new PageImpl<>(Arrays.asList(INVENTORY_BLUEPRINT_2, INVENTORY_BLUEPRINT_1)));

		Page<CandidateBlueprint> out = this.service.findCandidateBlueprints("z", page);
		assertEquals(2, out.getNumberOfElements());
		assertEquals(0, out.getNumber());
		assertEquals(1, out.getTotalPages());
		assertEquals(2, out.getTotalElements());
		assertCandidateBlueprint(out.getContent().get(0), INVENTORY_BLUEPRINT_2);
		assertCandidateBlueprint(out.getContent().get(1), INVENTORY_BLUEPRINT_1);
	}

	@Test
	public void findCandidateBlueprintsPaged() {
		PageRequest page = new PageRequest(0, 2);
		when(this.inventoryBlueprintTypeRepository.findUnknownBlueprintsBySearch("p", page)).thenReturn(
				new PageImpl<>(Arrays.asList(INVENTORY_BLUEPRINT_1, INVENTORY_BLUEPRINT_2), page, 34));

		Page<CandidateBlueprint> out = this.service.findCandidateBlueprints("p", page);
		assertEquals(2, out.getNumberOfElements());
		assertEquals(0, out.getNumber());
		assertEquals(17, out.getTotalPages());
		assertEquals(34, out.getTotalElements());
		assertCandidateBlueprint(out.getContent().get(0), INVENTORY_BLUEPRINT_1);
		assertCandidateBlueprint(out.getContent().get(1), INVENTORY_BLUEPRINT_2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createBlueprintAlreadyExists() {
		when(this.blueprintRepository.exists(1)).thenReturn(true);
		this.service.createBlueprint(new BlueprintIdReference(1), new BigDecimal(2), 3, 4, 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createBlueprintReferencedDoesntExist() {
		when(this.blueprintRepository.exists(1)).thenReturn(false);
		when(this.inventoryBlueprintTypeRepository.exists(1)).thenReturn(false);
		this.service.createBlueprint(new BlueprintIdReference(1), new BigDecimal(2), 3, 4, 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createBlueprintNullSaleValue() {
		when(this.blueprintRepository.exists(1)).thenReturn(false);
		when(this.inventoryBlueprintTypeRepository.exists(1)).thenReturn(true);
		this.service.createBlueprint(new BlueprintIdReference(1), null, 3, 4, 5);
	}

	@Test
	public void createBlueprint() {
		when(this.blueprintRepository.exists(1)).thenReturn(false);
		when(this.inventoryBlueprintTypeRepository.exists(1)).thenReturn(true);
		when(this.blueprintRepository.save(any(Blueprint.class))).thenReturn(BLUEPRINT_1);

		BlueprintSummary created = this.service.createBlueprint(new BlueprintIdReference(1), new BigDecimal(2), 3, 4, 5);

		ArgumentCaptor<Blueprint> captor = ArgumentCaptor.forClass(Blueprint.class);
		verify(this.blueprintRepository).save(captor.capture());
		Blueprint blueprint = captor.getValue();
		assertEquals(1, blueprint.getBlueprintTypeID());
		assertEquals(new BigDecimal(2), blueprint.getSaleValue());
		assertEquals(3, blueprint.getNumberPerRun());
		assertEquals(4, blueprint.getHours());
		assertEquals(5, blueprint.getMaterialEfficiency());

		assertBlueprintSummary(created, BLUEPRINT_1);
	}

	@Test(expected=BlueprintNotFoundException.class)
	public void editNonExistingBlueprint() {
		this.service.editBlueprint(new BlueprintIdReference(1), new BigDecimal(2), 3, 4, 5);
	}

	@Test
	public void editBlueprintAllFields() {
		Blueprint b = mock(Blueprint.class);
		when(this.blueprintRepository.findOne(1)).thenReturn(b);
		this.service.editBlueprint(new BlueprintIdReference(1), new BigDecimal(2), 3, 4, 5);

		verify(b).setSaleValue(new BigDecimal(2));
		verify(b).setLastUpdated(any(Timestamp.class));
		verify(b).setNumberPerRun(3);
		verify(b).setHours(4);
		verify(b).setMaterialEfficiency(5);
	}

	@Test
	public void editBlueprintSaleValue() {
		Blueprint b = mock(Blueprint.class);
		when(this.blueprintRepository.findOne(1)).thenReturn(b);
		this.service.editBlueprint(new BlueprintIdReference(1), new BigDecimal(2), null, null, null);

		verify(b).setSaleValue(new BigDecimal(2));
		verify(b).setLastUpdated(any(Timestamp.class));
		verify(b, never()).setNumberPerRun(anyInt());
		verify(b, never()).setHours(anyInt());
		verify(b, never()).setMaterialEfficiency(anyInt());
	}

	@Test
	public void editBlueprintNumberPerRun() {
		Blueprint b = mock(Blueprint.class);
		when(this.blueprintRepository.findOne(1)).thenReturn(b);
		this.service.editBlueprint(new BlueprintIdReference(1), null, 3, null, null);

		verify(b, never()).setSaleValue(any(BigDecimal.class));
		verify(b, never()).setLastUpdated(any(Timestamp.class));
		verify(b).setNumberPerRun(3);
		verify(b, never()).setHours(anyInt());
		verify(b, never()).setMaterialEfficiency(anyInt());
	}

	@Test
	public void editBlueprintHours() {
		Blueprint b = mock(Blueprint.class);
		when(this.blueprintRepository.findOne(1)).thenReturn(b);
		this.service.editBlueprint(new BlueprintIdReference(1), null, null, 4, null);

		verify(b, never()).setSaleValue(any(BigDecimal.class));
		verify(b, never()).setLastUpdated(any(Timestamp.class));
		verify(b, never()).setNumberPerRun(anyInt());
		verify(b).setHours(4);
		verify(b, never()).setMaterialEfficiency(anyInt());
	}

	@Test
	public void editBlueprintMaterialEfficiency() {
		Blueprint b = mock(Blueprint.class);
		when(this.blueprintRepository.findOne(1)).thenReturn(b);
		this.service.editBlueprint(new BlueprintIdReference(1), null, null, null, 5);

		verify(b, never()).setSaleValue(any(BigDecimal.class));
		verify(b, never()).setLastUpdated(any(Timestamp.class));
		verify(b, never()).setNumberPerRun(anyInt());
		verify(b, never()).setHours(anyInt());
		verify(b).setMaterialEfficiency(5);
	}
}
