package nz.net.dnh.eve.model.domain;

import java.sql.Timestamp;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public class AbstractLastUpdatedBean {

	@NotNull
	private Timestamp lastUpdated;

	/**
	 * Update the {@link #getLastUpdated()} field to be the current time
	 */
	public void touchLastUpdated() {
		this.lastUpdated = new Timestamp(System.currentTimeMillis());
	}

	public Timestamp getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(final Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}