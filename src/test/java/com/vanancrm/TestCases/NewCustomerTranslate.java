package com.vanancrm.TestCases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;

import java.awt.AWTException;

import java.io.FileOutputStream;
import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.vanancrm.Common.TestBase;

import com.vanancrm.PageObjects.MainPages.Translation;

public class NewCustomerTranslate extends TestBase {

	private WebDriver driver;

	private static List<String> fileTypes = new ArrayList<String>();
	private static List<Double> pages = new ArrayList<Double>();
	private static List<String> sourceLanguages = new ArrayList<String>();
	private static List<String> transactionLanguages = new ArrayList<String>();
	private static List<Double> tats = new ArrayList<Double>();
	private static List<Double> amounts = new ArrayList<Double>();
	private static List<Double> totals = new ArrayList<Double>();
	private static List<Double> expedites = new ArrayList<Double>();
	private static List<Double> subtotals = new ArrayList<Double>();
	private static List<Double> transactions = new ArrayList<Double>();
	private static List<Double> grandtotals = new ArrayList<Double>();

	@Test
	public void testStep() throws TimeoutException, IOException, AWTException, InterruptedException {

		sheet = workbook.getSheetAt(0);
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date date;
		driver.get(System.getProperty("website"));
		Translation translation = new Translation(driver);
		System.out.println("S.No \t File Type \t Page \t S Language \t" + " T Language \t Tat \t Amount \t Total \t"
				+ " Expedite \t Subtotal \t transaction \t Grandtotal");
		int count = 0;
		for (int i = 0; i < fileTypes.size(); i++) {
			System.out.print(i + 1 + " \t " + fileTypes.get(i) + " \t " + pages.get(i).intValue() + " \t "
					+ sourceLanguages.get(i) + " \t " + transactionLanguages.get(i) + " \t " + " \t "
					+ tats.get(i).intValue() + " \t " + amounts.get(i).intValue() + "\t" + totals.get(i) + "\t"
					+ expedites.get(i) + "\t" + subtotals.get(i) + "\t" + transactions.get(i) + "\t"
					+ grandtotals.get(i) + "\t");
			translation.selectFileType(fileTypes.get(i));
			translation.pageCount(String.valueOf(pages.get(i).intValue()));
			translation.selectLanguageFrom(sourceLanguages.get(i));
			translation.selectLanguageTo(transactionLanguages.get(i));
			// translation.selectCategory(categorys.get(i));

			if ((tats.get(i).intValue()) == 0 && (amounts.get(i).intValue()) == 0) {

			} else {

				translation.selectTAT(tats.get(i).intValue());
			}
			cell = sheet.getRow(i + 1).getCell(13);
			if ((amounts.get(i)) == translation.getActualCost()) {

				cell.setCellValue("Pass");
				// System.out.print("Pass\t");
			} else {

				cell.setCellValue(" Fail " + " Mismatched Actual amount : " + amounts.get(i).intValue()
						+ " Expected amount : " + translation.getActualCost());
				System.out.print(" Fail " + translation.getActualCost() + " \t ");
			}
			cell = sheet.getRow(i + 1).getCell(14);
			if ((totals.get(i)) == translation.getTranslationCost()) {

				cell.setCellValue("Pass");
				// System.out.print("Pass" + " \t ");
			} else {

				cell.setCellValue(" Fail " + " Mismatched Actual total : " + totals.get(i).intValue()
						+ " Expected total : " + translation.getTranslationCost());
				System.out.print(" Fail " + translation.getTranslationCost() + " \t ");
			}
			cell = sheet.getRow(i + 1).getCell(15);
			if ((expedites.get(i)) == translation.getExpeditedCost()) {

				cell.setCellValue("Pass");
				// System.out.print("Pass" + " \t ");
			} else {

				cell.setCellValue(" Fail " + " Mismatched Actual Expedite : " + expedites.get(i).intValue()
						+ " Expected Expedite : " + translation.getExpeditedCost());
				System.out.print(" Fail " + translation.getExpeditedCost() + " \t ");
			}
			cell = sheet.getRow(i + 1).getCell(16);
			if ((subtotals.get(i)) == translation.getSubTotal()) {

				cell.setCellValue("Pass");
				// System.out.print("Pass" + " \t ");
			} else {

				cell.setCellValue(" Fail " + " Mismatched Actual subtotal : " + subtotals.get(i).intValue()
						+ " Expected subtotal : " + translation.getSubTotal());
				System.out.print(" Fail " + translation.getSubTotal() + " \t ");
			}
			cell = sheet.getRow(i + 1).getCell(17);
			if ((transactions.get(i)) == translation.getTransactionFee()) {

				cell.setCellValue("Pass");
				// System.out.print("Pass" + " \t ");
			} else {

				cell.setCellValue(" Fail " + " Mismatched Actual Transaction fee : " + transactions.get(i).intValue()
						+ " Expected Transaction fee : " + translation.getTransactionFee());
				System.out.print(" Fail " + translation.getTransactionFee() + " \t ");
			}
			cell = sheet.getRow(i + 1).getCell(18);
			if ((grandtotals.get(i)) == translation.getGrandTotal()) {

				cell.setCellValue("Pass");
				// System.out.print("Pass" + " \t ");
			} else {

				cell.setCellValue(" Fail " + " Mismatched Actual Grand total : " + grandtotals.get(i).intValue()
						+ " Expected Grand total : " + translation.getGrandTotal());
				System.out.print(" Fail " + translation.getGrandTotal() + " \t ");
			}

			date = new Date();
			if ((amounts.get(i)) == translation.getActualCost() && (totals.get(i)) == translation.getTranslationCost()
					&& (expedites.get(i)) == translation.getExpeditedCost()
					&& (subtotals.get(i)) == translation.getSubTotal()
					&& (transactions.get(i)) == translation.getTransactionFee()
					&& (grandtotals.get(i)) == translation.getGrandTotal()) {
				cell = sheet.getRow(i + 1).getCell(19);
				cell.setCellValue("Pass");
				// System.out.print("Pass\t");
				cell = sheet.getRow(i + 1).getCell(20);
				cell.setCellValue(dateFormat.format(date));
				System.out.print(dateFormat.format(date));
			} else {
				cell = sheet.getRow(i + 1).getCell(19);
				cell.setCellValue("Fail");
				System.out.print("Fail \t");
				cell = sheet.getRow(i + 1).getCell(20);
				cell.setCellValue(dateFormat.format(date));
				System.out.print(dateFormat.format(date));
				count = count + 1;
			}
			System.out.println();
			// translation.deselectSourceLanguageFrom();
			// translation.deselectSourceLanguageTo();
			if (i % (fileTypes.size() / 100) == 0) {
				translation.pageRefresh();
			}
		}
		if (count >= 1) {
			System.out.println("===========================================");
			System.out.println("Below scenario is failed");
			System.out.println("===========================================");
			assertFalse(true, "Scenarios got failed");
		}
		System.out.println("======================Test completed=====================");
		workbook.write(fileOutput);
		fileOutput.close();
		screenshot(driver, "final1");

	}

	@BeforeClass
	public void beforeClass() throws IOException {
		System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		fullScreen(driver);
		readNewTranslateData();
		fileTypes = setData.getFileType();
		pages = setData.getPage();
		sourceLanguages = setData.getSourceLanguage();
		transactionLanguages = setData.getTransactionLanguage();
		tats = setData.getTat();
		amounts = setData.getAmount();
		totals = setData.getTotal();
		expedites = setData.getExpedite();
		subtotals = setData.getSubTotal();
		transactions = setData.getTransaction();
		grandtotals = setData.getGrandtotals();
		fileOutput = new FileOutputStream(file);
	}

	@AfterClass
	public void afterClass() {

		driver.quit();
	}
}
