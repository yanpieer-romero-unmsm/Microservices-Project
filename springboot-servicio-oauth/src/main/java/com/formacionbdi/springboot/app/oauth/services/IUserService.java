package com.formacionbdi.springboot.app.oauth.services;

import com.formacionbdi.springboot.app.commons.usuarios.models.entity.UserEntity;

public interface IUserService {
	UserEntity findByUsername(String username);
	UserEntity update(UserEntity userEntity, Long id);
}
