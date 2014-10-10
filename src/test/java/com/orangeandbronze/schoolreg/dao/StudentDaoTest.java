package com.orangeandbronze.schoolreg.dao;

import java.util.Collection;
import java.util.LinkedList;
import java.util.TreeSet;

import com.orangeandbronze.schoolreg.domain.Enrollment;
import com.orangeandbronze.schoolreg.domain.Student;
import com.orangeandbronze.schoolreg.domain.Term;


public class StudentDaoTest extends DaoTest {
	
	public StudentDaoTest() {
		super("StudentDaoTest.xml");
	}
	
	public void testFindById() {
		StudentDao dao = new StudentDao();
		Student student = dao.findById(2);
		Collection<Enrollment> expEnrollments = new LinkedList<>();
		Student expStudent = new Student(2);
		expEnrollments.add(new Enrollment(1, expStudent, Term.Y2012_1ST));		
		expEnrollments.add(new Enrollment(2, expStudent, Term.Y2012_2ND));
		assertEquals(expStudent, student);
		assertEquals(new TreeSet<Enrollment>(expEnrollments), new TreeSet<Enrollment>(student.getEnrollments()));
	}

}
