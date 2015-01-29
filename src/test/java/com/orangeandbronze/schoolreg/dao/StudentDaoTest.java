package com.orangeandbronze.schoolreg.dao;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.junit.experimental.categories.Category;

import com.orangeandbronze.schoolreg.domain.Enrollment;
import com.orangeandbronze.schoolreg.domain.Section;
import com.orangeandbronze.schoolreg.domain.Student;
import com.orangeandbronze.schoolreg.domain.Subject;
import com.orangeandbronze.test.IntegrationTest;

@Category(IntegrationTest.class)
public class StudentDaoTest extends DaoTest {
	
	public StudentDaoTest() {
		super("StudentDaoTest.xml");
	}
	
	public void testFindById() {
		StudentDao dao = new StudentDao();
		Student student = dao.findById(2);
		Student expStudent = new Student(2);		
		assertEquals(expStudent, student);
		
		Collection<Enrollment> expEnrollments = new HashSet<>();		
		
		final Subject math11 = new Subject("MATH 11");
		final Subject math14 = new Subject("MATH 14");
		Set<Subject> prerequisitesToNewSection = new HashSet<Subject>() {{ add(math11); add(math14); }};
		Subject math53 = new Subject("MATH 53", prerequisitesToNewSection);
		Section sec1 = new Section("AAA111", math53, "2014 1st");
		
		Enrollment enroll1 = new Enrollment(1, expStudent, "2012 1st");
		Section sec2 = new Section("GGG777", math11, "2012 1st");
		
		Enrollment enroll2 = new Enrollment(2, expStudent, "2012 2nd");		
		
		expEnrollments.add(enroll1);		
		expEnrollments.add(enroll2);
		
		assertEquals(expEnrollments, new HashSet<Enrollment>(student.getEnrollments()));
		
		
	}

}
