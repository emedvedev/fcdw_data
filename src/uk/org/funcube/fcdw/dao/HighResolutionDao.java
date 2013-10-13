package uk.org.funcube.fcdw.dao;

import org.springframework.data.repository.CrudRepository;

import uk.org.funcube.fcdw.domain.HighResolutionEntity;


public interface HighResolutionDao extends CrudRepository<HighResolutionEntity, Long> {
}
