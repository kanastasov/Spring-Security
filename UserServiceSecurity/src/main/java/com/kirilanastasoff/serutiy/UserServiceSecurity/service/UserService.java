package com.kirilanastasoff.serutiy.UserServiceSecurity.service;

import java.util.List;

import com.kirilanastasoff.serutiy.UserServiceSecurity.domain.Role;
import com.kirilanastasoff.serutiy.UserServiceSecurity.domain.User;

public interface UserService {
	User saveUser(User user);
	Role saveRole(Role role);
	void addRoleToUser(String username, String roleName);
	User getUser(String username);
	List <User> getUsers();
}
