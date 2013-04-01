package nz.net.dnh.eve.business.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import nz.net.dnh.eve.business.Component;
import nz.net.dnh.eve.business.Mineral;
import nz.net.dnh.eve.model.domain.Type;
import nz.net.dnh.eve.model.repository.InventoryTypeRepository;
import nz.net.dnh.eve.model.repository.TypeRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TypeServiceTest {
	private static final Type TYPE_1 = mock(Type.class);
	private static final Type TYPE_2 = mock(Type.class);

	static {
		when(TYPE_1.getTypeName()).thenReturn("Type 1");
		when(TYPE_2.getTypeName()).thenReturn("Type 2");
		when(TYPE_1.getCost()).thenReturn(new BigDecimal(15));
		when(TYPE_2.getCost()).thenReturn(new BigDecimal(40));
		when(TYPE_1.getLastUpdated()).thenReturn(new Timestamp(1000));
		when(TYPE_2.getLastUpdated()).thenReturn(new Timestamp(11111111));
	}

	@InjectMocks
	private final TypeServiceImpl typeService = new TypeServiceImpl();

	@Mock
	private TypeRepository typeRepository;

	@Mock
	private InventoryTypeRepository inventoryTypeRepository;

	@Test
	public void testListComponents() {
		when(this.typeRepository.findAllComponents()).thenReturn(
				Arrays.asList(TYPE_1, TYPE_2));

		List<Component> components = this.typeService.listComponents();

		assertEquals(2, components.size());

		Component component1 = components.get(0);
		assertEquals("Type 1", component1.getName());
		assertEquals(new BigDecimal(15), component1.getCost());
		assertEquals(new Timestamp(1000), component1.getCostLastUpdated());

		Component component2 = components.get(1);
		assertEquals("Type 2", component2.getName());
		assertEquals(new BigDecimal(40), component2.getCost());
		assertEquals(new Timestamp(11111111), component2.getCostLastUpdated());
	}

	@Test
	public void testListMinerals() {
		when(this.typeRepository.findAllMinerals()).thenReturn(
				Arrays.asList(TYPE_2, TYPE_1));

		List<Mineral> minerals = this.typeService.listMinerals();

		assertEquals(2, minerals.size());

		Mineral mineral1 = minerals.get(0);
		assertEquals("Type 2", mineral1.getName());
		assertEquals(new BigDecimal(40), mineral1.getCost());
		assertEquals(new Timestamp(11111111), mineral1.getCostLastUpdated());

		Mineral mineral2 = minerals.get(1);
		assertEquals("Type 1", mineral2.getName());
		assertEquals(new BigDecimal(15), mineral2.getCost());
		assertEquals(new Timestamp(1000), mineral2.getCostLastUpdated());
	}
}
