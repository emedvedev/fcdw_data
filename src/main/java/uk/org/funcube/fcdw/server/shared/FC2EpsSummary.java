// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;

public class FC2EpsSummary {

	private String battery0Current;
	private String battery0Volts;
	private String battery0Temperature;
	private String battery1Current;
	private String battery1Volts;
	private String battery1Temperature;
	private String battery2Current;
	private String battery2Volts;
	private String battery2Temperature;
	
	public FC2EpsSummary(String battery0Current, String battery0Volts,
			String battery0Temperature, String battery1Current,
			String battery1Volts, String battery1Temperature,
			String battery2Current, String battery2Volts,
			String battery2Temperature) {
		super();
		this.battery0Current = battery0Current;
		this.battery0Volts = battery0Volts;
		this.battery0Temperature = battery0Temperature;
		this.battery1Current = battery1Current;
		this.battery1Volts = battery1Volts;
		this.battery1Temperature = battery1Temperature;
		this.battery2Current = battery2Current;
		this.battery2Volts = battery2Volts;
		this.battery2Temperature = battery2Temperature;
	}

	public final String getBattery0Current() {
		return battery0Current;
	}

	public final String getBattery0Volts() {
		return battery0Volts;
	}

	public final String getBattery0Temperature() {
		return battery0Temperature;
	}

	public final String getBattery1Current() {
		return battery1Current;
	}

	public final String getBattery1Volts() {
		return battery1Volts;
	}

	public final String getBattery1Temperature() {
		return battery1Temperature;
	}

	public final String getBattery2Current() {
		return battery2Current;
	}

	public final String getBattery2Volts() {
		return battery2Volts;
	}

	public final String getBattery2Temperature() {
		return battery2Temperature;
	}
	
	

}
