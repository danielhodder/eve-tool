package nz.net.dnh.eve.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nz.net.dnh.eve.business.AbstractType;
import nz.net.dnh.eve.business.BlueprintReference;
import nz.net.dnh.eve.business.Component;
import nz.net.dnh.eve.business.Mineral;
import nz.net.dnh.eve.business.RequiredTypes;
import nz.net.dnh.eve.business.TypeService;
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
	public List<Mineral> listMinerals(final boolean includeMissing) {
		final List<Type> allTypes = this.typeRepository.findAllMinerals();
		final List<Mineral> minerals = new ArrayList<>(allTypes.size());
		for (final Type type : allTypes) {
			minerals.add(new MineralImpl(type));
		}
		if (includeMissing) {
			addMissingMinerals(minerals);
		}
		return minerals;
	}

	@Override
	public List<Component> listComponents(final boolean includeMissing) {
		final List<Type> allTypes = this.typeRepository.findAllComponents();
		final List<Component> components = new ArrayList<>(allTypes.size());
		for (final Type type : allTypes) {
			components.add(new ComponentImpl(type));
		}
		if (includeMissing) {
			addMissingComponents(components);
		}
		return components;
	}

	private void addMissingComponents(final List<? super Component> components) {
		addComponents(this.inventoryTypeRepository.findUnknownComponents(), components);
	}

	private void addMissingMinerals(final List<? super Mineral> missingTypes) {
		addMinerals(this.inventoryTypeRepository.findUnknownMinerals(), missingTypes);
	}

	@Override
	public List<? extends AbstractType> listMissingTypes() {
		final List<AbstractType> missingTypes = new ArrayList<>();
		addMissingMinerals(missingTypes);
		addMissingComponents(missingTypes);
		return missingTypes;
	}

	private static void addMinerals(final List<InventoryType> unknownTypes, final List<? super Mineral> missingMinerals) {
		for (final InventoryType type : unknownTypes) {
			assert type.isMineral();
			missingMinerals.add(new MissingMineralImpl(type));
		}
	}

	private static void addComponents(final List<InventoryType> unknownTypes, final List<? super Component> missingComponents) {
		for (final InventoryType type : unknownTypes) {
			assert !type.isMineral();
			missingComponents.add(new MissingComponentImpl(type));
		}
	}

	@Override
	public List<? extends AbstractType> listMissingTypes(final BlueprintReference blueprintRef) {
		final Blueprint blueprint = this.blueprintResolverService.toBlueprint(blueprintRef);
		final List<AbstractType> missingTypes = new ArrayList<>();
		addMinerals(this.inventoryTypeRepository.findUnknownMineralsForBlueprint(blueprint), missingTypes);
		addComponents(this.inventoryTypeRepository.findUnknownComponentsForBlueprint(blueprint), missingTypes);
		return missingTypes;
	}

	@Override
	public RequiredTypes getRequiredTypes(final BlueprintReference blueprintRef) {
		final Blueprint blueprint = this.blueprintResolverService.toBlueprint(blueprintRef);
		final Map<Component, Integer> requiredComponents = new HashMap<>();
		final Map<Mineral, Integer> requiredMinerals = new HashMap<>();

		for (final BlueprintRequiredType requiredType : blueprint.getRequiredTypes()) {
			final int units = requiredType.getUnits();
			final Type type = requiredType.getType();
			final InventoryType inventoryType = requiredType.getInventoryType();
			if (inventoryType.isMineral()) {
				final Mineral mineral;
				if (type != null) {
					mineral = new MineralImpl(type);
				} else {
					mineral = new MissingMineralImpl(inventoryType);
				}
				requiredMinerals.put(mineral, units);
			} else {
				final Component component;
				if (type != null) {
					component = new ComponentImpl(type);
				} else {
					component = new MissingComponentImpl(inventoryType);
				}
				requiredComponents.put(component, units);
			}
		}
		return new RequiredTypes(requiredComponents, requiredMinerals);
	}
}
