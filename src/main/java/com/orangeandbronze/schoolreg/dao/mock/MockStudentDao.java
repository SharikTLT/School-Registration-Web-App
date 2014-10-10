package com.orangeandbronze.schoolreg.dao.mock;

import com.orangeandbronze.schoolreg.dao.StudentDao;
import com.orangeandbronze.schoolreg.domain.Student;

public class MockStudentDao extends StudentDao {
	public Student findById(int studentId) {
		return new Student(studentId); 
	}
}
