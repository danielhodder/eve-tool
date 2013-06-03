package nz.net.dnh.eve.business;

import java.math.BigDecimal;

public class MarketPrice {
	private final BigDecimal average;
	private final BigDecimal median;

	public MarketPrice(final BigDecimal average, final BigDecimal median) {
		this.average = average;
		this.median = median;
	}

	@Override
	public String toString() {
		return "MarketPrice [average=" + this.average + ", median="
				+ this.median + "]";
	}

	public BigDecimal getAverage() {
		return this.average;
	}

	public BigDecimal getMedian() {
		return this.median;
	}
}
