// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import uk.org.funcube.fcdw.domain.HexFrameEntity;


public interface HexFrameDao extends CrudRepository<HexFrameEntity, Long> {

	@Query("select hfe from HexFrameEntity hfe where hfe.satelliteId = ?1 and hfe.sequenceNumber = ?2 and hfe.frameType = ?3")
	List<HexFrameEntity> findBySatelliteIdAndSequenceNumberAndFrameType(long satelliteId, long sequenceNumber, long frameType);

	@Query("SELECT hf FROM HexFrameEntity hf where hf.satelliteId = ?1 "
			+ "and hf.valid = 1 and hf.frameType between 0 and 11 " 
			+ "and hf.wodProcessed = 0 "
			+ "and createdDate > ?2 " 
			+ "order by hf.sequenceNumber,hf.frameType")
	List<HexFrameEntity> findUnprocessedWOD(long satelliteId, Date time);

	@Query("SELECT hf FROM HexFrameEntity hf where hf.satelliteId = ?1 "
			+ "and hf.valid = 1 and hf.frameType in (12,16,20) " 
			+ "and hf.highPrecisionProcessed = 0 "
			+ "and createdDate > ?2 " 
			+ "order by hf.sequenceNumber,hf.frameType")
	List<HexFrameEntity> findUnprocessedHighPrecision(long satelliteId, Date time);

	@Query("SELECT hf FROM HexFrameEntity hf where hf.satelliteId = ?1 "
			+ "and hf.valid = 1 and hf.frameType in (13,14,15,17,18,19,21,22,23) " 
			+ "and hf.fitterProcessed = 0 "
			+ "and createdDate > ?2 " 
			+ "order by hf.sequenceNumber,hf.frameType")
	List<HexFrameEntity> findUnprocessedFitter(long satelliteId, Date time);
	
	@Query("SELECT hexString from HexFrameEntity where satelliteId = ?1 and sequenceNumber >= ?2 and sequenceNumber <= ?3")
	List<String> getHexStringBetweenSequenceNumbers(long satelliteId, long startSequenceNumber, long endSequenceNumber);

	/**
	 * @param satelliteId
	 * @return
	 */
	@Query("SELECT max(sequenceNumber) from HexFrameEntity where satelliteId = ?1")
	Long getMaxSequenceNumber(long satelliteId);

	@Query
	List<HexFrameEntity> findBySatelliteIdAndSequenceNumber(long satelliteId, long sequenceNumber);

	@Query
	List<HexFrameEntity> findBySatelliteIdAndDigest(long satelliteId, String digest, Pageable pageable);
	

}
