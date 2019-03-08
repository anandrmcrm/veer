package com.vanan.CRM.PageObjects.MainPages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.vanancrm.PageObjects.MainPages.AccessingElement;

public class EmailConversation extends AccessingElement {

	private WebDriver driver;
	private Actions builder;
	private Action mouseOverHome;
	private EmailConversation emailConversation;

	public EmailConversation(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
		builder = new Actions(driver);

	}

	@FindBy(linkText = "Read more")
	private WebElement readMoreButton;

	// By noActionButton = By.linkText("No Action");
	@FindBy(linkText = "No Action")
	private WebElement noActionButton;

	@FindBy(linkText = "Close")
	private WebElement closeButton;

	public void clickReadMore() {

		clickElement(readMoreButton);
	}

	public EmailConversation clickNoActionButton() {

		mouseOverHome = builder.moveToElement(noActionButton).build();
		mouseOverHome.perform();
		clickElement(noActionButton);
		emailConversation = new EmailConversation(driver);
		return emailConversation;
	}

	public EmailConversation clickCloseButton() {
		clickElement(closeButton);
		return emailConversation;
	}

	public String getServiceDetails() {
		WebElement element = driver.findElement(
				By.xpath("//table[@id='quotemailhead']/tbody/tr/td/h1"));
		return element.getText();
	}

	public String getTicketFieldValues(String field) {

		List<WebElement> elements = driver
				.findElements(By.xpath("//table[@id='quotemailbody']/tbody/tr"));
		String value = "";
		WebElement eachElement;
		List<WebElement> columns;
		for (int i = 1; i <= elements.size(); i++) {
			try {

				columns = driver.findElements(By.xpath(
						"//table[@id='quotemailbody']/tbody/tr[" + i + "]/td"));
				if (columns.size() == 2) {
					eachElement = driver.findElement(
							By.xpath("//table[@id='quotemailbody']/tbody/tr[" + i
									+ "]/td[1]/p"));
					if (eachElement.getText().contains(field)) {
						value = driver.findElement(
								By.xpath("//table[@id='quotemailbody']/tbody/tr["
										+ i + "]/td[2]/p"))
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

	public void clickSignOut() {

		WebElement element = driver.findElement(By.className("user-image"));
		element.click();
		element = driver.findElement(By.linkText("Sign out"));
		element.click();
	}
}
