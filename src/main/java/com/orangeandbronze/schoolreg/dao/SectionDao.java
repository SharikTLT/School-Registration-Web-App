package com.orangeandbronze.schoolreg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;

import com.orangeandbronze.schoolreg.domain.Faculty;
import com.orangeandbronze.schoolreg.domain.Schedule;
import com.orangeandbronze.schoolreg.domain.Section;
import com.orangeandbronze.schoolreg.domain.Subject;

public class SectionDao extends Dao {

	/**
	 * Only retrieves one level of prerequisites. Does not recursively get
	 * prerequisites of prerequisites... that's beyond my powers of SQL. Anyone,
	 * none of the use cases require beyond first-level prerequisties.
	 **/
	public Section getById(String sectionNumber) {

		String sql = getSql("SectionDao.getById.sql");

		Section section = null;

		try (Connection conn = getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sectionNumber);
			ResultSet rs = pstmt.executeQuery();

			long pk = 0;
			long fkSubject = 0;
			long fkFaculty = 0;
			String scheduleString = null;
			String subjectId = null;

			Faculty instructor = null;

			Collection<Subject> prereqs = new LinkedList<>();
			while (rs.next()) {
				if (rs.isFirst()) {
					pk = rs.getInt("pk");
					fkSubject = rs.getInt("fk_subject");
					fkFaculty = rs.getInt("fk_faculty");
					scheduleString = rs.getString("schedule");
					subjectId = rs.getString("subject_id");
					int facultyNum = rs.getInt("faculty_number");
					instructor = newFaculty(fkFaculty, facultyNum);
				}

				getPrerequisites(rs, prereqs);

			}
			Subject subject = newSubject(fkSubject, subjectId, prereqs);

			Schedule schedule = newSchedule(scheduleString);
			section = newSection(pk, sectionNumber, subject, schedule, instructor);

		} catch (SQLException e) {
			handleException(section, e);
		}

		return section;

	}

	/** Does not get prerequisites. **/
	public Collection<Section> getAll() {

		String sql = getSql("SectionDao.getAll.sql");

		final Collection<Section> sections = new LinkedList<>();
		Section currentSection = null;

		try (Connection conn = getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			long pk = 0;
			long previousPk = 0;
			long fkSubject = 0;
			String scheduleString = null;
			String subjectId = null;
			String sectionNumber = null;
			Subject subject = null;
			Schedule schedule = null;
			Faculty instructor = null;
			int facultyNumber = 0;
			long fkFaculty = 0;

			Collection<Subject> prereqs = new LinkedList<>();

			while (rs.next()) {
				pk = rs.getInt("pk");

				if (pk != previousPk && previousPk != 0) { // previous row was last row in a series of rows w/ same pk
					createSubjectSectionAndAddToCollection(sections, pk, fkSubject, subjectId, sectionNumber, schedule, instructor, prereqs);
				}

				if (pk != previousPk) { // new Section
					sectionNumber = rs.getString("section_number");
					fkSubject = rs.getInt("fk_subject");
					fkFaculty = rs.getInt("fk_faculty");
					scheduleString = rs.getString("schedule");
					subjectId = rs.getString("subject_id");
					facultyNumber = rs.getInt("faculty_number");
					instructor = newFaculty(fkFaculty, facultyNumber);
					prereqs = new LinkedList<>(); // garbage collect old prereqs
					schedule = newSchedule(scheduleString);
					instructor = newFaculty(fkFaculty, facultyNumber);
				}

				// get prereqs
				getPrerequisites(rs, prereqs);

				previousPk = pk;
			}
			createSubjectSectionAndAddToCollection(sections, pk, fkSubject, subjectId, sectionNumber, schedule, instructor, prereqs);

		} catch (SQLException e) {
			handleException(currentSection, e);
		}

		return sections;
	}

	void getPrerequisites(ResultSet rs, Collection<Subject> prereqs) throws SQLException {
		int fkPrerequisite = rs.getInt("fk_prerequisite");
		Subject prereq = newSubject(fkPrerequisite, rs.getString("prerequisites"));
		prereqs.add(prereq);
	}

	private void createSubjectSectionAndAddToCollection(Collection<Section> sections, long pk, long fkSubject, String subjectId, String sectionNumber,
			Schedule schedule, Faculty instructor, Collection<Subject> prereqs) {
		Section currentSection;
		Subject subject;
		subject = newSubject(fkSubject, subjectId, prereqs);
		currentSection = newSection(pk, sectionNumber, subject, schedule, instructor);
		sections.add(currentSection);
	}

}
