package testcases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import coreUtilities.testutils.ApiHelper;
import coreUtilities.utils.FileOperations;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pages.StartupPage;
import pages.substore_page;
import testBase.AppTestBase;
import testBase.UserActions;
import testdata.LocatorsFactory;

import io.restassured.response.Response;
//import rest.RestAssuredUtils;

public class substore_testcase extends AppTestBase {

	Map<String, String> configData;
	Map<String, String> loginCredentials;
	String expectedDataFilePath = testDataFilePath + "expected_data.xlsx";
	String loginFilePath = loginDataFilePath + "Login.xlsx";
	StartupPage startupPage;
	String randomInvoiceNumber;
	LocatorsFactory locatorsFactoryInstance;
	UserActions userActionsInstance;
	substore_page substorePageInstance;
	
	substore_page restAssuredInstance;

	private final String FILEPATH = "src/main/java/pages/substore_page.java";

	FileOperations fileOperations = new FileOperations();

	private final String EXCEL_FILE_PATH = "src/main/resources/config.xlsx"; // Path to the Excel file
	private final String SHEET_NAME = "PostData"; // Sheet name in the Excel file

	@Parameters({ "browser", "environment" })
	@BeforeClass(alwaysRun = true)
	public void initBrowser(String browser, String environment) throws Exception {
		configData = new FileOperations().readExcelPOI(config_filePath, environment);
		configData.put("url", configData.get("url").replaceAll("[\\\\]", ""));
		configData.put("browser", browser);

		boolean isValidUrl = new ApiHelper().isValidUrl(configData.get("url"));
		Assert.assertTrue(isValidUrl,
				configData.get("url") + " might be Server down at this moment. Please try after sometime.");
		initialize(configData);
		startupPage = new StartupPage(driver);
	}

	@Test(priority = 1, groups = {"sanity" }, description = "Precondition: User should be logged in and on the healthapp section\n"
					+ "1. Login in the healthapp application\n" 
					+ "2. Scroll down menu till Substore\n"
					+ "3. Click on the Substore" 
					+ "4. SubStore module should be present")

	public void verifySubstoreCounterSubModule() throws Exception {
		substorePageInstance = new substore_page(driver);

		Map<String, String> substoreExpectedData = new FileOperations().readExcelPOI(expectedDataFilePath, "substore");
		Map<String, String> loginData = new FileOperations().readExcelPOI(loginFilePath, "credentials");

		Assert.assertTrue(substorePageInstance.loginToHealthAppByGivenValidCredetial(loginData),
				"Login failed, Invalid credentials ! Please check manually");
		Assert.assertTrue(substorePageInstance.scrollDownAndClickSubstoreTab());
		System.out.println("Substore Page url : " + substoreExpectedData.get("url"));
		Assert.assertEquals(substorePageInstance.verifySubstorePageUrl(), substoreExpectedData.get("url"));
	}

	@Test(priority = 2, groups = { "sanity" }, description = "1. Login in the healthapp application\r\n"
			+ "2. Click on the substore \r\n" 
			+ "3. \"Select your Substore\" pop up "
			+ "4. Expected value that should be present in \"Select your Substore\" modal\r\n"
			+ "Expected sub-modals button:  Accounts,male ward SubStore,SubStore1,SubStore1")

	public void countAvailableSubstores() {
		try {
			substorePageInstance = new substore_page(driver);

			Assert.assertTrue(substorePageInstance.countAvailableSubstores());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 3, groups = {
			"sanity" }, description = "Precondition: User should be logged in and on the Verification section\\n\"\r\n"
					+ "	+ \"1. Click on the Inventory Module\r\n"
					+ "	+ \"2. Hover over on module signout button and get text"
					+ "3. Verify text To change, you can always click here.")

	public void verifyModuleSignoutHoverText() throws Exception {
		substorePageInstance = new substore_page(driver);
		Map<String, String> substoreExpectedData = new FileOperations().readExcelPOI(expectedDataFilePath, "substore");
		System.out.println("SignOut Hovertext is : " + substoreExpectedData.get("moduleSignOutHoverText"));
		Assert.assertTrue(substorePageInstance.verifyModuleSignoutHoverText(substoreExpectedData));
	}

	@Test(priority = 4, groups = {
			"sanity" }, description = "Pre condition: User should be logged in and it is on SubStore module\r\n"
					+ "1. Login in the healthapp application\r\n" + "2. Click on the Substore\r\n"
					+ "3. \"Select your Substore\" pop up \r\n" + "4. Click on \"Account\" sub-modal\r\n"
					+ "5. Click on the \"Inventory\" sub-module\r\n" + "6. Click on\" Pharmacy\" sub-module\r\n"
					+ "7. All sub-modules should be displayed correctly.\r\n"
					+ "8. Expected Sub modules are : Pharmacy, Inventory")

	public void verifySubstoreSubModule() throws Exception {
		substorePageInstance = new substore_page(driver);

		Map<String, String> substoreExpectedData = new FileOperations().readExcelPOI(expectedDataFilePath, "substore");

		Assert.assertTrue(substorePageInstance.verifySubstoreSubModule(substoreExpectedData));
	}

	@Test(priority = 5, groups = {
			"sanity" }, description = "Pre condition: User should be logged in and it is on SubStore module\r\n"
					+ "1. Click on the SubStore Module drop-down arrow   \r\n"
					+ "2. Click on Inventory module. Expected Sub modules are : Stock, Inventory Requisition, Consumption, Reports, Patient Consumption,Return")

	public void verifyAllSubstoreModulesAreDisplayedInSublist() throws Exception {
		substorePageInstance = new substore_page(driver);

		Assert.assertTrue(substorePageInstance.verifyAllSubstoreModulesAreDisplayedInSublist("Inventory"));
	}

	@Test(priority = 6, groups = { "sanity" }, description = "Pre condition: User should be logged in \r\n"
			+ "1. Click on the Substore Module drop-down arrow" 
			+ "2. Verify navigation between sub-modules")

	public void verifyNavigationBetweenSubmodules() throws Exception {

		substorePageInstance = new substore_page(driver);
		Assert.assertTrue(substorePageInstance.verifyNavigationBetweenSubmodules());
	}

	@Test(priority = 7, groups = { "sanity" }, description = "Under Substore module > INventory Section\"\r\n"
	+ "Take the screenshot of the current page")
	public void takingScreenshotOfCurrentPage() throws Exception {
		substorePageInstance = new substore_page(driver);
		Assert.assertTrue(substorePageInstance.takingScreenshotOfTheCurrentPage());
	}

	@Test(priority = 8, groups = {
			"sanity" }, description = "Pre condition: User should be logged in and it is on SubStore module\r\n"
					+ "1. Click on the SubStore module drop-down arrow \r\n" 
					+ "2. Click on Inventory sub-module\r\n"
					+ "3. Click on Inventory Requisition section")

	public void verifyInventoryRequisitionSection() throws Exception {

		Assert.assertTrue(substorePageInstance.verifyIfInventoryReqInputFieldsDropdownsAndCheckboxesAreVisibleOrNot());
	}
	
	// Rest Assured
	
	@Test(priority = 9, groups = { "sanity" }, description = "Precondition: Access to JSONPlaceholder API\n"
			+ "1. Send GET request to retrieve post by ID\n" + "2. Verify the response status code is 200\n"
			+ "3. Validate the response body contains the expected post data")
	public void verifyGetPostById() throws IOException {

		// Expected data
		Map<String, Object> expectedData = new HashMap<>();
		expectedData.put("id", 1);
		expectedData.put("title", "sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
		expectedData.put("body", "quia et suscipit\n" + "suscipit repellat nihil non omnis\n"
				+ "occaecati quisquam qui autem\n" + "magni et impedit vero fugiat");
		expectedData.put("userId", 1);

		restAssuredInstance = new substore_page(driver);
		// Perform GET request
		Response response = restAssuredInstance.getPostById(1);
		int responseStatusCode = response.getStatusCode();
		String responseBody = response.getBody().asString();

		// Validate method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH, "getPostById",
				List.of("given", "when", "get", "then", "extract", "response"));

		// Expected endpoint and response for RestAssuredValidator
		String endpoint = "https://jsonplaceholder.typicode.com/posts/1";
		int expectedStatusCode = 200;
		String expectedResponseBody = "{" + "\"userId\": 1," + "\"id\": 1,"
				+ "\"title\": \"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\","
				+ "\"body\": \"quia et suscipit\\n" + "suscipit repellat nihil non omnis\\n"
				+ "occaecati quisquam qui autem\\n" + "magni et impedit vero fugiat\"" + "}";

		String validatedResponse = RestAssuredValidator.validateGetRequest(endpoint, expectedStatusCode,
				expectedResponseBody);

		Assert.assertEquals(isValidationSuccessful
				&& expectedResponseBody.substring(1, 15).contains(validatedResponse.substring(5, 15))
				&& responseStatusCode == 200 && responseBody.contains("\"id\": 1"), true);

	}

	

	@Test(priority = 10, groups = { "sanity" }, description = "Precondition: Access to JSONPlaceholder API\n"
			+ "1. Send PUT request to update an existing post\n" + "2. Verify the response status code is 200\n"
			+ "3. Validate the response body contains the updated post data")
	public void verifyUpdatePost() {

		// Data for updating the post
		int postId = 1;
		String updatedTitle = "Updated Post Title";
		String updatedBody = "This is the updated body of the post.";
		int userId = 1;

		// Instantiate RestAssuredUtils
		restAssuredInstance = new substore_page(driver);

		// Perform PUT request using the updatePost() method from RestAssuredUtils
		Response response = restAssuredInstance.updatePost(postId, updatedTitle, updatedBody, userId);

		// Extract status code and response body
		int responseStatusCode = response.getStatusCode();
		String responseBody = response.getBody().asString();

		// Validate status code is 200
		Assert.assertEquals(responseStatusCode, 200, "Expected status code 200 but got " + responseStatusCode);

		// Perform field-level validation using assertTrue
		Assert.assertTrue(
				responseBody.contains("\"title\": \"" + updatedTitle + "\"")
						&& responseBody.contains("\"body\": \"" + updatedBody + "\"")
						&& responseBody.contains("\"userId\": " + userId) && responseBody.contains("\"id\": " + postId),
				"The response does not match the expected updated post data.");
	}

	@Test(priority = 11, groups = { "sanity" }, description = "Precondition: Access to JSONPlaceholder API\n"
			+ "1. Send DELETE request to delete an existing post by ID\n"
			+ "2. Verify the response status code is 200 or 204\n"
			+ "3. Ensure the post is deleted by verifying a GET request returns 404")
	public void verifyDeletePost() {

		// ID of the post to delete
		int postId = 1;

		// Perform DELETE request to remove the post
		restAssuredInstance = new substore_page(driver);
		Response deleteResponse = restAssuredInstance.deletePost(postId);

		// Verify the status code of the DELETE response (can be 200 or 204 depending on
		// API design)
		int deleteStatusCode = deleteResponse.getStatusCode();
		System.out.println(deleteStatusCode);
		Assert.assertTrue(deleteStatusCode == 200 || deleteStatusCode == 204,
				"Expected status code 200 or 204, but got " + deleteStatusCode);
	}

	@Test(priority = 12, groups = { "sanity" }, description = "Precondition: Access to JSONPlaceholder API\n"
			+ "1. Send GET request to retrieve comments for a post by postId\n"
			+ "2. Verify the response status code is 200\n" + "3. Validate that the response contains comments data")
	public void verifyGetCommentsForPost() {

		// ID of the post for which we want to retrieve comments
		int postId = 1;

		// Perform GET request to retrieve the comments for the post
		restAssuredInstance = new substore_page(driver);
		Response getCommentsResponse = restAssuredInstance.getCommentsForPost(postId);

		// Verify the status code of the GET response (should be 200 OK)
		int responseStatusCode = getCommentsResponse.getStatusCode();

		// Verify that the response body contains comments (we expect an array of
		// comments)
		String responseBody = getCommentsResponse.getBody().asString();
		Assert.assertTrue(
				responseStatusCode == 200 && responseBody.contains("id") && responseBody.contains("postId")
						&& responseBody.contains("name") && responseBody.contains("body"),
				"The response does not contain expected comments data.");
	}


	@AfterClass(alwaysRun = true)
	public void tearDown() {
		System.out.println("before closing the browser");
		browserTearDown();
	}

	@AfterMethod
	public void retryIfTestFails() throws Exception {
		startupPage.navigateToUrl(configData.get("url"));
	}

}
