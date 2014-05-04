package uk.org.funcube.fcdw.domain;

import java.sql.Timestamp;

import junit.framework.Assert;

import org.junit.Test;

public class UserRankingTest {
	
	private static final Long ID = 999L;
	private static final Long NUMBER = 3L;
	private static final String SITE_ID = "g4dpz";
	private static final Long SATLLITE_ID = 1L;
	private static final Timestamp LATEST_UPLOADED_DATE = new Timestamp(2014, 1, 1, 23, 59, 0, 0);

	@Test
	public void testConstruction() {
		UserRankingEntity userRankingEntity 
			= new UserRankingEntity(SATLLITE_ID, SITE_ID, NUMBER, LATEST_UPLOADED_DATE);
		Assert.assertEquals(SATLLITE_ID, userRankingEntity.getSatelliteId());
		Assert.assertEquals(SITE_ID, userRankingEntity.getSiteId());
		Assert.assertEquals(NUMBER, userRankingEntity.getNumber());
		Assert.assertEquals(LATEST_UPLOADED_DATE, userRankingEntity.getLatestUploadDate());
	}
	
	@Test
	public void testSetters() {
		UserRankingEntity userRankingEntity = new UserRankingEntity();
		userRankingEntity.setId(ID);
		userRankingEntity.setSatelliteId(SATLLITE_ID);
		userRankingEntity.setSiteId(SITE_ID);
		userRankingEntity.setNumber(NUMBER);
		userRankingEntity.setLatestUploadDate(LATEST_UPLOADED_DATE);
		Assert.assertEquals(ID, userRankingEntity.getId());
		Assert.assertEquals(SATLLITE_ID, userRankingEntity.getSatelliteId());
		Assert.assertEquals(SITE_ID, userRankingEntity.getSiteId());
		Assert.assertEquals(NUMBER, userRankingEntity.getNumber());
		Assert.assertEquals(LATEST_UPLOADED_DATE, userRankingEntity.getLatestUploadDate());
		
	}

}
