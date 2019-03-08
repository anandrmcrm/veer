package com.vanancrm.TestCases;

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

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.vanancrm.Common.TestBase;

import com.vanancrm.PageObjects.MainPages.Transcription;

public class NewCustomerTranscription extends TestBase {

	private WebDriver driver;
	private Transcription transcription;
	private DateFormat dateFormat;
	private Date date;
	private int count = 0;

	// Sheet 0 (Individual language price checking)
	private static List<String> languages = new ArrayList<>();
	private static List<Double> minutes = new ArrayList<>();
	private static List<Double> basePrices = new ArrayList<>();
	private static List<Double> expectedPrices = new ArrayList<>();

	// Sheet 1 (Transcription price functionality checking)
	private static List<String> language = new ArrayList<>();
	private static List<Double> minute = new ArrayList<>();
	private static List<Double> translation = new ArrayList<>();
	private static List<String> timeCode = new ArrayList<>();
	private static List<Double> tat = new ArrayList<>();
	private static List<Double> basePrice = new ArrayList<>();
	private static List<Double> unitPrice = new ArrayList<>();
	private static List<Double> discountAmount = new ArrayList<>();
	private static List<Double> expeditePrices = new ArrayList<>();
	private static List<Double> timeCodePrices = new ArrayList<>();
	private static List<Double> subTotalPrices = new ArrayList<>();
	private static List<Double> transactionPrices = new ArrayList<>();
	private static List<Double> grandTotalPrices = new ArrayList<>();

	// Sheet 2 ('English' = > US speaker and Additional Qty price checking)
	private static List<Double> mins = new ArrayList<>();
	private static List<String> langs = new ArrayList<>();
	private static List<Double> nativeSpeakers = new ArrayList<>();
	private static List<Double> verbatims = new ArrayList<>();
	private static List<Double> categorys = new ArrayList<>();
	private static List<String> timeCodes = new ArrayList<>();
	private static List<Double> tats = new ArrayList<>();
	private static List<Double> additionalQtys = new ArrayList<>();
	private static List<Double> baseAmount = new ArrayList<>();
	private static List<Double> unitTotal = new ArrayList<>();
	private static List<Double> verbatimAmount = new ArrayList<>();
	private static List<Double> expediteAmount = new ArrayList<>();
	private static List<Double> additionalQtyAmount = new ArrayList<>();
	private static List<Double> timeCodeAmount = new ArrayList<>();
	private static List<Double> subTotalAmount = new ArrayList<>();
	private static List<Double> transcationAmount = new ArrayList<>();
	private static List<Double> grandTotalAmount = new ArrayList<>();

	// Sheet 3 (Sites UI Icon checking)
	private static List<String> sites = new ArrayList<>();

	@Test
	public void transcriptionTest() throws IOException {
	
		System.out.println("===============================================");
		System.out.println(" \t Transcription Service ");
		System.out.println("===============================================");

		if (!System.getProperty("transcription").equals("UIICON")) {
			driver.get(System.getProperty("website"));
		}
		transcription = new Transcription(driver);
		switch (System.getProperty("transcription")) {

		case "UIICON":

			sheet = workbook.getSheetAt(3);
			readSheet1();
			System.out.println(" Checking UI ICONS ");
			System.out.println("===============================================");
			System.out.println("S.NO\tSite\tStatus");
			checkIconsDisplayedInUI();
			checkFailureCount();
			break;

		case "LANGPRICE":

			sheet = workbook.getSheetAt(0);
			readSheet2();
			System.out.println(" Each Language Price Checking ");
			System.out.println("===============================================");
			System.out.println("S.NO\tLanguage\tStatus");
			checkLangVicePrice();
			checkFailureCount();
			break;

		case "DIFFLANG":

			sheet = workbook.getSheetAt(1);
			readSheet3();
			System.out.println(" Checking Different Language Scenario ");
			System.out.println("===============================================");
			checkDiffLangScenario();
			checkFailureCount();
			break;

		case "USSPEAKER":

			sheet = workbook.getSheetAt(2);
			readSheet4();
			System.out.println(" Checking US Speaker and Additional Language ");
			System.out.println("===============================================");
			checkUSSpeckerAndAddionalQtyPrice();
			checkFailureCount();
			break;
		}
		workbook.write(fileOutput);
		fileOutput.close();
		System.out.println(
				"======================Test completed=====================");
	}

	@BeforeClass
	public void beforeClass() throws IOException {

		System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		file = new File("src/test/resources/NewTranscription.xls");
		fileInput = new FileInputStream(file);
		workbook = new HSSFWorkbook(fileInput);
		fileOutput = new FileOutputStream(file);
		dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	}

	@AfterClass
	public void afterClass() throws IOException {
		
		screenshot(driver, "final1");
		driver.quit();
	}

	private static void readSheet1() {

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			cell = sheet.getRow(i).getCell(1);
			sites.add(cell.getStringCellValue());
		}

	}

	private static void readSheet2() {

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			cell = sheet.getRow(i).getCell(1);
			languages.add(cell.getStringCellValue());
			cell = sheet.getRow(i).getCell(2);
			minutes.add(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(3);
			basePrices.add(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(4);
			expectedPrices.add(cell.getNumericCellValue());
		}

	}

	private static void readSheet3() {

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {

			cell = sheet.getRow(i).getCell(2);
			minute.add(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(4);
			language.add(cell.getStringCellValue());
			cell = sheet.getRow(i).getCell(5);
			translation.add(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(6);
			timeCode.add(cell.getStringCellValue());
			cell = sheet.getRow(i).getCell(7);
			tat.add(cell.getNumericCellValue());

			cell = sheet.getRow(i).getCell(8);
			basePrice.add(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(9);
			unitPrice.add(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(10);
			discountAmount.add(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(11);
			expeditePrices.add(cell.getNumericCellValue());

			cell = sheet.getRow(i).getCell(12);
			timeCodePrices.add(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(13);
			subTotalPrices.add(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(14);
			transactionPrices.add(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(15);
			grandTotalPrices.add(cell.getNumericCellValue());
		}
	}

	private static void readSheet4() {

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {

			cell = sheet.getRow(i).getCell(2);
			mins.add(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(4);
			langs.add(cell.getStringCellValue());
			cell = sheet.getRow(i).getCell(5);
			nativeSpeakers.add(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(6);
			verbatims.add(cell.getNumericCellValue());

			cell = sheet.getRow(i).getCell(7);
			categorys.add(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(8);
			timeCodes.add(cell.getStringCellValue());
			cell = sheet.getRow(i).getCell(9);
			additionalQtys.add(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(10);
			tats.add(cell.getNumericCellValue());

			cell = sheet.getRow(i).getCell(11);
			baseAmount.add(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(12);
			unitTotal.add(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(13);
			verbatimAmount.add(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(14);
			expediteAmount.add(cell.getNumericCellValue());

			cell = sheet.getRow(i).getCell(15);
			additionalQtyAmount.add(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(16);
			timeCodeAmount.add(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(17);
			subTotalAmount.add(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(18);
			transcationAmount.add(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(19);
			grandTotalAmount.add(cell.getNumericCellValue());
		}
	}

	private void checkLangVicePrice() throws IOException {

		for (int i = 0; i < languages.size(); i++) {

			transcription.enterMinutes(String.valueOf(minutes.get(i).intValue()));
			transcription.selectLanguageFrom(languages.get(i));
			System.out.print(i + 1 + "\t" + languages.get(i) + "\t");
			cell = sheet.getRow(i + 1).getCell(5);

			if (!languages.get(i).equals("English")) {
				assertTrue(transcription.isTranslationDisplayed(),
						" translation is not displayed");
			} else {
				assertTrue(transcription.isUSNativeSpeakerDisplayed(),
						" US Native Speaker is not displayed");
			}

			if (basePrices.get(i) == transcription.getBasePrice()
					&& expectedPrices.get(i) == transcription.getUnitCost()) {
				System.out.print("Pass");
				cell.setCellValue("Pass");
			} else {
				System.out.print("Fail");
				count = count + 1;
				cell.setCellValue("Fail");
			}
			date = new Date();
			cell = sheet.getRow(i + 1).getCell(6);
			cell.setCellValue(dateFormat.format(date));

			System.out.println();
			if (i % (languages.size() / 10) == 0) {
				transcription.pageRefresh();
			}
		}
	}

	private void checkDiffLangScenario() throws IOException {

		for (int i = 0; i < language.size(); i++) {

			transcription.enterMinutes(String.valueOf(minute.get(i).intValue()));
			transcription.selectLanguageFrom(language.get(i));
			transcription.selectTranslation(translation.get(i).intValue());
			transcription.selectTimeCode(timeCode.get(i));

			if ((tat.get(i).intValue()) == 0
					&& (basePrice.get(i).intValue()) == 0) {

			} else {

				transcription.selectTAT(tat.get(i).intValue());
			}
			System.out.print(i + 1 + "\t" + language.get(i) + "\t");
			System.out.print(basePrice.get(i) + " \t"
					+ transcription.getBasePrice() + "\t");

			// Amount and Base price Comparison
			cell = sheet.getRow(i + 1).getCell(16);
			comparePrice(basePrice.get(i), transcription.getBasePrice());

			// Total and Unit price Comparison
			cell = sheet.getRow(i + 1).getCell(17);
			comparePrice(unitPrice.get(i), transcription.getUnitCost());

			// Discount and Discount price Comparison
			cell = sheet.getRow(i + 1).getCell(18);
			comparePrice(discountAmount.get(i), transcription.getDiscountPrice());

			// Expedite and Expedite price Comparison
			cell = sheet.getRow(i + 1).getCell(19);
			comparePrice(expeditePrices.get(i), transcription.getExpedite());

			// Timecode Cost and Timecode Cost price Comparison
			cell = sheet.getRow(i + 1).getCell(20);
			comparePrice(timeCodePrices.get(i), transcription.getTimeCode());

			// Subtotal /total and Subtoal price Comparison
			cell = sheet.getRow(i + 1).getCell(21);
			comparePrice(subTotalPrices.get(i), transcription.getTotalCost());

			// Transaction and Transaction fee Comparison
			cell = sheet.getRow(i + 1).getCell(22);
			comparePrice(transactionPrices.get(i),
					transcription.getTranscationFee());

			// GrandTotal and GrandTotal price Comparison
			cell = sheet.getRow(i + 1).getCell(23);
			comparePrice(grandTotalPrices.get(i), transcription.getGrandTotal());

			cell = sheet.getRow(i + 1).getCell(24);
			if (basePrice.get(i) == transcription.getBasePrice()
					&& unitPrice.get(i) == transcription.getUnitCost()
					&& discountAmount.get(i) == transcription.getDiscountPrice()
					&& expeditePrices.get(i) == transcription.getExpedite()
					&& timeCodePrices.get(i) == transcription.getTimeCode()
					&& subTotalPrices.get(i) == transcription.getTotalCost()
					&& transactionPrices.get(i) == transcription
							.getTranscationFee()
					&& grandTotalPrices.get(i) == transcription.getGrandTotal()) {

				cell.setCellValue("Pass");
				System.out.print("Pass\t");
			} else {

				cell.setCellValue(" Fail ");
				System.out.print(" Fail \t ");
				count = count + 1;
			}

			date = new Date();
			cell = sheet.getRow(i + 1).getCell(25);
			cell.setCellValue(dateFormat.format(date));

			System.out.println();
			if (i % (language.size() / 10) == 0) {
				transcription.pageRefresh();
			}
		}
	}

	private void checkIconsDisplayedInUI() {

		for (int i = 0; i < sites.size(); i++) {
			driver.get(sites.get(i));
			System.out.print((i + 1) + "\t" + sites.get(i) + "\t");
			transcription.enterMinutes("10");
			boolean status1;
			boolean status2;
			boolean status3;
			boolean status4;

			if (checkIcons(transcription.isFileIconDisplayed(), true,
					" file Icon is not displayed")
					&& checkIcons(transcription.isLangIconDisplayed(), true,
							" language Icon is not displayed")
					&& checkIcons(transcription.isTimeCodeIconDisplayed(), true,
							" time code Icon is not displayed")
					&& checkIcons(transcription.isAdditionalQtyIconDisplayed(),
							true, " additional qty Icon is not displayed")) {
				status1 = true;
			} else {
				status1 = false;
			}

			transcription.selectLanguageFrom("English");
			if (checkIcons(transcription.isCategoryIconDisplayed(), true,
					" category Icon is not displayed")
					&& checkIcons(transcription.isVerbatimIconDisplayed(), true,
							" verbatim Icon is not displayed")) {
				status2 = true;
			} else {
				status2 = false;
			}

			transcription.enterMinutes("100");
			if (checkIcons(transcription.isFileIconDisplayed(), true,
					" file Icon is not displayed")
					&& checkIcons(transcription.isLangIconDisplayed(), true,
							" language Icon is not displayed")
					&& checkIcons(transcription.isTimeCodeIconDisplayed(), true,
							" time code Icon is not displayed")
					&& checkIcons(transcription.isAdditionalQtyIconDisplayed(),
							true, " additional qty Icon is not displayed")) {
				status3 = true;
			} else {
				status3 = false;
			}

			transcription.selectLanguageFrom("English");
			if (checkIcons(transcription.isCategoryIconDisplayed(), true,
					" category Icon is not displayed")
					&& checkIcons(transcription.isVerbatimIconDisplayed(), true,
							" verbatim Icon is not displayed")
					&& checkIcons(transcription.isFreeTrailIconDisplayed(), true,
							" free trail Icon is not displayed")) {
				status4 = true;
			} else {
				status4 = false;
			}
			cell = sheet.getRow(i + 1).getCell(2);
			if (status1 == true && status2 == true && status3 == true
					&& status4 == true) {

				cell.setCellValue("Pass");
			} else {
				cell.setCellValue("Fail");
				count = count + 1;
			}
			date = new Date();
			cell = sheet.getRow(i + 1).getCell(3);
			cell.setCellValue(dateFormat.format(date));
			System.out.print(dateFormat.format(date));
			System.out.println();
		}
	}

	public void checkUSSpeckerAndAddionalQtyPrice() {

		for (int i = 0; i < langs.size(); i++) {

			transcription.enterMinutes(String.valueOf(mins.get(i).intValue()));
			transcription.selectLanguageFrom(langs.get(i));
			transcription.selectNativeSpeaker();
			transcription.selectVerbatim(verbatims.get(i).intValue());
			transcription.selectCategory(categorys.get(i).intValue());
			transcription.selectTimeCode(timeCodes.get(i));
			transcription
					.selectAdditionalQtyCheck(additionalQtys.get(i).intValue());

			if ((tats.get(i).intValue()) == 0
					&& (baseAmount.get(i).intValue()) == 0) {

			} else {

				transcription.selectTAT(tats.get(i).intValue());
			}
			System.out.print(i + 1 + "\t" + langs.get(i) + "\t");

			// Amount and Base price Comparison
			cell = sheet.getRow(i + 1).getCell(20);
			comparePrice(baseAmount.get(i), transcription.getBasePrice());

			// Total and Unit price Comparison
			cell = sheet.getRow(i + 1).getCell(21);
			comparePrice(unitTotal.get(i), transcription.getUnitCost());

			// Verbatim and UI Verbatim price Comparison
			cell = sheet.getRow(i + 1).getCell(22);
			comparePrice(verbatimAmount.get(i), transcription.getVerbatim());

			// Expedite and UI Expedite price Comparison
			cell = sheet.getRow(i + 1).getCell(23);
			comparePrice(expediteAmount.get(i), transcription.getExpedite());

			// AdditionalQty and UI AdditionalQty price Comparison
			cell = sheet.getRow(i + 1).getCell(24);
			comparePrice(additionalQtyAmount.get(i),
					transcription.getAddtionalQtyCheck());

			// TimeCode and UI TimeCode price Comparison
			cell = sheet.getRow(i + 1).getCell(25);
			comparePrice(timeCodeAmount.get(i), transcription.getTimeCode());

			// Subtotal and UI Subtotal price Comparison
			cell = sheet.getRow(i + 1).getCell(26);
			comparePrice(subTotalAmount.get(i), transcription.getTotalCost());

			// Transaction and UI Transaction fee Comparison
			cell = sheet.getRow(i + 1).getCell(27);
			comparePrice(transcationAmount.get(i),
					transcription.getTranscationFee());

			// GrandTotal and UI GrandTotal Comparison
			cell = sheet.getRow(i + 1).getCell(28);
			comparePrice(grandTotalAmount.get(i), transcription.getGrandTotal());

			cell = sheet.getRow(i + 1).getCell(29);

			if (baseAmount.get(i) == transcription.getBasePrice()
					&& unitTotal.get(i) == transcription.getUnitCost()
					&& verbatimAmount.get(i) == transcription.getVerbatim()
					&& expediteAmount.get(i) == transcription.getExpedite()
					&& additionalQtyAmount.get(i) == transcription
							.getAddtionalQtyCheck()
					&& timeCodeAmount.get(i) == transcription.getTimeCode()
					&& subTotalAmount.get(i) == transcription.getTotalCost()
					&& transcationAmount.get(i) == transcription
							.getTranscationFee()
					&& grandTotalAmount.get(i) == transcription.getGrandTotal()) {

				cell.setCellValue("Pass");
				System.out.print("Pass\t");
			} else {

				cell.setCellValue(" Fail ");
				System.out.print(" Fail \t");
				count = count + 1;
			}
			date = new Date();
			cell = sheet.getRow(i + 1).getCell(30);
			cell.setCellValue(dateFormat.format(date));
			System.out.print(dateFormat.format(date));
			if (i % (langs.size() / 10) == 0) {
				transcription.pageRefresh();
			}
			System.out.println();
		}
	}

	public void checkFailureCount() {

		if (count >= 1) {
			System.out.println("===========================================");
			System.out.println(count + " scenario is failed");
			System.out.println("===========================================");

		}
	}

	private boolean checkIcons(boolean firstValue, boolean secondValue,
			String message) {
		boolean status;
		if (firstValue == secondValue) {

			status = true;
			System.out.print("Pass\t");
		} else {

			status = false;
			System.out.print(" Fail =>" + message + " \t ");
		}
		return status;
	}

	private void comparePrice(double firstValue, double secondValue) {
		if (firstValue == secondValue) {

			cell.setCellValue("Pass");
			System.out.print("Pass\t");
		} else {

			cell.setCellValue(" Fail " + " Mismatched Actual amount : "
					+ secondValue + " Expected amount : " + firstValue);

			System.out.print(" Fail " + secondValue + " \t ");
		}
	}

	/**
	 * This method is used to generate input price
	 * 
	 * @throws IOException
	 */
	private void addDataToSheet() throws IOException {

		sheet = workbook.getSheetAt(2);
		for (int i = 0; i < langs.size(); i++) {

			transcription.enterMinutes(String.valueOf(mins.get(i).intValue()));
			transcription.selectLanguageFrom(langs.get(i));
			transcription.selectNativeSpeaker();
			transcription.selectVerbatim(verbatims.get(i).intValue());
			transcription.selectCategory(categorys.get(i).intValue());
			transcription.selectTimeCode(timeCodes.get(i));
			transcription
					.selectAdditionalQtyCheck(additionalQtys.get(i).intValue());
			transcription.selectTAT(tats.get(i).intValue());
			cell = sheet.getRow(i + 1).getCell(11);
			cell.setCellValue(transcription.getBasePrice());
			cell = sheet.getRow(i + 1).getCell(12);
			cell.setCellValue(transcription.getUnitCost());
			cell = sheet.getRow(i + 1).getCell(13);
			cell.setCellValue(transcription.getVerbatim());
			cell = sheet.getRow(i + 1).getCell(14);
			cell.setCellValue(transcription.getExpedite());
			cell = sheet.getRow(i + 1).getCell(15);
			System.out.println(i + 1);
			System.out.println(transcription.getAddtionalQtyCheck());
			cell.setCellValue(transcription.getAddtionalQtyCheck());
			cell = sheet.getRow(i + 1).getCell(16);
			cell.setCellValue(transcription.getTimeCode());
			cell = sheet.getRow(i + 1).getCell(17);
			cell.setCellValue(transcription.getTotalCost());
			cell = sheet.getRow(i + 1).getCell(18);
			cell.setCellValue(transcription.getTranscationFee());
			cell = sheet.getRow(i + 1).getCell(19);
			cell.setCellValue(transcription.getGrandTotal());
			if (i % (langs.size() / 10) == 0) {
				transcription.pageRefresh();
			}
		}
	}

}
