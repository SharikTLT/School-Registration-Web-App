package com.orangeandbronze.schoolreg.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.orangeandbronze.schoolreg.dao.EnrollmentDao;
import com.orangeandbronze.schoolreg.dao.SectionDao;
import com.orangeandbronze.schoolreg.dao.StudentDao;
import com.orangeandbronze.schoolreg.dao.mock.MockEnrollmentDao;
import com.orangeandbronze.schoolreg.dao.mock.MockStudentDao;
import com.orangeandbronze.schoolreg.domain.EnlistmentConflictException;
import com.orangeandbronze.schoolreg.domain.Enrollment;
import com.orangeandbronze.schoolreg.domain.MissingPrerequisitesException;
import com.orangeandbronze.schoolreg.domain.Section;
import com.orangeandbronze.schoolreg.domain.Student;
import com.orangeandbronze.schoolreg.domain.Term;

public class EnlistService {

	private StudentDao studentDao = new MockStudentDao();	 // TODO When real Dao is implemented, replace this with real Dao
	private SectionDao sectionDao = new SectionDao();
	private EnrollmentDao enrollmentDao = new MockEnrollmentDao(); // TODO When real Dao is implemented, replace this with real Dao
	
	public Collection<Section> getAllSections() {
		return sectionDao.getAll();
	}

	/**
	 * @param studentNumber
	 *            Student number of the student that the sections will attempt
	 *            to be enlisted.
	 * @param sectionNumbers
	 *            Section numbers of the sections to be enlisted with the
	 *            student.
	 * @return EnlistmentResult contains which sections were successfully
	 *         enlisted, and which were not. Those that were not include the
	 *         reason why they failed to enlist.
	 */
	public EnlistmentResult enlistSections(Integer studentNumber, String[] sectionNumbers) {
		// Fetch domain objects from DB
		Student student = studentDao.getById(studentNumber);
		Section[] sections = sectionNumbers != null ? new Section[sectionNumbers.length] : new Section[0];
		for (int i = 0; i < sections.length; i++) {
			sections[i] = sectionDao.getById(sectionNumbers[i]);
		}
		Enrollment enrollment = enrollmentDao.getBy(student, Term.getCurrent());

		// delegate work to domain model
		Set<Section> successfullyEnlisted = new HashSet<>();
		Map<Section, String> failedToEnlist = new HashMap<>();
		for (Section section : sections) {
			try {
				enrollment.enlist(section);
				successfullyEnlisted.add(section);
			} catch (EnlistmentConflictException e) {
				failedToEnlist.put(section, "Conflict with sections already enlisted.");
			} catch (MissingPrerequisitesException e) {
				failedToEnlist.put(section, "Missing prerequisite/s.");
			}
		}

		// must successfully save result to database before returning result, otherwise, throws DataAccessException
		enrollmentDao.save(enrollment);
		
		return new EnlistmentResult(successfullyEnlisted, failedToEnlist);
	}

	public static class EnlistmentResult {

		private final Set<Section> successfullyEnlisted;
		private final Map<Section, String> failedToEnlist;

		EnlistmentResult(Set<Section> successfullyEnlisted,
				Map<Section, String> failedToEnlist) {
			this.successfullyEnlisted = successfullyEnlisted;
			this.failedToEnlist = failedToEnlist;
		}

		public Set<Section> getSuccessfullyEnlisted() {
			return successfullyEnlisted;
		}

		/**
		 * Returns a map with the sections that failed to enlist as keys, and
		 * the reason why it failed as values.
		 **/
		public Map<Section, String> getFailedToEnlist() {
			return failedToEnlist;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime
					* result
					+ ((failedToEnlist == null) ? 0 : failedToEnlist.hashCode());
			result = prime
					* result
					+ ((successfullyEnlisted == null) ? 0
							: successfullyEnlisted.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			EnlistmentResult other = (EnlistmentResult) obj;
			if (failedToEnlist == null) {
				if (other.failedToEnlist != null)
					return false;
			} else if (!failedToEnlist.equals(other.failedToEnlist))
				return false;
			if (successfullyEnlisted == null) {
				if (other.successfullyEnlisted != null)
					return false;
			} else if (!successfullyEnlisted.equals(other.successfullyEnlisted))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "EnlistmentResult [successfullyEnlisted="
					+ successfullyEnlisted + ", failedToEnlist="
					+ failedToEnlist + "]";
		}

	}

	void setStudentDao(StudentDao studentDao) {
		this.studentDao = studentDao;
	}

	void setSectionDao(SectionDao sectionDao) {
		this.sectionDao = sectionDao;
	}

	void setEnrollmentDao(EnrollmentDao enrollmentDao) {
		this.enrollmentDao = enrollmentDao;
	}
	
	

}
