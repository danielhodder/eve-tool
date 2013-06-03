package nz.net.dnh.eve.market.eve_central;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "evec_api")
public class EveCentralMarketStatResponse {
	@XmlElement(name = "type")
	@XmlElementWrapper(name = "marketstat")
	private List<Type> types;

	public List<Type> getTypes() {
		return this.types;
	}

	public void setTypes(final List<Type> types) {
		this.types = types;
	}

	public static class MarketStatData {
		// @XmlElement
		// private long volume;

		@XmlElement(name = "avg")
		private BigDecimal average;

		// @XmlElement(name = "max")
		// private BigDecimal maximum;
		//
		// @XmlElement(name = "min")
		// private BigDecimal minimum;
		//
		// @XmlElement(name = "stddev")
		// private BigDecimal standardDeviation;

		@XmlElement
		private BigDecimal median;

		public BigDecimal getAverage() {
			return this.average;
		}

		public void setAverage(final BigDecimal average) {
			this.average = average;
		}

		public BigDecimal getMedian() {
			return this.median;
		}

		public void setMedian(final BigDecimal median) {
			this.median = median;
		}

		// @XmlElement
		// private BigDecimal percentile;
	}

	public static class Type {
		@XmlAttribute
		private int id;

		@XmlElement
		private MarketStatData buy;

		@XmlElement
		private MarketStatData sell;

		@XmlElement
		private MarketStatData all;

		public int getId() {
			return this.id;
		}

		public void setId(final int id) {
			this.id = id;
		}

		public MarketStatData getBuy() {
			return this.buy;
		}

		public void setBuy(final MarketStatData buy) {
			this.buy = buy;
		}

		public MarketStatData getSell() {
			return this.sell;
		}

		public void setSell(final MarketStatData sell) {
			this.sell = sell;
		}

		public MarketStatData getAll() {
			return this.all;
		}

		public void setAll(final MarketStatData all) {
			this.all = all;
		}
	}
}