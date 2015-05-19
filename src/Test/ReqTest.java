package Test;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.FCI.SWE.Models.ReqForm;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;


@Test(dependsOnGroups = "testUser", groups = "Entity")
public class ReqTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig()
					.setDefaultHighRepJobPolicyUnappliedJobPercentage(100));

	@BeforeClass
	public void setUp() {
		helper.setUp();
	}

	@AfterClass
	public void tearDown() {
		helper.tearDown();
	}

	public void testGetReq() {
		ReqForm r = new ReqForm("Ahmed", "Ali", "Pending");
		r.saveReq();
		Boolean b = true;
		Assert.assertEquals("Pending", r.getReq("Ahmed", "Ali").getStat());
	}

	// ==========================================================================
	@DataProvider(name = "testSaveReq1")
	public static Object[][] testReqCases() {
		ReqForm r = new ReqForm("Ahmed", "Ali", "Pending");
		ReqForm r2 = new ReqForm("Hussein", "Ahmed", "Pending");
		ReqForm r3 = new ReqForm("Omar", "Hussein", "Pending");
		Boolean b = true;

		return new Object[][] { { b, r }, { b, r2 }, { b, r3 } };
	}

	@Test(dataProvider = "testSaveReq1")
	public void testReq(Boolean a, ReqForm b) {
		Assert.assertEquals(a, b.saveReq());
	}
	// ==========================================================================

}
