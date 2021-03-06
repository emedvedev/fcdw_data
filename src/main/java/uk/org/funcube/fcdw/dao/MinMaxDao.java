// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import uk.org.funcube.fcdw.domain.MinMaxEntity;


public interface MinMaxDao extends CrudRepository<MinMaxEntity, Long> {

	@Query
	List<MinMaxEntity> findBySatelliteId(long satelliteId);
	
	@Query("SELECT mm FROM MinMaxEntity mm WHERE mm.satelliteId = ?1 "
		+ " and mm.channel = ?2 order by refDate desc")
	List<MinMaxEntity> findBySatelliteIdAndChannel(long satelliteId, long channel);
}
