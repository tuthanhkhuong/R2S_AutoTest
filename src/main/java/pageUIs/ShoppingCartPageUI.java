package pageUIs;

public class ShoppingCartPageUI {
	
	public static final String SHOPPING_CART_LABEL = "//span[@class='cart-label']";
	public static final String SHOPPING_CART_QUANTITY_TEXTBOX = "//a[text()='Lenovo IdeaCentre 600 All-in-One PC']/parent::td[@class='product']//following-sibling::td[@class='quantity']//input[@class='qty-input']";
	public static final String SHOPPING_CART_UPDATE_BUTTON = "//button[@id='updatecart']";
	public static final String SHOPPING_CART_REMOVE_BUTTON = "//a[text()='Lenovo IdeaCentre 600 All-in-One PC']/parent::td[@class='product']//following-sibling::td[@class='remove-from-cart']//button[@class='remove-btn']";
	
	//-------------ERROR MESSAGE-------------
	public static final String SHOPPINGCART_EMPTY_MESSAGE = "//div[@class='no-data']";	
	
}
