package com.kirilanastasoff.serutiy.UserServiceSecurity;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kirilanastasoff.serutiy.UserServiceSecurity.domain.Role;
import com.kirilanastasoff.serutiy.UserServiceSecurity.domain.User;
import com.kirilanastasoff.serutiy.UserServiceSecurity.service.UserService;

@SpringBootApplication
public class UserServiceSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceSecurityApplication.class, args);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
			
			userService.saveUser(new User(null, "ivan petrov", "vanka", "123",new ArrayList()));
			userService.saveUser(new User(null, "milan milan", "milan", "123",new ArrayList()));
			userService.saveUser(new User(null, "petkan petkan", "petkan", "123",new ArrayList()));
			userService.saveUser(new User(null, "trifon trifon", "trifon", "123",new ArrayList()));
			
			userService.addRoleToUser("vanka", "ROLE_USER");
			userService.addRoleToUser("vanka", "ROLE_MANAGER");
			userService.addRoleToUser("milan", "ROLE_MANAGER");
			userService.addRoleToUser("petkan", "ROLE_ADMIN");
			
			userService.addRoleToUser("trifon", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("trifon", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("trifon", "ROLE_SUPER_ADMIN");
		};
	}
}
