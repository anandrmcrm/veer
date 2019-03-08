package com.vanancrm.TestCases;

import java.io.FileReader;
import java.io.IOException;

import java.util.List;
import java.util.Properties;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;

import com.vanan.CRM.PageObjects.MainPages.DashBoardPage;
import com.vanan.CRM.PageObjects.MainPages.Edit;
import com.vanan.CRM.PageObjects.MainPages.EmailConversation;

import com.vanan.CRM.PageObjects.WholeSitePages.Login;
import com.vanan.CRM.PageObjects.WholeSitePages.Menus;
import com.vanan.CRM.PageObjects.WholeSitePages.ReadTableData;
import com.vanan.CRM.PageObjects.WholeSitePages.ViewTicketDetails;

import com.vanancrm.Common.TestBase;

import com.vanancrm.PageObjects.MainPages.QuickQuote;

/**
 *
 * Author - Manikavasagam (manikavasagam@vananservices.com)
 *
 */
public class CaptioningQuickQuote extends TestBase {

    private WebDriver driver;

    private String phoneNumber = "1-888-535-5668";

    private String[] languages = {"Spanish", "Apache"};
    private String[] formats = {"Standalone", "Embedded"};
    private String mailId = "automation.vananservices@gmail.com";
    private String service = "Captioning";
    private String channel = "Quick Quote";
    private String minutes = "180";
    private String comments = "Automation Testing";
    private String status = "Yes";

    private static String username = "";
    private static String password = "";
    private String url = "";

    private EmailConversation emailConversation;
    private Menus menus;
    private ReadTableData readTableData;
    private ViewTicketDetails viewTicketDetails;

    @Test
    public void transcriptionServices() throws IOException {

        System.out.println("===========================================");
        System.out.println("Scenario Started");
        System.out.println("===========================================");
        testScenario(languages[0], formats[1]);
        testScenario(languages[1], formats[0]);
        System.out.println("Test Completed");
        System.out.println("===========================================");
    }

    @BeforeClass
    public void beforeClass() {

        System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        fullScreen(driver);
    }

    @AfterClass
    public void afterClass() throws IOException {

        screenshot(driver, "CaptioningQuickQuote");
        driver.quit();
    }

    private void raiseTicket(String language, String format) {

        url = System.getProperty("website");
        driver.get(url);
        QuickQuote quickQuote = new QuickQuote(driver);
        System.out.print("Mail id : " + mailId + ",\t");
        quickQuote.enterEmail(mailId);
        System.out.print("Phone Number : " + phoneNumber + ",\t");
        quickQuote.enterPhoneNumber(phoneNumber);
        System.out.print("Language : " + language + ",\t");
        quickQuote.selectLanguageFrom(language);
        System.out.print("Format : " + format);
        quickQuote.selectFormat(format);
        quickQuote.selectTranscript();
        quickQuote.selectTranslate();
        quickQuote.clickGetQuote();
        waitForProcessCompletion(10);
        quickQuote.enterMinutes(minutes);
        quickQuote.enterComments(comments);
        quickQuote.clickPopupSubmit();
        waitForProcessCompletion(10);
    }

    private void checkCRM(String language, String typeOfService) {

        driver.get("https://secure-dt.com/crm/user/login");
        Login login = new Login(driver);
        DashBoardPage dashBoardPage = login.signIn(username, password);
        menus = dashBoardPage.clickAllProcess();
        viewTicketDetails = new ViewTicketDetails(driver);
        String ticketID = getTicketId(menus, channel);
        checkViewTicketInfo(language, typeOfService);

        System.out.println("Ticket ID: " + ticketID);
        changeTicketStatus();
        checkCRMEmailConversation(language, typeOfService);
        moveTicketToOtherStatus();
        waitForProcessCompletion(10);
        menus.clickSignOut();
    }

    private String getTicketId(Menus menus, String channel) {
        String ticketID = "";
        readTableData = menus.clickNewMenu();
        List<String> tickets = readTableData.readTableRows();
        for (int i = 0; i < tickets.size(); i++) {
            if (tickets.get(i).contains(service)) {
                ticketID = tickets.get(i).substring(tickets.get(i).indexOf("VS"),
                    tickets.get(i).indexOf(service) - 1);
                viewTicketDetails = readTableData.clickService(service,
                    i + 1);
                waitForProcessCompletion(10);
                if (viewTicketDetails.getRunTimeTicketFieldValues("Email")
                    .contains(mailId)) {
                    if (viewTicketDetails.getRunTimeTicketFieldValues("Channel")
                        .contains(channel)) {
                        System.out.println((i + 1) + " : Channel = " +
                            viewTicketDetails.getRunTimeTicketFieldValues(
                                "Channel"));
                        break;
                    }
                }
            }
        }
        return ticketID;
    }

    private static void getCRMCreadential() throws IOException {

        FileReader fileReader = new FileReader(System.getProperty("user.dir")
            + "/src/test/resources/CRM.txt");
        Properties properties = new Properties();
        properties.load(fileReader);
        username = properties.getProperty("USERNAME");
        password = properties.getProperty("PASSWORD");
    }

    private void evaluateCondition(String message, String first,
                                   String second) {

        System.out.print(message + " : " + second);
        if (first.contains(second)) {

            System.out.print("\t Status : Pass\n");
        } else {
            System.out.print("\t Status : Fail\n");
        }
    }

    private void changeTicketStatus() {

        // Edit a ticket and moved the status into Others
        Edit edit = menus.clickEdit();
        edit.selectStatus("Others");
        edit.clickUpdateButton();
        waitForProcessCompletion(10);
        emailConversation = menus.clickEmailConversation();
        emailConversation.clickReadMore();
    }

    private void checkCRMEmailConversation(String language, String format) {

        System.out.println("\n===========================================");
        System.out.println("Checking Email Conversation");
        System.out.println("===========================================\n");
        evaluateCondition("Email Service",
            emailConversation.getServiceDetails(), service);
        evaluateCondition("Minutes",
            emailConversation.getTicketFieldValues("Minutes"), minutes);
        evaluateCondition("Phone Number",
            emailConversation.getTicketFieldValues("Phone Number"), phoneNumber);
        evaluateCondition("Need Transcription",
            emailConversation.getTicketFieldValues("Need Transcription"), status);
        evaluateCondition("Need Translation",
            emailConversation.getTicketFieldValues("Need Translation"), status);
        evaluateCondition("Target Language",
            emailConversation.getTicketFieldValues("Target "
            + "Language"), language);
        evaluateCondition("Formats",
            emailConversation.getTicketFieldValues("Formats"), format);
        evaluateCondition("Comment",
            emailConversation.getTicketFieldValues("Comment"), comments);
        System.out.println("===========================================");
        emailConversation.clickNoActionButton();
    }

    private void checkViewTicketInfo(String language, String typeOfService) {

        System.out.println("===========================================");
        System.out.println("Checking View Ticket Details");
        System.out.println("===========================================\n");

        evaluateCondition("Email", viewTicketDetails
            .getRunTimeTicketFieldValues("Email"), mailId);
        evaluateCondition("Websites", url,
            viewTicketDetails.getRunTimeTicketFieldValues("Websites"));
        evaluateCondition("Channel", viewTicketDetails
            .getRunTimeTicketFieldValues("Channel"), channel);

        evaluateCondition("PhoneNo", viewTicketDetails
            .getRunTimeTicketFieldValues("PhoneNo"), phoneNumber);
        evaluateCondition("Need Transcription", viewTicketDetails
            .getRunTimeTicketFieldValues("Need Transcription"), status);
        evaluateCondition("Need Translation", viewTicketDetails
            .getRunTimeTicketFieldValues("Need Translation"), status);
        evaluateCondition("Video Target Language", viewTicketDetails
            .getRunTimeTicketFieldValues("Video Target Language"), language);

        evaluateCondition("Minutes", viewTicketDetails
            .getRunTimeTicketFieldValues("Minutes"), minutes);
        evaluateCondition("Format", viewTicketDetails
            .getRunTimeTicketFieldValues("Format"), typeOfService);
        evaluateCondition("Comment", viewTicketDetails
            .getRunTimeTicketFieldValues("Comment"), comments);
        evaluateCondition("Service", viewTicketDetails
            .getServiceValues(), service);
        System.out.println("===========================================\n");
    }

    private void moveTicketToOtherStatus() {

        driver.navigate().refresh();
        waitForProcessCompletion(10);
        menus.clickAllProcess();
        readTableData = menus.clickNewMenu();
        List<String> tickets = readTableData.readTableRows();
        for (int i = 0; i < tickets.size(); i++) {
            if (tickets.get(i).contains(service)) {
                viewTicketDetails = readTableData.clickService(service,
                    i + 1);
                waitForProcessCompletion(10);
                if (viewTicketDetails.getRunTimeTicketFieldValues("Email")
                    .contains(mailId)) {

                    changeTicketStatus();
                    emailConversation.clickNoActionButton();
                    driver.navigate().refresh();
                    waitForProcessCompletion(10);
                    menus.clickAllProcess();
                    menus.clickNewMenu();
                    break;
                }
            }
        }
    }

    private void testScenario(String language, String format) throws
        IOException {

        raiseTicket(language, format);
        getCRMCreadential();
        checkCRM(language, format);
    }
}
