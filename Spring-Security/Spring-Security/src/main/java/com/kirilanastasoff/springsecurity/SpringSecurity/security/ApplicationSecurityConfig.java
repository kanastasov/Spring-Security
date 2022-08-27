package com.kirilanastasoff.springsecurity.SpringSecurity.security;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;

import com.kirilanastasoff.springsecurity.SpringSecurity.auth.ApplicationUserService;
import com.kirilanastasoff.springsecurity.SpringSecurity.jwt.JwtUsernameAndPasswordAuthenticationFilter;

@SuppressWarnings("deprecation")
@Configurable
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	private  PasswordEncoder passwordEncoder;
	
	private ApplicationUserService applciationUserService;

	
	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder,ApplicationUserService applciationUserService) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.applciationUserService =applciationUserService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
//		.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//		.and()
		.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager()))
		.authorizeRequests()
		.antMatchers("/", "index", "/css/*", "/js/*").permitAll()
		.antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())
		.anyRequest()
		.authenticated();
//				.and()
//				.httpBasic();
//				.formLogin()
//					.loginPage("/login")
//					.permitAll()
//					.defaultSuccessUrl("/courses", true)
//					.passwordParameter("password")
//					.usernameParameter("username")
//				.and()
//				.rememberMe()
//					.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
//					.key("somethingsecured")
//					.rememberMeParameter("remember-me")
//				.and()
//				.logout()
//					.logoutUrl("/logout")
//					.clearAuthentication(true)
//					.invalidateHttpSession(true)
//					.deleteCookies("JSESSIONID", "XSRF-TOKEN")
//					.logoutSuccessUrl("/login");
//		default to 2 weeks
	}

	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails ivanov = 
				User.builder()
				.username("ivan")
				.password(passwordEncoder.encode("123"))
				.authorities(ApplicationUserRole.STUDENT.getGrantedAuthority())
//				.roles(ApplicationUserRole.STUDENT.name())
				.build();

		
		UserDetails petar = User.builder()
		.username("petar")
		.password(passwordEncoder.encode("456"))
		.authorities(ApplicationUserRole.ADMIN.getGrantedAuthority())
//		.roles(ApplicationUserRole.ADMIN.name())
		.build();
		
		
		UserDetails gosho = User.builder()
				.username("gosho")
				.password(passwordEncoder.encode("456"))
				.authorities(ApplicationUserRole.ADMINTRAINEE.getGrantedAuthority())
//				.roles(ApplicationUserRole.ADMINTRAINEE.name())
				.build();
		
		
		return new InMemoryUserDetailsManager(ivanov,petar,gosho);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
			auth.authenticationProvider(daoAth());	}
	
	
	@Bean
	public DaoAuthenticationProvider daoAth () {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(applciationUserService);
		return provider;
		
	}
}
