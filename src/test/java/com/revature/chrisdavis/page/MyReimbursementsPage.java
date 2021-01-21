package com.revature.chrisdavis.page;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MyReimbursementsPage {
	private WebDriver driver;
	private WebElement filterSelector;
	private UserLoginPage loginPage;
	
	
	public MyReimbursementsPage(WebDriver driver) {
		this.driver = driver;
		this.driver.get("http://localhost:9009/html/login.html");
		driver.manage().window().maximize();
		loginToPage("007", "shakennotstirred62");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);	
	}
	
	public void loginToPage(String username, String password) {
		loginPage = new UserLoginPage(driver);
		loginPage.setUserName(username);
		loginPage.setPassword(password);
		loginPage.submitLogin();		
	}
	
	public void goToMyReimbursements() {
		this.driver.findElement(By.id("reimburseDropdown")).click();
		this.driver.findElement(By.id("dropdownMyReimbursements")).click();
		this.filterSelector = driver.findElement(By.id("filterStatus"));
	}
	
	public void toggleReimbursementsDropdown() {
		driver.findElement(By.id("reimburseDropdown")).click();
	}
	
	public void toggleFirstRow() {
		driver.findElement(By.cssSelector("#insertTable > tr")).click();
	}
	
	public void changeFilter() {
		this.filterSelector.click();
		this.driver.findElement(By.id("filterApproved")).click();
		this.filterSelector.click();
	}

	public void navigateTo(String url) {
		this.driver.get(url);
	}
}
