package com.orangeandbronze.schoolreg.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Student extends Entity {
	
	private final Integer studentNumber;
	
	private final SortedSet<Enrollment> enrollments = new TreeSet<>();

	public Student(Integer studentNumber) {
		this.studentNumber = studentNumber;
	}
	
	public Student(Integer studentNumber, Collection<Enrollment> enrollments) {
		this.studentNumber = studentNumber;
		this.enrollments.addAll(enrollments);
	}

	public Integer getStudentNumber() {
		return studentNumber;
	}

	Set<Enrollment> getEnrollments() {
		return new HashSet<>(enrollments);
	}
	
	void add(Enrollment e) {
		enrollments.add(e);
	}

	Collection<Enrollment> getPreviousEnrollmentsTo(Enrollment e) {
		return new ArrayList(enrollments.headSet(e));
	}

	public boolean hasTakenPrerequisites(Section newSec, Enrollment currentEnrollment) {
		Collection<Enrollment> prevEnrollments = getPreviousEnrollmentsTo(currentEnrollment);
		return newSec.hasAllPrerequisitesIn(prevEnrollments);
	}
	
	

}
