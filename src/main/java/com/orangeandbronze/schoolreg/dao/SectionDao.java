package com.orangeandbronze.schoolreg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
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
			String scheduleString = null;
			String subjectId = null;

			Faculty instructor = null;

			Set<Subject> prereqs = new HashSet<>();
			while (rs.next()) {
				if (rs.isFirst()) {
					pk = rs.getInt("pk");
					fkSubject = rs.getInt("fk_subject");
					long fkFaculty = rs.getInt("fk_faculty");
					scheduleString = rs.getString("schedule");
					subjectId = rs.getString("subject_id");
					int facultyNum = rs.getInt("faculty_number");
					instructor = newFaculty(fkFaculty, facultyNum);
				}

				// get prereqs
				int fkPrerequisite = rs.getInt("fk_prerequisite");
				Subject prereq = newSubject(fkPrerequisite, rs.getString("prerequisites"));
				prereqs.add(prereq);

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
	public Set<Section> getAll() {

		String sql = getSql("SectionDao.getAll.sql");

		final Set<Section> sections = new HashSet<>();
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
			

			Set<Subject> prereqs = new HashSet<>();

			while (rs.next()) {	// TODO Prerequisites not being assigned to subject since Subject constructor just copies
				pk = rs.getInt("pk");
				
				if (pk != previousPk && previousPk != 0) { // previous row was last row in a series of rows w/ same pk
					createSubjectSectionAndAddToCollection(sections, pk, fkSubject, subjectId, sectionNumber, schedule, instructor, prereqs);
				} 
				
				if (pk != previousPk) { // new Section		
					fkSubject = rs.getInt("fk_subject");
					sectionNumber = rs.getString("section_number");
					fkFaculty = rs.getInt("fk_faculty");
					scheduleString = rs.getString("schedule");
					subjectId = rs.getString("subject_id");
					facultyNumber = rs.getInt("faculty_number");
					instructor = newFaculty(fkFaculty, facultyNumber);
					prereqs = new HashSet<>(); // garbage collect old prereqs
					schedule = newSchedule(scheduleString);
					instructor = newFaculty(fkFaculty, facultyNumber);
				}  
				
				// get prereqs
				int fkPrerequisite = rs.getInt("fk_prerequisite");
				Subject prereq = newSubject(fkPrerequisite, rs.getString("prerequisites"));
				prereqs.add(prereq);
				
				
				previousPk = pk;
			}
			createSubjectSectionAndAddToCollection(sections, pk, fkSubject, subjectId, sectionNumber, schedule, instructor, prereqs);

		} catch (SQLException e) {
			handleException(currentSection, e);
		}

		return sections;
	}

	private void createSubjectSectionAndAddToCollection(final Set<Section> sections, long pk, long fkSubject, String subjectId, String sectionNumber,
			Schedule schedule, Faculty instructor, Set<Subject> prereqs) {
		Section currentSection;
		Subject subject;
		subject = newSubject(fkSubject, subjectId, prereqs);
		currentSection = newSection(pk, sectionNumber, subject, schedule, instructor);
		sections.add(currentSection);
	}

}
