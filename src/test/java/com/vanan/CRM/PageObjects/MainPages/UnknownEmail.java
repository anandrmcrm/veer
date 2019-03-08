package com.vanan.CRM.PageObjects.MainPages;

import com.vanancrm.PageObjects.MainPages.AccessingElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class UnknownEmail extends AccessingElement {

    private WebDriver driver;

    public UnknownEmail(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//ul[@class='sidebar-menu']/li[@class='treeview scroll']/a")
    private WebElement allprocess;

    private void clickDropdownArrow() {

        WebElement eachElement = driver.findElement(
            By.xpath("//span[@class='caret']"));
        clickElement(eachElement);
    }

    private void selectFields(String field) {

        String elementParentPath = "//span[@class='caret']";
        switch (field) {

            case "Folder":
                clickElement(driver.findElement(By.xpath(elementParentPath +
                    "/li[1]")));
                break;

            case "Order No":
                clickElement(driver.findElement(By.xpath(elementParentPath +
                    "/li[2]")));
                break;

        }
    }

    public void clickEmail(String serviceDetails) {

        List<WebElement> tableRows = driver.findElements(By.xpath
            ("//div[@class='box box-primary']//..//table/tbody/tr"));
        WebElement eachElement;
        for (int i = 1; i <= tableRows.size(); i++) {

            eachElement = driver.findElement(
                By.xpath("//div[@class='box box-primary']//..//table/tbody/tr[" + i + "]/td[@class='mailbox-subject']"));
            //System.out.println(eachElement.getText() + "))))))))))");

            if (eachElement.getText().contains(serviceDetails)) {
                eachElement.click();
                break;
            }

        }


    }
}
