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
public class RfTest {

	@Test
	public final void testSetGet() {
		RF rf = new RF();
		rf.setC1(100L);
		rf.setC2(101L);
		rf.setC3(102L);
		rf.setC4(103L);
		rf.setC5(104L);
		rf.setC6(105L);
		rf.setC7(106L);
		rf.setC8(107L);
		rf.setC9(108L);
		rf.setC10(109L);
		Assert.assertEquals(100L, rf.getC1().longValue());
		Assert.assertEquals(101L, rf.getC2().longValue());
		Assert.assertEquals(102L, rf.getC3().longValue());
		Assert.assertEquals(103L, rf.getC4().longValue());
		Assert.assertEquals(104L, rf.getC5().longValue());
		Assert.assertEquals(105L, rf.getC6().longValue());
		Assert.assertEquals(106L, rf.getC7().longValue());
		Assert.assertEquals(107L, rf.getC8().longValue());
		Assert.assertEquals(108L, rf.getC9().longValue());
		Assert.assertEquals(109L, rf.getC10().longValue());
	}

}
