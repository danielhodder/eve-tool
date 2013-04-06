package nz.net.dnh.eve.business.impl;

import java.util.ArrayList;
import java.util.List;

import nz.net.dnh.eve.business.AbstractType;
import nz.net.dnh.eve.business.BlueprintReference;
import nz.net.dnh.eve.business.Component;
import nz.net.dnh.eve.business.Mineral;
import nz.net.dnh.eve.business.TypeService;
import nz.net.dnh.eve.business.impl.dto.type.AbstractMissingTypeImpl.MissingComponentImpl;
import nz.net.dnh.eve.business.impl.dto.type.AbstractMissingTypeImpl.MissingMineralImpl;
import nz.net.dnh.eve.business.impl.dto.type.AbstractTypeImpl.ComponentImpl;
import nz.net.dnh.eve.business.impl.dto.type.AbstractTypeImpl.MineralImpl;
import nz.net.dnh.eve.model.domain.Blueprint;
import nz.net.dnh.eve.model.domain.Type;
import nz.net.dnh.eve.model.raw.InventoryType;
import nz.net.dnh.eve.model.repository.InventoryTypeRepository;
import nz.net.dnh.eve.model.repository.TypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeServiceImpl implements TypeService {

	@Autowired
	private TypeRepository typeRepository;

	@Autowired
	private InventoryTypeRepository inventoryTypeRepository;

	@Autowired
	private BlueprintResolverService blueprintResolverService;

	@Override
	public List<Mineral> listMinerals(boolean includeMissing) {
		List<Type> allTypes = this.typeRepository.findAllMinerals();
		List<Mineral> minerals = new ArrayList<>(allTypes.size());
		for (Type type : allTypes) {
			minerals.add(new MineralImpl(type));
		}
		if (includeMissing) {
			addMissingMinerals(minerals);
		}
		return minerals;
	}

	@Override
	public List<Component> listComponents(boolean includeMissing) {
		List<Type> allTypes = this.typeRepository.findAllComponents();
		List<Component> components = new ArrayList<>(allTypes.size());
		for (Type type : allTypes) {
			components.add(new ComponentImpl(type));
		}
		if (includeMissing) {
			addMissingComponents(components);
		}
		return components;
	}

	private void addMissingComponents(List<? super Component> components) {
		addComponents(this.inventoryTypeRepository.findUnknownComponents(), components);
	}

	private void addMissingMinerals(List<? super Mineral> missingTypes) {
		addMinerals(this.inventoryTypeRepository.findUnknownMinerals(), missingTypes);
	}

	@Override
	public List<? extends AbstractType> listMissingTypes() {
		List<AbstractType> missingTypes = new ArrayList<>();
		addMissingMinerals(missingTypes);
		addMissingComponents(missingTypes);
		return missingTypes;
	}

	private static void addMinerals(List<InventoryType> unknownTypes, List<? super Mineral> missingMinerals) {
		for (InventoryType type : unknownTypes) {
			assert type.isMineral();
			missingMinerals.add(new MissingMineralImpl(type));
		}
	}

	private static void addComponents(List<InventoryType> unknownTypes, List<? super Component> missingComponents) {
		for (InventoryType type : unknownTypes) {
			assert !type.isMineral();
			missingComponents.add(new MissingComponentImpl(type));
		}
	}

	@Override
	public List<? extends AbstractType> listMissingTypes(BlueprintReference blueprintRef) {
		Blueprint blueprint = this.blueprintResolverService.toBlueprint(blueprintRef);
		List<AbstractType> missingTypes = new ArrayList<>();
		addMinerals(this.inventoryTypeRepository.findUnknownMineralsForBlueprint(blueprint), missingTypes);
		addComponents(this.inventoryTypeRepository.findUnknownComponentsForBlueprint(blueprint), missingTypes);
		return missingTypes;
	}

}
