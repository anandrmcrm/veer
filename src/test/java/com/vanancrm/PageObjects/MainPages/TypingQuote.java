package com.vanancrm.PageObjects.MainPages;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class TypingQuote extends AccessingElement {

	private WebDriver driver;
	
	public TypingQuote(WebDriver driver) {
		
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="trgtunitcost_disp")
	private WebElement actualCost;
	
	@FindBy(id="trgt_tot")
	private WebElement targetTotal;
	
	@FindBy(id="sub_amt")
	private WebElement subtotal;
	
	@FindBy(id="price_display")
	private WebElement grandTotalElement;
	
	@FindBy(id="nota_sub_amt")
	private WebElement notary;
	
	@FindBy(id="notapro_sub_amt")
	private WebElement processingFee;

	@FindBy(id = "emailquote")
	private WebElement emailMeQuote;
	
	@FindBy(id = "getquote")
	private WebElement getQuote;

	public void selectFileType(String fileType) throws TimeoutException {

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement selectFileTypeElement = driver
				.findElement(By.id("sourcefiletype"));
		Select fileTypes = new Select(selectFileTypeElement);
		List<WebElement> files = fileTypes.getOptions();
		for (int i = 0; i < files.size(); i++) {
			if(files.get(i).getText().equals(fileType)) {
				fileTypes.selectByVisibleText(fileType);
			}
		}
	}
	
	public void pageCount(String pageCounts) {
		
		WebElement pageCountElement = driver.findElement(By.id("pagecount"));
		pageCountElement.sendKeys(pageCounts);
	}
	
	public void selectSourceLanguage(String selectLanguage) {

		Select selectLanguageElement = new Select(
				driver.findElement(By.id("srclang")));
		selectLanguageElement.selectByVisibleText(selectLanguage);
	}
	
	 public void selectCategory(String categoryType) {
			
	    	WebElement selectFileTypeElement = driver.findElement(By.id("catetype"));
			Select fileTypes = new Select(selectFileTypeElement);
			fileTypes.selectByVisibleText(categoryType);
			/*List<WebElement> files = fileTypes.getOptions();
			for (int i = 0; i < files.size(); i++) {
				if(files.get(i).getText().equals(categoryType)) {
					fileTypes.selectByVisibleText(categoryType);
				}
			}*/
	}
	 
	public void selectFormatting(String formatType) {

		Select selectFormatTypeElement = new Select(
				driver.findElement(By.id("formatting")));
		selectFormatTypeElement.selectByVisibleText(formatType);
	} 
	
	public void selectStandardDelivery() {
		clickElementById("radio1");
	}
	
	public void selectExpeditedServices() {
		clickElementById("expedited");
	}
	
	public void selectNotarization() {
		clickElementById("notacrmpay");
	}
	

	public void enterDate(String date, String value) {
    	
    	enterValuesById("fileupload_tat", value);
    } 
	
	private void enterValuesById(String element, String value) {
		
    	WebElement uiElement = driver.findElement(By.id(element));
    	uiElement.sendKeys(value);
    }
	
	 public double getActualCost() {
	    	
		 return getTableValues(actualCost);
	 }
	 
	 public double getNotary() {
	    	
		 return getTableValues(notary);
	 }
	 
	 public double getProcessingFee() {
	    	
		 return getTableValues(processingFee);
	 }
	 
	 public double getSubTotal() {
	    	
		 return getTableValues(subtotal);
	 }
	 
	 public double getGrandTotal() {
		 
		 return getTableValues(grandTotalElement);
	 }
	 
	 public void clickRequestMailCopy(String country, String address) {
	    	
	    	driver.findElement(By.id("mailcountry")).click();
	    	List<WebElement> selectCountryElement = driver.findElements(By.xpath("//div[@id='mailcountry']//div[@class='combo-arrow']//ul[@class='combo-dropdown']/li"));
	    	driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
			for (WebElement element : selectCountryElement) {
				if (element.getText().equals(country)) {
					element.click();
					break;
				}
			}
			WebElement addressElement = driver.findElement(By.id("paytc_mailaddress"));
			addressElement.sendKeys(address);
	 }
	 
	 private double getTableValues(WebElement element) {
	    	
	    	if (element.isDisplayed()) {
	    		return Double.parseDouble(element.getText());
	    	} else {
	    		return 0;
	    	}
	 }
	 
	 private void clickElementById(String element) {
	    	WebElement uiElement = driver.findElement(By.id(element));
	    	uiElement.click();
	}

	public void clickEmailMeGetQuote() {
		try {
			clickElement(emailMeQuote);
		} catch (Exception e) {
			System.out.println("Unable to click Get Email Me Quote button " + e);
		}
	}
	
	public void clickGetQuote() {
		try {
			clickElement(getQuote);
		} catch (Exception e) {
			System.out.println("Unable to click Get Quote button " + e);
		}
	}
}
