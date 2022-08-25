package com.kirilanastasoff.springsecurity.SpringSecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@SuppressWarnings("deprecation")
@Configurable
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	private  PasswordEncoder passwordEncoder;
	

	
	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
		super();
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/", "index", "/css/*", "/js/*").permitAll()
		.antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())
		.anyRequest()
		.authenticated()
				.and()
				.httpBasic();
	}

	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails ivanov = 
				User.builder()
				.username("ivan")
				.password(passwordEncoder.encode("123"))
				.roles(ApplicationUserRole.STUDENT.name())
				.build();

		
		UserDetails petar = User.builder()
		.username("petar")
		.password(passwordEncoder.encode("456"))
		.roles(ApplicationUserRole.ADMIN.name())
		.build();
		
		
		UserDetails gosho = User.builder()
				.username("gosho")
				.password(passwordEncoder.encode("456"))
				.roles(ApplicationUserRole.ADMINTRAINEE.name())
				.build();
		
		
		return new InMemoryUserDetailsManager(ivanov,petar,gosho);
	}
}
