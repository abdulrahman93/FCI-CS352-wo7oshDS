package Test;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.FCI.SWE.Models.*;
import com.FCI.SWE.Services.Service;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

@Test(groups = "Entity")
public class NewTest {
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

	@Test
	public void testSingleMessage() {
		SingleMessage s = new SingleMessage("Ahmed", "Ali", "Hello, man");

		Boolean b = true;
		Assert.assertEquals(b, s.saveSingleMessage());
	}

	
	@Test
	public void f2() {
		Service s = new Service();

		JSONObject object = new JSONObject();
		object.put("Status", "OK");

		Assert.assertEquals(object.toString(),
				s.FreindReqService("Ahmed", "Hussein"));
	}
}
