package uk.org.funcube.fcdw.dao;

import java.util.Date;
import java.util.List;

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

}
