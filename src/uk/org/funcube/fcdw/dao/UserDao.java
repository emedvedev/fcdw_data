package uk.org.funcube.fcdw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import uk.org.funcube.fcdw.domain.UserEntity;


public interface UserDao extends CrudRepository<UserEntity, Long> {

	List<UserEntity> findBySiteId(String siteId);

}
