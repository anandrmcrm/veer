package com.vanancrm.PageObjects.MainPages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;

import java.awt.datatransfer.StringSelection;

import java.awt.event.KeyEvent;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Random;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.Select;

public class AdditionalInformation extends AccessingElement {

    WebDriver driver;

    public AdditionalInformation(WebDriver driver) {

        this.driver = driver;
    }

    public void enterCustomerName(String customerName) {

        WebElement nameElement = driver.findElement(By.id("cont_qnamecrm"));
        nameElement.sendKeys(customerName);
    }

    public void enterPhoneNumber(String phoneNumber) {

        WebElement phone = driver.findElement(By.id("cont_qphonecrm"));
        phone.sendKeys(phoneNumber);
    }

    public void selectCountry(String country) {

        WebElement selectCountryElement = driver.findElement(By.id("cont_qcountryscrm"));
        Select countries = new Select(selectCountryElement);
        countries.selectByVisibleText(country);

    }

    public void selectServiceType(String serviceType) {

        WebElement serviceTypeElement = driver.findElement(By.id("cont_hfc"));
        Select serviceTypes = new Select(serviceTypeElement);
        serviceTypes.selectByVisibleText(serviceType);
    }

    public void selectAdditionalServices(HashMap<String, String> additionalServices) {

        //List<WebElement> additionalServiceElement = driver.findElements(By.name("needotherservice"));
        //commentElement.sendKeys(additionalServices);
    }

    public void enterComments(String comment) {

        WebElement commentElement = driver.findElement(By.id("upcomments"));
        commentElement.sendKeys(comment);
    }

    public void clickSubmitButton() {

        WebElement submitButton = driver.findElement(By.id("filesubmit"));
        submitButton.click();
    }

    public void uploadFile(WebDriver driver)
        throws IOException, AWTException, InterruptedException {

        Random r = new Random();
        int randint = Math.abs(r.nextInt()) % 10000;
        String filePath = System.getProperty("user.dir") + "\\" + randint
            + ".txt";
        File file = new File(filePath);
        file.createNewFile();
        TimeUnit.SECONDS.sleep(10);
        WebElement fileUploadButton = driver.findElement(By.id("fileuploader"));
        fileUploadButton.click();
        Robot robot = new Robot();
        robot.setAutoDelay(1000);
        StringSelection stringSelection = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard()
            .setContents(stringSelection, null);
        robot.setAutoDelay(1000);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }
}
