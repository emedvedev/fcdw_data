// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.dao;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import uk.org.funcube.fcdw.domain.RealTimeEntity;
import uk.org.funcube.fcdw.domain.RealTimeEntity;


public interface RealTimeDao extends CrudRepository<RealTimeEntity, Long> {

	@Query
	List<RealTimeEntity> findById(long id);
	
	@Query("select max(rt.id) from RealTimeEntity rt where rt.satelliteId = ?1")
	Long findMaxId(Long satelliteId);
	
	@Query("select max(sequenceNumber) from RealTimeEntity rt where rt.satelliteId = ?1")
	Long findMaxSequenceNumber(Long satelliteId);
	
	@Query("select max(frameType) from RealTimeEntity rt where rt.satelliteId = ?1 and rt.sequenceNumber = ?2")
	Long findMaxFrameType(Long satelliteId, Long sequenceNumber);

	@Query
	List<RealTimeEntity> findBySatelliteIdAndSequenceNumberAndFrameType(
			Long satelliteId, Long maxSequenceNumber, Long maxFrameType);

	@Query("select max(satelliteTime) from RealTimeEntity where satelliteId = ?1")
	Timestamp getLatestSatelliteTime(long satelliteId);
	
	@Query("select realTime from RealTimeEntity realTime where satelliteId = ?1 and satelliteTime > ?2 order by satelliteTime asc")
	List<RealTimeEntity> getSinceSatelliteTime(long satelliteId, Timestamp satelliteTime);
}
