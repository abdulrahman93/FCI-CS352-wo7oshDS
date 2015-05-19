package Test;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.FCI.SWE.Models.UserEntity;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

@Test(groups = { "Entity", "testUser" })
public class UserTest {
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

	// ==========================================================================
	@DataProvider(name = "testGetName1")
	public static Object[][] testGetName() {
		UserEntity user = new UserEntity("Ahmed", "ahmed@ay7aga.com", "1234");
		UserEntity user2 = new UserEntity("Ali", "", "1234");
		UserEntity user3 = new UserEntity("", "", "1234");

		return new Object[][] { { "Ahmed", user.getName() },
				{ "Ali", user2.getName() }, { "", user3.getName() } };
	}

	@Test(dataProvider = "testGetName1")
	public void testGetUserEntity(String n, String user) {
		Assert.assertEquals(n, user);
	}

	// ==========================================================================
	@DataProvider(name = "testSaveUser1")
	public static Object[][] testSaveUser() {
		UserEntity user = new UserEntity("Ahmed", "ahmed@ay7aga.com", "1234");
		UserEntity user2 = new UserEntity("Ahmed", "", "1234");
		UserEntity user3 = new UserEntity("", "", "1234");
		Boolean b = true;

		return new Object[][] { { b, user }, { !b, user2 }, { !b, user3 } };
	}

	@Test(dataProvider = "testSaveUser1")
	public void testSaveUser(Boolean a, UserEntity user) {
		Assert.assertEquals(a, user.saveUser());
	}

	// ==========================================================================

	@Test
	public void f() {
	}
}
