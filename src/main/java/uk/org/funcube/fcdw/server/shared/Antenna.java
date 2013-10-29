// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;


public class Antenna {
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (antennaDeployment0 ? 1231 : 1237);
		result = prime * result + (antennaDeployment1 ? 1231 : 1237);
		result = prime * result + (antennaDeployment2 ? 1231 : 1237);
		result = prime * result + (antennaDeployment3 ? 1231 : 1237);
		result = prime * result
				+ ((antennaTemp0 == null) ? 0 : antennaTemp0.hashCode());
		result = prime * result
				+ ((antennaTemp1 == null) ? 0 : antennaTemp1.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Antenna other = (Antenna) obj;
		if (antennaDeployment0 != other.antennaDeployment0)
			return false;
		if (antennaDeployment1 != other.antennaDeployment1)
			return false;
		if (antennaDeployment2 != other.antennaDeployment2)
			return false;
		if (antennaDeployment3 != other.antennaDeployment3)
			return false;
		if (antennaTemp0 == null) {
			if (other.antennaTemp0 != null)
				return false;
		} else if (!antennaTemp0.equals(other.antennaTemp0))
			return false;
		if (antennaTemp1 == null) {
			if (other.antennaTemp1 != null)
				return false;
		} else if (!antennaTemp1.equals(other.antennaTemp1))
			return false;
		return true;
	}
	

}
