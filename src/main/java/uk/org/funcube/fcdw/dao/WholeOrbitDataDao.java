// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import uk.org.funcube.fcdw.domain.WholeOrbitDataEntity;


public interface WholeOrbitDataDao extends CrudRepository<WholeOrbitDataEntity, Long> {

	@Query
	List<WholeOrbitDataEntity> findBySatelliteIdAndSatelliteTime(long satelliteId, Timestamp satelliteTime);
	
	@Query
	List<WholeOrbitDataEntity> findBySatelliteIdAndFrameNumber(long satelliteId, long frameNumber);
	
	@Query("select max(wod.id) from WholeOrbitDataEntity wod where wod.satelliteId = ?1")
	Long findMaxId(Long satelliteId);

	@Query("select wod from WholeOrbitDataEntity wod where satelliteId = ?1 and wod.id < ?2")
	List<WholeOrbitDataEntity> getSinceId(Long satelliteId, Long id);
	
	@Query
	List<WholeOrbitDataEntity> findBySatelliteIdAndC1AndC2AndC3AndC4AndC5AndC6AndC7AndC8AndC9AndC10AndC11(
			Long satelliteId, 
			Long c1, 
			Long c2, 
			Long c3, 
			Long c4, 
			Long c5, 
			Long c6, 
			Long c7, 
			Long c8, 
			Long c9, 
			Long c10, 
			Long c11);

	@Query("select max(satelliteTime) from WholeOrbitDataEntity where satelliteId = ?1")
	Timestamp getLatestSatelliteTime(long satelliteId);
	
	@Query("select wod from WholeOrbitDataEntity wod where satelliteId = ?1 and satelliteTime > ?2 order by satelliteTime asc")
	List<WholeOrbitDataEntity> getSinceSatelliteTime(long satelliteId, Timestamp satelliteTime);

	@Query("select wod from WholeOrbitDataEntity wod where satelliteId = ?1 and valid = 1 and createdDate between ?2 and ?3 order by sequenceNumber, frameNumber")
	List<WholeOrbitDataEntity> getBySatelliteIdAndValidAndCreatedDateBetween(
			long satelliteId, Date lastDumpDate, Date newDumpDate);
		

}
