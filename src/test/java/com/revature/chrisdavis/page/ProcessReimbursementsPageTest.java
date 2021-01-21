package com.revature.chrisdavis.page;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ProcessReimbursementsPageTest {
	private ProcessReimbursementsPage page;
	private static WebDriver driver;
	
	
	@BeforeClass public static void classInitialization(){
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);		
	}
	
	@AfterClass public static void classDismantle() throws Exception{
		driver.quit();
	}
	
	@Before public void testSetup(){
		this.page = new ProcessReimbursementsPage(driver);
	}	
	
	
	@Test public void testDenyPendingRequest() {
		int pendingTotal = page.goToProcessReimbursements();
		WebDriverWait wait = new WebDriverWait(driver, 2);
		page.filterStatusToPending();
		wait.until(ExpectedConditions.invisibilityOf((driver.findElement(By.className("btn-success")))));
		page.viewPendingRequest();
		wait.until(ExpectedConditions.visibilityOf((driver.findElement(By.id("resolveRequest")))));
		page.denyPendingRequest();
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.className("close"))));
		page.navigateTo("http://localhost:9009/html/welcome.html");
		pendingTotal -= page.goToProcessReimbursements();
		assertTrue(pendingTotal == 1);
	}
}


