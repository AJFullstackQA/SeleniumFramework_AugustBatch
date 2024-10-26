package tests;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

import pages.LoginPage;
import pages.ProductsPage;

public class LoginLogoutTest extends BaseTest {

	@Test
	public void loginTest() throws IOException, InterruptedException {
		logger = Logger.getLogger(LoginLogoutTest.class);
		report = new ExtentReports("./src/test/resources/ExtentReportResults.html");
		test = report.startTest("ExtentDemo");
		
		//Fetching Test-Data
		String url = xlop.getExcelData("Sheet1", 0, 0, this.getClass().getSimpleName());
		String uName = xlop.getExcelData("Sheet1", 1, 0, this.getClass().getSimpleName());
		String pwd = xlop.getExcelData("Sheet1", 1, 1, this.getClass().getSimpleName());

		// Page classes object creation steps
		LoginPage lp = new LoginPage(driver);
		ProductsPage prodPage = new ProductsPage(driver);

		// Goto the saucedemo url
		lp.goToURL(url);
		logger.info("URL was loaded successfully");
		test.log(LogStatus.PASS, "URL was loaded successfully");

		// Verify whether Page was actually loaded
		AssertJUnit.assertEquals(lp.verifyHeaderText(), true);

		// Login into the application
		lp.login(uName, pwd);

		// Verify whether login was successful
		AssertJUnit.assertEquals(prodPage.verifyProductsPageTitleText(), true);
		logger.info("Login was successful");
		test.log(LogStatus.INFO, "Login was successful");

		// Logout of the application
		prodPage.logout();

		// Verify whether logout was successful
		AssertJUnit.assertEquals(lp.verifyHeaderText(), true);
		
		test.log(LogStatus.FAIL,test.addScreenCapture(capture(driver))+ "Test Failed");
		
		report.endTest(test);
		report.flush();

	}
}
