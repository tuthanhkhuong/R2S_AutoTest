package com.nopcommerce.demo;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import common.BaseTest;
import pageObjects.LoginPageObject;
import pageObjects.SearchPageObject;
import pageObjects.ShoppingCartPageObject;
import pageObjects.WishListPageObject;

public class TS_06_ShoppingCart extends BaseTest {
	WebDriver driver;
	LoginPageObject loginPage;
	SearchPageObject searchPage;
	WishListPageObject wishLishPage;
	ShoppingCartPageObject shoppingCartPage;

	public String email = "finaltest@gmail.com";
	public String password = "7290612";
	
	@Parameters("browser")

	@BeforeClass
	public void beforeClass(String browserName) {
		driver = getBrowserDriver(browserName, "https://demo.nopcommerce.com/login?returnUrl=%2F");

		loginPage = new LoginPageObject(driver);
		searchPage = new SearchPageObject(driver);
		wishLishPage = new WishListPageObject(driver);
		shoppingCartPage = new ShoppingCartPageObject(driver);
	}

	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	@Test
	public void TC_00_Login() {
		loginPage.inputToEmailTextBox(email);
		loginPage.inputToPasswordTextBox(password);
		loginPage.clickToLoginButton();
	}
	
	@Test
	public void TC_01_UpdateShoppingCart() {
		shoppingCartPage.clickToShoppingCartLabel();
		shoppingCartPage.clickToQuantityShoppingCartTextBox("5");
		shoppingCartPage.clickToUpdateShoppingCartButton();
		
		shoppingCartPage.isUpdatedQuantityShoppingCart("5");
	}
	
	@Test
	public void TC_02_RemoveProductFormShoppingCart() {
		shoppingCartPage.clickToShoppingCartLabel();
		shoppingCartPage.clickToRemoveProductShoppingCart();
		
		Assert.assertTrue(shoppingCartPage.isEmptyShoppingCart("Your Shopping Cart is empty!"));
	}
}
