// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;

/**
 * @author  g4dpz
 */
public class SoftwareSummary {
	
	private String sequenceNumber;
	private String dataValidAsib;
	private String dataValidEps;
	private String dataValidPa;
	private String dataValidRf;
	private String dataValidMse;
	private String dataValidAntsBusB;
	private String dataValidAntsBusA;
	private String inEclipseMode;
	private String inSafeMode;
	/**
	 * @param sequenceNumber
	 * @param dataValidAsib
	 * @param dataValidEps
	 * @param dataValidPa
	 * @param dataValidRf
	 * @param dataValidMse
	 * @param dataValidAntsBusB
	 * @param dataValidAntsBusA
	 * @param inEclipseMode
	 * @param inSafeMode
	 */
	public SoftwareSummary(String sequenceNumber, String dataValidAsib,
			String dataValidEps, String dataValidPa, String dataValidRf,
			String dataValidMse, String dataValidAntsBusB,
			String dataValidAntsBusA, String inEclipseMode, String inSafeMode) {
		super();
		this.sequenceNumber = sequenceNumber;
		this.dataValidAsib = dataValidAsib;
		this.dataValidEps = dataValidEps;
		this.dataValidPa = dataValidPa;
		this.dataValidRf = dataValidRf;
		this.dataValidMse = dataValidMse;
		this.dataValidAntsBusB = dataValidAntsBusB;
		this.dataValidAntsBusA = dataValidAntsBusA;
		this.inEclipseMode = inEclipseMode;
		this.inSafeMode = inSafeMode;
	}
	public final String getSequenceNumber() {
		return sequenceNumber;
	}
	public final String getDataValidAsib() {
		return dataValidAsib;
	}
	public final String getDataValidEps() {
		return dataValidEps;
	}
	public final String getDataValidPa() {
		return dataValidPa;
	}
	public final String getDataValidRf() {
		return dataValidRf;
	}
	public final String getDataValidMse() {
		return dataValidMse;
	}
	public final String getDataValidAntsBusB() {
		return dataValidAntsBusB;
	}
	public final String getDataValidAntsBusA() {
		return dataValidAntsBusA;
	}
	public final String getInEclipseMode() {
		return inEclipseMode;
	}
	public final String getInSafeMode() {
		return inSafeMode;
	}
	
	
	
}