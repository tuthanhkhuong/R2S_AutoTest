package com.nopcommerce.demo;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.RegisterPageObject;

public class TS_01_Register {
	WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    RegisterPageObject registerPage;
    
    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //Can chinh khung cua so
        driver.manage().window().maximize();
        driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");
        
        registerPage = new RegisterPageObject(driver);
    }
   
   @AfterClass
   public void afterClass() {
      // driver.quit();
   }
   
   @Test
   public void TC_01_RegisterWithEmptyData() {
	   registerPage.clickToRegisterButton();
	   Assert.assertTrue(registerPage.isRegisterEmailTextBoxWithEmptyData("Email is required."));
   }
   
   @Test
   public void TC_02_RegisterWithInvalidEmail() {
	   registerPage.refeshCurrentPage(driver);
	   registerPage.inputToRegisterEmailTextBox("abcd");
	   registerPage.clickToRegisterButton();
	   Assert.assertTrue(registerPage.isRegisterEmailTextBoxWithIvalidData("Wrong email"));
   }
   
   //Dang ky tai khoan (Thay doi gia tri Email moi lan dang ky)
   //@Test
   public void TC_04_RegisterWithExistsEmail() {
	   registerPage.refeshCurrentPage(driver);
	   registerPage.clickToMaleRaido();
	   
	   registerPage.inputToRegisterFirstnameTextBox("AAA");
	   registerPage.inputToRegisterLastnameTextBox("BBB");
	   registerPage.clickToRegisterDayOfBirth("1");
	   registerPage.clickToRegisterMonthOfBirth("May");
	   registerPage.clickToRegisterYearOfBirth("2000");
	   registerPage.inputToRegisterEmailTextBox("finaltest@gmail.com");
	   registerPage.inputToRegisterCompanyTextBox("none");
	   registerPage.inputToRegisterPasswordTextBox("abcdef");
	   registerPage.inputToRegisterConfirmPasswordTextBox("abcdef");
	   
	   registerPage.clickToRegisterButton();
	   Assert.assertTrue(registerPage.isRegisterSuccess("Your registration completed"));
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
	   registerPage.inputToRegisterPasswordTextBox("abcdef");
	   registerPage.inputToRegisterConfirmPasswordTextBox("abcd");
	   registerPage.clickToRegisterButton();
	   Assert.assertTrue(registerPage.isRegisterPassTextBoxWithUncorrectComfirm("The password and confirmation password do not match."));
   }
}

