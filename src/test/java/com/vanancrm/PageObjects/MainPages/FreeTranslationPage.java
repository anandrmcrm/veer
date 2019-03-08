package com.vanancrm.PageObjects.MainPages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FreeTranslationPage extends AccessingElement {
	
	@FindBy(id = "qnamecrm")
	private WebElement customerName;

	@FindBy(id = "qemailcrm")
	private WebElement customerEmail;

	@FindBy(id = "qcountryscrm")
	private WebElement customerCountry;

	@FindBy(id = "qphonecrm")
	private WebElement customerPhoneNumber;

	@FindBy(id = "qsourcecrm")
	private WebElement sourceLang;

	@FindBy(id = "qtargetcrm")
	private WebElement targetLang;
	
	@FindBy(id = "audioVideo")
	private WebElement fileType;
	
	@FindBy(id = "fileuploader")
	private WebElement fileUploadButton;
	
	@FindBy(id = "qcommentcrm")
	private WebElement comment;
	
	@FindBy(id = "qsubmitcrm")
	private WebElement submitBtn;
	
	public FreeTranslationPage (WebDriver driver) {

		PageFactory.initElements(driver, this);		
	}
	
	public void enterCustomerName(String name) {

		enterTestBoxValues(customerName, name);
	}
	
	public void enterCustomerEmail(String email) {

		enterTestBoxValues(customerEmail, email);
	}
	
	public void enterCustomerPhoneNumber(String phone) {

		enterTestBoxValues(customerPhoneNumber, phone);
	}
	
	public void selectCountry(String country) {

		selectDropDown(customerCountry, country);
	}
	
	public void selectSourceLang(String sourceLan) {

		selectDropDown(sourceLang, sourceLan);
	}
	
	public void selectTargetLang(String targetLan) {

		selectDropDown(targetLang, targetLan);
	}
	
	public void selectFileType(String file) {

		selectDropDown(fileType, file);
	}
	
	public void enterComment(String message) {

		enterTestBoxValues(comment, message);
	}
	
	public void uploadFile() throws IOException, AWTException, InterruptedException {

		Random r = new Random();
		int randint = Math.abs(r.nextInt()) % 10000;
		String filePath = System.getProperty("user.dir") + "\\" + randint + ".txt";
		File file = new File(filePath);
		file.createNewFile();
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
	
	public void clickSubmitButton() {
		clickElement(submitBtn);
	}
	
}
