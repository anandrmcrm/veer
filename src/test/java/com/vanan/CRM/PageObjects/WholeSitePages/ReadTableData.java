package com.vanan.CRM.PageObjects.WholeSitePages;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;


import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ReadTableData {

	private WebDriver driver;

	public ReadTableData(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@id='process_lists_wrapper']/table/tbody/tr")
	private List<WebElement> tableRows;

	private int getTableRowSize() {

		return tableRows.size();
	}

	public int getTicketCount() {

        int size = getTableRowSize();
        String count;
        if (size == 50) {
            count = driver.findElement(By.xpath(
                "//div[@id='process_lists_wrapper']/table/tbody/tr[50]/td[2" +
                    "]")).getText();
            size = Integer.parseInt(count);
        } else {
            List<WebElement> columnSize = driver.findElements(
                By.xpath("//div[@id='process_lists_wrapper']/table/tbody/tr["
                    + size + "]/td"));
            if (columnSize.size() == 1) {
                String message = "No data available in table";
                if (driver.findElement(
                    By.xpath("//div[@id='process_lists_wrapper']/table/tbody/tr["
                        + size + "]/td[1]")).getText().equals(message)) {
                    size = 0;

                } else {
                    System.out.println("Somethink went wrong please check " +
                        "manually");
                }

            } else {
                count = driver.findElement(
                    By.xpath("//div[@id='process_lists_wrapper']/table/tbody/tr["
                        + size + "]/td[2]"))
                    .getText();
                size = Integer.parseInt(count);
            }
        }
        return size;
    }

    public void isNoDataMessageDisplayed() {
        int size = getTableRowSize();
        List<WebElement> columnSize = driver.findElements(
            By.xpath("//div[@id='process_lists_wrapper']/table/tbody/tr["
                + size + "]/td"));
        if (columnSize.size() == 1) {
            String message = "No data available in table";
            if (driver.findElement(
                By.xpath("//div[@id='process_lists_wrapper']/table/tbody/tr["
                    + size + "]/td[1]")).getText().equals(message)) {
                size = 0;
                System.out.println(message + " message is displayed");
            } else {
                System.out.println("Somethink went wrong please check " +
                    "manually");
            }
        }
    }

    public void isNoDataMessageDisplayedOrderList() {
        int size = getTableRowSize();
        List<WebElement> columnSize = driver.findElements(
            By.xpath("//div[@id='order_lists_wrapper']/table/tbody/tr["
                + size + "]/td"));
        if (columnSize.size() == 1) {
            String message = "No data available in table";
            if (driver.findElement(
                By.xpath("//div[@id='order_lists_wrapper']/table/tbody/tr["
                    + size + "]/td[1]")).getText().equals(message)) {
                size = 0;
                System.out.println(message + " message is displayed");
            } else {
                System.out.println("Somethink went wrong please check " +
                    "manually");
            }
        }
    }

    public int getUnrespondTicketDetails() {
        int count = 0;
        for (int i = 1; i <= getTableRowSize(); i++) {
            String value = driver.findElement(
                By.xpath("//div[@id='process_lists_wrapper']/table/tbody/tr["
                    + i + "]/td[12]/i")).getCssValue("color");

					//+ i + "]/td[11]/i")).getCssValue("color");

            if (value.contains("rgba(255, 0, 0, 1)")) {
                count = count + 1;
            }
        }
        return count;
    }

    public int getLastTicketNumber() throws IOException {

        String value = driver.findElement(
            By.xpath("//div[@id='process_lists_wrapper']/table/tbody/tr["
                + getTableRowSize() + "]/td[2]")).getText();
        return Integer.parseInt(value);
    }

    public int getLastTicketNumberFromTAT() throws IOException {

        List<WebElement> elements = driver.findElements(By.xpath
            ("//div[@id='order_lists_wrapper']/table/tbody/tr"));
        String value = driver.findElement(
            By.xpath("//div[@id='order_lists_wrapper']/table/tbody/tr["
                + elements.size() + "]/td[2]")).getText();
        return Integer.parseInt(value);
    }

	public int getTicketCountWithCheckBox() {

        int size = getTableRowSize();
        String count;
        if (size == 50) {
            count = driver.findElement(By.xpath(
                "//div[@id='process_lists_wrapper']/table/tbody/tr[50]/td[2]"))
                .getText();
            size = Integer.parseInt(count);
        } else {
            count = driver.findElement(
                By.xpath("//div[@id='process_lists_wrapper']/table/tbody/tr["
                    + size + "]/td[2]"))
                .getText();
            size = Integer.parseInt(count);
        }
        return size;
    }

	public List<String> readTableRows() {

		List<String> rowValues = new ArrayList<String>();
		for (int i = 0; i < getTableRowSize(); i++) {
			rowValues.add(tableRows.get(i).getText());
			// System.out.println("i==" + tableRows.get(i).getText());
		}
		return rowValues;
	}

	public ViewTicketDetails clickOrderNo(String orderNo) {

		WebElement orderNos = driver.findElement(By.linkText(orderNo));
		orderNos.click();
		ViewTicketDetails viewTicketDetails = new ViewTicketDetails(driver);
		return viewTicketDetails;
		/*
		 * for (int i=1; i<= getTableRowSize(); i++) {
		 * 
		 * orderNos = driver.findElement(By.xpath(
		 * "//div[@id='process_lists_wrapper']/table/tbody/tr[" + i + "]/td[2]"));
		 * if(orderNos.getText().equals(orderNo)) {
		 * 
		 * } }
		 */
	}

	public ViewTicketDetails clickService(String serviceName, int row) {
		WebElement service;

		try {

			service = driver.findElement(
					By.xpath("//div[@id='process_lists_wrapper']/table/tbody/tr["
							+ row + "]/td[4]"));

			if (service.getText().equals(serviceName)) {
				service.click();

				if (isAlertPresent()) {
					Alert alert = driver.switchTo().alert();
					System.out.println(alert.getText());
					alert.accept();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		ViewTicketDetails viewTicketDetails = new ViewTicketDetails(driver);
		return viewTicketDetails;
	}

	public void handleAlert() {

		if (isAlertPresent()) {
			Alert alert = driver.switchTo().alert();
			System.out.println(alert.getText());
			alert.accept();
		}
	}

	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException ex) {
			return false;
		}
	}
}