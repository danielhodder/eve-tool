package nz.net.dnh.eve.web;

import java.math.BigDecimal;
import java.util.Arrays;

import nz.net.dnh.eve.business.BlueprintIdReference;
import nz.net.dnh.eve.business.BlueprintService;
import nz.net.dnh.eve.market.eve_central.EveCentralMarketStatRequester;
import nz.net.dnh.eve.market.eve_central.EveCentralMarketStatResponse;
import nz.net.dnh.eve.market.eve_central.EveCentralMarketUpdator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PriceController {
	@Autowired
	private EveCentralMarketStatRequester remoteMarketData;

	@Autowired
	private BlueprintService blueprintService;

	@Autowired
	private EveCentralMarketUpdator marketDataUpdater;

	private static final Logger logger = LoggerFactory.getLogger(PriceController.class);

	@RequestMapping(value = "/price/{type_id}", method = RequestMethod.GET)
	public @ResponseBody
	Response getPriceForType(@PathVariable("type_id") final int typeId) {
		try {
			final EveCentralMarketStatResponse response =
					this.remoteMarketData.getDataForType(Arrays.asList(new Integer[] { typeId }));
			
			return new Response(response.getTypes().get(0).getSell().getMedian());
		} catch (final Exception e) {
			logger.warn("Could not get market data for type: " + typeId, e);

			return new Response(new BigDecimal(-1));
		}
	}

	@RequestMapping(value = "/price/blueprint/{blueprint_id}", method = RequestMethod.GET)
	public @ResponseBody
	Response getPriceForBlueprint(@PathVariable("blueprint_id") final int blueprint_id) {
		logger.info("Looking up information for blueprint: {}", blueprint_id);

		return new Response(this.blueprintService.getMarketPrice(new BlueprintIdReference(blueprint_id)));
	}

	@RequestMapping(value = "/price/update_all", method = RequestMethod.POST)
	public @ResponseBody
	boolean updateAllPrices() {
		try {
			this.marketDataUpdater.doUpdate();

			return true;
		} catch (final Exception e) {
			logger.error("Error in updating price data", e);
			return false;
		}
	}

	private final class Response {
		private final BigDecimal value;

		private Response(final BigDecimal value) {
			this.value = value;
		}

		@SuppressWarnings("unused")
		public BigDecimal getValue() {
			return this.value;
		}
	}
}