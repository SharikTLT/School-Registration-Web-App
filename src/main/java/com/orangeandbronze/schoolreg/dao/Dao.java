package com.orangeandbronze.schoolreg.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.orangeandbronze.schoolreg.domain.Entity;
import com.orangeandbronze.schoolreg.domain.Section;

/** Layer Supertype for all Daos, to hold common code. **/
public class Dao {

	public Dao() {
		try {
			Class.forName("com.mysql.jdbc.Driver"); // load driver class into JVM
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

	

}