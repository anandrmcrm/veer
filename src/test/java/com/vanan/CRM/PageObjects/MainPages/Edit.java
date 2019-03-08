package com.vanan.CRM.PageObjects.MainPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.vanancrm.PageObjects.MainPages.AccessingElement;

public class Edit extends AccessingElement {

	private WebDriver driver;

	public Edit(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "process_id")
	private WebElement status;

	@FindBy(id = "update_btn")
	private WebElement updateButton;

	public void selectStatus(String option) {
		try {
			selectDropDown(status, option);
		} catch (Exception e) {
			System.out.println("Unable to select status " + e);
		}
	}

	public void clickUpdateButton() {
		try {
			clickElement(updateButton);
		} catch (Exception e) {
			System.out.println("Unable to click update button " + e);
		}
	}

	public void clickSignOut() {

		WebElement element = driver.findElement(By.className("user-image"));
		element.click();
		element = driver.findElement(By.linkText("Sign out"));
		element.click();
	}
}
