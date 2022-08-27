package com.kirilanastasoff.springsecurity.SpringSecurity.jwt;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Autowired
	private AuthenticationManager authenticationManager;
	private JwtConfig jwtConfig;
	private JwtSecretKey secretKey;

	@Autowired
	public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig,
			JwtSecretKey secretKey) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			UsernameAndPasswordAuthentictionRequest authReq = new ObjectMapper().readValue(request.getInputStream(),
					UsernameAndPasswordAuthentictionRequest.class);

			Authentication authentication = new UsernamePasswordAuthenticationToken(authReq.getUsername(),
					authReq.getPassword());

			Authentication authenticate = authenticationManager.authenticate(authentication);
			return authenticate;
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

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException {
		String token = Jwts.builder().setSubject(authResult.getName()).claim("authorities", authResult.getAuthorities())
				.setIssuedAt(new Date()).setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
				.signWith(Keys.hmacShaKeyFor(
						"securesecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecure"
								.getBytes()))
				.compact();

		response.addHeader("Authorization", "Bearer " + token);

	}

}
