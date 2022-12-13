package com.nopcommerce.demo;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import common.BaseTest;
import pageObjects.LoginPageObject;
import pageObjects.MyAccountPageObject;
import pageUIs.MyAccountPageUI;

public class TS_03_MyAccount extends BaseTest {
	WebDriver driver;
	MyAccountPageObject MyAccountPage;
	LoginPageObject loginPage;

	@Parameters("browser")

	@BeforeClass
	public void beforeClass(String browserName) {
		driver = getBrowserDriver(browserName, "https://demo.nopcommerce.com/customer/info");

		MyAccountPage = new MyAccountPageObject(driver);
		loginPage = new LoginPageObject(driver);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	@Test
	public void TC_00_Login() {
		MyAccountPage.inputEmailTextBox("finaltest@gmail.com");
		MyAccountPage.inputPasswordTextBox("abcd1234");
		
		MyAccountPage.clickToLoginButton();

	}

	// TC_01: Ở tab Customer Info, update thông tin của Customer, save lại
	@Test
	public void TC_01_UpdateCustomer() {
		MyAccountPage.refeshCurrentPage(driver);

		MyAccountPage.changeFeMaleRaido();
		MyAccountPage.changeFirstnameTextBox("Thanh");
		MyAccountPage.changeLastnameTextBox("Khuong");
		MyAccountPage.ChangeEmailTextBox("finaltest@gmail.com");
		MyAccountPage.changeDayOfBirth("10");
		MyAccountPage.changeMonthOfBirth("January");
		MyAccountPage.changeYearOfBirth("2012");
		MyAccountPage.changeCompanyTextBox("Company");
		MyAccountPage.changeNewSelecter(null);

		MyAccountPage.clickToSaveAccountButton();

		Assert.assertTrue(MyAccountPage.isNewUpdateInfo(MyAccountPageUI.MYACCOUNT_FIRST_NAME_TEXTBOX, "Thanh"));
		Assert.assertTrue(MyAccountPage.isNewUpdateInfo(MyAccountPageUI.MYACCOUNT_LAST_NAME_TEXTBOX, "Khuong"));
		Assert.assertTrue(
				MyAccountPage.isNewUpdateInfo(MyAccountPageUI.MYACCOUNT_EMAIL_TEXTBOX, "finaltest@gmail.com"));

	}

	// TC_02: Ở tab Addresses, add Address với thông tin tuỳ thích, save lại
	@Test
	public void TC_02_AddAdress() {
		MyAccountPage.clickToAddressesLabel();
		MyAccountPage.clickToAddNewButton();

		MyAccountPage.addFirstNameTextBox("Thanh");
		MyAccountPage.addLastNameTextBox("Khuong");
		MyAccountPage.addEmailTextBox("finaltest@gmail.com");
		MyAccountPage.addCountryTextBox("Viet Nam");
		MyAccountPage.addCityTextBox("Ho Chi Minh");
		MyAccountPage.addAddress1TextBox("3066/1 Le Thi Hoa");
		MyAccountPage.addZipCodeTextBox("71313");
		MyAccountPage.addPhoneNumberTextBox("0392543689");

		MyAccountPage.clickToSaveAddressesButton();

		Assert.assertTrue(
				MyAccountPage.isNewUpdateAdrressInfo(MyAccountPageUI.ADDRESS_EMAIL_TEXT, "finaltest@gmail.com"));
		Assert.assertTrue(MyAccountPage.isNewUpdateAdrressInfo(MyAccountPageUI.ADDRESS_PHONE_TEXT, "0392543689"));

	}

	@Test
	public void TC_03_ChangeAndUpdatePassword() {
		MyAccountPage.clickToChangePasswordLable();
		MyAccountPage.refeshCurrentPage(driver);

		MyAccountPage.inputToOldPassWordTextbox("abcd1234");
		MyAccountPage.inputToNewPassWordTextbox("7290612");
		MyAccountPage.inputToConfirmNewPasswordTextbox("7290612");
		
		MyAccountPage.clickToChangePasswordButton();
		

		MyAccountPage.clickToCloseSpanButton();
		
		MyAccountPage.openUrl(driver, "https://demo.nopcommerce.com");

		MyAccountPage.clickToLogOutLable();
		MyAccountPage.clickToLogInLable();

		// Nhap pass cu
		MyAccountPage.inputEmailTextBox("finaltest@gmail.com");
		MyAccountPage.inputPasswordTextBox("abcd1234");

		MyAccountPage.clickToLoginButton();
		Assert.assertTrue(
				loginPage.isLoginErrorMessage("Login was unsuccessful. Please correct the errors and try again"));
		Assert.assertTrue(loginPage.isLoginErrorMessage("No customer account found"));

		// Nhap pass moi
		MyAccountPage.refeshCurrentPage(driver);
		
		MyAccountPage.inputEmailTextBox("finaltest@gmail.com");
		MyAccountPage.inputPasswordTextBox("7290612");
		
		MyAccountPage.clickToLoginButton();
		Assert.assertEquals(loginPage.getPageTitle(driver), "nopCommerce demo store");
	}

}
