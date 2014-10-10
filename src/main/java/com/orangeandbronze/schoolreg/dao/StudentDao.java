package com.orangeandbronze.schoolreg.dao;

import com.orangeandbronze.schoolreg.domain.Student;

public class StudentDao extends Dao {
	
	public Student findById(int studentId) {
		// find student
		
		return new Student(studentId); // TODO Just a stub; implement actual as JDBC
	}

}
