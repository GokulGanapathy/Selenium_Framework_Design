package Tests;

import org.testng.annotations.Test;

import Pageobjects.CartPage;
import Pageobjects.CheckoutPage;
import Pageobjects.ConfirmationPage;
import Pageobjects.ProductCatalogue;
import TestComponents.BaseTest;
import TestComponents.Retry;

import org.testng.AssertJUnit;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ErrorValidationsTest extends BaseTest {

	@Test(groups = {"ErrorHandling"}, retryAnalyzer = Retry.class)
	public void LoginErrorValidation() throws IOException, InterruptedException {

		landingPage.loginApplication("dummyX@mail.com", "Pass123");
		Assert.assertEquals(landingPage.getErrorMessage(),"Incorrect email or password.");
	}

	@Test
	public void ProductErrorValidation() throws IOException, InterruptedException {

		String productName = "ZARA COAT 3";
		
		ProductCatalogue productCatalogue = landingPage.loginApplication("dummy@mailid.com", "Pass1234");
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
		
		CartPage cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.VerifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);

	}

}
