package com.vanancrm.TestCases;

import java.io.FileReader;
import java.io.IOException;

import java.util.List;
import java.util.Properties;

import java.util.concurrent.TimeUnit;

import com.vanancrm.PageObjects.MainPages.Translation;
import com.vanancrm.PageObjects.MainPages.Typing;
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
 * Author - Manikavasagam (manikavasagam@vananservices.com)
 */
public class TypingQuickQuote extends TestBase {

    private WebDriver driver;

    private String mp = "20";

    private String[] languages = {"English", "Apache"};
    private String[] categorys = {"General", "Legal"};
    private String[] fileTypes = {"Document", "Audio/Video"};
    private String mailId = "automation.vananservices@gmail.com";
    private String service = "Typing";
    private String channel = "Quick Quote";
    private String formatting = "Yes";

    private static String username = "";
    private static String password = "";
    private String url = "";

    private EmailConversation emailConversation;
    private Menus menus;
    private ReadTableData readTableData;
    private ViewTicketDetails viewTicketDetails;

    @Test
    public void transcriptionServices() throws IOException {

        System.out.println("======================================");
        System.out.println("Scenario Started");
        System.out.println("======================================");
        testScenario(languages[0], categorys[0], fileTypes[0]);
        testScenario(languages[1], categorys[1], fileTypes[1]);
        System.out.println("Test Completed");
        System.out.println("======================================");
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

        screenshot(driver, "TypingQuickQuote");
        driver.quit();
    }

    private void raiseTicket(String language, String category, String
        fileType) {

        url = System.getProperty("website");
        driver.get(url);
        QuickQuote quickQuote = new QuickQuote(driver);
        System.out.print("Mail id : " + mailId + ",\t");
        quickQuote.enterEmail(mailId);
        System.out.print("Language : " + language + ",\t");
        quickQuote.selectLanguageFrom(language);
        System.out.print("File Type : " + fileType + ",\t");
        quickQuote.selectFileType(fileType);
        System.out.print("Minute / Pages : " + mp + ",\t");
        if (fileType.equals(fileTypes[0])) {

            System.out.print("Formatting : " + formatting + ",\t");
            quickQuote.selectFormatting(formatting);
            quickQuote.enterPages(mp);
        } else {

            quickQuote.enterMinutes(mp);

        }
        quickQuote.category(category);
        quickQuote.clickGetQuote();

        waitForProcessCompletion(15);
        Typing typing = new Typing(driver);
        if (fileType.equals(fileTypes[1])) {

            typing.clickGetQuote();
        } else {

            typing.clickEmailMeGetQuote();
        }
        waitForProcessCompletion(15);
    }

    private void checkCRM(String language, String category, String
        fileType) {

        driver.get("https://secure-dt.com/crm/user/login");
        Login login = new Login(driver);
        DashBoardPage dashBoardPage = login.signIn(username, password);
        menus = dashBoardPage.clickAllProcess();
        viewTicketDetails = new ViewTicketDetails(driver);
        String ticketID = getTicketId(menus, channel);
        checkViewTicketInfo(language, category, fileType);

        System.out.println("Ticket ID: " + ticketID);
        changeTicketStatus();
        checkCRMEmailConversation(language, category, fileType);
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

    private void checkCRMEmailConversation(String language, String category,
            String fileType) {

        System.out.println("\n===========================================");
        System.out.println("Checking Email Conversation");
        System.out.println("===========================================\n");
        evaluateCondition("Email Service",
            emailConversation.getServiceDetails(), service);
        if (fileType.equals(fileTypes[0])) {

            evaluateCondition("No Of Pages", viewTicketDetails
                .getRunTimeTicketFieldValues("No Of Pages"), mp);
            evaluateCondition("Formatting", viewTicketDetails
                .getRunTimeTicketFieldValues("Formatting"), formatting);
        } else {

            evaluateCondition("Minutes",
                emailConversation.getTicketFieldValues("Minutes"), mp);
        }

        evaluateCondition("Type Of Service",
            emailConversation.getTicketFieldValues("Type Of Service"), fileType);
        evaluateCondition("Language",
            emailConversation.getTicketFieldValues("Language"), language);
        evaluateCondition("Category",
            emailConversation.getTicketFieldValues("Category"), category);
        System.out.println("===========================================");
        emailConversation.clickNoActionButton();
    }

    private void checkViewTicketInfo(String language, String category, String
        fileType) {

        System.out.println("\n===========================================");
        System.out.println("Checking View Ticket Details");
        System.out.println("===========================================\n");

        evaluateCondition("Email", viewTicketDetails
            .getRunTimeTicketFieldValues("Email"), mailId);
        evaluateCondition("Websites", url,
            viewTicketDetails.getRunTimeTicketFieldValues("Websites"));
        evaluateCondition("Channel", viewTicketDetails
            .getRunTimeTicketFieldValues("Channel"), channel);

        evaluateCondition("Language", viewTicketDetails
            .getRunTimeTicketFieldValues("Language"), language);
        evaluateCondition("Category", viewTicketDetails
            .getRunTimeTicketFieldValues("Category"), category);
        if (fileType.equals(fileTypes[0])) {

            evaluateCondition("Pages", viewTicketDetails
                .getRunTimeTicketFieldValues("Pages"), mp);
            evaluateCondition("Formatting", viewTicketDetails
                .getRunTimeTicketFieldValues("Formatting"), formatting);
        } else {

            evaluateCondition("Minutes", viewTicketDetails
                .getRunTimeTicketFieldValues("Minutes"), mp);
        }
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

    private void testScenario(String language, String category, String
        fileType) throws
        IOException {

        raiseTicket(language, category, fileType);
        getCRMCreadential();
        checkCRM(language, category, fileType);
    }
}
