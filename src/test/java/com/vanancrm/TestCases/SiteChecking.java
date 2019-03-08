package com.vanancrm.TestCases;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileOutputStream;
import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.concurrent.TimeUnit;

import com.vanancrm.Common.TestBase;

public class SiteChecking extends TestBase {

	private WebDriver driver;

	@Test
	public void testStep() throws IOException {

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date date;
		String message = "";
		
		System.out.println("S.No \t Site Name \t Status");
		for (int i = 0; i < setData.getUrls().size(); i++) {

			driver.get(setData.getUrls().get(i));
			if (driver.getTitle().contains(setData.getTitles().get(i))) {
				message = " is running";

			} else {
				message = " is down";
			}
			date = new Date();
			cell = sheet.getRow(i + 1).getCell(3);
			cell.setCellValue(setData.getSiteNames().get(i) + message);
			cell = sheet.getRow(i + 1).getCell(4);
			cell.setCellValue(dateFormat.format(date));
			System.out.print((i+1) + "\t" + setData.getUrls().get(i) 
					+ " \t " + message + "\n");
			screenshot(driver, setData.getSiteNames().get(i));
		}
		workbook.write(fileOutput);
		fileOutput.close();
	}

	@BeforeClass
	public void beforeClass() throws IOException {

		// Linux driver loading
		System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");

		// Windows driver loading
		//System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");

		// Headless Chrome option
		// ChromeOptions chromeOptions = new ChromeOptions();
		// chromeOptions.addArguments("--headless");
		// driver = new ChromeDriver(chromeOptions);

		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		readSiteUrl();
		fullScreen(driver);
		fileOutput = new FileOutputStream(file);
	}

	@AfterClass
	public void afterClass() {
	
		driver.quit();
	}

}
