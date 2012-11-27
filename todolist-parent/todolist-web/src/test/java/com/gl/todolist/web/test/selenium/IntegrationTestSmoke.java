package com.gl.todolist.web.test.selenium;

import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dumbster.smtp.SmtpMessage;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gl.todolist.web.controllers.impl.FakeMailServer;
import com.gl.todolist.web.webdriver.resources.UITestHelper;
import com.gl.todolist.web.webdriver.resources.WebdriverTestHelper;

@ContextConfiguration( locations={"classpath:integration-test-context.xml"} )
public class IntegrationTestSmoke extends AbstractTestNGSpringContextTests{

	private HtmlUnitDriver driver;
	private UITestHelper uiTestHelper = new UITestHelper();
	
	@Autowired 
	private FakeMailServer fakeMailServer;
	private WebdriverTestHelper helper = new WebdriverTestHelper();	
	String appUrl = "http://localhost:9090/todolist-web";
	
	@BeforeClass
	public void beforeMethod(){

		driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_3_6);		
		driver.setJavascriptEnabled(true); 

		driver.navigate().to(appUrl+"/backbone/index.html");
	}
 
	@Test
	public void testNavigate() throws InterruptedException{
		
		String mail = helper.generateString(5, "text")+"@hotmail.com";
		String pass = "123";
		String tarea = "Correr integration-test";
		
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("createAcount")));
		driver.findElement(By.xpath("//*[@id='createAcount']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("newaccount")));
		driver.findElement(By.id("inputEmail")).sendKeys(mail);
		driver.findElement(By.id("inputPassword")).sendKeys(pass);
 		driver.findElement(By.id("inputPasswordConfirmation")).sendKeys(pass);
		driver.findElement(By.id("newaccount")).click();
		wait.until(ExpectedConditions.textToBePresentInElement(By.id("confirmation"), "Please confirm the registration by email that we sent you"));
		
		uiTestHelper.waitMoment(String.valueOf(10000));
    
		//mock mail
		Iterator emailIter =  fakeMailServer.getServer().getReceivedEmail();
		String bodyMail = null;
		while (emailIter.hasNext()){
			 SmtpMessage email =  (SmtpMessage) emailIter.next();
			if(email.getBody().contains(mail)){
				bodyMail = email.getBody();
			}
		}
		
        int x = bodyMail.indexOf("#token");
        int y = bodyMail.indexOf(">http://")-1;
        
        String tokenUrl = bodyMail.substring(x,y).replace("=", "");
        
		driver.navigate().to(appUrl+"/backbone/index.html"+tokenUrl);
		
		driver.get(appUrl+"/backbone/index.html"+tokenUrl);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("loginButton")));
		driver.findElement(By.id("inputEmail")).sendKeys(mail);
		driver.findElement(By.id("inputPassword")).sendKeys(pass);
		
		driver.findElement(By.id("loginButton")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("createtask")));
		driver.findElement(By.id("createtask")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("inputTitle")));
		driver.findElement(By.id("inputTitle")).sendKeys(tarea);
		
		Select selectStatus = new Select(driver.findElement(By.id("status")));
		selectStatus.selectByIndex(1);
		
		Select selectType = new Select(driver.findElement(By.id("type")));
		selectType.selectByIndex(1);
		
		driver.findElement(By.id("createnewtask")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("createtask")));
	}
 
	 @AfterClass
	 public void afterMethod() {
		 driver.quit();
	 } 

}