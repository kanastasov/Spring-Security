package com.kirilanastasoff.springsecurity.SpringSecurity.student;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

	private static final List<Student> STUDENTS = Arrays.asList(new Student(1, "Ivan Ivanow"),
			new Student(2, "Petkan Ivanow"), new Student(3, "Traqn Ivanow"));

	@GetMapping(path = "{studentId}")
	public Student getStudent(@PathVariable("studentId") Integer studentId) {

		return STUDENTS.stream().filter(student -> studentId.equals(student.getStudentId())).findFirst()
				.orElseThrow(() -> new IllegalStateException("Student " + studentId + " does not exist"));
	}
}
