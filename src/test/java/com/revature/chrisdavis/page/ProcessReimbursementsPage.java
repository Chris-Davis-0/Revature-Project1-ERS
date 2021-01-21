package com.revature.chrisdavis.page;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProcessReimbursementsPage {
	private WebDriver driver;
	private WebElement resolveRequest;
	private WebElement filterSelector;
	private WebElement statusPendingFilter;
	
	private UserLoginPage loginPage;
	
	
	public ProcessReimbursementsPage(WebDriver driver) {
		this.driver = driver;
		this.driver.get("http://localhost:9009/html/login.html");
		driver.manage().window().maximize();
		loginToPage("$.01", "apennyaday");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);	
	}
	
	public void loginToPage(String username, String password) {
		loginPage = new UserLoginPage(driver);
		loginPage.setUserName(username);
		loginPage.setPassword(password);
		loginPage.submitLogin();		
	}
	
	public int goToProcessReimbursements() {
		driver.findElement(By.id("reimburseDropdown")).click();
		driver.findElement(By.id("dropdownProcessReimbursements")).click();
		this.filterSelector = driver.findElement(By.id("filterStatus"));
		this.statusPendingFilter = driver.findElement(By.id("statusPending"));	
		return driver.findElements(By.className("btn-warning")).size();
	}
	
	public void filterStatusToPending() {
		this.filterSelector.click();
		this.statusPendingFilter.click();
		this.filterSelector.click();
	}	
	
	public void viewPendingRequest() {
		driver.findElements(By.className("btn-warning")).get(0).click();
		this.resolveRequest = driver.findElement(By.className("btn-primary"));	
	}
	
	public void denyPendingRequest() {
		this.resolveRequest.click();
	}
	
	public void navigateTo(String url) {
		this.driver.get(url);
	}
}
