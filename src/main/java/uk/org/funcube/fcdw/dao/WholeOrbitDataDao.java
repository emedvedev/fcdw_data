// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import uk.org.funcube.fcdw.domain.WholeOrbitDataEntity;


public interface WholeOrbitDataDao extends CrudRepository<WholeOrbitDataEntity, Long> {

	@Query
	List<WholeOrbitDataEntity> findBySatelliteIdAndFrameNumber(long satelliteId, long frameNumber);
	
	@Query("select max(wod.id) from WholeOrbitDataEntity wod where wod.satelliteId = ?1")
	Long findMaxId(Long satelliteId);

	@Query("select wod from WholeOrbitDataEntity wod where satelliteId = ?1 and id > ?2 order by id desc")
	List<WholeOrbitDataEntity> getLastHour(Long satelliteId, Long id);

}
