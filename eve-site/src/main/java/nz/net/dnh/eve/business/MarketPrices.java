package nz.net.dnh.eve.business;

import java.util.Collections;
import java.util.Map;

public class MarketPrices<T> {
	private final Map<T, MarketPrice> prices;

	public MarketPrices(final Map<T, MarketPrice> prices) {
		this.prices = Collections.unmodifiableMap(prices);
	}

	public Map<T, MarketPrice> getPrices() {
		return this.prices;
	}
}
