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

	@Override
	public List<Mineral> listMinerals() {
		List<Type> allTypes = this.typeRepository.findAllMinerals();
		List<Mineral> minerals = new ArrayList<>(allTypes.size());
		for (Type type : allTypes) {
			minerals.add(new MineralImpl(type));
		}
		return minerals;
	}

	@Override
	public List<Component> listComponents() {
		List<Type> allTypes = this.typeRepository.findAllComponents();
		List<Component> components = new ArrayList<>(allTypes.size());
		for (Type type : allTypes) {
			components.add(new ComponentImpl(type));
		}
		return components;
	}

	@Override
	public List<? extends AbstractType> listMissingTypes() {
		List<InventoryType> unknownTypes = this.inventoryTypeRepository
				.findUnknownTypes();
		return toTypes(unknownTypes);
	}

	private static List<? extends AbstractType> toTypes(
			List<InventoryType> unknownTypes) {
		List<AbstractType> missingTypes = new ArrayList<>(unknownTypes.size());
		for (InventoryType type : unknownTypes) {
			if (type.isMineral()) {
				missingTypes.add(new MissingMineralImpl(type));
			} else {
				missingTypes.add(new MissingComponentImpl(type));
			}
		}
		return missingTypes;
	}

	@Override
	public List<? extends AbstractType> listMissingTypes(
			BlueprintReference blueprintRef) {
		Blueprint blueprint = BlueprintReferenceUtil.toBlueprint(blueprintRef);
		List<InventoryType> unknownTypes = this.inventoryTypeRepository
				.findUnknownTypesForBlueprint(blueprint);
		return toTypes(unknownTypes);
	}

}
