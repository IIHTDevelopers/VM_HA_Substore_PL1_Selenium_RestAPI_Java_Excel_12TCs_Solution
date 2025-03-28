package pages;

import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import static io.restassured.RestAssured.given;
import coreUtilities.utils.FileOperations;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class substore_page extends StartupPage {

	public substore_page(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
//	TC-1 Locators
	public By getUsernameTextfieldLocator() {
		return By.id("username_id");
	}

	public By getPasswordTextboxLocator() {

		return By.xpath("//input[@id='password']");
	}

	public By getSignInButtonLocator() {
		return By.xpath("//button[@id='login']");
	}

	public By getDropDownLocater() {
		return By.xpath("//a[@href='#/WardSupply']");
	}
//	TC-2 Locators
	public By getCounterButtonFourth() {
		return By.xpath("//a[@class='report_list']");
	}
//	TC-3,5 and 7 common Locator
	public By getAnchorTagLocatorByText(String anchorTagName) {
		return By.xpath("//a[contains(text(),'" + anchorTagName + "')]");
	}
	
	public By getModuleSignoutLocator() {
	return By.xpath("//i[contains(@class,'sign-out')]");
	}
	
	public By getHoverText() {
		return By.xpath("//h6[contains(text(),'To change, you can always click here.')]");
	}
	
//	TC-4 Locators
	public By getSubModuleLocator() {
		return By.xpath("//ul[contains(@class,'nav-tabs')]//li//a");
	}
	
//	TC-8 Locators
	public By getButtonLocatorsBytext(String buttonName) {
		return By.xpath("//button[contains(text(),'" + buttonName + "')]");
	}
	
	public By getCreateRequisitionButton() {
		return By.xpath("//button/span[text()='Create Requisition']");
	}
	
	public By searchBarId() {
		return By.id("quickFilterInput");
	}
	
	public By getStarIconLocator() {
		return By.xpath("//i[contains(@class,'icon-favourite')]/..");
	}
	
	public By getRadioButtonLocator(String radioButtonName) {
		return By.xpath("//label[contains(text(),'" + radioButtonName + "')]/span");
	}
	
	
	private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
	private static final String EXCEL_FILE_PATH = "src/main/resources/config.xlsx"; // Path to the Excel file
	private static final String SHEET_NAME = "PostData"; // Sheet name to read data from

	// FileOperations instance to read data from Excel
	FileOperations fileOperations = new FileOperations();


	/**
	 * @Test 1.1 : about this method loginToHealthAppByGivenValidCredetial()
	 * @param : Map<String, String>, Fetch and fill the details from expected_data.xlsx file
	 * @description : fill usernameTextbox & passwordTextbox and click on sign in button
	 * @return : Boolean
	 * @author : Yaksha
	 */
	public boolean loginToHealthAppByGivenValidCredetial(Map<String, String> expectedData) throws Exception {
		Boolean textIsDisplayed = false;
		try {
			WebElement usernametextFieldWebElement = commonEvents.findElement(getUsernameTextfieldLocator());
			commonEvents.highlightElement(usernametextFieldWebElement);
			commonEvents.sendKeys(getUsernameTextfieldLocator(), expectedData.get("username"));

			WebElement passwordtextFieldWebElement = commonEvents.findElement(getPasswordTextboxLocator());
			commonEvents.highlightElement(passwordtextFieldWebElement);
			commonEvents.sendKeys(getPasswordTextboxLocator(), expectedData.get("password"));

			WebElement signinButtonWebElement = commonEvents.findElement(getPasswordTextboxLocator());
			commonEvents.highlightElement(signinButtonWebElement);
			commonEvents.click(getSignInButtonLocator());
			textIsDisplayed = true;
		} catch (Exception e) {
			throw e;
		}
		return textIsDisplayed;

	}

	/**
	 * @Test 1.2 about this method scrollDownAndClickSubstoreTab()
	 * @param : null
	 * @description : Scroll to Substore tab, click it and verify its presence
	 * @return : boolean
	 * @author : YAKSHA
	 */
	public boolean scrollDownAndClickSubstoreTab() throws Exception {
		boolean scrolledTillElement = false;
		try {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			WebElement SubstoreTab = commonEvents.findElement(getDropDownLocater());
			jsExecutor.executeScript("arguments[0].scrollIntoView(true);", SubstoreTab);
			jsExecutor.executeScript("window.scrollBy(0, -50)");
			commonEvents.highlight(SubstoreTab);
			commonEvents.click(SubstoreTab);

			// Wait for the URL to contain "WardSupply"
			commonEvents.waitForUrlContains("WardSupply", 30);

			scrolledTillElement = true;
		} catch (Exception e) {
			throw e;
		}
		return scrolledTillElement;
	}

	/**
	 * @Test1.3 about this method verifySubstorePageUrl()
	 * @param : null
	 * @description : return the URL of the current page. 
	 * @return : String
	 * @author : YAKSHA
	 */

	public String verifySubstorePageUrl() throws Exception {
		
		try {
			String titleToVerify = commonEvents.getCurrentUrl();
			return titleToVerify;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @Test2 about this method countAvailableSubstores()
	 * @param : null
	 * @description : Clicks Counter modules
	 * @return : Boolean
	 * @throws : YAKSHA
	 */
	public boolean countAvailableSubstores() throws Exception {
		try {
			List<WebElement> counterElements = commonEvents.getWebElements(getCounterButtonFourth());
			System.out.println("Elemets size >> " + counterElements.size());
			int numberOfCounterElements = counterElements.size();
			if (numberOfCounterElements > 0) {
				commonEvents.highlight(counterElements.get(0)).click(counterElements.get(0));
			}
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @Test3 - about this method verifyModuleSignoutHoverText()
	 * @param substoreExpectedData : Map<String, String> - Contains expected hover text
	 * @description : This method verifies that the hover text on the "Sign Out" module matches the expected value.
	 * @return : boolean - true if the hover text matches the expected value, false otherwise.
	 * @author : YAKSHA
	 */
	public boolean verifyModuleSignoutHoverText(Map<String, String> substoreExpectedData) throws Exception {
		try {
			// Click on the "Inventory" section.
			commonEvents.click(getAnchorTagLocatorByText("Inventory"));

			// Locate the "Sign Out" module for hover action.
			WebElement elementToHover = commonEvents.findElement(getModuleSignoutLocator());

			// Create an instance of Actions class to perform hover action.
			Actions actions = new Actions(driver);

			// Perform the hover action on the "Sign Out" module.
			actions.moveToElement(elementToHover).perform();

			// Retrieve the hover text displayed.
			String elementText = commonEvents.findElement(getHoverText()).getText();
			System.out.println("Element text -->  " + elementText);

			// Check if the hover text matches the expected value.
			if (elementText.contains(substoreExpectedData.get("moduleSignOutHoverText"))) {
				return true;
			} else {
				throw new Exception("Hover text did not match the expected value.");
			}

		} catch (Exception e) {
			// Throw a meaningful exception indicating what failed.
			throw new Exception("Failed to verify the hover text on the 'Sign Out' module: " + e.getMessage(), e);
		}
	}

	/**
	 * @Test4 about this method verifySubstoreSubModule()
	 * @param substoreExpectedData : Map<String, String> - A map containing expected substore data, such as URLs or other related information.
	 * @description : This method verifies that the substore module's sub-modules(Inventory, Pharmacy) are visible.
	 * @return : boolean - true if the sub-modules are visible and clickable, false otherwise.
	 * @throws : Exception - if there is an issue finding or interacting with the sub-modules.
	 * @author : YAKSHA
	 */
	public boolean verifySubstoreSubModule(Map<String, String> substoreExpectedData) throws Exception {
		try {
			System.out.println("Substore Page URL: " + substoreExpectedData.get("URL"));

			// Find the Inventory and Pharmacy sub-modules
			WebElement inventorySubModule = commonEvents.findElement(getAnchorTagLocatorByText("Inventory"));
			WebElement pharmacySubModule = commonEvents.findElement(getAnchorTagLocatorByText("Pharmacy"));

			// Highlight and click on the Inventory sub-module
			commonEvents.highlight(inventorySubModule).click(inventorySubModule);

			// Highlight and click on the Pharmacy sub-module
			commonEvents.highlight(pharmacySubModule).click(pharmacySubModule);

			return true;
		} catch (Exception e) {
			throw new Exception("Failed to verify substore sub-modules due to: " + e.getMessage(), e);
		}
	}

	/**
	 * @Test5 about this method verifyAllSubstoreModulesAreDisplayedInSublist()
	 * @param moduleName : String - The name of the module to verify.
	 * @description : This method verifies if the specified module's sub-modules are present and visible.
	 * @return : boolean - true if the sub-modules are displayed, false otherwise.
	 * @throws : Exception - if there is an issue finding the sub-modules or if no elements are found.
	 * @author : YAKSHA
	 */
	public boolean verifyAllSubstoreModulesAreDisplayedInSublist(String moduleName) throws Exception {
		boolean areModulesDisplayed = false;
		try {
			// Click on the specified module
			commonEvents.click(getAnchorTagLocatorByText(moduleName));

			// Get the list of sub-module elements
			List<WebElement> subModuleElements = commonEvents.getWebElements(getSubModuleLocator());
			System.out.println("Sub-module count: " + subModuleElements.size());

			// Check if the sub-modules are displayed
			if (!subModuleElements.isEmpty()) {
				for (WebElement subModule : subModuleElements) {
					boolean isDisplayed = commonEvents.isDisplayed(subModule);
					System.out.println("Sub-module displayed: " + isDisplayed);
					areModulesDisplayed = areModulesDisplayed || isDisplayed;
				}
			} else {
				System.out.println("No sub-modules found under the specified module.");
			}

		} catch (Exception e) {
			throw new Exception("Failed to find elements: " + e.getMessage(), e);
		}
		return areModulesDisplayed;
	}

	/**
	 * @Test6 about this method verifyNavigationBetweenSubmodules()
	 * @param : null
	 * @description : This method verifies that the user is able to navigate between the sub modules.
	 * @return : boolean
	 * @author : YAKSHA
	 */
	public boolean verifyNavigationBetweenSubmodules() throws Exception {
		try {
			// Clicking on the "Inventory" submodule to start navigation.
			commonEvents.click(getAnchorTagLocatorByText("Inventory"));

			// Navigating to the "Stock" submodule and waiting for the URL to update.
			commonEvents.click(getAnchorTagLocatorByText("Stock"));
			commonEvents.waitForUrlContains("Inventory/Stock", 5000);

			// Navigating to the "Inventory Requisition" submodule and waiting for the URL
			// to update.
			commonEvents.click(getAnchorTagLocatorByText("Inventory Requisition"));
			commonEvents.waitForUrlContains("Inventory/InventoryRequisitionList", 5000);

			// Navigating to the "Consumption" submodule and waiting for the URL to update.
			commonEvents.click(getAnchorTagLocatorByText("Consumption"));
			commonEvents.waitForUrlContains("Inventory/Consumption/ConsumptionList", 5000);

			// Navigating to the "Reports" submodule and waiting for the URL to update.
			commonEvents.click(getAnchorTagLocatorByText("Reports"));
			commonEvents.waitForUrlContains("Inventory/Reports", 5000);

			// Navigating to the "Patient Consumption" submodule and waiting for the URL to
			// update.
			commonEvents.click(getAnchorTagLocatorByText("Patient Consumption"));
			commonEvents.waitForUrlContains("Inventory/PatientConsumption/PatientConsumptionList", 5000);

			// Navigating to the "Return" submodule and waiting for the URL to update.
			commonEvents.click(getAnchorTagLocatorByText("Return"));
			commonEvents.waitForUrlContains("Inventory/Return", 5000);

			// Finally, navigating back to the "Stock" submodule.
			commonEvents.click(getAnchorTagLocatorByText("Stock"));

			// Return true if all navigations are successful.
			return true;
		} catch (Exception e) {
			// Throwing the exception if any issue occurs during navigation.
			throw e;
		}
	}

	/**
	 * @Test7 about this method takingScreenshotOfTheCurrentPage()
	 * @param : null
	 * @description : Taking screenshot of the current page.
	 * @return : Boolean
	 * @author : YAKSHA
	 */
	public Boolean takingScreenshotOfTheCurrentPage() throws Exception {
		boolean isDisplayed = false;
		try {
			commonEvents.takeScreenshot("SubStore");
			isDisplayed = true;

		} catch (Exception e) {
			throw e;
		}
		return isDisplayed;
	}

	/**
	 * @Test8 about this method
	 * @param : null
	 * @description : This method verifies the visibility of various UI components on the page, including buttons, input fields, dropdowns, and checkboxes.
	 * @return : boolean - Returns true if all specified UI components are
	 *         displayed, otherwise false.
	 * @throws : Exception - if there is an issue finding any of the UI components.
	 * @author : YAKSHA
	 */
	public boolean verifyIfInventoryReqInputFieldsDropdownsAndCheckboxesAreVisibleOrNot() throws Exception {
		boolean areAllFieldsDisplayed = false;
		try {
			commonEvents.click(getAnchorTagLocatorByText("Inventory Requisition"));
			commonEvents.waitForUrlContains("Inventory/InventoryRequisitionList", 5000);

			WebElement firstButton = commonEvents.findElement(getButtonLocatorsBytext("First"));
			WebElement previousButton = commonEvents.findElement(getButtonLocatorsBytext("Previous"));
			WebElement nextButton = commonEvents.findElement(getButtonLocatorsBytext("Next"));
			WebElement lastButton = commonEvents.findElement(getButtonLocatorsBytext("Last"));
			WebElement okButton = commonEvents.findElement(getButtonLocatorsBytext("OK"));
			WebElement createReqButton = commonEvents.findElement(getCreateRequisitionButton());
			WebElement searchBarId = commonEvents.findElement(searchBarId());
			WebElement starIconLocator = commonEvents.findElement(getStarIconLocator());
			WebElement pendingRadiobtn = commonEvents.findElement(getRadioButtonLocator("Pending"));
			WebElement completeRadiobtn = commonEvents.findElement(getRadioButtonLocator("Complete"));
			WebElement cancelledRadiobtn = commonEvents.findElement(getRadioButtonLocator("Cancelled"));
			WebElement withdrawnRadiobtn = commonEvents.findElement(getRadioButtonLocator("Withdrawn"));
			WebElement allRadiobtn = commonEvents.findElement(getRadioButtonLocator("All"));

			List<WebElement> options = Arrays.asList(firstButton, previousButton, nextButton, lastButton, okButton,
					searchBarId, createReqButton, starIconLocator, pendingRadiobtn, completeRadiobtn, cancelledRadiobtn,
					withdrawnRadiobtn, allRadiobtn);

			for (int i = 0; i < options.size(); i++) {
				WebElement option = options.get(i);
				commonEvents.highlight(option);
				if (!option.isDisplayed()) {
					areAllFieldsDisplayed = false;
					throw new Exception("Visibility check failed for: " + option.getText());
				}
			}
			areAllFieldsDisplayed = true;
		} catch (Exception e) {
			// Throw an exception with a meaningful message if any UI component is not found
			throw new Exception("Failed to verify if all fields are displayed!", e);
		}
		// Return the result of the visibility check
		return areAllFieldsDisplayed;
	}
	
	// rest assured
	
	/**
	 * @Test9 about this method getPostById()
	 * 
	 * @param : int id - The ID of the post to retrieve.
	 * @description : This method sends a GET request to retrieve a specific post by
	 *              its ID.
	 * @return : Response - The response from the API containing the post details.
	 */
	public Response getPostById(int id) {
		return given().when().get(BASE_URL + "/posts/" + id).then().extract().response();
	}
	
	/**
	 * @Test10 about this method updatePost()
	 * 
	 * @param : int id - The ID of the post to update.
	 * @param : String newTitle - The new title to update the post with.
	 * @param : String newBody - The new body content to update the post with.
	 * @param : int userId - The user ID associated with the post.
	 * @description : This method sends a PUT request to update a post with new
	 *              title, body, and user ID.
	 * @return : Response - The response from the API after updating the post.
	 */
	public Response updatePost(int id, String newTitle, String newBody, int userId) {
		return given().contentType(ContentType.JSON)
				.body("{ \"title\": \"" + newTitle + "\", \"body\": \"" + newBody + "\", \"userId\": " + userId + " }")
				.when().put(BASE_URL + "/posts/" + id).then().extract().response();
	}
	
	/**
	 * @Test11 about this method deletePost()
	 * 
	 * @param : int id - The ID of the post to delete.
	 * @description : This method sends a DELETE request to remove a specific post
	 *              by its ID.
	 * @return : Response - The response from the API after attempting to delete the
	 *         post.
	 */
	public Response deletePost(int id) {
		return given().when().delete(BASE_URL + "/posts/" + id).then().extract().response();
	}
	
	/**
	 * @Test12 about this method getCommentsForPost()
	 * 
	 * @param : int postId - The ID of the post to retrieve comments for.
	 * @description : This method sends a GET request to retrieve comments
	 *              associated with a specific post.
	 * @return : Response - The response from the API containing the list of
	 *         comments.
	 */
	public Response getCommentsForPost(int postId) {
		return given().when().get(BASE_URL + "/posts/" + postId + "/comments").then().extract().response();
	}
	
}
