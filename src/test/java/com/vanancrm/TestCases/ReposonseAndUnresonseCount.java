package com.vanancrm.TestCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.ui.Select;

import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.vanan.CRM.PageObjects.MainPages.DashBoardPage;

import com.vanan.CRM.PageObjects.WholeSitePages.Login;
import com.vanan.CRM.PageObjects.WholeSitePages.Menus;
import com.vanan.CRM.PageObjects.WholeSitePages.ReadTableData;

import com.vanancrm.Common.TestBase;


/**
 *
 * @author - Manikavasagam (manikavasagam@vananservices.com)
 *
 */
public class ReposonseAndUnresonseCount extends TestBase {

    private static String username = "";
    private static String password = "";
    private int size = 0;

    private Actions builder;
    private Action mouseOverHome;
    private DashBoardPage dashBoardPage;
    private Menus menus;
    private ReadTableData readTableData;
    private WebDriver driver;
    private WebElement element;

    private static List<String> menu = new ArrayList<>();
    private static List<String> unrespond = new ArrayList<>();
    private static List<String> respond = new ArrayList<>();

    @Test
    public void testStep() throws InterruptedException, IOException {

        for (int i = 0; i < menu.size(); i++) {

            readTableData = new ReadTableData(driver);
            System.out.println((i + 1) + ". Menu : " + menu.get(i));
            if (i <= size) {
                if(menu.get(i).equals("New")) {

                } else {
                    if (i >= 38) {
                        selectMenuUsingXpath(menu.get(i));
                        waitForProcessCompletion(10);
                    } else {
                        selectMenu(menu.get(i));
                        waitForProcessCompletion(10);
                    }
                 checkScenario(respond.get(i), unrespond.get(i));
                }
            } else {
                if (i == 24) {
                    driver.navigate().refresh();
                    waitForProcessCompletion(5);
                    menus.clickAllocatorDashboard();
                    if(System.getProperty("myQueue")
                        .equalsIgnoreCase("YES")) {
                        dashBoardPage.enableMyQueue();
                    } else if(System.getProperty("myQueue").equalsIgnoreCase
                        ("NO")){
                        dashBoardPage.disableMyQueue();
                    }

                    driver.navigate().refresh();
                    waitForProcessCompletion(5);
                    menus.clickAllocatorDashboard();
                    waitForProcessCompletion(5);
                }

                selectMenu(menu.get(i));
                waitForProcessCompletion(10);
                checkOrderListScenario(respond.get(i), unrespond.get(i));
            }
            waitForProcessCompletion(10);
        }
    }

    @BeforeClass
    public void beforeClass() throws IOException, InterruptedException {

        System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        fullScreen(driver);
        file = new File("src/test/resources/RespondAndUnrespondTicket.xls");
        fileInput = new FileInputStream(file);
        workbook = new HSSFWorkbook(fileInput);
        getCRMCreadential();
        builder = new Actions(driver);
        driver.get(System.getProperty("website"));
        Login login = new Login(driver);
        dashBoardPage = login.signIn(username, password);
        if (username.equals("admin")||username.equals("developer")) {

            readSheet1();
            menus = dashBoardPage.clickAllProcess();
            size = 39;
        } else if (username.equals("allocator1")||username.equals
            ("allocator-test")) {

            readSheet2();
            menus = new Menus(driver);
            readTableData = new ReadTableData(driver);
            System.out.print("Checking TAT Today : \t");
            checkElementValue("header_today_cnt");
            System.out.print("Checking TAT Overdue : \t");
            checkElementValue("header_overdue_cnt");
            size = 23;
        }
    }

    private void checkScenario(String respond, String unrespond) throws
        IOException, InterruptedException {

        checkCount("Reposond count is ", respond, true);

        clickTicket(unrespond);
        checkCount("Unreposond count is ", unrespond, false);
        System.out.println("\n");
    }

    private void checkOrderListScenario(String respond, String unrespond) throws
        IOException, InterruptedException {

        checkCountOrderList("Reposond count is ", respond, true);

        if (!unrespond.equals("#")) {
            clickTicket(unrespond);
            checkCountOrderList("Unreposond count is ", unrespond, false);
        }
        System.out.println("\n");
    }

    private static void getCRMCreadential() throws IOException {

        username = System.getProperty("username");
        password = System.getProperty("password");
    }

    private static void readSheet1() {

        sheet = workbook.getSheetAt(0);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            cell = sheet.getRow(i).getCell(1);
            menu.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(2);
            unrespond.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(3);
            respond.add(cell.getStringCellValue());
        }

    }

    private static void readSheet2() {

        sheet = workbook.getSheetAt(1);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            cell = sheet.getRow(i).getCell(1);
            menu.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(2);
            unrespond.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(3);
            respond.add(cell.getStringCellValue());
        }

    }

    private void selectMenu(String elementName) {
        element = driver.findElement(By.linkText(elementName));
        mouseOverHome = builder.moveToElement(element).build();
        mouseOverHome.perform();
        menus.clickElement(element);
    }

    private void selectMenuUsingXpath(String elementName) {
        element = driver.findElement(By.xpath("//a[contains(text(), " +
            "'" + elementName + "')]"));
        mouseOverHome = builder.moveToElement(element).build();
        mouseOverHome.perform();
        menus.clickElement(element);
    }

    private int getCount(String elementName) {
        int value = 0;
        try {
            element = driver.findElement(By.id(elementName));
            if (menus.isElementDisplayed(element)) {
                value = Integer.parseInt(menus.getElementValues(element));
            } else {
                value = 0;
            }
        } catch (NoSuchElementException ex) {

        }
        return value;
    }

    private boolean isRespondDisplayed(String elementName) {

        element = driver.findElement(By.id(elementName));
        return menus.isElementDisplayed(element);

    }

    private void checkElementValue(String elements) throws IOException,
        InterruptedException {
        int value = 0;
        try {
            element = driver.findElement(By.id(elements));
            if (element.isDisplayed()) {
                value = Integer.parseInt(element
                    .getText());
                element.click();
                if (value == 0) {

                    System.out.print("\t Count is Zero and No data available in" +
                        " table\n");
                } else {

                    double respondCount = value;
                    int size = (int) (Math.ceil(respondCount / 50));
                    int menuOption = (int) (Math.ceil(respondCount / 50)) * 50;
                    waitForProcessCompletion(5);
                    SelectDisplayOrder(String.valueOf((size - 1) * 50));
                    waitForProcessCompletion(5);
                    if (value >= 50) {
                        if (value == readTableData.getLastTicketNumberFromTAT()) {
                            System.out.print(value + "\t Pass\n");
                        } else {
                            System.out.print(value + "\t Fail\n");
                        }

                    } else {
                        if (value == readTableData.getLastTicketNumberFromTAT()) {
                            System.out.print("\t Pass\n");
                        } else {
                            System.out.print("\t Fail\n");
                        }
                    }
                }
                waitForProcessCompletion(10);
            }
        } catch (NoSuchElementException ex) {

        }
    }

    private void checkCount(String message, String element, boolean
        respondStatus) throws IOException, InterruptedException {
        if (isRespondDisplayed(element)) {
            System.out.print(message + " : " + getCount(element));
            checkStatus(element, true);
        } else {
            System.out.println(message + " zero [" + getCount(element)
                + "]");
            if (respondStatus) {
                readTableData.isNoDataMessageDisplayed();
            }

        }
    }

    private void checkCountOrderList(String message, String element, boolean
        respondStatus) throws IOException, InterruptedException {
        if (isRespondDisplayed(element)) {
            System.out.print(message + " : " + getCount(element));
            checkStatusOrderList(element, true);
        } else {
            System.out.println(message + " zero [" + getCount(element)
                + "]");
            if (respondStatus) {
                readTableData.isNoDataMessageDisplayedOrderList();
            }

        }
    }


    private void checkTicketStaus(String element) {

        double respondCount = getCount(element);
        int size = (int) (Math.ceil(respondCount / 50));
        int menuOption = (int) (Math.ceil(respondCount / 50)) * 50;

        SelectDisplayOrder(String.valueOf((size - 1) * 50));
        waitForProcessCompletion(5);
    }

    private void SelectDisplayOrder(String value) {

        Select dropdown = new Select(driver.findElement(By.id("disp_range")));
        dropdown.selectByValue(value);
    }

    private void checkStatus(String element, boolean respondStatus) throws
        IOException, InterruptedException {
        if (respondStatus) {

            checkTicketStaus(element);
        } else {

            clickTicket(element);
        }

        int ecount = getCount(element);
        waitForProcessCompletion(5);
        int acount = readTableData.getLastTicketNumber();
        if (ecount >= 50) {
            if (ecount == acount) {

                System.out.print("\t Pass\n");
            } else {

                System.out.print("\t Fail\n");
            }

        } else {
            if (ecount == acount) {

                System.out.print("\t Pass\n");
            } else {

                System.out.print("\t Fail\n");
            }

        }
    }

    private void checkStatusOrderList(String element, boolean respondStatus)
        throws IOException, InterruptedException {
        if (respondStatus) {

            checkTicketStaus(element);
        } else {

            clickTicket(element);
        }

        int ecount = getCount(element);
        waitForProcessCompletion(5);
        int acount = readTableData.getLastTicketNumberFromTAT();
        if (ecount >= 50) {
            if (ecount == acount) {

                System.out.print("\t Pass\n");
            } else {

                System.out.print("\t Fail\n");
            }

        } else {
            if (ecount == acount) {

                System.out.print("\t Pass\n");
            } else {

                System.out.print("\t Fail\n");
            }

        }
    }

    private void clickTicket(String element) throws InterruptedException {
        for (int i = 0; i <= 5; i++) {
            try {
                WebElement resp = driver.findElement(By.id(element));
                if (resp.isDisplayed()) {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("$('#" + element + "').click();");
                    TimeUnit.SECONDS.sleep(10);
                    break;
                } else {

                    continue;

                }
            } catch (NoSuchElementException ex) {

                continue;
            }
        }

    }

    @AfterClass
    public void afterClass() {

        //driver.quit();
    }
}
