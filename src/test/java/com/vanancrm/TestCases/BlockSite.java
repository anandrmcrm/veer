package com.vanancrm.TestCases;

import com.vanancrm.Common.TestBase;
import com.vanancrm.PageObjects.MainPages.Translation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class BlockSite extends TestBase {
    private WebDriver driver;

    @Test
    public void testAllAvailableTest() {
        Translation translation = new Translation(driver);
        for (int i = 0; i <= 60; i++) {
            System.out.println("i = " + i);
            driver.get("https://typingglobal.com/Typing-Quote.php");
            translation.pageCount("10");
            driver.navigate().refresh();
        }
    }

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

}
