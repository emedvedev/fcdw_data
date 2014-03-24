// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import uk.org.funcube.fcdw.domain.MinMaxEntity;
import uk.org.funcube.fcdw.server.util.UTCClock;

/**
 * @author g4dpz
 *
 */
public class MinMaxTest {

	/**
	 * Test method for {@link uk.org.funcube.fcdw.server.shared.MinMax#MinMax()}.
	 */
	@Test
	public final void testMinMax() {
		MinMax minMax = new MinMax();
		minMax.setSatelliteId(1L);
		minMax.setChannel(2L);
		minMax.setMinimum(3L);
		minMax.setMaximum(4L);
		
		Assert.assertEquals(1L,  minMax.getSatelliteId().longValue());
		Assert.assertEquals(2L,  minMax.getChannel().longValue());
		Assert.assertEquals(3L,  minMax.getMinimum().longValue());
		Assert.assertEquals(4L,  minMax.getMaximum().longValue());
	}

	/**
	 * Test method for {@link uk.org.funcube.fcdw.server.shared.MinMax#MinMax(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)}.
	 */
	@Test
	public final void testMinMaxLongLongLongLong() {
		
		UTCClock clock = new UTCClock();
		long currentTime = clock.currentTime();
		
		MinMax minMax = new MinMax(1L, 2L, 3L, 4L, new Date(currentTime), true);
		
		Assert.assertEquals(1L,  minMax.getSatelliteId().longValue());
		Assert.assertEquals(2L,  minMax.getChannel().longValue());
		Assert.assertEquals(3L,  minMax.getMinimum().longValue());
		Assert.assertEquals(4L,  minMax.getMaximum().longValue());
		Assert.assertEquals(currentTime,  minMax.getRefDate().getTime());
		Assert.assertTrue(minMax.isEnabled());
	}

	/**
	 * Test method for {@link uk.org.funcube.fcdw.server.shared.MinMax#MinMax(uk.org.funcube.fcdw.domain.MinMaxEntity)}.
	 */
	@Test
	public final void testMinMaxMinMaxEntity() {
		
		UTCClock clock = new UTCClock();
		long currentTime = clock.currentTime();
		
		MinMaxEntity minMaxEntity = new MinMaxEntity(1L, 2L, 3L, 4L, new Date(currentTime), true);
		
		MinMax minMax = new MinMax(minMaxEntity);
		
		Assert.assertEquals(1L,  minMax.getSatelliteId().longValue());
		Assert.assertEquals(2L,  minMax.getChannel().longValue());
		Assert.assertEquals(3L,  minMax.getMinimum().longValue());
		Assert.assertEquals(4L,  minMax.getMaximum().longValue());
		Assert.assertEquals(currentTime,  minMax.getRefDate().getTime());
		Assert.assertTrue(minMax.isEnabled());
	}

}
