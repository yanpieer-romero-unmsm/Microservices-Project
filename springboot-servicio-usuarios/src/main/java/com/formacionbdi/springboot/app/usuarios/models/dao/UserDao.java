package com.formacionbdi.springboot.app.usuarios.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.formacionbdi.springboot.app.commons.usuarios.models.entity.User;

@RepositoryRestResource(path="users")
public interface UserDao extends PagingAndSortingRepository<User, Long>{
	@RestResource(path="search-username")
    User findByUsername(@Param("username") String username);
	@Query("select u from User u where u.username=?1")
    User obtenerPorUsername(String username);
	/*
	//Alternativa para realizar consultas a la base de datos
	@Query("select u from Usuario u where u.username=?1 and u.email=?2")
	Usuario obtenerPorUsername(String username, String email);
	//Option to get all emails from database with a native query
	@Query(value = "select * from usuarios u where u.email=?1", nativeQuery = true)
	Usuario getByEmail(String email);
	*/
}
