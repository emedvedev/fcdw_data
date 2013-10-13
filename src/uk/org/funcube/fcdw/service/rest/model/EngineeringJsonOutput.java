package uk.org.funcube.fcdw.service.rest.model;

import java.io.Serializable;

public class EngineeringJsonOutput implements Serializable, JsonOutput {

	private static final long serialVersionUID = 7664601945564472159L;
	
	private Long satelliteId;

	public EngineeringJsonOutput(final Long satelliteId) {
		this.satelliteId = satelliteId;
	}

	public final Long getSatelliteId() {
		return satelliteId;
	}

	public final void setSatelliteId(Long satelliteId) {
		this.satelliteId = satelliteId;
	}
	
}
