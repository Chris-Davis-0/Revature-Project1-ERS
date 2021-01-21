package com.revature.chrisdavis.page;

import static org.junit.Assert.assertFalse;
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


public class MyReimbursementsPageTest {
	private MyReimbursementsPage page;
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
		this.page = new MyReimbursementsPage(driver);
	}	
	
	@Test public void testToggleReimbursementRow() {
		page.goToMyReimbursements();
		page.toggleFirstRow();
		WebDriverWait wait = new WebDriverWait(driver, 1);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.tagName("p"))));
		assertTrue(driver.findElement(By.tagName("p")).isDisplayed());
	}
	
	@Test public void testFilterByStatus() {
		page.goToMyReimbursements();
		page.changeFilter();
		WebDriverWait wait = new WebDriverWait(driver, 1);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-success")));
		assertFalse(driver.findElements(By.className("btn-warning")).size() == 0);
	}	
}
