package com.formacionbdi.springboot.app.oauth.security.event;

import com.formacionbdi.springboot.app.commons.usuarios.models.entity.UserEntity;
import com.formacionbdi.springboot.app.oauth.services.IUserService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher{
	@Autowired
	private IUserService userService;
	@Autowired
	private Environment env;
	
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		
		if(authentication.getName().equalsIgnoreCase(env.getProperty("config.security.oauth.client.id"))){
		    return;
		}
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String message = "Success Login: " + userDetails.getUsername();
		System.out.println(message);
		log.info(message);
		
		UserEntity userEntity = userService.findByUsername(authentication.getName());
		
		if (userEntity.getAttempts() != null && userEntity.getAttempts() > 0) {
			userEntity.setAttempts(0);
			userService.update(userEntity, userEntity.getId());
		}
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		String message = "Error Login: " + exception.getMessage();
		log.error(message);
		System.out.println(message);
		
		try {
			UserEntity userEntity = userService.findByUsername(authentication.getName());
			if (userEntity.getAttempts() == null) {
				userEntity.setAttempts(0);
			}

			log.info("Current attempts is: " + userEntity.getAttempts());
			userEntity.setAttempts(userEntity.getAttempts()+1);
			log.info("Attempts after is from: " + userEntity.getAttempts());
			
			if (userEntity.getAttempts() >= 3) {
				log.error(String.format("The user %s disabled by max tries", userEntity.getUsername()));
				userEntity.setEnabled(false);
			}
			
			userService.update(userEntity, userEntity.getId());

		} catch (FeignException e) {
			log.error(String.format("The user %s doesn't exist in the system", authentication.getName()));
		}
	}
}
