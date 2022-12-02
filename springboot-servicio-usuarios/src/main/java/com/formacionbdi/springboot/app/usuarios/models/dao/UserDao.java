package com.formacionbdi.springboot.app.usuarios.models.dao;

import com.formacionbdi.springboot.app.commons.usuarios.models.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path="users")
public interface UserDao extends PagingAndSortingRepository<UserEntity, Long>{
	@RestResource(path="search-username")
    UserEntity findByUsername(@Param("username") String username);
	@Query("select u from UserEntity u where u.username=?1")
    UserEntity obtenerPorUsername(String username);
}
