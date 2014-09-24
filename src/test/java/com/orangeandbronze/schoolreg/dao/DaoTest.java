package com.orangeandbronze.schoolreg.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import com.orangeandbronze.schoolreg.domain.Entity;
import com.orangeandbronze.schoolreg.domain.Faculty;

public class DaoTest {
	
	@Test
	public void getPrimaryKey() {
		Dao dao = new Dao() {};
		Entity entity = new Faculty(500);
		assertEquals(new Long(0), dao.getPrimaryKey(entity));
		dao.setPrimaryKey(entity, 1000);
		assertEquals(new Long(1000), dao.getPrimaryKey(entity));		
	}

}
