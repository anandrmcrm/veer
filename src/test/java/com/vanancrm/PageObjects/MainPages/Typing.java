package com.vanancrm.PageObjects.MainPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Typing extends AccessingElement {

    private WebDriver driver;

    public Typing(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "getquote")
    private WebElement getQuote;

    @FindBy(id = "emailquote")
    private WebElement emailMeQuote;

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
