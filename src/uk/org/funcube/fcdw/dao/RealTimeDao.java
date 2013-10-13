package uk.org.funcube.fcdw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import uk.org.funcube.fcdw.domain.RealTimeEntity;


public interface RealTimeDao extends CrudRepository<RealTimeEntity, Long> {

	@Query
	List<RealTimeEntity> findBySatelliteId(long satelliteId);
}
