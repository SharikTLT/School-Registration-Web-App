package spec;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import com.orangeandbronze.test.IntegrationTest;

@Category(IntegrationTest.class)
@RunWith(ConcordionRunner.class)
public class TuitionFeeAssessment {
	
	private int units = 0;
	
	public void add(int units) {
		this.units += units;
	}
	
	public int getTotalUnits() {
		return units;
	}
	
	public int getTuition() {
		return units * 1000 +2000;
	}

}
