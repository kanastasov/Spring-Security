package com.kirilanastasoff.springsecurity.SpringSecurity.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService implements UserDetailsService {

	private ApplicationUserDao applicationUserDao;
	
	
	
	public ApplicationUserService(ApplicationUserDao applicationUserDao) {
		super();
		this.applicationUserDao = applicationUserDao;
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return applicationUserDao
				.selectApplicationUserByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("Username not found: " + username)));
	}

}
