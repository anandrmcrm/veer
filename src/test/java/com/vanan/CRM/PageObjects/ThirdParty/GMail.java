package com.vanan.CRM.PageObjects.ThirdParty;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.vanancrm.PageObjects.MainPages.AccessingElement;

public class GMail extends AccessingElement {

	private WebDriver driver;

	@FindBy(id = "identifierId")
	private WebElement emailId;

	@FindBy(id = "identifierNext")
	private WebElement nextButton;

	@FindBy(id = "password")
	private WebElement password;

	@FindBy(xpath = "//div[@id='passwordNext']")
	private WebElement nextSubmitButton;

	private void gmailUrl() {

		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		PageFactory.initElements(driver, this);
	}

	private void enterEmailId(String email) {

		enterTestBoxValues(emailId, email);
	}

	private void clickNextButton() {

		clickElement(nextButton);
	}

	private void enterPassword(String pass) {

		enterTestBoxValues(password, pass);
	}

	private void clickPasswordButton() {

		clickElement(nextSubmitButton);
	}

	public void loginToGmail(String emailId, String password) {

		gmailUrl();
		enterEmailId(emailId);
		clickNextButton();
		enterPassword(password);
		clickPasswordButton();
		waitForProcessCompletion(10);
		displayMainPage();
	}

	private void displayMainPage() {

		// click a Gmail Option
		clickElement(driver.findElement(By.xpath(
				"//a[@href='https://www.google.co.in/intl/en/options/']")));

		// Selecting Gmail Option in List of options
		clickElement(driver.findElement(By.id("gb23")));
	}

	public void clickParticularMail(String service, String mailerDetails) {

		List<WebElement> unreademeil = driver
				.findElements(By.xpath("//*[@class='zF']"));
		for (int i = 0; i < unreademeil.size(); i++) {
			if (unreademeil.get(i).isDisplayed() == true) {
				if (unreademeil.get(i).getText().equals(mailerDetails)) {

					// Clik a unread email
					clickElement(driver.findElement(
							By.xpath("//span[@class='bog' and contains(text(),"
									+ "'" + service + "')]")));
					break;
				} else {
					System.out.println("No mail form " + mailerDetails);
				}
			}
		}

	}

	public String readServiceDetails() {
		
		return getElementValues(driver.findElement(By.xpath(
				"//table[contains(@id,'servicedetails')]/tbody/tr[2]/td/p[2]")));
	}

	private void waitForProcessCompletion(int waitTime) {
		try {
			TimeUnit.SECONDS.sleep(waitTime);
		} catch (Exception e) {
		}
	}

	public String getTicketFieldValues(String field) {

		List<WebElement> elements = driver.findElements(
				By.xpath("//table[contains(@id,'paysummary')]/tbody/tr"));
		String value = "";
		WebElement eachElement;
		List<WebElement> columns;
		for (int i = 1; i <= elements.size(); i++) {
			try {

				columns = driver.findElements(
						By.xpath("//table[contains(@id,'paysummary')]/tbody/tr["
								+ i + "]/td"));
				if (columns.size() == 2) {
					eachElement = driver.findElement(By
							.xpath("//table[contains(@id,'paysummary')]/tbody/tr["
									+ i + "]/td[1]/b"));
					if ("Email Id".contains(field)) {
						value = driver.findElement(By.xpath(
								"//table[contains(@id,'paysummary')]/tbody/tr["
										+ i + "]/td[2]/a"))
								.getText();
						break;
					}
					if (eachElement.getText().contains(field)) {
						value = driver.findElement(By.xpath(
								"//table[contains(@id,'paysummary')]/tbody/tr["
										+ i + "]/td[2]"))
								.getText();
						break;
					}
				}
			} catch (Exception ex) {
				continue;
			}
		}
		return value;
	}
}
