package uk.org.funcube.fcdw.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SatelliteStatus")
public class SatelliteStatusEntity implements SatelliteStatus {
	
	@Id
	Long satelliteId;
	
	Long sequenceNumber;
	Boolean eclipseModeForced;
	Boolean eclipsed;
	Timestamp lastUpdated;
	
	public SatelliteStatusEntity() {
	}

	public final Long getSatelliteId() {
		return satelliteId;
	}

	public final void setSatelliteId(Long satelliteId) {
		this.satelliteId = satelliteId;
	}

	public final Long getSequenceNumber() {
		return sequenceNumber;
	}

	public final void setSequenceNumber(Long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public final Boolean isEclipseModeForced() {
		return eclipseModeForced;
	}

	public final void setEclipseModeForced(Boolean eclipseModeForced) {
		this.eclipseModeForced = eclipseModeForced;
	}

	public final Boolean isEclipsed() {
		return eclipsed;
	}

	public final void setEclipsed(Boolean eclipsed) {
		this.eclipsed = eclipsed;
	}

	public final Timestamp getLastUpdated() {
		return lastUpdated;
	}

	public final void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
