// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;

import org.apache.commons.lang3.StringUtils;

public class Antenna {
	
	private static final String ONE = "1";
	private static final String ZERO = "0";
	private static final int TEMPERATURE_BINARY_STRING_LENGTH = 16;
	private static final int DEPLOYMENT_BINARY_STRING_LENGTH = 4;
	public static final int BINARY_STRING_LENGTH = TEMPERATURE_BINARY_STRING_LENGTH + DEPLOYMENT_BINARY_STRING_LENGTH;
	
	private Long antennaTemp0;
    private Long antennaTemp1;

    private boolean antennaDeployment0;
    private boolean antennaDeployment1;
    private boolean antennaDeployment2;
    private boolean antennaDeployment3;


	public Antenna() {
	}

	/**
	 * @param antennaTemp0
	 * @param antennaTemp1
	 * @param antennaDeployment0
	 * @param antennaDeployment1
	 * @param antennaDeployment2
	 * @param antennaDeployment3
	 */
	public Antenna(final Long antennaTemp0, final Long antennaTemp1,
			final boolean antennaDeployment0, final boolean antennaDeployment1,
			final boolean antennaDeployment2, final boolean antennaDeployment3) {
		this.antennaTemp0 = antennaTemp0;
		this.antennaTemp1 = antennaTemp1;
		this.antennaDeployment0 = antennaDeployment0;
		this.antennaDeployment1 = antennaDeployment1;
		this.antennaDeployment2 = antennaDeployment2;
		this.antennaDeployment3 = antennaDeployment3;
	}

	public Antenna(final String binaryDataString) {
		
		int pos;
		
		for (pos = 0; pos < TEMPERATURE_BINARY_STRING_LENGTH; pos += 8) {
			final long temperature = Long.parseLong(binaryDataString.substring(pos, pos + 8), 2);
			if (0 == pos / 8) {
				antennaTemp0 = temperature;
			} 
			else {
				antennaTemp1 = temperature;
			}
		}
		
		// we do not reset pos before the loop
		for (; pos < TEMPERATURE_BINARY_STRING_LENGTH + DEPLOYMENT_BINARY_STRING_LENGTH ; pos += 1) {
			final boolean deployed = binaryDataString.substring(pos, pos + 1).equals(ONE);
			switch(pos - 16) {
				case 0:
					antennaDeployment0 = deployed;
					break;
				case 1:
					antennaDeployment1 = deployed;
					break;
				case 2:
					antennaDeployment2 = deployed;
					break;
				case 3:
					antennaDeployment3 = deployed;
					break;
			}
		}
	}

	public final String toBinaryString() {
		final StringBuffer sb = new StringBuffer();
		sb.append(StringUtils.leftPad(Long.toString(antennaTemp0, 2), 8, ZERO));
		sb.append(StringUtils.leftPad(Long.toString(antennaTemp1, 2), 8, ZERO));
		sb.append(antennaDeployment0 ? ONE : ZERO);
		sb.append(antennaDeployment1 ? ONE : ZERO);
		sb.append(antennaDeployment2 ? ONE : ZERO);
		sb.append(antennaDeployment3 ? ONE : ZERO);
		return sb.toString();
	}

	/**
	 * @return the antennaTemp0
	 */
	public final Long getAntennaTemp0() {
		return antennaTemp0;
	}

	/**
	 * @return the antennaTemp1
	 */
	public final Long getAntennaTemp1() {
		return antennaTemp1;
	}

	/**
	 * @return the antennaDeployment0
	 */
	public final boolean isAntennaDeployment0() {
		return antennaDeployment0;
	}

	/**
	 * @return the antennaDeployment1
	 */
	public final boolean isAntennaDeployment1() {
		return antennaDeployment1;
	}

	/**
	 * @return the antennaDeployment2
	 */
	public final boolean isAntennaDeployment2() {
		return antennaDeployment2;
	}

	/**
	 * @return the antennaDeployment3
	 */
	public final boolean isAntennaDeployment3() {
		return antennaDeployment3;
	}
	

}
