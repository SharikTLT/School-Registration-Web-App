package com.orangeandbronze.schoolreg.domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

public class StudentTest {

	@Test
	public void getPreviousEnrollments() {
		Student student = new Student(123);
		final Enrollment enrollment1 = new Enrollment(1, student, Term.Y2012_1ST);
		final Enrollment enrollment2 = new Enrollment(2, student, Term.Y2012_2ND);
		final Enrollment enrollment3 = new Enrollment(3, student, Term.Y2013_1ST);
		final Enrollment enrollment4 = new Enrollment(4, student, Term.Y2013_2ND);
		
		Collection<Enrollment> previous = student.getPreviousEnrollmentsTo(enrollment3);
		Collection<Enrollment> expected = new ArrayList<Enrollment>() {{ add(enrollment1); add(enrollment2); }};
		assertEquals(expected, previous);
	}

}
