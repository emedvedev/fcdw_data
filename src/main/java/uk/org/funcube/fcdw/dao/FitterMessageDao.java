// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import uk.org.funcube.fcdw.domain.FitterMessageEntity;


public interface FitterMessageDao extends CrudRepository<FitterMessageEntity, Long> {

	@Query
	List<FitterMessageEntity> findBySatelliteIdAndMessageTextAndDebug(long satelliteId, String messageText, Boolean debug);

	@Query("select fm from FitterMessageEntity fm where satelliteId = ?1 and lastReceived < ?2")
	List<FitterMessageEntity> getNoneDebugReceivedBefore(long satelliteId, Date time);
	
    @Query("select fm from FitterMessageEntity fm where satelliteId = ?1 and lastReceived > ?2 and fm.debug = 0 order by fm.createdDate asc")
    List<FitterMessageEntity> getNoneDebugReceivedAfter(long satelliteId, Timestamp time);
	
	@Query("SELECT fm FROM FitterMessageEntity fm where fm.satelliteId = ?1 and fm.debug = 1 order by fm.id desc")
	List<FitterMessageEntity> getLatestDebug(long satelliteId, Pageable pageable);

}
