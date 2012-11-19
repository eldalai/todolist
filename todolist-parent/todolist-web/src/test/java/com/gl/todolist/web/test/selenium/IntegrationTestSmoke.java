package com.gl.todolist.web.test.selenium;

import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dumbster.smtp.SmtpMessage;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gl.todolist.web.controllers.impl.FakeMailServer;
import com.sun.org.apache.commons.logging.*;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:integration-test-context.xml"}) 
public class IntegrationTestSmoke {

//	private WebDriver driver;
	private HtmlUnitDriver driver;
	
	@Autowired 
	FakeMailServer fakeMailServer;
	
	String appUrl = "http://localhost:9090/todolist-web";
	
	@Before
	public void beforeMethod(){
		//firefox
//	    driver = new FirefoxDriver();
		
		//HtmlUnit
		driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_3_6);		
		driver.setJavascriptEnabled(true); 

		driver.navigate().to(appUrl+"/backbone/index.html");
	}
 
	@Test
	public void testNavigate() throws InterruptedException{
		
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("createAcount")));
		driver.findElement(By.xpath("//*[@id='createAcount']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("newaccount")));
		driver.findElement(By.id("inputEmail")).sendKeys("octavio_ar21@hotmail.com");
		driver.findElement(By.id("inputPassword")).sendKeys("123");
 		driver.findElement(By.id("inputPasswordConfirmation")).sendKeys("123");
		driver.findElement(By.id("newaccount")).click();
		wait.until(ExpectedConditions.textToBePresentInElement(By.id("confirmation"), "Please confirm the registration by email that we sent you"));
		
		Thread.sleep(10000);
		fakeMailServer.getServer().stop();
		assertTrue("Envio de mail de registracion",fakeMailServer.getServer().getReceivedEmailSize() == 2);
	
		//mock mail
		Iterator emailIter =  fakeMailServer.getServer().getReceivedEmail();
        emailIter.next();
        SmtpMessage email =  (SmtpMessage) emailIter.next();
        
        int x = email.getBody().indexOf("#token");
        int y = email.getBody().indexOf(">http://")-1;
        
        String tokenUrl = email.getBody().substring(x,y).replace("=", "");
        
		driver.navigate().to(appUrl+"/backbone/index.html"+tokenUrl);
		
		driver.get(appUrl+"/backbone/index.html"+tokenUrl);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("loginButton")));
		driver.findElement(By.id("inputEmail")).sendKeys("octavio_ar21@hotmail.com");
		driver.findElement(By.id("inputPassword")).sendKeys("123");
		
		driver.findElement(By.id("loginButton")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("createtask")));
		driver.findElement(By.id("createtask")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("inputTitle")));
		driver.findElement(By.id("inputTitle")).sendKeys("Correr integration-test");
		
		Select selectStatus = new Select(driver.findElement(By.id("status")));
		selectStatus.selectByIndex(1);
		
		Select selectType = new Select(driver.findElement(By.id("type")));
		selectType.selectByIndex(1);
		
		driver.findElement(By.id("createnewtask")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("createtask")));
		Thread.sleep(3000);
		
	}
 
	 @After
	 public void afterMethod() {
		 driver.quit();
	 } 

}