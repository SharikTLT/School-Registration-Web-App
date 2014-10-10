package com.orangeandbronze.schoolreg.dao.mock;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.orangeandbronze.schoolreg.dao.SectionDao;
import com.orangeandbronze.schoolreg.domain.Days;
import com.orangeandbronze.schoolreg.domain.Period;
import com.orangeandbronze.schoolreg.domain.Schedule;
import com.orangeandbronze.schoolreg.domain.Section;
import com.orangeandbronze.schoolreg.domain.Subject;

public class MockSectionDao extends SectionDao {
	private Map<String, Section> allSections = new HashMap<String, Section>() {
		{
			put("AAA111", new Section("AAA111", new Subject("MATH53"), new Schedule(Days.MTH, Period.AM10)));
			put("BBB222", new Section("BBB222", new Subject("COM1")));
			put("CCC333", new Section("CCC333", new Subject("CS11")));
			put("DDD444", new Section("DDD444", new Subject("PHILO1"), new Schedule(Days.TF, Period.PM4)));
			put("EEE555", new Section("EEE555", new Subject("CS11")));
			put("ZZZ000", new Section("ZZZ000", new Subject("CHEM11"), new Schedule(	Days.TF, Period.PM4)));
		}
	};

	public Section findById(String sectionNumber) {
		return allSections.get(sectionNumber);
	}

	public Set<Section> findAll() {
		return new HashSet<Section>(allSections.values());
	}
}
