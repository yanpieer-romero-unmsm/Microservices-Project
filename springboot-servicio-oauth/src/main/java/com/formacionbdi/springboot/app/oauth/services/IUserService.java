package com.formacionbdi.springboot.app.oauth.services;

import com.formacionbdi.springboot.app.commons.usuarios.models.entity.User;

public interface IUserService {
	User findByUsername(String username);
	User update(User user, Long id);
}
