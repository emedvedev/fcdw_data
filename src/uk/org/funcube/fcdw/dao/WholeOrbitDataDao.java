package uk.org.funcube.fcdw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import uk.org.funcube.fcdw.domain.WholeOrbitDataEntity;


public interface WholeOrbitDataDao extends CrudRepository<WholeOrbitDataEntity, Long> {

	List<WholeOrbitDataEntity> findBySatelliteIdAndFrameNumber(long satelliteId, long frameNumber);

}
