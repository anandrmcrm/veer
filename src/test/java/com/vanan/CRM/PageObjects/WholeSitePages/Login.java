package com.vanan.CRM.PageObjects.WholeSitePages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.vanan.CRM.PageObjects.MainPages.DashBoardPage;
import com.vanancrm.PageObjects.MainPages.AccessingElement;

public class Login extends AccessingElement {

	private WebDriver driver;
	private JavascriptExecutor js;

	public Login(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
		js = (JavascriptExecutor) driver;
	}

	@FindBy(id = "login_email")
	private WebElement userName;

	@FindBy(name = "password")
	private WebElement userPassword;
	
	@FindBy(xpath = "//button[text()='Sign In']")
	private WebElement signin;

	public void enterUserName(String username) {
		try {
			enterTestBoxValues(userName, username);
		} catch (Exception e) {
			System.out.println("Unable to enter a user name " + e);
		}
	}

	public void enterPassword(String password) {
		try {
			enterTestBoxValues(userPassword, password);
		} catch (Exception e) {
			System.out.println("Unable to enter a password " + e);
		}
	}
	
	private void clickSignIn() {
		
		clickElement(signin);
	}
	
	public DashBoardPage signIn(String userName, String pass) {
		
		enterUserName(userName);
		enterPassword(pass);
		clickSignIn();
		try{
            TimeUnit.SECONDS.sleep(10);
        }catch (InterruptedException ex) {

        }
		DashBoardPage dashBoard = new DashBoardPage(driver);
		return dashBoard;
	}	
}
