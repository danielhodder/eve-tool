/**
 * 
 */
package nz.net.dnh.eve.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.HashMap;
import java.util.Map;

import nz.net.dnh.eve.business.ShoppingCartService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Creates a shopping cart by calling the {@link ShoppingCartService} and presenting that to the user
 * 
 * @author Daniel Hodder (danielh)
 * 
 */
@Controller
@RequestMapping(value = "/shopping-cart")
public class ShoppingCartController {
	/**
	 * Populate and show a new shopping cart form to the user.
	 * 
	 * @return The view that should be rendered and the form that should be used to create the shopping cart
	 */
	@RequestMapping(value = "/new", method = GET)
	public ModelAndView newShoppingCart() {
		return new ModelAndView("shopping-cart/form", "shopping-cart-form", new ShoppingCartForm());
	}
	
	@RequestMapping(method = POST)
	public ModelAndView showShoppingCart(@ModelAttribute(value = "shopping-cart-form") final ShoppingCartForm form) {
		// TODO
		return new ModelAndView("shopping-cart/show", "shopping-cart", null);
	}

	public static class ShoppingCartForm {
		private Map<Integer, Integer> blueprintIDToQuantityMap = new HashMap<>();

		public Map<Integer, Integer> getBlueprintIDToQuantityMap() {
			return this.blueprintIDToQuantityMap;
		}

		public void setBlueprintIDToQuantityMap(final Map<Integer, Integer> blueprintIDToQuantityMap) {
			this.blueprintIDToQuantityMap = blueprintIDToQuantityMap;
		}
	}
}
