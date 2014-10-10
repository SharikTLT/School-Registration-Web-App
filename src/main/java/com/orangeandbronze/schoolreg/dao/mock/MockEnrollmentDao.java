package com.orangeandbronze.schoolreg.dao.mock;

import com.orangeandbronze.schoolreg.dao.EnrollmentDao;
import com.orangeandbronze.schoolreg.domain.Enrollment;
import com.orangeandbronze.schoolreg.domain.Student;
import com.orangeandbronze.schoolreg.domain.Term;

public class MockEnrollmentDao extends EnrollmentDao {
	
	public Enrollment findBy(Student student, Term term) {
		return new Enrollment(777, student, term); 
	}

	public void save(Enrollment enrollment) {
	}

}
