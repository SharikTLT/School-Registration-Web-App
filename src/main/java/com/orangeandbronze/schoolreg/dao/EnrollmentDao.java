package com.orangeandbronze.schoolreg.dao;

import com.orangeandbronze.schoolreg.domain.Enrollment;
import com.orangeandbronze.schoolreg.domain.Student;
import com.orangeandbronze.schoolreg.domain.Term;

public class EnrollmentDao extends Dao {

	/** Should return a new Enrollment if none exists, or fetch an existing one. **/
	public Enrollment findBy(Student student, Term term) {
		return new Enrollment(777, student, term); // TODO Just a stub; implement actual as JDBC
	}

	public void save(Enrollment enrollment) {
		// TODO Auto-generated method stub		
	}

}
