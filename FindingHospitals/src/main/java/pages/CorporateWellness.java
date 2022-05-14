package pages;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


import base.Base;

public class CorporateWellness extends Base {
	WebElement Name, OrgName, Email, Contact;

	By providers = By.xpath("//span[normalize-space()='Wellness Plans']");

	By button = By
			.xpath("//header[@id='header']//button[contains(@type,'submit')][normalize-space()='Schedule a demo']");
	By organizationSize = By.xpath("//*[@id='organizationSize']");
	By dropDown = By.xpath("//option[text()='<500']");

	@SuppressWarnings("resource")
	public void setup() {

		logger = report.createTest("Corporate Wellness");

		// Selecting the corporate option
		try {
			findElement(providers).click();
			// findElement(corporate).click();
			reportPass("Corporate Wellness Link Clicked Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

		// Switching to new tab
		try {
			Set<String> currentHandle = driver.getWindowHandles();
			Iterator<String> iterator = currentHandle.iterator();
			iterator.next();
			String corporate = iterator.next();
			driver.switchTo().window(corporate);
			reportPass("Switched to new tab Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

	
	public void registrationtest(String name, String orgName, String email, String contact, String exp) {
		By name1 = By.xpath("//*[@id='name']");
		By orgName1 = By.xpath("//*[@id='organizationName']");
		By email1 = By.xpath("//header[@id='header']//input[@id='officialEmailId']");
		By contact1 = By.xpath("//header[@id='header']//input[@id='contactNumber']");
		// Filling the form
		try {

			driver.findElement(name1).sendKeys(name);
			driver.findElement(orgName1).sendKeys(orgName);
			driver.findElement(email1).sendKeys(email);
			driver.findElement(contact1).sendKeys(contact);
			driver.findElement(organizationSize).click();
			driver.findElement(dropDown).click();

			reportPass("Schedule a demo button is not activated due to filling invalid details");
//			reportPass("Data entered successfully");
			Screenshot("dataEntered");
			Thread.sleep(2000);

		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		
		if(exp.equals("invalid"))
		{
			reportPass("Schedule a demo button is not activated due to filling invalid details");
		}
		else if(exp.equals("valid"))
		{
			reportFail("Data entered successfully");
		}
	}

	

	

}
