package uk.org.funcube.fcdw.service.rest.model;

import java.io.Serializable;

public abstract class AbstractWrappper implements Serializable {

	private static final long serialVersionUID = 6897439770300650368L;
	
	private Long satelliteId;

	protected AbstractWrappper(Long sateliteId) {
		satelliteId = sateliteId;
	}

	public final Long getSatelliteId() {
		return satelliteId;
	}

	public final void setSatelliteId(Long satelliteId) {
		this.satelliteId = satelliteId;
	}
}
