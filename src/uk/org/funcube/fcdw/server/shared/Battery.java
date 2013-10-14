// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;


public class Battery {
	
	private static final String ZERO_STRING = "0";
	private static final int CURRENT_BINARY_STRING_LENGTH = 16;
	private static final int CURRENT_DIRECTION_BINARY_STRING_LENGTH = 2;
	private static final int VOLTAGE_BINARY_STRING_LENGTH = 16;
	public static final int BINARY_STRING_LENGTH = 
		CURRENT_BINARY_STRING_LENGTH 
		+ CURRENT_DIRECTION_BINARY_STRING_LENGTH 
		+ VOLTAGE_BINARY_STRING_LENGTH;
	
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

	public Battery(final String binaryDataString) {
		
		for (int pos = 0; pos < CURRENT_BINARY_STRING_LENGTH; pos += 8) {
			final long value = Long.parseLong(binaryDataString.substring(pos, pos + 8), 2);
			switch (pos / 8) {
				case 0: batteryCurrent1 = value; break;
				case 1: batteryCurrent2 = value; break;
			}
		}
		
		for (int pos = CURRENT_BINARY_STRING_LENGTH; 
			pos < CURRENT_BINARY_STRING_LENGTH + CURRENT_DIRECTION_BINARY_STRING_LENGTH; 
			pos++) {
			
			final int value = (0 == Integer.parseInt(binaryDataString.substring(pos, pos + 1), 2)) ? 1 : -1;
			switch(pos - 16) {
				case 0: batteryCurrent1 *= value; break;
				case 1: batteryCurrent2 *= value; break;
			}
		}
		
		for (int pos = CURRENT_BINARY_STRING_LENGTH + CURRENT_DIRECTION_BINARY_STRING_LENGTH; 
			pos < CURRENT_BINARY_STRING_LENGTH 
				+ CURRENT_DIRECTION_BINARY_STRING_LENGTH 
				+ VOLTAGE_BINARY_STRING_LENGTH; 
			pos += 8) {
			
			final long value = Long.parseLong(binaryDataString.substring(pos, pos + 8), 2);
			switch ((pos - 18) / 8) {
				case 0: batteryVoltage1 = value; break;
				case 1: batteryVoltage2 = value; break;
			}
		}
	}

	public final Long getBatteryCurrent1() {
		return batteryCurrent1;
	}

	public final void setBatteryCurrent1(final Long batteryCurrent1) {
		this.batteryCurrent1 = batteryCurrent1;
	}

	public final Long getBatteryCurrent2() {
		return batteryCurrent2;
	}

	public final void setBatteryCurrent2(final Long batteryCurrent2) {
		this.batteryCurrent2 = batteryCurrent2;
	}

	public final Long getBatteryVoltage1() {
		return batteryVoltage1;
	}

	public final void setBatteryVoltage1(final Long batteryVoltage1) {
		this.batteryVoltage1 = batteryVoltage1;
	}

	public final Long getBatteryVoltage2() {
		return batteryVoltage2;
	}

	public final void setBatteryVoltage2(final Long batteryVoltage2) {
		this.batteryVoltage2 = batteryVoltage2;
	}

}
