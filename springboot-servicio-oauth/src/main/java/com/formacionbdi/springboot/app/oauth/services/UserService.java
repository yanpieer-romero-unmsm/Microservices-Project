package com.formacionbdi.springboot.app.oauth.services;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.formacionbdi.springboot.app.commons.usuarios.models.entity.Usuario;
import com.formacionbdi.springboot.app.oauth.clients.UserFeignClient;

import feign.FeignException;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService, UserDetailsService {
	private final Logger log = LoggerFactory.getLogger(UserService.class);
	private final UserFeignClient client;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			Usuario user = client.findByUsername(username);
			/*
			if (user == null) {
				log.error("Error in login, doesn't exist the user '{}' in the system", username);
				throw new UsernameNotFoundException("Error in login, doesn't exist the user '" + username + "' in the system");
			}*/
			List<GrantedAuthority> authorities = user.getRoles().stream()
														.map(role -> new SimpleGrantedAuthority(role.getNombre()))
														.peek(authority -> log.info("Role: {}", authority.getAuthority()))
														.collect(Collectors.toList());
			log.info("Authenticated user: {}", username);
			return new User(user.getUsername(),
					user.getPassword(),
					user.getEnabled(),
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
	public Usuario findByUsername(String username) {
		return client.findByUsername(username);
	}

	@Override
	public Usuario update(Usuario usuario, Long id) {
		return client.update(usuario, id);
	}

}
