package com.orangeandbronze.schoolreg.dao;

import com.orangeandbronze.schoolreg.domain.Enrollment;
import com.orangeandbronze.schoolreg.domain.Student;

public class EnrollmentDao extends Dao {

	
	public Enrollment findLatestBy(Student student) {
		return new Enrollment(777, student,  "2014 1st"); // TODO Just a stub; implement actual as JDBC
	}
	
	/** Should return a new Enrollment if none exists, or fetch an existing one. **/
	public Enrollment findBy(Student student, String term) {
		return new Enrollment(777, student,  "2014 1st"); // TODO Just a stub; implement actual as JDBC
	}

	public void save(Enrollment enrollment) {
		// TODO Auto-generated method stub		
	}

}
