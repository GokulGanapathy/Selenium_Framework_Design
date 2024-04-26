package Pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import AbstractComponents.AbstractComponent;
import java.util.*;

import org.openqa.selenium.JavascriptExecutor;

public class CheckoutPage extends AbstractComponent {

	WebDriver driver;
	
	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//a[text()='Place Order ']")
	private WebElement placeOrder;

	@FindBy(css="input[placeholder='Select Country']")
	private WebElement countryselect;
	
	/*@FindBy(xpath = "(//button[contains(@class,'ta-item')])[2]")
	private WebElement selectCountry;*/
	
	@FindBy(css=".ta-item")
	private List<WebElement> countries;

	//private By results = By.cssSelector(".ta-results");

	public void selectCountry(String countryName) {
		
		Actions action = new Actions(driver);
		action.sendKeys(countryselect, countryName).perform();
		waitForElementToAppear(By.cssSelector(".ta-item"));
		for(WebElement s:countries)
		{
			String country = s.findElement(By.tagName("span")).getText();
			if(country.equalsIgnoreCase(countryName))
			{
				s.findElement(By.tagName("span")).click();
				break;
			}
		
		}
	}

	public ConfirmationPage submitOrder() {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", placeOrder);
		//placeOrder.click();
		return new ConfirmationPage(driver);

	}

}
