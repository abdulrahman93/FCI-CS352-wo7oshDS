package Test;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import com.FCI.SWE.Models.UserEntity;
import com.FCI.SWE.Services.Service;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

@Test(dependsOnGroups={"Entity"})
public class TestService {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig()
					.setDefaultHighRepJobPolicyUnappliedJobPercentage(100));

	@BeforeClass
	public void setUp() {
		helper.setUp();
		
		Service s = new Service();
		s.registrationService("Ali", "Ali@test", "123");
		s.registrationService("hali", "HAli@test", "123");
	}

	@AfterClass
	public void tearDown() {
		helper.tearDown();
	}
	
	@DataProvider(name = "testReg")
	public static Object[][] testReg(){
		Service s = new Service();
		
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		
		return new Object[][] {{object.toString(),s.registrationService("Ali", "Ali@test", "123")},
				{null, s.registrationService("Hussein", "", "123")}};
	}
	
	@Test(dataProvider = "testReg")
	public void testRegistration(String obj, String status) {
		Assert.assertEquals(status, obj);
	}
	
	@DataProvider(name = "testLogin")
	public static Object[][] testLogin(){
		Service s = new Service();
		
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		
		JSONObject object2 = new JSONObject();
		object2.put("Status", "Failed");
		
		/*
		s.registrationService("Ali", "Ali@test", "123");
		
		
		UserEntity u = new UserEntity("Ali", "Ali@test", "123");
		Boolean b = u.saveUser();
		System.out.println(b);
		
		return new Object[][] {{object.toString(), s.loginService("Ali", "123")},
				{object2.toString(), s.loginService("Ahmed", "1234")}};
		
		*/
		return new Object[][] {{object2.toString(), s.loginService("Ahmed", "1234")}};
		
	}
	
	@Test(dataProvider = "testLogin")
	public void testLogin(String obj, String status) {
		Assert.assertEquals(status, obj);
	}
	
	/*
	@Test
	public void testFriendReq(){
		Service s = new Service();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		
		Assert.assertEquals(s.FreindReqService("Ali","Ahmed"),object.toString());
	}
	
	
	@Test
	public void testFriendSer(){
		Service s = new Service();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		
		Assert.assertEquals(s.FreindsService("Ali","Ahmed"),object.toString());
	}
	
	@Test
	public void testSendIMessage(){
		Service s = new Service();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		
		Assert.assertEquals(s.postCreated("Ahmed", "Ahlan", "happy", "public"),object.toString());
	}
	
	@Test
	public void testSender(){
		Service s = new Service();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		
		Assert.assertEquals(s.postCreated("Ahmed", "Ahlan", "happy", "public"),object.toString());
	}
	
	*/
	@Test
	public void testPost(){
		Service s = new Service();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		
		Assert.assertEquals(s.postCreated("Ahmed", "Ahlan", "happy", "public"),object.toString());
	}

}
