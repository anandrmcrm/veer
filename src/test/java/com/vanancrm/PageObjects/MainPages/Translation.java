package com.vanancrm.PageObjects.MainPages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;

import java.awt.datatransfer.StringSelection;

import java.awt.event.KeyEvent;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.openqa.selenium.support.ui.Select;

public class Translation extends AccessingElement {
	
	private WebDriver driver;
	
	public Translation(WebDriver driver) {
		
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="trgtunitcost_disp")
	private WebElement actualCost;
	
	@FindBy(id="expd_sub_amt")
	private WebElement expeditedCost;
	
	@FindBy(id="price_display")
	private WebElement grandTotalElement;
	
	@FindBy(id="mfile_sub_amt")
	private WebElement mailingFeeElement;
	
	@FindBy(id="cert_sub_amt")
	private WebElement certificationFeeElement;
	
	@FindBy(id="qcheck_sub_amt")
	private WebElement additionalQualityAmountElement;
	
	@FindBy(id="trans_rate")
	private WebElement transactionFeeElement;
	
	@FindBy(id="sub_amt")
	private WebElement subTotalElement;
	
	@FindBy(id="trgt_tot")
	private WebElement translationCostElement;
	
	@FindBy(id="sourcefiletype")
	private WebElement selectFileTypeElement;
	
	@FindBy(id="pagecount")
	private WebElement pageCountElement;
	
	@FindBy(id="srclang")
	private WebElement selectSourceLanguageFromElement;
	
	@FindBy(id="trglang")
	private WebElement selectSourceLanguageToElement;
	
	@FindBy(id="catetype")
	private WebElement categoryElement;
	
	@FindBy(id="filecomments")
	private WebElement fileCommentElement;

	@FindBy(id = "emailquote")
	private WebElement emailMeQuote;

    @FindBy(id = "getquote")
    private WebElement getQuote;

	public void selectFileType(String fileType) throws TimeoutException {
		try {
			selectDropDown(selectFileTypeElement, fileType);
		} catch (Exception e) {
			System.out.println("Unable to select file type " + e);
		}

	}

	public void pageCount(String pageCounts) {
		try {
			enterTestBoxValues(pageCountElement, pageCounts);
		} catch (Exception e) {
			System.out.println("Unable to enter a page count value " + e);
		}
	}

	public void selectSourceLanguageFrom(String selectLanguage) {

		selectDropDown(selectSourceLanguageFromElement, selectLanguage);
	}

	public void selectLanguageFrom(String sourceLanguage) {
		try {
			selectDropDown(selectSourceLanguageFromElement, sourceLanguage);
			driver.findElement(By.tagName("body")).click();
		} catch (Exception e) {
			System.out.println("Unable to select source language " + e);
		}
	}

	public void deselectSourceLanguageFrom() {
		selectDropDown(selectSourceLanguageFromElement, " ");
	}

	public void deselectFileType() throws TimeoutException {

		selectDropDown(selectFileTypeElement, "");
	}
    
    public void selectSourceLanguageTo(String selectLanguage) {
    	
    	selectDropDown(selectSourceLanguageToElement, selectLanguage);
	}

	public void selectLanguageTo(String targetLanguage) {
		try {
			selectDropDown(selectSourceLanguageToElement, targetLanguage);
			driver.findElement(By.tagName("body")).click();
		} catch (Exception e) {
			System.out.println("Unable to select target language " + e);
		}
	}

    public void deselectSourceLanguageTo() {
    	
    	selectDropDown(selectSourceLanguageToElement, "");
    }
    
    public void selectCategory(String categoryType) {
		
    	selectDropDown(categoryElement, categoryType);
	}
    
    public void deselectCategory() {
		
    	selectDropDown(categoryElement, "");
    }
    
   
    public void uploadFile() throws IOException, AWTException, InterruptedException {
    	
    	Random r = new Random();
    	int randint = Math.abs(r.nextInt()) % 10000;
    	String filePath = System.getProperty("user.dir")+"\\" + randint + ".txt";
    	File file = new File(filePath);
    	file.createNewFile();
    	WebElement fileUploadButton = driver.findElement(By.id("fileuploader"));
    	fileUploadButton.click();
    	Robot robot = new Robot();
    	robot.setAutoDelay(1000);
    	StringSelection stringSelection = new StringSelection(filePath);
    	Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    	robot.setAutoDelay(1000);
    	robot.keyPress(KeyEvent.VK_CONTROL);
    	robot.keyPress(KeyEvent.VK_V);
    	robot.keyRelease(KeyEvent.VK_V);
    	robot.keyRelease(KeyEvent.VK_CONTROL);
    	robot.keyPress(KeyEvent.VK_ENTER);
    	robot.keyRelease(KeyEvent.VK_ENTER);
	}
    
    public void fileComments(String message) {
    	
    	enterTestBoxValues(fileCommentElement, message);
    }
    
    public void selectTurnaroundTime(int optionValue) {
    	
    	switch (optionValue) {
    	case 0:

			if ((driver.findElement(By.id("rushopts0"))).isSelected()) {
				break;	
			} else {
				clickElement(driver.findElement(By.id("rushopts0")));
				break;
			}    			
		case 1:
			clickElement(driver.findElement(By.id("rushopts1")));
			break;
		case 2:
			clickElement(driver.findElement(By.id("rushopts2")));
			break;
		case 3:
			clickElement(driver.findElement(By.id("rushopts3")));
			break;		
		}

	}

	public void selectTAT(int optionValue) {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		switch (optionValue) {
		case 0:

			if ((driver.findElement(By.xpath("//input[@id='rushopts0']"))).isSelected()) {
				break;
			} else {
				js.executeScript("$('#rushopts0').click();");
				break;
			}
		case 1:
			js.executeScript("$('#rushopts1').click();");
			break;
		case 2:
			js.executeScript("$('#rushopts2').click();");
			break;
		case 3:
			js.executeScript("$('#rushopts3').click();");
			break;
		case 4:
			if ((driver.findElement(By.xpath("//input[@id='deliveryReq']"))).isSelected()) {
				break;
			} else {
				js.executeScript("$('#deliveryReq').click();");
				break;
			}
		}

	}

	public void emailId(String emailId) {

		enterTestBoxValues(driver.findElement(By.id("paytc_qemailcrm")), emailId);
	}

	public AdditionalInformation clickEmailQuote() {

		clickElement(driver.findElement(By.id("emailquote")));
		AdditionalInformation additionalInformation = new AdditionalInformation(driver);
		return additionalInformation;
	}

	public void clickAdditionalQtyCheck() {

		clickElement(driver.findElement(By.id("qcheck")));
	}

	public void clickCertificationLanguage() {

		clickElement(driver.findElement(By.id("certlang")));
	}

	public void clickRequestMailCopy(String country, String address) {

		driver.findElement(By.id("mailcountry_chosen")).click();
		List<WebElement> selectCountryElement = driver.findElements(
				By.xpath("//div[@id='mailcountry_chosen']//div[@class='chosen-drop']//ul[@class='chosen-results']/li"));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		for (WebElement element : selectCountryElement) {
			if (element.getText().equals(country)) {
				element.click();
				break;
			}
		}
		WebElement addressElement = driver.findElement(By.id("paytc_mailaddress"));
		addressElement.sendKeys(address);
    }
    
    /*public void clickLiveChat() throws InterruptedException {
    	String master = driver.getWindowHandle();
    	int timeCount = 1;
    	do
    	{
    	   driver.getWindowHandles();
    	   Thread.sleep(200);
    	   timeCount++;
    	   if ( timeCount > 50 ) 
    	   {
    	       break;
    	   }
    	}
    	while ( driver.getWindowHandles().size() == 1 );
    	Set<String> handles = driver.getWindowHandles();
    	//Switching to the popup window.
    	for ( String handle : handles )
    	{
    	    if(!handle.equals(master))
    	    {
    	         driver.switchTo().window(handle);
    	    }
    	}
    	WebElement liveChat = driver.findElement(By.xpath("//*[@id='wrapper']/a"));
    	liveChat.click();
    }*/
    
    public double getActualCost() {
    	
    	return convertAndGetValue(actualCost);
    }
    
    public double getExpeditedCost() {
    	
    	return convertAndGetValue(expeditedCost);
    }
    
    public double getTranslationCost() {
    	
    	return convertAndGetValue(translationCostElement);
    }
    
    public double getSubTotal() {
    	
		return convertAndGetValue(subTotalElement);
    }   
    
    public double getTransactionFee() {
    	
    	return convertAndGetValue(transactionFeeElement);
    }
    
    public double getAdditionalQualityAmount() {

    	return convertAndGetValue(additionalQualityAmountElement);
    }

    public double getCertificationFee() {

		return convertAndGetValue(certificationFeeElement);
	}

	public double getMailingFee() {

		return convertAndGetValue(mailingFeeElement);
	}

	public double getGrandTotal() {

		return convertAndGetValue(grandTotalElement);
	}

	public double convertAndGetValue(WebElement element) {

		if (element.isDisplayed()) {
			return Double.parseDouble(getElementValues(element));
		} else {
			return 0;
		}
	}

	public void pageRefresh() {

		driver.navigate().refresh();
	}

	public boolean checkElementVisible(String value, String element) {

		WebElement locator = null;
		switch (value) {
		case "id":
			locator = driver.findElement(By.id(element));
			break;
		case "name":
			locator = driver.findElement(By.name(element));
			break;
		case "xpath":
			locator = driver.findElement(By.xpath(element));
			break;
		case "classname":
			locator = driver.findElement(By.className(element));
			break;
		case "classselector":
			locator = driver.findElement(By.cssSelector(element));
			break;
		case "partiallinktext":
			locator = driver.findElement(By.partialLinkText(element));
			break;
		case "linktext":
			locator = driver.findElement(By.linkText(element));
			break;
		case "tagname":
			locator = driver.findElement(By.tagName(element));
			break;
		}
		return isElementDisplayed(locator);
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

            if (isElementDisplayed(getQuote)) {
                clickElement(getQuote);
            }
        } catch (Exception e) {
            System.out.println("Unable to click Get Quote button " + e);
        }
    }

}
