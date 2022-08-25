package com.kirilanastasoff.springsecurity.SpringSecurity.student;

public class Student {
	private Integer studentId;
	private String sutdentName;

	public Student() {
		super();
	}

	public Student(Integer studentId, String sutdentName) {
		super();
		this.studentId = studentId;
		this.sutdentName = sutdentName;
	}

	public String getSutdentName() {
		return sutdentName;
	}

	public void setSutdentName(String sutdentName) {
		this.sutdentName = sutdentName;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", sutdentName=" + sutdentName + "]";
	}

}
