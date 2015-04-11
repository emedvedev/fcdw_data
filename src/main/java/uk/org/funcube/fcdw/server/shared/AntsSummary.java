// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;

/**
 * @author  g4dpz
 */
public class AntsSummary {
	
	private String antennaTempZero;
	private String antennaTempTwo;
	private String antennaDeploymentVhfA;
	private String antennaDeploymentUhfA;
	private String antennaDeploymentVhfB;
	private String antennaDeploymentUhfB;
	/**
	 * @param antennaTempZero
	 * @param antennaTempTwo
	 * @param antennaDeploymentVhfA
	 * @param antennaDeploymentUhfA
	 * @param antennaDeploymentVhfB
	 * @param antennaDeploymentUhfB
	 */
	public AntsSummary(String antennaTempZero, String antennaTempTwo,
			String antennaDeploymentVhfA, String antennaDeploymentUhfA,
			String antennaDeploymentVhfB, String antennaDeploymentUhfB) {
		super();
		this.antennaTempZero = antennaTempZero;
		this.antennaTempTwo = antennaTempTwo;
		this.antennaDeploymentVhfA = antennaDeploymentVhfA;
		this.antennaDeploymentUhfA = antennaDeploymentUhfA;
		this.antennaDeploymentVhfB = antennaDeploymentVhfB;
		this.antennaDeploymentUhfB = antennaDeploymentUhfB;
	}
	public final String getAntennaTempZero() {
		return antennaTempZero;
	}
	public final String getAntennaTempTwo() {
		return antennaTempTwo;
	}
	public final String getAntennaDeploymentVhfA() {
		return antennaDeploymentVhfA;
	}
	public final String getAntennaDeploymentUhfA() {
		return antennaDeploymentUhfA;
	}
	public final String getAntennaDeploymentVhfB() {
		return antennaDeploymentVhfB;
	}
	public final String getAntennaDeploymentUhfB() {
		return antennaDeploymentUhfB;
	}
	
	
	
}
