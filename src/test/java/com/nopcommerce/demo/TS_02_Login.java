package com.nopcommerce.demo;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.LoginPageObject;

public class TS_02_Login {
	WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    LoginPageObject loginPage;
    
    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //Can chinh khung cua so
        driver.manage().window().maximize();
        driver.get("https://demo.nopcommerce.com/login?returnUrl=%2F");
        
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
	   Assert.assertTrue(loginPage.isLoginErrorMessage("Login was unsuccessful. Please correct the errors and try again"));
	   Assert.assertTrue(loginPage.isLoginErrorMessage("No customer account found"));
	   
	   
   }
   
}
