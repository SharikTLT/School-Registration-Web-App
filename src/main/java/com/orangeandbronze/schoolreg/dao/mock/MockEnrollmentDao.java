package com.orangeandbronze.schoolreg.dao.mock;

import com.orangeandbronze.schoolreg.dao.EnrollmentDao;
import com.orangeandbronze.schoolreg.domain.Enrollment;
import com.orangeandbronze.schoolreg.domain.Student;

public class MockEnrollmentDao extends EnrollmentDao {
	
	@Override
	public Enrollment findLatestBy(Student student) {
		return new Enrollment(777, student, "2014 1st"); 
	}

	public void save(Enrollment enrollment) {
	}

}
