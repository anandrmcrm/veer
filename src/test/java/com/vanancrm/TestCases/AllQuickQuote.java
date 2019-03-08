package com.vanancrm.TestCases;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.vanan.CRM.PageObjects.MainPages.DashBoardPage;
import com.vanan.CRM.PageObjects.MainPages.Edit;
import com.vanan.CRM.PageObjects.MainPages.EmailConversation;
import com.vanan.CRM.PageObjects.WholeSitePages.Login;
import com.vanan.CRM.PageObjects.WholeSitePages.Menus;
import com.vanan.CRM.PageObjects.WholeSitePages.ReadTableData;
import com.vanan.CRM.PageObjects.WholeSitePages.ViewTicketDetails;
import com.vanancrm.Common.TestBase;
import com.vanancrm.PageObjects.MainPages.QuickQuote;
import com.vanancrm.PageObjects.MainPages.Transcription;

public class AllQuickQuote extends TestBase {
	
	private WebDriver driver;
	private String service;

	@Test
	public void transcriptionServices() {

		String mailId = "automation.vananservices@gmail.com";
		QuickQuote quickQuote = new QuickQuote(driver);
		quickQuote.enterEmail(mailId);
		quickQuote.enterMinutes("2");
		quickQuote.selectLanguageFrom("English");
		quickQuote.category("General");
		quickQuote.clickGetQuote();
		if (service.equals("Captioning")) {
			waitForProcessCompletion(15);
			quickQuote.enterMinutes("23");
			quickQuote.clickPopupSubmit();
			waitForProcessCompletion(20);
		}

		Transcription transcription = new Transcription(driver);
		transcription.clickEmailMeGetQuote();

		// Login into CRM
		driver.get("https://secure-dt.com/crm/user/login");
		Login login = new Login(driver);

		// CRM Dashboard
		DashBoardPage dashBoardPage = login.signIn("developer", "vanan@dev");
		Menus menus = dashBoardPage.clickAllProcess();

		// Read new ticket details
		ReadTableData readTableData = menus.clickNewMenu();
		List<String> tickets = readTableData.readTableRows();
		String ticketID = "";
		ViewTicketDetails viewTicketDetails;
		for (int i = 0; i < tickets.size(); i++) {
			if (tickets.get(i).contains(service)) {
				ticketID = tickets.get(i).substring(tickets.get(i).indexOf("VS"),
						tickets.get(i).indexOf(service) - 1);
				viewTicketDetails = readTableData.clickService(service,
						i + 1);
				waitForProcessCompletion(10);
				if (viewTicketDetails.getRunTimeTicketFieldValues("Email")
						.contains(mailId)) {
					break;
				}
			}
		}
		viewTicketDetails = new ViewTicketDetails(driver);
		System.out.println("===========================================");
		System.out.println("View Ticket Details");
		System.out.println("===========================================");
		System.out.println("Email : => "
				+ viewTicketDetails.getRunTimeTicketFieldValues("Email"));
		System.out.println("Websites : => "
				+ viewTicketDetails.getRunTimeTicketFieldValues("Websites"));
		System.out.println("Channel : => "
				+ viewTicketDetails.getRunTimeTicketFieldValues("Channel"));
		System.out.println("Language : => "
				+ viewTicketDetails.getRunTimeTicketFieldValues("Language"));
		System.out.println("Minutes : => "
				+ viewTicketDetails.getRunTimeTicketFieldValues("Minutes"));
		System.out.println("Type Of Service : => " + viewTicketDetails
				.getRunTimeTicketFieldValues("Type Of Service"));
		System.out.println("Service : => "
				+ viewTicketDetails.getRunTimeTicketFieldValues("Service"));
		System.out.println("===========================================\n");
		// Edit a ticket and moved the status into Others
		Edit edit = menus.clickEdit();
		edit.selectStatus("Others");
		edit.clickUpdateButton();
		waitForProcessCompletion(10);
		EmailConversation emailConversation = menus.clickEmailConversation();
		emailConversation.clickReadMore();

		System.out.println("===========================================");
		System.out.println("Email Conversation");
		System.out.println("===========================================");
		System.out.println(
				"Email Service : => " + emailConversation.getServiceDetails());
		System.out.println("Email Minutes : => "
				+ emailConversation.getTicketFieldValues("Minutes"));

		System.out.println("Transcription Language : => "
				+ emailConversation.getTicketFieldValues("Transcription Language"));

		System.out.println(
				"Service  : => " + emailConversation.getTicketFieldValues("Service"));

		System.out.println("===========================================");
		emailConversation.clickNoActionButton();
		emailConversation.clickSignOut();
	}

	@BeforeClass
	public void beforeClass() {

		System.setProperty("webdriver.chrome.driver",
				"src/main/resources/chromedriver.exe");
		// System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		fullScreen(driver);
		driver.get(System.getProperty("website"));
		service = System.getProperty("service");
	}

	@AfterClass
	public void afterClass() {
	}
}
