package com.kirilanastasoff.serutiy.UserServiceSecurity.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.logging.slf4j.SLF4JLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kirilanastasoff.serutiy.UserServiceSecurity.domain.Role;
import com.kirilanastasoff.serutiy.UserServiceSecurity.domain.User;
import com.kirilanastasoff.serutiy.UserServiceSecurity.repository.RoleRepository;
import com.kirilanastasoff.serutiy.UserServiceSecurity.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User saveUser(User user) {
		System.out.println("Saving new user to "
				+ "the database" + " " + user.getName());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public Role saveRole(Role role) {
		System.out.println("Saving new role to the database" + " " + role.getName());

		return roleRepository.save(role);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		System.out.println("Adding role" + roleName + " to user" + " " + username);

		User user = userRepository.findByUsername(username);
		Role role = roleRepository.findByName(roleName);
		user.getRoles().add(role);
	}

	@Override
	public User getUser(String username) {
		System.out.println("Fetching user" + " " + username);

		return userRepository.findByUsername(username);
	}

	@Override
	public List<User> getUsers() {
		System.out.println("Fetching all userers");

		return userRepository.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found in the database");
		}

		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		);

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				authorities);
	}

}
