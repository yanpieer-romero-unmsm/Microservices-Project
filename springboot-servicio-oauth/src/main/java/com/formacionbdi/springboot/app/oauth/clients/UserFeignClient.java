package com.formacionbdi.springboot.app.oauth.clients;

import com.formacionbdi.springboot.app.commons.usuarios.models.entity.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="service-users")
public interface UserFeignClient {

	@GetMapping("/users/search/search-username")
    UserEntity findByUsername(@RequestParam String username);
	
	@PutMapping("/users/{id}")
    UserEntity update(@RequestBody UserEntity userEntity, @PathVariable Long id);
}
