package com.kirilanastasoff.serutiy.UserServiceSecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kirilanastasoff.serutiy.UserServiceSecurity.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
