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
public class SoftwareStateText {

	/**
	 * Test method for {@link uk.org.funcube.fcdw.server.shared.SoftwareState#SoftwareState()}.
	 */
	@Test
	public final void testSoftwareState() {
		SoftwareState softwareState = new SoftwareState();
		softwareState.setC1(true);
		softwareState.setC2(false);
		softwareState.setC3(true);
		softwareState.setC4(false);
		softwareState.setC5(true);
		softwareState.setC6(false);
		softwareState.setC7(true);
		softwareState.setC8(false);
		softwareState.setC9(true);
		softwareState.setC10(false);
		softwareState.setC11(true);
		softwareState.setC12(false);
		softwareState.setC13(true);
		softwareState.setC14(false);
		softwareState.setC15(true);
		softwareState.setC16(false);
		softwareState.setC17(true);
		softwareState.setC18(false);
		softwareState.setC19(true);
		softwareState.setC20(false);
		softwareState.setC21(true);
		softwareState.setC22(false);
		softwareState.setC23(true);
		softwareState.setC24(false);
		Assert.assertTrue(softwareState.getC1());
		Assert.assertFalse(softwareState.getC2());
		Assert.assertTrue(softwareState.getC3());
		Assert.assertFalse(softwareState.getC4());
		Assert.assertTrue(softwareState.getC5());
		Assert.assertFalse(softwareState.getC6());
		Assert.assertTrue(softwareState.getC7());
		Assert.assertFalse(softwareState.getC8());
		Assert.assertTrue(softwareState.getC9());
		Assert.assertFalse(softwareState.getC10());
		Assert.assertTrue(softwareState.getC11());
		Assert.assertFalse(softwareState.getC12());
		Assert.assertTrue(softwareState.getC13());
		Assert.assertFalse(softwareState.getC14());
		Assert.assertTrue(softwareState.getC15());
		Assert.assertFalse(softwareState.getC16());
		Assert.assertTrue(softwareState.getC17());
		Assert.assertFalse(softwareState.getC18());
		Assert.assertTrue(softwareState.getC19());
		Assert.assertFalse(softwareState.getC20());
		Assert.assertTrue(softwareState.getC21());
		Assert.assertFalse(softwareState.getC22());
		Assert.assertTrue(softwareState.getC23());
		Assert.assertFalse(softwareState.getC24());
		
		softwareState.setC1(false);
		softwareState.setC2(true);
		softwareState.setC3(false);
		softwareState.setC4(true);
		softwareState.setC5(false);
		softwareState.setC6(true);
		softwareState.setC7(false);
		softwareState.setC8(true);
		softwareState.setC9(false);
		softwareState.setC10(true);
		softwareState.setC11(false);
		softwareState.setC12(true);
		softwareState.setC13(false);
		softwareState.setC14(true);
		softwareState.setC15(false);
		softwareState.setC16(true);
		softwareState.setC17(false);
		softwareState.setC18(true);
		softwareState.setC19(false);
		softwareState.setC20(true);
		softwareState.setC21(false);
		softwareState.setC22(true);
		softwareState.setC23(false);
		softwareState.setC24(true);
		Assert.assertFalse(softwareState.getC1());
		Assert.assertTrue(softwareState.getC2());
		Assert.assertFalse(softwareState.getC3());
		Assert.assertTrue(softwareState.getC4());
		Assert.assertFalse(softwareState.getC5());
		Assert.assertTrue(softwareState.getC6());
		Assert.assertFalse(softwareState.getC7());
		Assert.assertTrue(softwareState.getC8());
		Assert.assertFalse(softwareState.getC9());
		Assert.assertTrue(softwareState.getC10());
		Assert.assertFalse(softwareState.getC11());
		Assert.assertTrue(softwareState.getC12());
		Assert.assertFalse(softwareState.getC13());
		Assert.assertTrue(softwareState.getC14());
		Assert.assertFalse(softwareState.getC15());
		Assert.assertTrue(softwareState.getC16());
		Assert.assertFalse(softwareState.getC17());
		Assert.assertTrue(softwareState.getC18());
		Assert.assertFalse(softwareState.getC19());
		Assert.assertTrue(softwareState.getC20());
		Assert.assertFalse(softwareState.getC21());
		Assert.assertTrue(softwareState.getC22());
		Assert.assertFalse(softwareState.getC23());
		Assert.assertTrue(softwareState.getC24());
	}

}
