package com.kirilanastasoff.serutiy.UserServiceSecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kirilanastasoff.serutiy.UserServiceSecurity.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}
