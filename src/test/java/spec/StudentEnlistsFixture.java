package spec;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.concordion.integration.junit4.ConcordionRunner;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import com.orangeandbronze.schoolreg.domain.Section;
import com.orangeandbronze.schoolreg.service.EnlistService;
import com.orangeandbronze.schoolreg.service.EnlistService.EnlistmentResult;
import com.orangeandbronze.test.IntegrationTest;

@Category(IntegrationTest.class)
@RunWith(ConcordionRunner.class)
public class StudentEnlistsFixture {

	public Collection enlistAndCheckIfSaved(int studentNumber, String sectionId) throws Exception {
		Connection jdbcConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_registration?sessionVariables=FOREIGN_KEY_CHECKS=0", "root", "");
		IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		builder.setDtdMetadata(false);		 
		IDataSet dataSet = builder.build(getClass().getResourceAsStream("StudentEnlistsFixture.xml"));
		try {
			DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
		} finally {
			connection.close();
		}

		EnlistService service = new EnlistService();
		EnlistmentResult result = service.enlistSections(studentNumber, sectionId);
		Set<Section> resultSections = result.getSuccessfullyEnlisted();
		return new ArrayList(resultSections); 
	}
}
