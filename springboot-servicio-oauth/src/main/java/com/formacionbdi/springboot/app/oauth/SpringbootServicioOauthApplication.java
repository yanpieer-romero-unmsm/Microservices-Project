package com.formacionbdi.springboot.app.oauth;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@RequiredArgsConstructor
public class SpringbootServicioOauthApplication implements CommandLineRunner {
	private final Logger log = LoggerFactory.getLogger(SpringbootServicioOauthApplication.class);
	private final BCryptPasswordEncoder passwordEncode;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioOauthApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String password = "12345";
		log.info("PASSWORDS");
		for (int i = 0; i < 4; i++) {
			String passwordBCrypt = passwordEncode.encode(password);
			log.info(passwordBCrypt);
		}
	}
}
