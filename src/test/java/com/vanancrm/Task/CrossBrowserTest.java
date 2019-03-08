package com.vanancrm.Task;

import org.testng.annotations.Test;

import com.vanancrm.Common.TestBase;
import com.vanancrm.PageObjects.MainPages.Translation;
import com.vanancrm.Task.BrowserTesting.MyRunnable;

import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertTrue;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;

public class CrossBrowserTest extends TestBase {

	private static final int MYTHREADS = 2;
	private static WebDriver driver;
	private static String message = "";
	private static Date date;
	private static DateFormat dateFormat;
	private static ExecutorService executor;

	@Test
	public void f() {

		executor = Executors.newFixedThreadPool(MYTHREADS);
		for (int i = 0; i< setData.getUrls().size(); i++) {
			Runnable worker = new MyRunnable("Chrome");
			executor.execute(worker);	
		}
		
		executor.shutdown();
		// Wait until all threads are finish
		while (!executor.isTerminated()) {

		}
		System.out.println("\nFinished all threads");
	}

	private static void setChromeBrowserProperty() {
		
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	private static void setFirefoxBrowserProperty() {
		System.setProperty("webdriver.gecko.driver", "E:\\geckodriver.exe");
		driver = new FirefoxDriver();
	}

	private static String test(int i) throws IOException {

		driver.get(setData.getUrls().get(i));
		Translation translation = new Translation(driver);

		if (translation.checkElementVisible(setData.getLocators().get(i), setData.getUIElements().get(i))) {

			message = " Running";
		}
		return message;
	}

	public static class MyRunnable implements Runnable {
		
		String browserName;
		public MyRunnable(String browserName) {
			this.browserName = browserName;
		}

		@Override
		public void run() {

			dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			List<Integer> failureCount = new ArrayList<>();
			List<String> failedUrls = new ArrayList<>();
			int count = 0;

			System.out.println("S.No \t Site Name \t Status");
			for (int i = 0; i < setData.getUrls().size(); i++) {

				try {

					message = test(i);
					
				} catch (NoSuchElementException e) {
					message = " Down";
					count = count + 1;
					failureCount.add(count);
					failedUrls.add(setData.getUrls().get(i));
					continue;
				} catch (IOException e) {

				}
				System.out.println("Tested on " + browserName + " : Site URL=> " 
				        + setData.getUrls().get(i) + " Status : " + message);
			}

			if ((failureCount.size()) > 0) {
				System.out.println("===========================================");
				System.out.println("Below Sites got down");
				System.out.println("===========================================");
				for (int i = 0; i < failureCount.size(); i++) {
					System.out.print(i + "===>");
					System.out.print(failedUrls.get(i) + " is Down");
					System.out.println();
				}
				assertTrue(false, "Sites Got Down");
			}
		}
	}

	@BeforeClass
	public void beforeClass() throws IOException {
		readAllSiteUrl();
	}

	@AfterClass
	public void afterClass() {
	}

}
