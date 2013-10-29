// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

/**
 * @author g4dpz
 *
 */
public class EpsTest {

	@Test
	public final void testSetAndGet() {
		EPS eps = new EPS();
		eps.setC1(1L);
		eps.setC2(2L);
		eps.setC3(3L);
		eps.setC4(4L);
		eps.setC5(5L);
		eps.setC6(6L);
		eps.setC7(7L);
		eps.setC8(8L);
		eps.setC9(9L);
		eps.setC10(10L);
		eps.setC11(11L);
		eps.setC12(12L);
		eps.setC13(13L);
		eps.setC14(14L);
		eps.setC15(15L);
		eps.setC16(16L);
		eps.setC17(17L);
		eps.setC18(18L);
		eps.setC19(19L);
		eps.setC20(20L);
		eps.setC21(21L);
		eps.setC22(22L);
		eps.setC23(23L);
		eps.setC24(24L);
		eps.setC25(25L);
		eps.setC26(26L);
		eps.setC27(27L);
		eps.setC28(28L);
		eps.setC29(29L);
		eps.setC30(30L);
		eps.setC31(31L);
		
		Assert.assertEquals(1L, eps.getC1().longValue());
		Assert.assertEquals(2L, eps.getC2().longValue());
		Assert.assertEquals(3L, eps.getC3().longValue());
		Assert.assertEquals(4L, eps.getC4().longValue());
		Assert.assertEquals(5L, eps.getC5().longValue());
		Assert.assertEquals(6L, eps.getC6().longValue());
		Assert.assertEquals(7L, eps.getC7().longValue());
		Assert.assertEquals(8L, eps.getC8().longValue());
		Assert.assertEquals(9L, eps.getC9().longValue());
		Assert.assertEquals(10L, eps.getC10().longValue());
		Assert.assertEquals(11L, eps.getC11().longValue());
		Assert.assertEquals(12L, eps.getC12().longValue());
		Assert.assertEquals(13L, eps.getC13().longValue());
		Assert.assertEquals(14L, eps.getC14().longValue());
		Assert.assertEquals(15L, eps.getC15().longValue());
		Assert.assertEquals(16L, eps.getC16().longValue());
		Assert.assertEquals(17L, eps.getC17().longValue());
		Assert.assertEquals(18L, eps.getC18().longValue());
		Assert.assertEquals(19L, eps.getC19().longValue());
		Assert.assertEquals(20L, eps.getC20().longValue());
		Assert.assertEquals(21L, eps.getC21().longValue());
		Assert.assertEquals(22L, eps.getC22().longValue());
		Assert.assertEquals(23L, eps.getC23().longValue());
		Assert.assertEquals(24L, eps.getC24().longValue());
		Assert.assertEquals(25L, eps.getC25().longValue());
		Assert.assertEquals(26L, eps.getC26().longValue());
		Assert.assertEquals(27L, eps.getC27().longValue());
		Assert.assertEquals(28L, eps.getC28().longValue());
		Assert.assertEquals(29L, eps.getC29().longValue());
		Assert.assertEquals(30L, eps.getC30().longValue());
		Assert.assertEquals(31L, eps.getC31().longValue());
	}

}
