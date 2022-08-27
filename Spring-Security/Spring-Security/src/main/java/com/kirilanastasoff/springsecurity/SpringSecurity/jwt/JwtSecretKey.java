package com.kirilanastasoff.springsecurity.SpringSecurity.jwt;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.security.Keys;

@Configuration
public class JwtSecretKey {

	private JwtConfig jwtCongit;

	@Autowired
	public JwtSecretKey(JwtConfig jwtCongit) {
		super();
		this.jwtCongit = jwtCongit;
	}

	@Bean
	public SecretKey getSecretKeyForSigning() {
		return Keys.hmacShaKeyFor(jwtCongit.getSecretKye().getBytes());
	}
}
