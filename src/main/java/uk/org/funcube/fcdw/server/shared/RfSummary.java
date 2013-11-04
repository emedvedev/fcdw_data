// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;

/**
 * @author  g4dpz
 */
public class RfSummary {
	
	private String rfTemperature;
	private String receiveCurrent;
	private String transmitCurrentBusThree;
	private String transmitCurrentBusFive;

	public RfSummary(String rfTemperature, String receiveCurrent,
			String transmitCurrentBusThree, String transmitCurrentBusFive) {
		super();
		this.rfTemperature = rfTemperature;
		this.receiveCurrent = receiveCurrent;
		this.transmitCurrentBusThree = transmitCurrentBusThree;
		this.transmitCurrentBusFive = transmitCurrentBusFive;
	}

	public final String getRfTemperature() {
		return rfTemperature;
	}

	public final String getReceiveCurrent() {
		return receiveCurrent;
	}

	public final String getTransmitCurrentBusThree() {
		return transmitCurrentBusThree;
	}

	public final String getTransmitCurrentBusFive() {
		return transmitCurrentBusFive;
	}
	
}