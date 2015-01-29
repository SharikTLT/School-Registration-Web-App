package spec;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

import com.orangeandbronze.schoolreg.dao.EnrollmentDao;
import com.orangeandbronze.schoolreg.dao.SectionDao;
import com.orangeandbronze.schoolreg.dao.StudentDao;
import com.orangeandbronze.schoolreg.domain.Days;
import com.orangeandbronze.schoolreg.domain.Enrollment;
import com.orangeandbronze.schoolreg.domain.Period;
import com.orangeandbronze.schoolreg.domain.Schedule;
import com.orangeandbronze.schoolreg.domain.Section;
import com.orangeandbronze.schoolreg.domain.Student;
import com.orangeandbronze.schoolreg.domain.Subject;
import com.orangeandbronze.schoolreg.service.EnlistService;
import com.orangeandbronze.test.IntegrationTest;

@Category(IntegrationTest.class)
@RunWith(ConcordionRunner.class)
public class StudentEnlistsFixture {

	public String enlistAndCheckIfSaved(int studentNumber, String sectionId) {
		EnlistService service = new EnlistService();
		Student student = new Student(studentNumber);
		String[] sectionNumbers = { "AAA111", "BBB222", "CCC333", "DDD444", "EEE555" };

		/* domain model */
		final Section alreadyEnlisted = new Section("ZZZ000", new Subject("CHEM11"), "2014 1st", new Schedule(Days.TF, Period.PM4));
		final Enrollment currentEnrollment = new Enrollment(143, student, "2014 1st", new HashSet<Section>() {{add(alreadyEnlisted);}});
		// three sections should pass
		final Section bbb222 = new Section(sectionNumbers[1], new Subject("COM1"), "2014 1st");
		final Section ccc333 = new Section(sectionNumbers[2], new Subject("CS11"), "2014 1st");
		final Section eee555 = new Section(sectionNumbers[4], new Subject("CS11"), "2014 1st");
		final Set<Section> successfullyEnlisted = new HashSet<Section>() {{
				add(bbb222);
				add(ccc333);
				add(eee555);
			}};

		// Map of failed enlistments
		final Map<Section, String> failedToEnlist = new HashMap<>();

		// one section should have schedule conflict
		final Section ddd444 = new Section(sectionNumbers[3], new Subject("PHILO1"), "2014 1st", new Schedule(Days.TF, Period.PM4));
		failedToEnlist.put(ddd444, "Conflict with sections already enlisted.");

		// one section should have problems with prerequistes
		final Subject math11 = new Subject("MATH11");
		final Subject math14 = new Subject("MATH14");
		final Set<Subject> prerequisitesToMath53 = new HashSet<Subject>() {{
				add(math11);
				add(math14);
			}};
		final Subject math53 = new Subject("MATH53", prerequisitesToMath53);
		final Section aaa111 = new Section(sectionNumbers[0], math53, "2014 1st", new Schedule(Days.MTH, Period.AM10));
		// only enrolled previously in Math11 but not Math14
		final Set<Section> previousSections = new HashSet<Section>() {{
				add(new Section("GGG777", math11, "2012 1st"));
			}};
		final Enrollment previousEnrollment = new Enrollment(100, student, "2012 1st", previousSections);
		failedToEnlist.put(aaa111, "Missing prerequisite/s.");

		/* Mock the daos */
		StudentDao studentDao = mock(StudentDao.class);
		when(studentDao.findById(studentNumber)).thenReturn(student);
		SectionDao sectionDao = mock(SectionDao.class);
		when(sectionDao.findById(sectionNumbers[0])).thenReturn(aaa111);
		when(sectionDao.findById(sectionNumbers[1])).thenReturn(bbb222);
		when(sectionDao.findById(sectionNumbers[2])).thenReturn(ccc333);
		when(sectionDao.findById(sectionNumbers[3])).thenReturn(ddd444);
		when(sectionDao.findById(sectionNumbers[4])).thenReturn(eee555);
		EnrollmentDao enrollmentDao = mock(EnrollmentDao.class);
		when(enrollmentDao.findLatestBy(student)).thenReturn(currentEnrollment);
		when(enrollmentDao.findBy(student, "2012 1st")).thenReturn(previousEnrollment);
		
		service.setStudentDao(studentDao);
		service.setSectionDao(sectionDao);
		service.setEnrollmentDao(enrollmentDao);

		service.enlistSections(studentNumber, sectionId);

		return "AAA111";
	}
}
