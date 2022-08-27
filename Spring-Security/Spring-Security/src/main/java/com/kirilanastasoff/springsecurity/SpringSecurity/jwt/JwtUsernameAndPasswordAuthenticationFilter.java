package com.kirilanastasoff.springsecurity.SpringSecurity.jwt;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			UsernameAndPasswordAuthentictionRequest authReq = new ObjectMapper().readValue(request.getInputStream(),
					UsernameAndPasswordAuthentictionRequest.class);

			Authentication authentication = new UsernamePasswordAuthenticationToken(authReq.getUsername(),
					authReq.getPassword());

			return authenticationManager.authenticate(authentication);
		} catch (StreamReadException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (DatabindException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
}
