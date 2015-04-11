// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;

/**
 * @author g4dpz
 */
public class FC2AntsSummary {

    private String antennaTemperature;
    private String antennaStatus0;
    private String antennaStatus1;
    private String antennaStatus2;
    private String antennaStatus3;

    public FC2AntsSummary(final String antennaTemperature,
            final String antennaStatus0, final String antennaStatus1,
            final String antennaStatus2, final String antennaStatus3) {
        super();
        this.antennaTemperature = antennaTemperature;
        this.antennaStatus0 = antennaStatus0;
        this.antennaStatus1 = antennaStatus1;
        this.antennaStatus2 = antennaStatus2;
        this.antennaStatus3 = antennaStatus3;
    }

    public final String getAntennaTemperature() {
        return antennaTemperature;
    }

    public final String getAntennaStatus0() {
        return antennaStatus0;
    }

    public final String getAntennaStatus1() {
        return antennaStatus1;
    }

    public final String getAntennaStatus2() {
        return antennaStatus2;
    }

    public final String getAntennaStatus3() {
        return antennaStatus3;
    }

}
