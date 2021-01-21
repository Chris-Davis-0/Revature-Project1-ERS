package com.revature.chrisdavis.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UserLoginPage {
	private WebDriver driver;
	private WebElement userNameField;
	private WebElement passwordField;
	private WebElement submitLogin;
	
	public UserLoginPage(WebDriver driver) {
		this.driver = driver;
		this.driver.get("http://localhost:9009/html/login.html");
		driver.manage().window().maximize();
		this.userNameField = driver.findElement(By.name("inputUserName"));
		this.passwordField = driver.findElement(By.name("inputUserPassword"));
		this.submitLogin = driver.findElement(By.id("submitLogin"));
	}
	
	public void setUserName(String username) {
		this.userNameField.clear();
		this.userNameField.sendKeys(username);
	}
	
	public void setPassword(String password) {
		this.passwordField.clear();
		this.passwordField.sendKeys(password);
	}
	
	public void submitLogin() {
		this.submitLogin.click();
	}
	
	public void navigateTo(String url) {
		this.driver.get(url);
	}
	
	public void logout() {
		WebElement profile = driver.findElement(By.id("profileDropdown"));
		WebElement logout = driver.findElement(By.id("logoutSession"));
		profile.click();
		logout.click();
	}
}
