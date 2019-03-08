package com.vanancrm.TestCases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileOutputStream;
import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.concurrent.TimeUnit;

import com.vanancrm.Common.TestBase;

import com.vanancrm.PageObjects.MainPages.Translation;


public class AllSiteChecking extends TestBase {

	private WebDriver driver;
	private String message = "";
	private Date date;
	private DateFormat dateFormat;
	
	@Test
	public void testAllAvailableTest() throws IOException {

		dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		List<Integer> failureCount = new ArrayList<>();
		List<String> failedUrls = new ArrayList<>();
		int count = 0;
		
		System.out.println("S.No \t Site Name \t Status");
		for (int i = 0; i < setData.getUrls().size(); i++) {

			try {
				message = test(i);
				writeFileContent(i, message);
			} catch (NoSuchElementException e) {
				message = " Down";
				count = count + 1;
				failureCount.add(count);
				failedUrls.add(setData.getUrls().get(i));
				writeFileContent(i, message);
				continue;
			}
	
		}

		workbook.write(fileOutput);
		fileOutput.close();
		System.out.println("===========================================");
		System.out.println("Test case completed");
		System.out.println("===========================================");
		if((failureCount.size())>0) {
			System.out.println("===========================================");
			System.out.println("Below Sites got down");
			System.out.println("===========================================");
			for (int i=0; i<failureCount.size();i++) {
				System.out.print((i+1) + "===>");
				System.out.print(failedUrls.get(i) + " is Down");
				System.out.println();
			}
			assertTrue(false, "Sites Got Down");
		}
	}

	private String test(int i) throws IOException {

		driver.get(setData.getUrls().get(i));
		Translation translation = new Translation(driver);
		
		if (translation.checkElementVisible(setData.getLocators()
				.get(i), setData.getUIElements().get(i))) {
			
			message = " Running";
		} 
		return message;
	}
	
	private void writeFileContent(int row, String content)
			throws IOException{
		String name = setData.getUrls().get(row).substring(setData.getUrls()
				.get(row).indexOf("/")+2, setData.getUrls().get(row).indexOf("."))
				+ "_" + (setData.getUrls().get(row).substring(setData.getUrls()
						.get(row).lastIndexOf("/") +1, setData.getUrls().get(row)
						.lastIndexOf(".")));
		date = new Date();
		cell = sheet.getRow(row + 1).getCell(4);
		cell.setCellValue(content);
		cell = sheet.getRow(row + 1).getCell(5);
		cell.setCellValue(dateFormat.format(date));
		System.out.print((row + 1) + "\t" + setData.getUrls().get(row)
				+ " \t =>" + content + "\n");
		screenshot(driver, "" + name);
	}

	@BeforeClass
	public void beforeClass() throws IOException {
		// Linux driver loading
		System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");

		// Windows driver loading
		// System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");

		// Headless Chrome option
		// ChromeOptions chromeOptions = new ChromeOptions();
		// chromeOptions.addArguments("--headless");
		// driver = new ChromeDriver(chromeOptions);

		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		readAllSiteUrl();
		fullScreen(driver);
		fileOutput = new FileOutputStream(file);
	}

	@AfterClass
	public void afterClass() throws IOException {

		screenshot(driver, "Failure");
		driver.quit();
	}

}
