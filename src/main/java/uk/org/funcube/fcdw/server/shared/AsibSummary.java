// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;

/**
 * @author  g4dpz
 */
public class AsibSummary {
	
	private String solarPanelTempXplus;
	private String solarPanelTempXminus;
	private String solarPanelTempYplus;
	private String solarPanelTempYminus;
	private String busVoltageThree;
	private String busCurrentThree;
	private String busVoltageFive;
	
	public AsibSummary(String solarPanelTempXplus, String solarPanelTempXminus,
			String solarPanelTempYplus, String solarPanelTempYminus,
			String busVoltageThree, String busCurrentThree,
			String busVoltageFive) {
		super();
		this.solarPanelTempXplus = solarPanelTempXplus;
		this.solarPanelTempXminus = solarPanelTempXminus;
		this.solarPanelTempYplus = solarPanelTempYplus;
		this.solarPanelTempYminus = solarPanelTempYminus;
		this.busVoltageThree = busVoltageThree;
		this.busCurrentThree = busCurrentThree;
		this.busVoltageFive = busVoltageFive;
	}

	public final String getSolarPanelTempXplus() {
		return solarPanelTempXplus;
	}

	public final String getSolarPanelTempXminus() {
		return solarPanelTempXminus;
	}

	public final String getSolarPanelTempYplus() {
		return solarPanelTempYplus;
	}

	public final String getSolarPanelTempYminus() {
		return solarPanelTempYminus;
	}

	public final String getBusVoltageThree() {
		return busVoltageThree;
	}

	public final String getBusCurrentThree() {
		return busCurrentThree;
	}

	public final String getBusVoltageFive() {
		return busVoltageFive;
	}
	
}