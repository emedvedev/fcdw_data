// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;

/**
 * @author g4dpz
 *
 */
public class SatellitePosition {

	private String latitude;
	private String longitude;
	private String eclipsed;
	private String eclipseDepth;
	private boolean aboveHorizon;

	public SatellitePosition() {
	}

	public SatellitePosition(final String latitude, final String longitude,
			final String eclipsed, final String eclipseDepth, final boolean aboveHorizon) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.eclipsed = eclipsed;
		this.eclipseDepth = eclipseDepth;
		this.aboveHorizon = aboveHorizon;
	}

	public void setLatitude(final String latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(final String longitude) {
		this.longitude = longitude;
	}

	public void setEclipsed(final String eclipsed) {
		this.eclipsed = eclipsed;
	}

	public void setEclipseDepth(final String eclipseDepth) {
		this.eclipseDepth = eclipseDepth;
	}

	public final String getLatitude() {
		return latitude;
	}

	public final String getLongitude() {
		return longitude;
	}

	public final String getEclipsed() {
		return eclipsed;
	}

	public final String getEclipseDepth() {
		return eclipseDepth;
	}

	/**
	 * @return
	 */
	public boolean isAboveHorizon() {
		return aboveHorizon;
	}

}
