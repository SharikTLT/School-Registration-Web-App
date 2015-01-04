package com.orangeandbronze.schoolreg.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import com.orangeandbronze.schoolreg.domain.Days;
import com.orangeandbronze.schoolreg.domain.Entity;
import com.orangeandbronze.schoolreg.domain.Faculty;
import com.orangeandbronze.schoolreg.domain.Period;
import com.orangeandbronze.schoolreg.domain.Schedule;
import com.orangeandbronze.schoolreg.domain.Section;
import com.orangeandbronze.schoolreg.domain.Subject;

/** Layer Supertype for all Daos, to hold common code. **/
public class Dao {

	/*
	 * TODO How to generate primary keys? There are two options: you can either
	 * have the database do auto-increment, or you can generate keys in the
	 * application. In the real world, both have their advantages and
	 * disadvantages.
	 */

	Dao() {
		try {
			Class.forName("com.mysql.jdbc.Driver"); // load driver class into
													// JVM
		} catch (ClassNotFoundException e) {
			throw new DataAccessException("Problem while loading JDBC driver.", e);
		}
	}

	Connection getConnection() {
		try {
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/school_registration", "root", "");
		} catch (SQLException e) {
			throw new DataAccessException("Problem while trying to get DB connection.", e);
		}
	}

	void setPrimaryKey(Entity entity, long pk) {
		try {
			Field field = Section.class.getSuperclass().getDeclaredField("primaryKey");
			field.setAccessible(true);
			field.set(entity, pk);
		} catch (ReflectiveOperationException e) {
			throw new DataAccessException("Something happend setting " + entity.getClass() + " primary key via reflection.", e);
		}
	}

	Long getPrimaryKey(Entity entity) {
		try {
			Field field = Section.class.getSuperclass().getDeclaredField("primaryKey");
			field.setAccessible(true);
			return (Long) field.get(entity);
		} catch (ReflectiveOperationException e) {
			throw new DataAccessException("Something happend getting " + entity.getClass() + " primary key via reflection.", e);
		}
	}

	void handleException(Entity entity, Exception e) {
		throw new DataAccessException("Problem while accessing data for " + entity.getClass(), e);
	}

	String getSql(String sqlFile) {
		try (Reader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream('/'+sqlFile)))) {
			StringBuilder bldr = new StringBuilder();
			for (int i = reader.read(); i > 0; i = reader.read()) { 
				bldr.append((char) i);
			}
			return bldr.toString();
		} catch (IOException e) {
			throw new DataAccessException("Problem while trying to read file from classpath.", e);
		}
	}

	Schedule newSchedule(String scheduleString) {
		String[] dayPeriod = scheduleString.split("\\s+");
		return dayPeriod.length > 1 ? new Schedule(Days.valueOf(dayPeriod[0]), Period.valueOf(dayPeriod[1])) : Schedule.TBA;
	}

	Faculty newFaculty(long pk, int facultyNum) {
		Faculty instructor;
		if (pk != 0) { // not TBA
			instructor = new Faculty(facultyNum);
			setPrimaryKey(instructor, pk);
		} else { // TBA
			instructor = Faculty.TBA;
		}
		return instructor;
	}

	Subject newSubject(long pk, String subjectId, Collection<Subject> prereqs) {
		Subject subject = new Subject(subjectId, prereqs);
		setPrimaryKey(subject, pk);
		return subject;
	}

	Subject newSubject(long pk, String subjectId) {
		return newSubject(pk, subjectId, new LinkedList<Subject>());
	}

	Section newSection(long pk, String sectionNumber, Subject subject, String term, Schedule schedule, Faculty instructor) {
		Section section = new Section(sectionNumber, subject, term, schedule, instructor);
		setPrimaryKey(section, pk);
		return section;
	}

}