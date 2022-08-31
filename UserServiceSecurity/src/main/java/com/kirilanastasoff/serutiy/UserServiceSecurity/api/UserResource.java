package com.kirilanastasoff.serutiy.UserServiceSecurity.api;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirilanastasoff.serutiy.UserServiceSecurity.domain.Role;
import com.kirilanastasoff.serutiy.UserServiceSecurity.domain.User;
import com.kirilanastasoff.serutiy.UserServiceSecurity.service.UserService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserResource {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers() {
		return ResponseEntity.ok().body(userService.getUsers());
	}

	@PostMapping("/user/save")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		URI url = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
		return ResponseEntity.created(url).body(userService.saveUser(user));
	}

	@PostMapping("/role/save")
	public ResponseEntity<Role> saveRole(@RequestBody Role role) {
		URI url = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
		return ResponseEntity.created(url).body(userService.saveRole(role));
	}

	@PostMapping("/role/addtouser")
	public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm from) {
		userService.addRoleToUser(from.getUsername(), from.getRolename());
		return ResponseEntity.ok().build();
	}
	
	
	@GetMapping("/token/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse repsonse) throws StreamWriteException, DatabindException, IOException {
		String authorizationHeader = request.getHeader("Authorization");

			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				try {
					String refreshToken = authorizationHeader.substring("Bearer ".length());
					Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
					JWTVerifier verifier = JWT.require(algorithm).build();
					DecodedJWT decodedJWT = verifier.verify(refreshToken);
					String username = decodedJWT.getSubject();
					
					User user = userService.getUser(username);
					
					String access_token = JWT.create().withSubject(user.getUsername())
							.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
							.withIssuer(request.getRequestURI().toString())
							.withClaim("roles",
									user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
							.sign(algorithm);
					
					
		

					Map<String,String> tokens = new HashMap<>();
					tokens.put("access_token", access_token);
					repsonse.setContentType("application/json");
					
					new ObjectMapper().writeValue(repsonse.getOutputStream(), tokens);
					
					
				} catch (Exception ex) {
					System.out.println("Error loggin in:" + ex.getMessage());
					repsonse.setHeader("error", ex.getMessage());
//					response.sendError(403);
					repsonse.setStatus(403);
					
					Map<String,String> error = new HashMap<>();
					error.put("error", ex.getMessage());
					repsonse.setContentType("application/json");
					
					new ObjectMapper().writeValue(repsonse.getOutputStream(), error);
				}

			}else {
				throw new RuntimeException("Refresh token is missing");

			}
		
	}

}

@Data
class RoleToUserForm {
	private String username;
	private String rolename;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

}
