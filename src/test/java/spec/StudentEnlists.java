package spec;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.concordion.integration.junit4.ConcordionRunner;
import org.concordion.api.MultiValueResult;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

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
import com.orangeandbronze.schoolreg.service.EnlistService.EnlistmentResult;
import com.orangeandbronze.test.IntegrationTest;

@Category(IntegrationTest.class)
@RunWith(ConcordionRunner.class)
public class StudentEnlists {

	public Section enlistInFirstSection(int studentNumber, String sectionId) throws Exception {

		setupDb("StudentEnlists.enlistInFirstSection.xml");

		// Actual test
		EnlistService service = new EnlistService();
		EnlistmentResult result = service.enlistSections(studentNumber, sectionId);
		return result.getSuccessfullyEnlisted().iterator().next();
	}

	public Section enlistSuccessfullyInSecondSection(int studentNumber, String sectionIdCurrent, String scheduleCurrent, String sectionIdNew, String scheduleNew) {

		// Mock data
		final Student student = new Student(1);
		final Section alreadyEnlisted = new Section(sectionIdCurrent, new Subject("CHEM11"), "2014 1st", new Schedule(scheduleCurrent));
		final Section ccc333 = new Section("CCC333", new Subject("CS11"), "2014 1st", new Schedule(scheduleNew));
		final Enrollment currentEnrollment = new Enrollment(143, student, "2014 1st", new HashSet<Section>() {{add(alreadyEnlisted);}});

		// Mock DAOs
		StudentDao studentDao = mock(StudentDao.class);
		when(studentDao.findById(studentNumber)).thenReturn(student);
		SectionDao sectionDao = mock(SectionDao.class);
		when(sectionDao.findById("CCC333")).thenReturn(ccc333);
		EnrollmentDao enrollmentDao = mock(EnrollmentDao.class);
		when(enrollmentDao.findLatestBy(student)).thenReturn(currentEnrollment);

		// Create the service
		EnlistService service = new EnlistService();

		// Inject the mock DAOs into the service
		service.setStudentDao(studentDao);
		service.setSectionDao(sectionDao);
		service.setEnrollmentDao(enrollmentDao);

		EnlistmentResult result = service.enlistSections(studentNumber, sectionIdNew);
		return result.getSuccessfullyEnlisted().iterator().next();
	}
	
	public MultiValueResult missingPrereq(int studentNumber, String sectionId) throws Exception {
		setupDb("StudentEnlists.missingPrereq.xml");
		
		EnlistService service = new EnlistService();
		EnlistmentResult result = service.enlistSections(studentNumber, sectionId);
		Map<Section, String> failures = result.getFailedToEnlist();
		Section section = failures.keySet().iterator().next();

		return new MultiValueResult().with("section", section).with("message", failures.get(section));
	}

	public MultiValueResult scheduleConflict(int studentNumber, String sectionId) {

		// Mock data
		final Student student = new Student(1);
		final Section alreadyEnlisted = new Section("ZZZ000", new Subject("CHEM11"), "2014 1st", new Schedule(Days.TF, Period.PM4));
		final Section ddd444 = new Section("DDD444", new Subject("PHILO1"), "2014 1st", new Schedule(Days.TF, Period.PM4));
		final Enrollment currentEnrollment = new Enrollment(143, student, "2014 1st", new HashSet<Section>() {{add(alreadyEnlisted);}});

		// Mock DAOs
		StudentDao studentDao = mock(StudentDao.class);
		when(studentDao.findById(studentNumber)).thenReturn(student);
		SectionDao sectionDao = mock(SectionDao.class);
		when(sectionDao.findById("DDD444")).thenReturn(ddd444);
		EnrollmentDao enrollmentDao = mock(EnrollmentDao.class);
		when(enrollmentDao.findLatestBy(student)).thenReturn(currentEnrollment);

		// Create the service
		EnlistService service = new EnlistService();

		// Inject the mock DAOs into the service
		service.setStudentDao(studentDao);
		service.setSectionDao(sectionDao);
		service.setEnrollmentDao(enrollmentDao);
		
		EnlistmentResult result = service.enlistSections(studentNumber, sectionId);
		Map<Section, String> failures = result.getFailedToEnlist();
		Section section = failures.keySet().iterator().next();

		return new MultiValueResult().with("section", section).with("message", failures.get(section));
	}	
	
	private void setupDb(String datasetFilename) throws SQLException, DatabaseUnitException, DataSetException {
		Connection jdbcConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_registration?sessionVariables=FOREIGN_KEY_CHECKS=0",
				"root", "");
		IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		builder.setDtdMetadata(false);
		IDataSet dataSet = builder.build(getClass().getResourceAsStream(datasetFilename));
		try {
			DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
		} finally {
			connection.close();
		}
	}
}
