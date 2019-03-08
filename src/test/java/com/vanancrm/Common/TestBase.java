package com.vanancrm.Common;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class TestBase {
	
	public static File file;
	public static FileInputStream fileInput;
	public FileOutputStream fileOutput;
	
	public static HSSFWorkbook workbook;
	public static HSSFSheet sheet;
	public static HSSFCell cell;
	 
	public static SetData setData = new SetData();
	
	public static List<String> readUrls(String filePath) throws IOException {
		
		file = new File(filePath);
		fileInput = new FileInputStream(file);
		Properties properties = new Properties();
		properties.load(fileInput);
		List<String> urls = new ArrayList<String>();
		for (int i=1; i <= properties.size() ; i++) {
			urls.add(properties.getProperty("url"+i));
		}
		fileInput.close();
		return urls;
	}
	
	public void fullScreen(WebDriver driver) {
		
		try {
			/*Robot robot = new Robot();
			robot.keyPress(java.awt.event.KeyEvent.VK_F11);
			robot.keyRelease(java.awt.event.KeyEvent.VK_F11);
			TimeUnit.SECONDS.sleep(10);*/
			driver.manage().window().maximize();
		} catch (Exception e) {

			//driver.manage().window().maximize();
		}
	}

	public void takeScreenshot(WebDriver driver, String screenshotname,
			String location) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("document.body.style.zoom='50%'");
			screenshot(driver, screenshotname, location);
			TimeUnit.SECONDS.sleep(50);
			js.executeScript("document.body.style.zoom='100%'");
			TimeUnit.SECONDS.sleep(50);
		} catch (Exception e) {

		}
		/*
		 * Screenshot screenshot = new
		 * AShot().shootingStrategy(ShootingStrategies.viewportPasting(10000)).
		 * takeScreenshot(driver); ImageIO.write(screenshot.getImage(),"PNG",new
		 * File(System.getProperty("user.dir")+"/screenshot/"+screenshotname+".png"));
		 * 
		 * 
		 * 
		 * Screenshot screenshot = new AShot().takeScreenshot(driver);
		 * ImageIO.write(screenshot.getImage(),"PNG",new
		 * File(System.getProperty("user.dir")+"/screenshot/"+screenshotname+".png"));
		 */

	}

	public void screenshot(WebDriver driver, String screenshotname,
			String location) throws IOException {

		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile = new File(location + screenshotname + ".png");
		FileUtils.copyFile(SrcFile, DestFile);
	}

	public void screenshot(WebDriver driver, String screenshotname)
			throws IOException {

		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile = new File(System.getProperty("user.dir")
				+ "/src/test/resources/" + screenshotname + ".png");
		FileUtils.copyFile(SrcFile, DestFile);
	}
	
	
	public static void readData() throws IOException {
		
		file =new File("src/test/resources/input.xls");
		fileInput = new FileInputStream(file);
		workbook = new HSSFWorkbook(fileInput);
		sheet = workbook.getSheetAt(0);
		 for(int i=1; i <=sheet.getLastRowNum(); i++) {
			 cell = sheet.getRow(i).getCell(1);
			 setData.setFileType(cell.getStringCellValue());
			 cell = sheet.getRow(i).getCell(2);
			 setData.setPage(cell.getNumericCellValue());
			 cell = sheet.getRow(i).getCell(4);
			 setData.setSourceLanguage(cell.getStringCellValue());
			 cell = sheet.getRow(i).getCell(5);
			 setData.setTransactionLanguage(cell.getStringCellValue());
			 cell = sheet.getRow(i).getCell(6);
			 setData.setCategory(cell.getStringCellValue());
			 cell = sheet.getRow(i).getCell(7);
			 setData.setTat(cell.getNumericCellValue());
			 cell = sheet.getRow(i).getCell(8);
			 setData.setAmount(cell.getNumericCellValue());
			 cell = sheet.getRow(i).getCell(9);
			 setData.setTotal(cell.getNumericCellValue());
			 cell = sheet.getRow(i).getCell(10);
			 setData.setExpedite(cell.getNumericCellValue());
			 cell = sheet.getRow(i).getCell(11);
			 setData.setSubTotal(cell.getNumericCellValue());
			 cell = sheet.getRow(i).getCell(12);
			 setData.setTransaction(cell.getNumericCellValue());
			 cell = sheet.getRow(i).getCell(13);
			 setData.setGrandtotals(cell.getNumericCellValue());
		 }
	}	
	
	public static void readNewTranslateData() throws IOException {

		file = new File("src/test/resources/NewTranslation.xls");
		fileInput = new FileInputStream(file);
		workbook = new HSSFWorkbook(fileInput);
		sheet = workbook.getSheetAt(0);
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			cell = sheet.getRow(i).getCell(1);
			setData.setFileType(cell.getStringCellValue());
			cell = sheet.getRow(i).getCell(2);
			setData.setPage(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(4);
			setData.setSourceLanguage(cell.getStringCellValue());
			cell = sheet.getRow(i).getCell(5);
			setData.setTransactionLanguage(cell.getStringCellValue());
			cell = sheet.getRow(i).getCell(6);
			setData.setTat(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(7);
			setData.setAmount(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(8);
			setData.setTotal(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(9);
			setData.setExpedite(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(10);
			setData.setSubTotal(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(11);
			setData.setTransaction(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(12);
			setData.setGrandtotals(cell.getNumericCellValue());
		}
	}

	public void readFile(String filePath) throws IOException {
		
		file =new File(filePath);

		fileInput = new FileInputStream(file);
		workbook = new HSSFWorkbook(fileInput);
		sheet = workbook.getSheetAt(0);
		for(int i=1; i <=sheet.getLastRowNum(); i++) {
			System.out.println("i=="+i);
		}
	}

	public static void readSiteUrl() throws IOException {

		file = new File("src/test/resources/urls.xls");
		fileInput = new FileInputStream(file);
		workbook = new HSSFWorkbook(fileInput);
		sheet = workbook.getSheetAt(0);
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			cell = sheet.getRow(i).getCell(0);
			setData.setUrls(cell.getStringCellValue());
			cell = sheet.getRow(i).getCell(1);
			setData.setTitles(cell.getStringCellValue());
			cell = sheet.getRow(i).getCell(2);
			setData.setSiteNames(cell.getStringCellValue());
		}
	}
	
	public static void readAllSiteUrl() throws IOException {

		file = new File("src/test/resources/AllSite.xls");
		fileInput = new FileInputStream(file);
		workbook = new HSSFWorkbook(fileInput);
		sheet = workbook.getSheetAt(0);
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			cell = sheet.getRow(i).getCell(1);
			setData.setUrls(cell.getStringCellValue());
			cell = sheet.getRow(i).getCell(2);
			setData.setLocators(cell.getStringCellValue());
			cell = sheet.getRow(i).getCell(3);
			setData.setUIElements(cell.getStringCellValue());
		}
	}

	public void waitForProcessCompletion(int waitTime) {
		try {
			TimeUnit.SECONDS.sleep(waitTime);
		} catch (Exception e) {
		}
	}

	public int generateName() {

        Random r = new Random();
        return Math.abs(r.nextInt()) % 10000;
    }
}
