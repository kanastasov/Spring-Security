package com.kirilanastasoff.springsecurity.SpringSecurity.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.kirilanastasoff.springsecurity.SpringSecurity.security.ApplicationUserRole;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao {

	private PasswordEncoder passwordEncoder;
	
	
	@Override
	public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
		return getApplicationUsers().stream().filter(applicationUser -> username.equals(applicationUser.getUsername()))
		.findFirst();
	}
	
	private List<ApplicationUser> getApplicationUsers(){
		List<ApplicationUser> applicationUsers = Lists.newArrayList(
				new ApplicationUser(
						ApplicationUserRole.STUDENT.getGrantedAuthority(),
						passwordEncoder.encode("123"),
						"ivan",
						true,true,true,true
					),
				
				new ApplicationUser(
						ApplicationUserRole.ADMIN.getGrantedAuthority(),
						passwordEncoder.encode("123"),
						"petar",
						true,true,true,true
					),
				
				new ApplicationUser(
						ApplicationUserRole.ADMINTRAINEE.getGrantedAuthority(),
						passwordEncoder.encode("123"),
						"gosho",
						true,true,true,true
					)
				);
		
		return applicationUsers;
	}

}
