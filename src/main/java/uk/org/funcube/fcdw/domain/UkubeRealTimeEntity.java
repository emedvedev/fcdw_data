// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;

import uk.org.funcube.fcdw.server.shared.UkubeRealTime;

@Entity
public class UkubeRealTimeEntity extends RealTimeEntity {

	public UkubeRealTimeEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UkubeRealTimeEntity(UkubeRealTime ukubeRealTime, Timestamp satelliteTime) {
		super();
		setSatelliteId(ukubeRealTime.getSatelliteId());
		setFrameType(ukubeRealTime.getFrameType());
		setSatelliteTime(satelliteTime);
	}
	
	

}
