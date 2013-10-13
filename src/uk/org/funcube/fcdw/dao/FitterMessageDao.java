package uk.org.funcube.fcdw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import uk.org.funcube.fcdw.domain.FitterMessageEntity;


public interface FitterMessageDao extends CrudRepository<FitterMessageEntity, Long> {

	@Query
	List<FitterMessageEntity> findBySatelliteIdAndMessageTextAndDebug(long satelliteId, String messageText, Boolean debug);

}
