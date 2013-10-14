// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

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
