package com.orangeandbronze.schoolreg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.orangeandbronze.schoolreg.domain.Enrollment;
import com.orangeandbronze.schoolreg.domain.Student;

public class StudentDao extends Dao {

	public Student findById(int studentId) {
		String sql = getSql("StudentDao.findById.sql");

		Student student = Student.NONE;

		try (Connection conn = getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, studentId);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				if (student == Student.NONE) {
					student = new Student(rs.getInt("student_number"));
					setPrimaryKey(student, rs.getLong("student_pk"));
				}
				Enrollment e = new Enrollment(rs.getInt("enrollment_number"), student, rs.getString("term"));
				setPrimaryKey(e, rs.getLong("enrollment_pk"));
			}
		} catch (SQLException e) {
			handleException(student, e);
		}

		return student;
	}

}
