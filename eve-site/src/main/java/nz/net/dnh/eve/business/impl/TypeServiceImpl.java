package nz.net.dnh.eve.business.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nz.net.dnh.eve.business.AbstractType;
import nz.net.dnh.eve.business.BlueprintReference;
import nz.net.dnh.eve.business.Component;
import nz.net.dnh.eve.business.Mineral;
import nz.net.dnh.eve.business.RequiredTypes;
import nz.net.dnh.eve.business.TypeReference;
import nz.net.dnh.eve.business.TypeService;
import nz.net.dnh.eve.business.impl.dto.type.AbstractMissingTypeImpl;
import nz.net.dnh.eve.business.impl.dto.type.AbstractMissingTypeImpl.MissingComponentImpl;
import nz.net.dnh.eve.business.impl.dto.type.AbstractMissingTypeImpl.MissingMineralImpl;
import nz.net.dnh.eve.business.impl.dto.type.AbstractTypeImpl;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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

	@Override
	public Mineral createMissingMineral(final TypeReference id, final BigDecimal cost) {
		final Type savedType = createMissingType(cost, true, id);
		return new MineralImpl(savedType);
	}

	@Override
	public Component createMissingComponent(final TypeReference id, final BigDecimal cost) {
		final Type savedType = createMissingType(cost, false, id);
		return new ComponentImpl(savedType);
	}

	private Type createMissingType(final BigDecimal cost, final boolean isMineral, final TypeReference id) {
		final InventoryType inventoryType = toInventoryType(id);
		if (inventoryType == null)
			throw new IllegalArgumentException("Unknown type with id " + id.getId());
		validateInventoryType(isMineral, inventoryType);

		final Type newType = new Type();
		newType.setTypeID(id.getId());
		updateCostAndLastUpdated(newType, cost);
		return this.typeRepository.save(newType);
	}

	@Override
	public Mineral updateMineral(final TypeReference type, final BigDecimal cost) {
		final Type savedType = updateType(type, cost, true);
		return new MineralImpl(savedType);
	}

	@Override
	public Component updateComponent(final TypeReference type, final BigDecimal cost) {
		final Type savedType = updateType(type, cost, false);
		return new ComponentImpl(savedType);
	}

	private Type updateType(final TypeReference type, final BigDecimal cost, final boolean mineral) {
		final Type persistentType = getTypeById(type, mineral);
		if (persistentType == null)
			throw new IllegalArgumentException("Unknown type with id " + type.getId());
		updateCostAndLastUpdated(persistentType, cost);
		return this.typeRepository.save(persistentType);
	}

	private static void updateCostAndLastUpdated(final Type type, final BigDecimal cost) {
		type.setCost(cost);
		type.setLastUpdated(new Timestamp(System.currentTimeMillis()));
	}

	@Override
	public Component getComponent(final TypeReference id) {
		final Type componentType = getTypeById(id, false);
		if (componentType == null)
			return null;

		return new ComponentImpl(componentType);
	}

	@Override
	public Mineral getMineral(final TypeReference id) {
		final Type mineralType = getTypeById(id, true);
		if (mineralType == null)
			return null;

		return new MineralImpl(mineralType);
	}

	private Type getTypeById(final TypeReference typeReference, final boolean mineral) {
		final Type type = toType(typeReference);
		if (type != null)
			validateInventoryType(mineral, type.getType());
		return type;
	}

	/**
	 * Unwrap or retrieve the type for the given type reference
	 */
	Type toType(final TypeReference typeReference) {
		if (typeReference instanceof AbstractTypeImpl)
			return ((AbstractTypeImpl) typeReference).toType();
		return this.typeRepository.findOne(typeReference.getId());
	}

	/**
	 * Unwrap or retrieve the inventory type for the given type reference
	 */
	InventoryType toInventoryType(final TypeReference typeReference) {
		if (typeReference instanceof AbstractTypeImpl)
			return ((AbstractTypeImpl) typeReference).toType().getType();
		if (typeReference instanceof AbstractMissingTypeImpl)
			return ((AbstractMissingTypeImpl) typeReference).toInventoryType();
		return this.inventoryTypeRepository.findOne(typeReference.getId());
	}

	/**
	 * Validate that the given inventory type is a mineral iff the mineral flag
	 * is true
	 * 
	 * @throws IllegalArgumentException
	 *             if the given inventory type is a mineral and the mineral flag
	 *             is false, or vice versa
	 */
	private void validateInventoryType(final boolean mineral, final InventoryType inventoryType) {
		if (inventoryType.isMineral() != mineral)
			throw new IllegalArgumentException(String.format("The type with id %d is a %s but needed a %s", inventoryType.getTypeID(),
					mineral ? "component" : "mineral", mineral ? "mineral" : "component"));
	}
}
