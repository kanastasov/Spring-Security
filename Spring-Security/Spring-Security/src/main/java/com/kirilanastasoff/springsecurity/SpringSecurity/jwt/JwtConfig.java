package com.kirilanastasoff.springsecurity.SpringSecurity.jwt;

import javax.crypto.SecretKey;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.google.common.net.HttpHeaders;

import io.jsonwebtoken.security.Keys;

@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {
	private String secretKey;
	private String tokenPrexix;
	private Integer tokenExpirationAfterDays;

	public JwtConfig() {
		super();
	}

	public String getSecretKye() {
		return secretKey;
	}

	public void setSecretKye(String secretKye) {
		this.secretKey = secretKye;
	}

	public String getTokenPrexix() {
		return tokenPrexix;
	}

	public void setTokenPrexix(String tokenPrexix) {
		this.tokenPrexix = tokenPrexix;
	}

	public Integer getTokenExpirationAfterDays() {
		return tokenExpirationAfterDays;
	}

	public void setTokenExpirationAfterDays(Integer tokenExpirationAfterDays) {
		this.tokenExpirationAfterDays = tokenExpirationAfterDays;
	}
	

	
	public String getAuthorizationHeader() {
		return HttpHeaders.AUTHORIZATION;
	}

}
