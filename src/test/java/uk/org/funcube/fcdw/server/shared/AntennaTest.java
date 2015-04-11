// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author g4dpz
 *
 */
public class AntennaTest {

    public AntennaTest() {
        super();
    }

    @Test
    public final void testConstruction() {
        Antenna antenna = new Antenna(100L, 101L, true, false, true, false);
        Assert.assertEquals(100L, antenna.getAntennaTemp0().longValue());
        Assert.assertEquals(101L, antenna.getAntennaTemp1().longValue());
        Assert.assertTrue(antenna.isAntennaDeployment0());
        Assert.assertFalse(antenna.isAntennaDeployment1());
        Assert.assertTrue(antenna.isAntennaDeployment2());
        Assert.assertFalse(antenna.isAntennaDeployment3());

        antenna = new Antenna(102L, 103L, false, true, false, true);
        Assert.assertEquals(102L, antenna.getAntennaTemp0().longValue());
        Assert.assertEquals(103L, antenna.getAntennaTemp1().longValue());
        Assert.assertFalse(antenna.isAntennaDeployment0());
        Assert.assertTrue(antenna.isAntennaDeployment1());
        Assert.assertFalse(antenna.isAntennaDeployment2());
        Assert.assertTrue(antenna.isAntennaDeployment3());
    }

}
