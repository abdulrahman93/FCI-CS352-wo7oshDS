package Test;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.FCI.SWE.Models.Post;
import com.FCI.SWE.Models.UserEntity;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

@Test(groups = "Entity")
public class PostTest {
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
	public void testgetStat() {
		Post p = new Post("Ahmed", "Ahlan", "happy", "public");

		Assert.assertEquals("happy", p.getStat());
	}

	// ==========================================================================
	@DataProvider(name = "testSavePost1")
	public static Object[][] testSavePostCases() {
		Post p = new Post("Ahmed", "Ahlan", "happy", "public");
		Post p2 = new Post("Ali", "Hello", "", "friends");
		Boolean b = true;

		return new Object[][] { { b, p }, { !b, p2 }};
	}

	@Test(dataProvider = "testSavePost1")
	public void testSavePost(Boolean a, Post b) {
		Assert.assertEquals(a, b.savePost());
	}

	// ==========================================================================
	
	@Test(dependsOnMethods = {"testSavePost"})
	public void testgetPost2() {
		Post p = new Post("Ahmed", "Ahlan", "happy", "public");
		p.savePost();
		
		Assert.assertEquals("Ahmed", p.getPost2("Ahmed").getOwner());
	}
	
	
}
