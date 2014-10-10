package com.orangeandbronze.schoolreg.dao;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

import com.orangeandbronze.schoolreg.domain.Days;
import com.orangeandbronze.schoolreg.domain.Period;
import com.orangeandbronze.schoolreg.domain.Schedule;
import com.orangeandbronze.schoolreg.domain.Section;
import com.orangeandbronze.schoolreg.domain.Subject;

public class SectionDaoTest extends DaoTest {
	
	public SectionDaoTest() {
		super("SectionDaoTest.xml");
	}

	public void testGetAll() {
		final Subject math11 = new Subject("MATH11");
		final Subject math14 = new Subject("MATH14");
		final Set<Subject> expectedPrereq = new HashSet<Subject>() {
			{
				add(math11);
				add(math14);
			}
		};
		final Subject subjWithPrereq = new Subject("MATH53", expectedPrereq);
		Set<Section> expectedSections = new HashSet<Section>() {
			{
				add(new Section("AAA111", subjWithPrereq, new Schedule(Days.MTH, Period.AM10)));
				add(new Section("BBB222", new Subject("COM1")));
				add(new Section("CCC333", new Subject("CS11")));
				add(new Section("DDD444", new Subject("PHILO1"), new Schedule(Days.TF, Period.PM4)));
				add(new Section("EEE555", new Subject("CS11")));
				add(new Section("GGG777", new Subject("MATH11")));
				add(new Section("ZZZ000", new Subject("CHEM11"), new Schedule(Days.TF, Period.PM4)));
			}
		};
		SectionDao dao = new SectionDao();
		Collection<Section> actual = new HashSet<>(dao.findAll());
		assertEquals(expectedSections, actual);
		
//		assertThat(
//				actual,
//				containsInAnyOrder(new Section("AAA111", subjWithPrereq, new Schedule(Days.MTH, Period.AM10)), new Section("BBB222", new Subject("COM1")),
//						new Section("CCC333", new Subject("CS11")), new Section("DDD444", new Subject("PHILO1"), new Schedule(Days.TF, Period.PM4)),
//						new Section("EEE555", new Subject("CS11")), new Section("GGG777", new Subject("MATH11")), new Section("ZZZ000", new Subject("CHEM11"),
//								new Schedule(Days.TF, Period.PM4))));

		Iterator<Section> iActual = actual.iterator();
		Iterator<Section> iExpected = expectedSections.iterator();

		while (iActual.hasNext() && iExpected.hasNext()) {
			Section sActual = iActual.next();
			Section sExpected = iExpected.next();
			assertEquals(sExpected, sActual);
			assertEquals(sExpected.getInstructor(), sActual.getInstructor());
			assertEquals(sExpected.getSchedule(), sActual.getSchedule());

			Subject actualSubject = sActual.getSubject();
			Subject expectedSubject = sExpected.getSubject();
			assertEquals(expectedSubject, actualSubject);

			if (actualSubject.equals(subjWithPrereq)) {
				Collection<Subject> actualPrereq = new HashSet<>(actualSubject.getPrerequisites());
				assertEquals(expectedPrereq, actualPrereq);
			}
		}
	}

	public void testGetByIdSubjectNoPrerequisites() {
		SectionDao dao = new SectionDao();
		Section actual = dao.findById("BBB222");
		Section expected = new Section("BBB222", new Subject("COM1"));
		assertEquals(expected, actual);
		assertEquals(expected.getSubject(), actual.getSubject());
		assertEquals(expected.getInstructor(), actual.getInstructor());
		assertEquals(expected.getSchedule(), actual.getSchedule());
	}

	/**
	 * Only checks one level of prerequistes. Does not check nested
	 * prerequisties.
	 **/
	public void testGetByIdSubjectHasPrerequisites() {
		SectionDao dao = new SectionDao();
		Section actual = dao.findById("AAA111");
		final Subject math11 = new Subject("MATH11");
		final Subject math14 = new Subject("MATH14");
		final Collection<Subject> expectedPrereq = new LinkedList<Subject>() {
			{
				add(math11);
				add(math14);
			}
		};
		Subject expectedSubject = new Subject("MATH53", expectedPrereq);
		Section expected = new Section("AAA111", expectedSubject, new Schedule(Days.MTH, Period.AM10));
		assertEquals(expected, actual);
		assertEquals(expected.getInstructor(), actual.getInstructor());
		assertEquals(expected.getSchedule(), actual.getSchedule());

		Subject actualSubject = actual.getSubject();
		assertEquals(expectedSubject, actualSubject);

		Collection<Subject> actualPrereq = actualSubject.getPrerequisites();

		assertEquals(expectedPrereq, actualPrereq);

	}

}
