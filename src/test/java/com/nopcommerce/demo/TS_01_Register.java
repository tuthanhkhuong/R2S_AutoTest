package com.nopcommerce.demo;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import common.BaseTest;
import pageObjects.RegisterPageObject;

public class TS_01_Register extends BaseTest {
	WebDriver driver;
	RegisterPageObject registerPage;

	public String email = "finaltest@gmail.com";
	public String password = "Abcd1234";
	
	
	@Parameters("browser")
	
	@BeforeClass
	public void beforeClass(String browserName) {
		driver = getBrowserDriver(browserName, "https://demo.nopcommerce.com/register?returnUrl=%2F");
		
		/*
		System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver.exe");
        driver = new ChromeDriver();
        //Can chinh khung cua so
        driver.manage().window().maximize();
        driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");
        */
		
		registerPage = new RegisterPageObject(driver);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	@Test
	public void TC_01_RegisterWithEmptyData() {
		registerPage.clickToRegisterButton();
		
		Assert.assertTrue(registerPage.isRegisterFirstNameTextBoxWithEmptyData("First name is required."));
		Assert.assertTrue(registerPage.isRegisterLastNameTextBoxWithEmptyData("Last name is required."));
		Assert.assertTrue(registerPage.isRegisterEmailTextBoxWithEmptyData("Email is required."));
		Assert.assertTrue(registerPage.isRegisterPassTextBoxWithLess6Key("Password is required."));
		Assert.assertTrue(registerPage.isRegisterPassTextBoxWithUncorrectComfirm("Password is required."));
	}

	@Test
	public void TC_02_RegisterWithInvalidEmail() {
		registerPage.refeshCurrentPage(driver);
		registerPage.inputToRegisterEmailTextBox("abcd");
		registerPage.clickToRegisterButton();
		
		Assert.assertTrue(registerPage.isRegisterEmailTextBoxWithIvalidData("Wrong email"));
	}

	
	@Test
	public void TC_03_RegisterSuccess() {
		registerPage.refeshCurrentPage(driver);

		registerPage.clickToMaleRaido();
		registerPage.inputToRegisterFirstnameTextBox("AAA");
		registerPage.inputToRegisterLastnameTextBox("BBB");
		registerPage.clickToRegisterDayOfBirth("1");
		registerPage.clickToRegisterMonthOfBirth("May");
		registerPage.clickToRegisterYearOfBirth("2000");
		registerPage.inputToRegisterEmailTextBox(email);
		registerPage.inputToRegisterCompanyTextBox("none");
		registerPage.inputToRegisterPasswordTextBox(password);
		registerPage.inputToRegisterConfirmPasswordTextBox(password);

		registerPage.clickToRegisterButton();
		Assert.assertTrue(registerPage.isRegisterSuccess("Your registration completed"));
	}

	@Test
	public void TC_04_RegisterWithExistsEmail() {
		registerPage.openUrl(driver, "https://demo.nopcommerce.com/");
		registerPage.refeshCurrentPage(driver);

		registerPage.clickToLogOutLabel();
		
		registerPage.openUrl(driver, "https://demo.nopcommerce.com/register?returnUrl=%2F");
		registerPage.clickToMaleRaido();
		registerPage.inputToRegisterFirstnameTextBox("AAAA");
		registerPage.inputToRegisterLastnameTextBox("BBB");
		registerPage.clickToRegisterDayOfBirth("25");
		registerPage.clickToRegisterMonthOfBirth("May");
		registerPage.clickToRegisterYearOfBirth("2010");
		registerPage.inputToRegisterEmailTextBox(email);
		registerPage.inputToRegisterCompanyTextBox("none");
		registerPage.inputToRegisterPasswordTextBox(password);
		registerPage.inputToRegisterConfirmPasswordTextBox(password);

		registerPage.clickToRegisterButton();
		Assert.assertTrue(registerPage.isRegisterEmailTextBoxWithExistsEmail("The specified email already exists"));
	}

	@Test
	public void TC_05_RegisterWithPasswordLessThan6Keys() {
		registerPage.refeshCurrentPage(driver);
		registerPage.inputToRegisterPasswordTextBox("abc");
		registerPage.clickToRegisterButton();
		
		Assert.assertTrue(registerPage.isRegisterPassTextBoxWithLess6Key("Password must meet the following rules:"));
		Assert.assertTrue(registerPage.isRegisterPassTextBoxWithLess6Key("must have at least 6 characters"));
	}

	@Test
	public void TC_06_RegisterWithPasswordLessThan6Keys() {
		registerPage.refeshCurrentPage(driver);
		registerPage.inputToRegisterPasswordTextBox(password);
		registerPage.inputToRegisterConfirmPasswordTextBox("abcd");
		
		registerPage.clickToRegisterButton();		
		Assert.assertTrue(registerPage
				.isRegisterPassTextBoxWithUncorrectComfirm("The password and confirmation password do not match."));
	}
}
