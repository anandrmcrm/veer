package com.vanan.CRM.PageObjects.WholeSitePages;

import com.vanancrm.PageObjects.MainPages.AccessingElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ReadUnknownEmailData {

    private WebDriver driver;

    public ReadUnknownEmailData(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//table[@id='vstabbody']/tbody/tr")
    private List<WebElement> tableRows;

    public String getServiceValues() {

        WebElement eachElement = driver.findElement(
            By.xpath("//table[@id='vstabhead']/tbody/tr/td/div"));

        return eachElement.getText();
    }

    public String getRunTimeTicketFieldValues(String field) {

        String value = "";
        WebElement eachElement;
        String tableRow ="//table[@id='vstabbody']/tbody/tr";
        for (int i = 1; i <= tableRows.size(); i++) {
            List<WebElement> eachElemente = driver.findElements(
                By.xpath(tableRow + "[" + i + "]/td"));
            if(eachElemente.size() ==2) {
                eachElement = driver.findElement(
                    By.xpath(tableRow + "[" + i + "]/td[1]/p"));
                //System.out.println(eachElement.getText() + "))))))))))");

                if (eachElement.getText().contains(field)) {
                    value = driver
                        .findElement(By.xpath(
                            tableRow + "[" + i + "]/td[2]/p"))
                        .getText();
                    break;
                }
            }
        }
        return value;
    }

    public String getEmailIdValues() {

        WebElement eachElement = driver.findElement(
            By.xpath("//div[@class='post-content']/b"));

        return eachElement.getText();
    }
}
