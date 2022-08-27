package com.kirilanastasoff.springsecurity.SpringSecurity.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.base.Strings;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtTokenVerifier extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authorizationHeader = request.getHeader("Authorization");
		if(Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		try {
			String token = authorizationHeader.replace("Bearer ", "");
			String secretKey = "secred";
			Jws<Claims> claimsJws = Jwts.parser()
			.setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
			.parseClaimsJws(token);
			
			Claims body = claimsJws.getBody();
			
			String username = body.getSubject();
			
			var autorities = (List<Map<String,String>>)body.get("authorities");
			
			Set<SimpleGrantedAuthority> simple = autorities.stream()
			.map( m -> new SimpleGrantedAuthority(m.get("authority")))
			.collect(Collectors.toSet());
			
			org.springframework.security.core.Authentication auth = new UsernamePasswordAuthenticationToken(
					username,
					null,
					simple);
			
		} catch(JwtException e) {
			throw new IllegalStateException("Token canot be trusted");
		}

	}

}
