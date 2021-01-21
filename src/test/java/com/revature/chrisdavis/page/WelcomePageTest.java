package com.revature.chrisdavis.page;

import static org.junit.Assert.assertEquals;
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


public class WelcomePageTest {
	private WelcomePage page;
	private static WebDriver driver;
	
	
	@BeforeClass public static void classInitialization(){
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);		
	}
	
	@AfterClass public static void classDismantle() throws Exception{
		driver.quit();
	}
	
	@Before public void testSetup(){
		this.page = new WelcomePage(driver);
	}	
	
	@Test 
	public void testNavToMyReimbursements() {
		page.goToMyReimbursements();
		WebDriverWait wait = new WebDriverWait(driver, 1);
		wait.until(ExpectedConditions.urlMatches("/reimbursements.html"));
		assertEquals("http://localhost:9009/html/reimbursements.html", driver.getCurrentUrl());
	}
	
	@Test
	public void testNavToProcessReimbursements() {
		page.goToProcessReimbursements();
		WebDriverWait wait = new WebDriverWait(driver, 1);
		wait.until(ExpectedConditions.urlMatches("/processreimbursements.html"));
		assertEquals("http://localhost:9009/html/processreimbursements.html", driver.getCurrentUrl());
	}
		
	@Test
	public void testCreateRequest() {
		page.goToRequestReimbursement();
		page.setExpenseAmount("222.22");
		page.setExpenseDescription("Automated martini bill");
		page.selectRequestReimbursementType("food");
		page.createRequestReimbursement();
		WebDriverWait wait = new WebDriverWait(driver, 1);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("closeModal"))));
		assertTrue(driver.findElement(By.id("closeModal")).isDisplayed());
	}
}
