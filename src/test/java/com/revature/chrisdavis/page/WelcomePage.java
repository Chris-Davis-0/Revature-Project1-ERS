package com.revature.chrisdavis.page;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WelcomePage {
	private WebDriver driver;
	private WebElement reimbursementDropdown;
	private WebElement dropdownProcessReimbursements;
	private WebElement expenseTypeTravel;
	private WebElement expenseTypeFood;
	private WebElement expenseTypeLodging;
	private WebElement expenseTypeEquipment;
	private WebElement expenseTypeLegal;
	private WebElement expenseTypeOther;
	
	
	private UserLoginPage loginPage;
	
	
	public WelcomePage(WebDriver driver) {
		this.driver = driver;
		this.driver.get("http://localhost:9009/html/login.html");
		driver.manage().window().maximize();
		loginToPage("007", "shakennotstirred62");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);	
		this.reimbursementDropdown = driver.findElement(By.id("reimburseDropdown"));
		this.dropdownProcessReimbursements = driver.findElement(By.id("dropdownProcessReimbursements"));
		this.expenseTypeTravel = driver.findElement(By.id("typeTravel"));
		this.expenseTypeFood = driver.findElement(By.id("typeFood"));
		this.expenseTypeLodging = driver.findElement(By.id("typeLodging"));
		this.expenseTypeEquipment = driver.findElement(By.id("typeEquipment"));
		this.expenseTypeLegal = driver.findElement(By.id("typeLegal"));
		this.expenseTypeOther = driver.findElement(By.id("typeOther"));
	}
	
	public void loginToPage(String username, String password) {
		loginPage = new UserLoginPage(driver);
		loginPage.setUserName(username);
		loginPage.setPassword(password);
		loginPage.submitLogin();		
	}
	
	public void goToMyReimbursements() {
		this.reimbursementDropdown.click();
		this.driver.findElement(By.id("dropdownMyReimbursements")).click();
	}
	
	public void goToProcessReimbursements() {
		this.reimbursementDropdown.click();
		this.dropdownProcessReimbursements.click();
	}
	
	public void goToRequestReimbursement() {
		this.reimbursementDropdown.click();
		this.driver.findElement(By.id("dropdownRequestReimbursement")).click();
	}
		
	public void createRequestReimbursement() {
		this.driver.findElement(By.id("createRequest")).click();
	}
	
	public void selectRequestReimbursementType(String type) {
		this.driver.findElement(By.id("expensetype")).click();
		switch(type) {
			case "travel":
				this.expenseTypeTravel.click();
				break;
			case "food":
				this.expenseTypeFood.click();
				break;
			case "lodging":
				this.expenseTypeLodging.click();
				break;
			case "equipment":
				this.expenseTypeEquipment.click();
				break;
			case "legal":
				this.expenseTypeLegal.click();
				break;
			case "other":
				this.expenseTypeOther.click();
				break;
		}
	}
	
	public void setExpenseAmount(String amount){
		WebElement requestExpenseAmount = driver.findElement(By.id("expenseamount"));
		requestExpenseAmount.click();
		requestExpenseAmount.sendKeys(amount);
	}
	
	public void setExpenseDescription(String description) {
		WebElement requestExpenseDescription = driver.findElement(By.id("requestText"));
		requestExpenseDescription.click();
		requestExpenseDescription.sendKeys(description);
	}
	
	public void navigateTo(String url) {
		this.driver.get(url);
	}
}
