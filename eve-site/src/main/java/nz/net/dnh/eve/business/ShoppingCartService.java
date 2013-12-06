package nz.net.dnh.eve.business;

/**
 * The shopping cart service is an aggregation service. In essence it allows you to total up all of the required
 * components needed to build many different things in one batch.
 * 
 * @author Daniel Hodder (danielh)
 */
public interface ShoppingCartService {
	/**
	 * Creates a shopping cart from a collection of blueprints and related information. This will resolve all materials
	 * and components required to make each blueprint. Then total these up while adding the multipliers as required.
	 * 
	 * @param cart
	 * @return
	 */
	ShoppingCart createShoppingCart(BlueprintCollection cart);
}
