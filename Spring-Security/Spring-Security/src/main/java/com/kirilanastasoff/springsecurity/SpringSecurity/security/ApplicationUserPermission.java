package com.kirilanastasoff.springsecurity.SpringSecurity.security;

public enum ApplicationUserPermission {
	STUDENT_READ("student:read"), STUDENT_WRITE("student:write"), COURSE_READ("course:read"),
	COURSE_WRITE("course:write");

	private String permission;

	private ApplicationUserPermission(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}

}
