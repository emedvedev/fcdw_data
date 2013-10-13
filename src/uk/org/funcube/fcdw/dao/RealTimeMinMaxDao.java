package uk.org.funcube.fcdw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import uk.org.funcube.fcdw.domain.RealTimeMinMaxEntity;


public interface RealTimeMinMaxDao extends CrudRepository<RealTimeMinMaxEntity, Long> {
	
	@Query
	List<RealTimeMinMaxEntity> findBySatelliteId(long satelliteId);
	
	@Query
	List<RealTimeMinMaxEntity> findBySatelliteIdAndChannelId(long satelliteId, long channelId);

}
