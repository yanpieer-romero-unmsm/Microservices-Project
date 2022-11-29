package com.formacionbdi.springboot.app.oauth.security;

import com.formacionbdi.springboot.app.commons.usuarios.models.entity.UserEntity;
import com.formacionbdi.springboot.app.oauth.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class InfoAdditionalToken implements TokenEnhancer{
	private final IUserService userService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> info = new HashMap<>();
		UserEntity userEntity = userService.findByUsername(authentication.getName());
		info.put("name", userEntity.getName());
		info.put("lastname", userEntity.getLastname());
		info.put("email", userEntity.getEmail());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}
	
}
