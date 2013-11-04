// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;

/**
 * @author  g4dpz
 */
public class EpsSummary {
	
	String totalPhotoCurrent;
	String batteryVoltage;
	String totalSystemCurrent;
	String batteryTemp;
	
	public EpsSummary(String totalPhotoCurrent, String batteryVoltage,
			String totalSystemCurrent, String batteryTemp) {
		super();
		this.totalPhotoCurrent = totalPhotoCurrent;
		this.batteryVoltage = batteryVoltage;
		this.totalSystemCurrent = totalSystemCurrent;
		this.batteryTemp = batteryTemp;
	}

	public final String getTotalPhotoCurrent() {
		return totalPhotoCurrent;
	}

	public final String getBatteryVoltage() {
		return batteryVoltage;
	}

	public final String getTotalSystemCurrent() {
		return totalSystemCurrent;
	}

	public final String getBatteryTemp() {
		return batteryTemp;
	}
}