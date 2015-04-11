// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;

/**
 * @author g4dpz
 */
public class FC2SoftwareSummary {

    private String sequenceNumber;
    private String inEclipseMode;
    private String obcResetCount;
    private String epsResetCount;

    public FC2SoftwareSummary(final String sequenceNumber, final String inEclipseMode, final String obcResetCount,
            final String epsResetCount) {
        super();
        this.sequenceNumber = sequenceNumber;
        this.inEclipseMode = inEclipseMode;
        this.obcResetCount = obcResetCount;
        this.epsResetCount = epsResetCount;
    }

    public final String getSequenceNumber() {
        return sequenceNumber;
    }

    public final String getInEclipseMode() {
        return inEclipseMode;
    }

    public final String getEpsResetCount() {
        return epsResetCount;
    }

    public final String getObcResetCount() {
        return obcResetCount;
    }

}
