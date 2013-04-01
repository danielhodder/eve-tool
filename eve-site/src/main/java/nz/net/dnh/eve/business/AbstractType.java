package nz.net.dnh.eve.business;

import java.math.BigDecimal;
import java.util.Date;

public interface AbstractType {
	public String getName();

	public BigDecimal getCost();

	public Date getCostLastUpdated();
}
