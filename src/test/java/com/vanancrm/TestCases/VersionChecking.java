package com.vanancrm.TestCases;

import org.testng.annotations.Test;

import com.vanancrm.Common.TestBase;
import com.vanancrm.PageObjects.MainPages.Transcription;

import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;

public class VersionChecking extends TestBase {
	private WebDriver driver;
	private List<String> urls = new ArrayList<>();
	private List<String> locators = new ArrayList<>();
	private List<String> elements = new ArrayList<>();
	private List<String> versions = new ArrayList<>();
	private List<String> failureSite = new ArrayList<>();

	private DateFormat dateFormat;
	private Date date;

	@Test
	public void checkVersion() throws IOException {
		
		String version = "";
		int count = 0;
		for (int i = 0; i < urls.size(); i++) {
			driver.get(urls.get(i));
			System.out.print((i + 1) + "\t");
			version = driver.findElement(By.id(elements.get(i)))
					.getAttribute("value");
			System.out.print(urls.get(i) + "\t" + version + "\t");
			cell = sheet.getRow(i + 1).getCell(5);
			if (version.equals(versions.get(i))) {
				System.out.print("pass");
				cell.setCellValue("Pass");
			} else {
				System.out.print("fail");
				cell.setCellValue("Fail");
				failureSite.add(urls.get(i) + " Version: " + version);
				count = count + 1;
			}
			date = new Date();
			cell = sheet.getRow(i + 1).getCell(6);
			cell.setCellValue(dateFormat.format(date));
			System.out.println();
		}
		workbook.write(fileOutput);
		fileOutput.close();
		System.out.println(
				"=============================================================================");
		System.out.println("Test Completed");
		System.out.println(
				"=============================================================================");

		if (count >= 1) {
			System.out.println();
			System.out.println(
					"=============================================================================");
			System.out.println("Failure Site Details");
			System.out.println(
					"=============================================================================");
			for (int i = 0; i < failureSite.size(); i++) {
				System.out.print((i + 1) + "\t" + failureSite.get(i));
			}
			System.out.println(
					"=============================================================================");
			assertTrue(count >= 1, " CRM version is mismatched");
		}

	}

	@BeforeClass
	public void beforeClass() throws IOException {
		System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
		//System.setProperty("webdriver.chrome.driver",
				//"src/main/resources/chromedriver.exe");
		// ChromeOptions chromeOptions = new ChromeOptions();
		// chromeOptions.addArguments("--headless");
		// driver = new ChromeDriver(chromeOptions);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
		file = new File("src/test/resources/VersionChecking.xls");
		fileInput = new FileInputStream(file);
		workbook = new HSSFWorkbook(fileInput);
		sheet = workbook.getSheetAt(0);
		fileOutput = new FileOutputStream(file);
		dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		readInputData();
	}

	private void readInputData() throws IOException {
		
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			cell = sheet.getRow(i).getCell(1);
			urls.add(cell.getStringCellValue());
			cell = sheet.getRow(i).getCell(2);
			locators.add(cell.getStringCellValue());
			cell = sheet.getRow(i).getCell(3);
			elements.add(cell.getStringCellValue());
			cell = sheet.getRow(i).getCell(4);
			versions.add(cell.getStringCellValue());
		}

	}

	@AfterClass
	public void afterClass() {
	}

}
