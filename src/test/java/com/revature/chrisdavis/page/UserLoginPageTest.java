package com.revature.chrisdavis.page;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserLoginPageTest {
	private UserLoginPage page;
	private static WebDriver driver;
	
	
	@BeforeClass public static void classInitialization() throws Exception{
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);		
	}
	
	@AfterClass public static void classDismantle() throws Exception{
		driver.quit();
	}
	
	@Before public void testSetup() throws Exception{
		this.page = new UserLoginPage(driver);
		//driver.get("http://localhost:9009/html/login.html");
	}
	
	@After public void testDismantle() throws Exception{
		//driver.get("http://localhost:9009/html/login.html");
	}
	
	
	@Test public void testSuccessfulLogin() {
		page.setUserName("007");
		page.setPassword("shakennotstirred62");
		page.submitLogin();
		WebDriverWait wait = new WebDriverWait(driver, 3);
		wait.until(ExpectedConditions.urlMatches("/welcome.html"));
		assertEquals("http://localhost:9009/html/welcome.html", driver.getCurrentUrl());
	}
	
	@Test public void testFailLogin() {
		page.setUserName("000");
		page.setPassword("secretagentman");
		page.submitLogin();
		WebDriverWait wait = new WebDriverWait(driver, 3);
		wait.until(ExpectedConditions.urlMatches("/loginretry.html"));
		assertEquals("http://localhost:9009/html/loginretry.html", driver.getCurrentUrl());
	}
	
	@Test public void testFailLoginDifferentUsersPassword() {
		page.setUserName("007");
		page.setPassword("suckitTrebek");
		page.submitLogin();
		WebDriverWait wait = new WebDriverWait(driver, 3);
		wait.until(ExpectedConditions.urlMatches("/loginretry.html"));
		assertEquals("http://localhost:9009/html/loginretry.html", driver.getCurrentUrl());
	}
	
	@Test public void testFailToBypassLogin() {
		page.navigateTo("http://localhost:9009/html/welcome.html");
		page.logout();
		page.navigateTo("http://localhost:9009/html/welcome.html");
		WebDriverWait wait = new WebDriverWait(driver, 3);
		wait.until(ExpectedConditions.urlMatches("/loginretry.html"));
		assertEquals("http://localhost:9009/html/loginretry.html", driver.getCurrentUrl());
	}
		
}
