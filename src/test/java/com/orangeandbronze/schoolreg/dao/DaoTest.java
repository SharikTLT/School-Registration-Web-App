package com.orangeandbronze.schoolreg.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import com.orangeandbronze.schoolreg.domain.Entity;
import com.orangeandbronze.schoolreg.domain.Faculty;

public class DaoTest {
	
	@Test
	public void getPrivateKey() {
		Dao dao = new Dao() {};
		Entity entity = new Faculty(500);
		assertEquals(new Long(0), dao.getPrivateKey(entity));
		dao.setPrivateKey(entity, 1000);
		assertEquals(new Long(1000), dao.getPrivateKey(entity));		
	}

}
