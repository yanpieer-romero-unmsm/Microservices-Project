package com.formacionbdi.springboot.app.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.formacionbdi.springboot.app.commons.usuarios.models.entity.User;
import com.formacionbdi.springboot.app.oauth.services.IUserService;

import feign.FeignException;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher{

	private final Logger log = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);

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
		
		User user = userService.findByUsername(authentication.getName());
		
		if (user.getAttempts() != null && user.getAttempts() > 0) {
			user.setAttempts(0);
			userService.update(user, user.getId());
		}
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		String message = "Error Login: " + exception.getMessage();
		log.error(message);
		System.out.println(message);
		
		try {
			User user = userService.findByUsername(authentication.getName());
			if (user.getAttempts() == null) {
				user.setAttempts(0);
			}

			log.info("Current attempts is: " + user.getAttempts());
			user.setAttempts(user.getAttempts()+1);
			log.info("Attempts after is from: " + user.getAttempts());
			
			if (user.getAttempts() >= 3) {
				log.error(String.format("The user %s disabled by max tries", user.getUsername()));
				user.setEnabled(false);
			}
			
			userService.update(user, user.getId());

		} catch (FeignException e) {
			log.error(String.format("The user %s doesn't exist in the system", authentication.getName()));
		}
	}

}
