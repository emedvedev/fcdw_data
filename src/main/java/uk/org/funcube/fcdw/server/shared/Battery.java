// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;


public class Battery {
	
	private Long batteryCurrent1;
	private Long batteryCurrent2;
	private Long batteryVoltage1;
	private Long batteryVoltage2;
	
	public Battery() {
		
	}

	/**
	 * @param batteryCurrent1
	 * @param batteryCurrent2
	 * @param batteryVoltage1
	 * @param batteryVoltage2
	 */
	public Battery(final Long batteryCurrent1, final Long batteryCurrent2, final Long batteryVoltage1, final Long batteryVoltage2) {
		super();
		this.batteryCurrent1 = batteryCurrent1;
		this.batteryCurrent2 = batteryCurrent2;
		this.batteryVoltage1 = batteryVoltage1;
		this.batteryVoltage2 = batteryVoltage2;
	}

	public final Long getBatteryCurrent1() {
		return batteryCurrent1;
	}

	public final Long getBatteryCurrent2() {
		return batteryCurrent2;
	}

	public final Long getBatteryVoltage1() {
		return batteryVoltage1;
	}

	public final Long getBatteryVoltage2() {
		return batteryVoltage2;
	}

}
