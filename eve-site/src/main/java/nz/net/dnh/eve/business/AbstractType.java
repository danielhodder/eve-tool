package nz.net.dnh.eve.business;

import java.math.BigDecimal;
import java.util.Date;

/**
 * A type required to build a blueprint
 *
 * @see Component
 * @see Mineral
 */
public interface AbstractType {
	/**
	 * @return The name of the type
	 */
	public String getName();

	/**
	 * @return The cost of buying an instance of the type. May be null if the type does not exist in the database
	 */
	public BigDecimal getCost();

	/**
	 * @return The date and time the cost was last updated. May be null if the type does not exist in the database
	 */
	public Date getCostLastUpdated();

	/**
	 * @return True if the type is missing from the database
	 */
	public boolean isMissing();

	/**
	 * @return The id of the type
	 */
	public int getId();
}
