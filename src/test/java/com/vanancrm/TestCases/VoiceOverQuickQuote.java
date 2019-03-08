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
public class VoiceOverQuickQuote extends TestBase {

    private WebDriver driver;

    private String phoneNumber = "1-888-535-5668";

    private String[] languages = {"English", "Apache"};
    private String[] ageGroups = {"Under 14", "26-35"};
    private String[] genders = {"Male", "Female"};
    private String mailId = "automation.vananservices@gmail.com";
    private String service = "Voice Over";
    private String channel = "Quick Quote";
    private String wordCount = "180";
    private String comments = "Automation Testing";
    private String status = "Yes";
    private String voice = "1";

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
        testScenario(languages[0], ageGroups[1], genders[1]);
        testScenario(languages[1], ageGroups[0], genders[0]);
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

        screenshot(driver, "VoiceOverQuickQuote");
        driver.quit();
    }

    private void raiseTicket(String language, String ageGroup, String gender) {

        url = System.getProperty("website");
        driver.get(url);
        QuickQuote quickQuote = new QuickQuote(driver);
        System.out.print("Mail id : " + mailId + ",\t");
        quickQuote.enterEmail(mailId);
        System.out.print("Phone Number : " + phoneNumber + ",\t");
        quickQuote.enterPhoneNumber(phoneNumber);
        System.out.print("Language : " + language + ",\t");
        quickQuote.selectLanguageFrom(language);
        System.out.print("Age Group : " + ageGroup);
        quickQuote.selectAgeGroup(ageGroup);
        waitForProcessCompletion(5);
        quickQuote.selectGender(gender);
        quickQuote.selectBroadcast();
        quickQuote.selectTranslation();
        quickQuote.clickGetQuote();
        waitForProcessCompletion(10);
        quickQuote.enterWordCount(wordCount);
        quickQuote.enterComments(comments);
        quickQuote.clickPopupSubmit();
        waitForProcessCompletion(10);
    }

    private void checkCRM(String language, String ageGroup, String gender) {

        driver.get("https://secure-dt.com/crm/user/login");
        Login login = new Login(driver);
        DashBoardPage dashBoardPage = login.signIn(username, password);
        menus = dashBoardPage.clickAllProcess();
        viewTicketDetails = new ViewTicketDetails(driver);
        String ticketID = getTicketId(menus, channel);
        checkViewTicketInfo(language, ageGroup, gender);

        System.out.println("Ticket ID: " + ticketID);
        changeTicketStatus();
        checkCRMEmailConversation(language, ageGroup, gender);
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

    private void checkCRMEmailConversation(String language, String ageGroup,
            String gender) {

        System.out.println("\n===========================================");
        System.out.println("Checking Email Conversation");
        System.out.println("===========================================\n");
        evaluateCondition("Email Service",
            emailConversation.getServiceDetails(), service);
        evaluateCondition("Phone Number",
            emailConversation.getTicketFieldValues("Phone Number"), phoneNumber);
        evaluateCondition("Words",
            emailConversation.getTicketFieldValues("Words"), wordCount);
        evaluateCondition("Broadcasts",
            emailConversation.getTicketFieldValues("Broadcasts"), status);
        evaluateCondition("Need Translation",
            emailConversation.getTicketFieldValues("Need Translation"), status);
        evaluateCondition("Target Language",
            emailConversation.getTicketFieldValues("Target "
                + "Language"), language);
        evaluateCondition("Gender And Age",
            emailConversation.getTicketFieldValues("Gender And Age"), gender
            + ":" + ageGroup);
        evaluateCondition("No Of Voices",
            emailConversation.getTicketFieldValues("No Of Voices"), voice);
        evaluateCondition("Comment",
            emailConversation.getTicketFieldValues("Comment"), comments);
        System.out.println("===========================================");
        emailConversation.clickNoActionButton();
    }

    private void checkViewTicketInfo(String language, String ageGroup,
            String gender) {

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
        evaluateCondition("Broadcasts", viewTicketDetails
            .getRunTimeTicketFieldValues("Broadcasts"), status);
        evaluateCondition("Need Translation", viewTicketDetails
            .getRunTimeTicketFieldValues("Need Translation"), status);
        evaluateCondition("Language", viewTicketDetails
            .getRunTimeTicketFieldValues("Language"), language);

        evaluateCondition("Word Count", viewTicketDetails
            .getRunTimeTicketFieldValues("Word Count"), wordCount);
        evaluateCondition("Voice Details ", viewTicketDetails
            .getVoiceDetails(), gender + ":" + ageGroup);
        evaluateCondition("Voices", viewTicketDetails
            .getRunTimeTicketFieldValues("Voices"), voice);
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

    private void testScenario(String language, String ageGroup, String gender)
        throws IOException {

        raiseTicket(language, ageGroup, gender);
        getCRMCreadential();
        checkCRM(language, ageGroup, gender);
    }
}
