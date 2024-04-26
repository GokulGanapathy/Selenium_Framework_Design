package Tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Pageobjects.CartPage;
import Pageobjects.CheckoutPage;
import Pageobjects.ConfirmationPage;
import Pageobjects.LandingPage;
import Pageobjects.OrderPage;
import Pageobjects.ProductCatalogue;
import TestComponents.BaseTest;
import TestComponents.Retry;

import org.testng.AssertJUnit;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class SubmitOrderTest extends BaseTest {
	String productName = "ZARA COAT 3";

	@Test(dataProvider = "getData", groups = { "Purchase" })
	public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException {

		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("product"));
		CartPage cartPage = productCatalogue.goToCartPage();

		Boolean match = cartPage.VerifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

	}

	@Test(dependsOnMethods = {"submitOrder"}, groups = {"Sanity"}, retryAnalyzer = Retry.class)
	public void VerifyOrderTest() {
		// "ZARA COAT 3";
		ProductCatalogue productCatalogue = landingPage.loginApplication("dummy@mailid.com", "Pass1234");
		OrderPage ordersPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName));

	}

	// Extent Reports -

	@DataProvider
	public Object[][] getData() throws IOException {

		List<HashMap<String, String>> data = getJsonDataAsMap(System.getProperty("user.dir") + "//src//main//java//Resources//PurchaseOrder.json");
		return new Object[][] { { data.get(0) }, { data.get(1) } };

	}

		//	 @DataProvider
		//	  public Object[][] getData()
		//	  {
		//	    return new Object[][]  {{"anshika@gmail.com","Iamking@000","ZARA COAT 3"}, {"shetty@gmail.com","Iamking@000","ADIDAS ORIGINAL" } };
		//	    
		//	  }
		//	HashMap<String,String> map = new HashMap<String,String>();
		//	map.put("email", "anshika@gmail.com");
		//	map.put("password", "Iamking@000");
		//	map.put("product", "ZARA COAT 3");
		//	
		//	HashMap<String,String> map1 = new HashMap<String,String>();
		//	map1.put("email", "shetty@gmail.com");
		//	map1.put("password", "Iamking@000");
		//	map1.put("product", "ADIDAS ORIGINAL");

}
