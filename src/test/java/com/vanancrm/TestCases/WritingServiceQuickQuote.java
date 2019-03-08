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
 * Author - Manikavasagam (manikavasagam@vananservices.com)
 */
public class WritingServiceQuickQuote extends TestBase {

    private WebDriver driver;

    private String phoneNumber = "1-888-535-5668";

    private String[] academicLevel = {"Undergraduate", "Phd"};
    private String[] paperTypes = {"Dissertation abstract", "Problem solving"};
    private String mailId = "automation.vananservices@gmail.com";
    private String service = "Writing";
    private String channel = "Quick Quote";
    private String pages = "180";
    private String comments = "Automation Testing";

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
        testScenario(academicLevel[0], paperTypes[1]);
        testScenario(academicLevel[1], paperTypes[0]);
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

        screenshot(driver, "WritingServiceQuickQuote");
        driver.quit();
    }

    private void raiseTicket(String academicLevel, String paperType) {

        url = "https://vananservices.com/Writing-Services.php";
            //System.getProperty("website");
        driver.get(url);
        QuickQuote quickQuote = new QuickQuote(driver);
        System.out.print("Mail id : " + mailId + ",\t");
        quickQuote.enterEmail(mailId);
        System.out.print("Phone Number : " + phoneNumber + ",\t");
        quickQuote.enterPhoneNumber(phoneNumber);
        System.out.print("Academic Level : " + academicLevel + ",\t");
        quickQuote.selectAcademicLevel(academicLevel);
        System.out.print("Paper Type : " + paperType);
        quickQuote.selectPaperType(paperType);
        quickQuote.clickGetQuote();
        waitForProcessCompletion(10);
        quickQuote.enterPopUpPages(pages);
        quickQuote.enterComments(comments);
        quickQuote.clickPopupSubmit();
        waitForProcessCompletion(10);
    }

    private void checkCRM(String academicLevel, String paperType) {

        driver.get("https://secure-dt.com/crm/user/login");
        Login login = new Login(driver);
        DashBoardPage dashBoardPage = login.signIn(username, password);
        menus = dashBoardPage.clickAllProcess();
        viewTicketDetails = new ViewTicketDetails(driver);
        String ticketID = getTicketId(menus, channel);
        checkViewTicketInfo(academicLevel, paperType);

        System.out.println("Ticket ID: " + ticketID);
        changeTicketStatus();
        checkCRMEmailConversation(academicLevel, paperType);
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

    private void checkCRMEmailConversation(String academicLevel, String paperType) {

        System.out.println("\n===========================================");
        System.out.println("Checking Email Conversation");
        System.out.println("===========================================\n");
        evaluateCondition("Email Service",
            emailConversation.getServiceDetails(), service);
        evaluateCondition("Phone Number",
            emailConversation.getTicketFieldValues("Phone Number"), phoneNumber);
        evaluateCondition("No Of Pages",
            emailConversation.getTicketFieldValues("No Of Pages"), pages);
        evaluateCondition("Academic Level",
            emailConversation.getTicketFieldValues("Academic Level"), academicLevel);
        evaluateCondition("Type Of Paper",
            emailConversation.getTicketFieldValues("Type Of Paper"), paperType);
        evaluateCondition("Comment",
            emailConversation.getTicketFieldValues("Comment"), comments);
        System.out.println("===========================================");
        emailConversation.clickNoActionButton();
    }

    private void checkViewTicketInfo(String academicLevel, String paperType) {

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
        evaluateCondition("Academic Level", viewTicketDetails
            .getRunTimeTicketFieldValues("Academic Level"), academicLevel);

        evaluateCondition("No Of Pages", viewTicketDetails
            .getRunTimeTicketFieldValues("No Of Pages"), pages);
        evaluateCondition("Type Of Paper ", viewTicketDetails
            .getRunTimeTicketFieldValues("Type Of Paper"), paperType);
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

    private void testScenario(String academicLevel, String paperType)
        throws IOException {

        raiseTicket(academicLevel, paperType);
        getCRMCreadential();
        checkCRM(academicLevel, paperType);
    }
}
