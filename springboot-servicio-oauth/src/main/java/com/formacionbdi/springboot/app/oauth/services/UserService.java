package com.formacionbdi.springboot.app.oauth.services;

import com.formacionbdi.springboot.app.commons.usuarios.models.entity.UserEntity;
import com.formacionbdi.springboot.app.oauth.clients.UserFeignClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService, UserDetailsService {
	private final UserFeignClient client;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			UserEntity userEntity = client.findByUsername(username);
			List<GrantedAuthority> authorities = userEntity.getRoles().stream()
					.map(role -> new SimpleGrantedAuthority(role.getName()))
					.peek(authority -> log.info("Role: {}", authority.getAuthority()))
					.collect(Collectors.toList());
			log.info("Authenticated user: {}", username);

			return new User(userEntity.getUsername(),
					userEntity.getPassword(),
					userEntity.isEnabled(),
					true,
					true,
					true,
					authorities);
		} catch (FeignException e) {
			log.error("Error in login, doesn't exist the user '{}' in the system", username);
			throw new UsernameNotFoundException("Error in login, doesn't exist the user '" + username + "' in the system");
		}
	}

	@Override
	public UserEntity findByUsername(String username) {
		return client.findByUsername(username);
	}

	@Override
	public UserEntity update(UserEntity userEntity, Long id) {
		return client.update(userEntity, id);
	}
}
