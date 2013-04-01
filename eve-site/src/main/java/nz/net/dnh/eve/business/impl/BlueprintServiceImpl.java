package nz.net.dnh.eve.business.impl;

import java.util.ArrayList;
import java.util.List;

import nz.net.dnh.eve.business.BlueprintService;
import nz.net.dnh.eve.business.BlueprintSummary;
import nz.net.dnh.eve.business.impl.dto.blueprint.BlueprintSummaryImpl;
import nz.net.dnh.eve.model.domain.Blueprint;
import nz.net.dnh.eve.model.repository.BlueprintRepository;
import nz.net.dnh.eve.model.repository.InventoryBlueprintTypeRepository;
import nz.net.dnh.eve.model.repository.TypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlueprintServiceImpl implements BlueprintService {

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
}
