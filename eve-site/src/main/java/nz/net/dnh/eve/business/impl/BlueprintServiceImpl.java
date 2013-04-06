package nz.net.dnh.eve.business.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import nz.net.dnh.eve.business.BlueprintNotFoundException;
import nz.net.dnh.eve.business.BlueprintReference;
import nz.net.dnh.eve.business.BlueprintService;
import nz.net.dnh.eve.business.BlueprintSummary;
import nz.net.dnh.eve.business.CandidateBlueprint;
import nz.net.dnh.eve.business.impl.dto.blueprint.BlueprintSummaryImpl;
import nz.net.dnh.eve.business.impl.dto.blueprint.CandidateBlueprintImpl;
import nz.net.dnh.eve.model.domain.Blueprint;
import nz.net.dnh.eve.model.raw.InventoryBlueprintType;
import nz.net.dnh.eve.model.repository.BlueprintRepository;
import nz.net.dnh.eve.model.repository.InventoryBlueprintTypeRepository;
import nz.net.dnh.eve.model.repository.TypeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BlueprintServiceImpl implements BlueprintService, BlueprintResolverService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BlueprintServiceImpl.class);

	@Autowired
	private BlueprintRepository blueprintRepository;
	@Autowired
	private TypeRepository typeRepository;
	@Autowired
	private InventoryBlueprintTypeRepository inventoryBlueprintTypeRepository;

	@Override
	public List<BlueprintSummary> listSummaries() {
		List<Blueprint> blueprints = this.blueprintRepository.findAll();
		List<BlueprintSummary> blueprintSummaries = new ArrayList<>(
				blueprints.size());
		for (Blueprint blueprint : blueprints) {
			blueprintSummaries.add(new BlueprintSummaryImpl(blueprint));
		}
		return blueprintSummaries;
	}

	@Override
	public Blueprint toBlueprint(BlueprintReference blueprintReference) {
		if (blueprintReference instanceof BlueprintSummaryImpl) {
			return ((BlueprintSummaryImpl) blueprintReference).toBlueprint();
		}
		Blueprint blueprint = this.blueprintRepository.findOne(blueprintReference.getId());
		if (blueprint == null) {
			throw new BlueprintNotFoundException(blueprintReference);
		}
		return blueprint;
	}

	private static Pageable sanitisePageable(Pageable page) {
		if (page.getSort() != null) {
			throw new IllegalArgumentException("The page parameter must not provide sorting information");
		}
		return page;
	}

	private static Page<CandidateBlueprint> toCandidateBlueprints(Pageable page, Page<InventoryBlueprintType> unknownBlueprints) {
		List<CandidateBlueprint> candidateBlueprints = new ArrayList<>(unknownBlueprints.getNumberOfElements());
		for (InventoryBlueprintType inventoryBlueprint : unknownBlueprints) {
			candidateBlueprints.add(new CandidateBlueprintImpl(inventoryBlueprint));
		}
		return new PageImpl<>(candidateBlueprints, page, unknownBlueprints.getTotalElements());
	}

	@Override
	public Page<CandidateBlueprint> listCandidateBlueprints(Pageable page) {
		Page<InventoryBlueprintType> unknownBlueprints = this.inventoryBlueprintTypeRepository
				.findUnknownBlueprints(sanitisePageable(page));

		return toCandidateBlueprints(page, unknownBlueprints);
	}

	@Override
	public Page<CandidateBlueprint> findCandidateBlueprints(String search, Pageable page) {
		Page<InventoryBlueprintType> unknownBlueprints = this.inventoryBlueprintTypeRepository.findUnknownBlueprintsBySearch(search,
				sanitisePageable(page));

		return toCandidateBlueprints(page, unknownBlueprints);
	}

	@Override
	public BlueprintSummary getBlueprint(BlueprintReference blueprintReference) {
		if (blueprintReference instanceof BlueprintSummary) {
			LOGGER.warn("You already had a blueprint summary, why are you asking for another one?");
			return (BlueprintSummary) blueprintReference;
		}
		return new BlueprintSummaryImpl(toBlueprint(blueprintReference));
	}

	@Override
	public BlueprintSummary createBlueprint(BlueprintReference blueprintReference, BigDecimal saleValue, int numberPerRun, int hours,
			int materialEfficiency) {
		// Check it doesn't already exist
		if (this.blueprintRepository.exists(blueprintReference.getId())) {
			throw new IllegalArgumentException("The blueprint " + blueprintReference + " already exists");
		}
		// Check it matches something in the EVE dump
		if (!this.inventoryBlueprintTypeRepository.exists(blueprintReference.getId())) {
			throw new IllegalArgumentException("The blueprint " + blueprintReference + " does not match any InventoryBlueprintType");
		}
		if (saleValue == null) {
			throw new IllegalArgumentException("Sale value cannot be null");
		}
		// Create it
		Blueprint newBlueprint = new Blueprint(blueprintReference.getId(), numberPerRun, hours, saleValue, materialEfficiency);
		Blueprint savedBlueprint = this.blueprintRepository.save(newBlueprint);
		return new BlueprintSummaryImpl(savedBlueprint);
	}

	@Override
	public BlueprintSummary editBlueprint(BlueprintReference blueprintReference, BigDecimal saleValue, Integer numberPerRun, Integer hours,
			Integer materialEfficiency) {
		Blueprint blueprint = toBlueprint(blueprintReference);
		if (saleValue != null) {
			blueprint.setSaleValue(saleValue);
			blueprint.setLastUpdated(new Timestamp(System.currentTimeMillis()));
		}
		if (numberPerRun != null) {
			blueprint.setNumberPerRun(numberPerRun);
		}
		if (hours != null) {
			blueprint.setHours(hours);
		}
		if (materialEfficiency != null) {
			blueprint.setMaterialEfficiency(materialEfficiency);
		}

		Blueprint savedBlueprint = this.blueprintRepository.save(blueprint);
		return new BlueprintSummaryImpl(savedBlueprint);
	}
}
