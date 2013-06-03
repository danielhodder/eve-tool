package nz.net.dnh.eve.business;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Provides access to Blueprints
 */
@Service
public interface BlueprintService {
	/**
	 * Retrieve summary information about all blueprints in the system
	 * 
	 * @return summaries of all blueprints
	 */
	public List<BlueprintSummary> listSummaries();

	/**
	 * Retrieve detailed information about the given blueprint
	 * 
	 * @param blueprint
	 *            A reference to a blueprint to retrieve information about
	 * @return Detailed information about the given blueprint, never null
	 * @throws BlueprintNotFoundException
	 *             if the blueprint is not found in the database
	 * @see BlueprintIdReference
	 */
	public BlueprintSummary getBlueprint(BlueprintReference blueprint);

	/**
	 * Retrieve a page of candidate blueprints; that is, blueprints that do not
	 * exist in our database, but could be added.
	 * <p>
	 * This method is paged because it returns a <b>lot</b> of results.
	 * 
	 * @param page
	 *            The page to retrieve. Sorting parameters in the page should
	 *            not be provided
	 * @return A page of candidate blueprints
	 * @see PageRequest
	 * @see CandidateBlueprint
	 */
	public Page<CandidateBlueprint> listCandidateBlueprints(Pageable page);

	/**
	 * Retrieve a page of candidate blueprints; that is, blueprints that do not
	 * exist in our database, but could be added.
	 * <p>
	 * This method is paged because it returns a <b>lot</b> of results.
	 * 
	 * @param search
	 *            The search string to find candidate blueprints with
	 * @param page
	 *            The page to retrieve. Sorting parameters in the page should
	 *            not be provided
	 * @return A page of candidate blueprints
	 * @see PageRequest
	 * @see CandidateBlueprint
	 */
	public Page<CandidateBlueprint> findCandidateBlueprints(String search, Pageable page);

	/**
	 * Create a blueprint that does not already exist in the database
	 * 
	 * @param blueprint
	 *            a reference to the blueprint to create
	 * @param saleValue
	 *            The sale value of the blueprint, in Isk.
	 *            {@link BlueprintSummary#getSaleValue()}
	 * @param numberPerRun
	 *            The number per run. {@link BlueprintSummary#getNumberPerRun()}
	 * @param productionEfficiency
	 *            The production efficiency.
	 *            {@link BlueprintSummary#getProductionEfficiency()}
	 * @param materialEfficiency
	 *            The material efficiency.
	 *            {@link BlueprintSummary#getMaterialEfficiency()}
	 * @return The created blueprint
	 * @see #listCandidateBlueprints(Pageable)
	 * @see CandidateBlueprint
	 */
	public BlueprintSummary createBlueprint(BlueprintReference blueprint, BigDecimal saleValue, int numberPerRun, int productionEfficiency,
			int materialEfficiency, Boolean automaticSalePriceUpdate);

	/**
	 * Modify an existing blueprint. Parameters can be null to keep the current
	 * value.
	 * 
	 * @param blueprint
	 *            a reference to the blueprint to edit
	 * @param saleValue
	 *            The sale value of the blueprint, in Isk. May be null to keep
	 *            the current value. {@link BlueprintSummary#getSaleValue()}
	 * @param numberPerRun
	 *            The number per run. May be null to keep the current value.
	 *            {@link BlueprintSummary#getNumberPerRun()}
	 * @param productionEfficiency
	 *            The production efficiency. May be null to keep the current
	 *            value. {@link BlueprintSummary#getHours()}
	 * @param materialEfficiency
	 *            The material efficiency. May be null to keep the current
	 *            value. {@link BlueprintSummary#getMaterialEfficiency()}
	 * @param automaticSalePriceUpdate
	 *            A boolean flag indicating that this blueprint's price should
	 *            be updated automatically.
	 * @return The modified blueprint
	 * @see #listSummaries()
	 * @see BlueprintSummary
	 */
	public BlueprintSummary editBlueprint(BlueprintReference blueprint, BigDecimal saleValue, Integer numberPerRun,
			Integer productionEfficiency, Integer materialEfficiency, Boolean automaticSalePriceUpdate);

	/**
	 * Gets the sale value of one item of the type the blueprint makes. Returns
	 * either the latest data in the data source or fetches from a remote source
	 * and stores that if appropriate.
	 * 
	 * @param blueprint
	 * @return The sale price of one item that this blueprint makes
	 */
	public BigDecimal getMarketPrice(BlueprintReference blueprint);

	/**
	 * Get all the blueprints that are marked for automatic updating
	 * 
	 * @return
	 */
	public Collection<BlueprintSummary> getBlueprintsForAutomaticUpdate();
}
