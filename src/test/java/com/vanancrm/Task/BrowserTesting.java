package com.vanancrm.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
 
public class BrowserTesting {
	
	private static final int MYTHREADS = 2;
	private static WebDriver driver;
	public static void main(String args[]) throws Exception {
		ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);
		String[] hostList = { "http://yahoo.com", "http://google.com"};
 
		for (int i = 0; i < hostList.length; i++) {
 
			String url = hostList[i];
			setBrowserProperty(i);
			Runnable worker = new MyRunnable(url);
			executor.execute(worker);
		}
		executor.shutdown();
		// Wait until all threads are finish
		while (!executor.isTerminated()) {
 
		}
		System.out.println("\nFinished all threads");
	}
	
	private static void setBrowserProperty(int browserType) {
		switch(browserType) {
		case 0:
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		case 1: 
			  System.setProperty("webdriver.gecko.driver", "E:\\geckodriver.exe");
			  driver = new FirefoxDriver();
			  break;
		}
	}
 
	public static class MyRunnable implements Runnable {
		private final String url;
 
		MyRunnable(String url) {
			this.url = url;
		}
 
		@Override
		public void run() {
 
			try {
				driver.get(url);
			} catch (Exception e) {
			}
		
		}
	}
}