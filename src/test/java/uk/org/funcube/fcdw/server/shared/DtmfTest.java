// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;


import org.junit.Assert;
import org.junit.Test;

/**
 * @author g4dpz
 *
 */
public class DtmfTest {

	public DtmfTest() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Test
	public final void testConstruction() {
		final DTMF dtmf = new DTMF("11111111110");
		Assert.assertEquals(63L, dtmf.getCommandCount());
		Assert.assertEquals(30L, dtmf.getLastCommand());
	}

}
