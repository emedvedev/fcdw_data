// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;

/**
 * @author  g4dpz
 */
public class PaSummary {
	
	private String forwardPower;
	private String reversePower;
	private String paDeviceTemperature;
	private String paBusCurrent;
	/**
	 * @param forwardPower
	 * @param reversePower
	 * @param paDeviceTemperature
	 * @param paBusCurrent
	 */
	public PaSummary(String forwardPower, String reversePower,
			String paDeviceTemperature, String paBusCurrent) {
		super();
		this.forwardPower = forwardPower;
		this.reversePower = reversePower;
		this.paDeviceTemperature = paDeviceTemperature;
		this.paBusCurrent = paBusCurrent;
	}
	public final String getForwardPower() {
		return forwardPower;
	}
	public final String getReversePower() {
		return reversePower;
	}
	public final String getPaDeviceTemperature() {
		return paDeviceTemperature;
	}
	public final String getPaBusCurrent() {
		return paBusCurrent;
	}
	
	
	
}