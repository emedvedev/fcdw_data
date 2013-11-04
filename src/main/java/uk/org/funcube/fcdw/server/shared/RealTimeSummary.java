// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;

public class RealTimeSummary {
	
	private EpsSummary epsSummary;
	private AsibSummary asibSummary;
	private RfSummary rfSummary;
	private PaSummary paSummary;
	private AntsSummary antsSummary;
	private SoftwareSummary softwareSummary;

	public RealTimeSummary(EpsSummary epsSummary, AsibSummary asibSummary, 
			RfSummary rfSummary, PaSummary paSummary, 
			AntsSummary antsSummary, SoftwareSummary softwareSummary) {
		super();
		this.epsSummary = epsSummary;
		this.asibSummary = asibSummary;
		this.rfSummary = rfSummary;
		this.paSummary = paSummary;
		this.antsSummary = antsSummary;
		this.softwareSummary = softwareSummary;
	}

	public final EpsSummary getEpsSummary() {
		return epsSummary;
	}

	public final AsibSummary getAsibSummary() {
		return asibSummary;
	}

	public final RfSummary getRfSummary() {
		return rfSummary;
	}

	public final PaSummary getPaSummary() {
		return paSummary;
	}

	public final AntsSummary getAntsSummary() {
		return antsSummary;
	}

	public final SoftwareSummary getSoftwareSummary() {
		return softwareSummary;
	}
	
	

}
	

