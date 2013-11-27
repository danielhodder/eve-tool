package nz.net.dnh.eve.model.repository;

import static nz.net.dnh.eve.model.predicate.InventoryTypePredicates.isMineral;

import java.util.List;

import nz.net.dnh.eve.config.AvoidComponentScan;
import nz.net.dnh.eve.model.domain.QBlueprintRequiredType;
import nz.net.dnh.eve.model.domain.QType;
import nz.net.dnh.eve.model.raw.InventoryGroup;
import nz.net.dnh.eve.model.raw.InventoryType;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Service;

@Service
@AvoidComponentScan
public class InventoryTypeRepositoryImpl extends QueryDslRepositorySupport implements InventoryTypeRepositoryCustom {
	public InventoryTypeRepositoryImpl() { super(InventoryType.class); }
	
	@Override
	public List<InventoryType> findUnknownMinerals() {
		QBlueprintRequiredType blueprintRequiredType = QBlueprintRequiredType.blueprintRequiredType;
		QType type = QType.type1;
		
		return from(blueprintRequiredType)
				.leftJoin(blueprintRequiredType.type, type)
				.where(type.cost.isNull())
				.where(isMineral())
				.distinct().list(blueprintRequiredType.inventoryType);
	}
}