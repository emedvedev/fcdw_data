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
public class BatteryTest {

	@Test
	public final void testConstruction() {
		Battery battery = new Battery(100L, 101L, 102L, 103L);
		Assert.assertEquals(100L, battery.getBatteryCurrent1().longValue());
		Assert.assertEquals(101L, battery.getBatteryCurrent2().longValue());
		Assert.assertEquals(102L, battery.getBatteryVoltage1().longValue());
		Assert.assertEquals(103L, battery.getBatteryVoltage2().longValue());
	}

}
