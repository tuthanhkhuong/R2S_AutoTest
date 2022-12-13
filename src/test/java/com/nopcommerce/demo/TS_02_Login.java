package com.nopcommerce.demo;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import common.BaseTest;
import pageObjects.LoginPageObject;

public class TS_02_Login extends BaseTest {
	WebDriver driver;
	LoginPageObject loginPage;

	@Parameters("browser")

	@BeforeClass
	public void beforeClass(String browserName) {
		driver = getBrowserDriver(browserName, "https://demo.nopcommerce.com/login?returnUrl=%2F");

		loginPage = new LoginPageObject(driver);
	}

	@AfterClass
	public void afterClass() {
		// driver.quit();
	}

	@Test
	public void TC_01_LoginWithEmptyData() {
		loginPage.clickToLoginButton();
		Assert.assertTrue(loginPage.isEmailErrorMessage("Please enter your email"));
	}

	@Test
	public void TC_02_LoginWithInvalidData() {
		loginPage.refeshCurrentPage(driver);
		loginPage.inputToEmailTextBox("abcd");
		loginPage.clickToLoginButton();
		Assert.assertTrue(loginPage.isEmailErrorMessage("Wrong email"));
	}

	@Test
	public void TC_03_LoginWithUnregisteredEmail() {
		loginPage.refeshCurrentPage(driver);
		loginPage.inputToEmailTextBox("abcd@gmail.com");
		loginPage.clickToLoginButton();
		Assert.assertTrue(
				loginPage.isLoginErrorMessage("Login was unsuccessful. Please correct the errors and try again"));
		Assert.assertTrue(loginPage.isLoginErrorMessage("No customer account found"));
	}

	@Test
	public void TC_04_LoginWithRegisteredEmail() {
		loginPage.refeshCurrentPage(driver);
		loginPage.inputToEmailTextBox("finaltest@gmail.com");
		loginPage.inputToPasswordTextBox("");
		loginPage.clickToLoginButton();
		Assert.assertTrue(loginPage
				.isRegisterdEmailErrorMessage("Login was unsuccessful. Please correct the errors and try again"));
		Assert.assertTrue(loginPage.isRegisterdEmailErrorMessage("The credentials provided are incorrect"));
	}

	@Test
	public void TC_05_LoginWithRegisteredEmail() {
		loginPage.refeshCurrentPage(driver);
		loginPage.inputToEmailTextBox("finaltest@gmail.com");
		loginPage.inputToPasswordTextBox("abcdef123555");
		loginPage.clickToLoginButton();
		Assert.assertTrue(loginPage
				.isRegisterdEmailErrorMessage("Login was unsuccessful. Please correct the errors and try again"));
		Assert.assertTrue(loginPage.isRegisterdEmailErrorMessage("The credentials provided are incorrect"));
	}

	@Test
	public void TC_06_LoginWithRegisteredEmail() {
		loginPage.refeshCurrentPage(driver);
		loginPage.inputToEmailTextBox("finaltest@gmail.com");
		loginPage.inputToPasswordTextBox("abcd1234");
		loginPage.clickToLoginButton();

		String parentID = loginPage.getWindowHandle(driver);
		loginPage.switchToWindowByID(parentID);

		Assert.assertEquals(loginPage.getPageTitle(driver), "nopCommerce demo store");
		driver.switchTo().window(parentID);
	}
}
