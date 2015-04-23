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

import uk.org.funcube.fcdw.domain.HighResolutionEntity;


public interface HighResolutionDao extends CrudRepository<HighResolutionEntity, Long> {

	@Query("select max(hires.id) from HighResolutionEntity hires where hires.satelliteId = ?1")
	Long findMaxId(Long satelliteId);

	@Query("select hires from HighResolutionEntity hires where satelliteId = ?1 and id > ?2 order by id desc")
	List<HighResolutionEntity> getLastHour(Long satelliteId, Long id);

	@Query("select max(satelliteTime) from HighResolutionEntity where satelliteId = ?1")
	Timestamp getLatestSatelliteTime(long satelliteId);
	
	@Query("select hires from HighResolutionEntity hires where satelliteId = ?1 and satelliteTime > ?2 order by satelliteTime asc")
	List<HighResolutionEntity> getSinceSatelliteTime(long satelliteId, Timestamp satelliteTime);

	@Query("select hires from HighResolutionEntity hires where satelliteId = ?1 and createdDate >= ?2 order by satelliteTime asc")
    List<HighResolutionEntity> getSinceCreatedDate(long satelliteId, Timestamp since);
}
