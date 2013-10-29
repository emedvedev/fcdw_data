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
public class StringPairTest {

	@Test
	public void testConstruction() {
		StringPair valMinMax = new StringPair("Name", "1.001");
		Assert.assertEquals("Name", valMinMax.getName());
		Assert.assertEquals("1.001", valMinMax.getValue());
	}

}
