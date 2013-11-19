package com.gl.todolist.web.test.selenium;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.remote.DesiredCapabilities;
//import org.openqa.selenium.remote.RemoteWebDriver;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
//import org.testng.ITestResult;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Parameters;
//import org.testng.annotations.Test;
//
//import com.dumbster.smtp.SmtpMessage;
//import com.gl.todolist.web.controllers.impl.FakeMailServer;
//import com.gl.todolist.web.test.saucelabs.SauceConnectTwoManager;
//import com.gl.todolist.web.test.saucelabs.SauceTunnelManager;
//import com.gl.todolist.web.webdriver.resources.UITestHelper;
//import com.gl.todolist.web.webdriver.resources.WebdriverTestHelper;
//import com.saucelabs.common.SauceOnDemandAuthentication;
//import com.saucelabs.saucerest.SauceREST;

//@ContextConfiguration( locations={"classpath:integration-test-context.xml"} )
public class IntegrationTestRemoteSmoke /*extends AbstractTestNGSpringContextTests */ {

//	@Autowired 
//	private FakeMailServer fakeMailServer;
//	private String appUrl = "";
//	private String user = "";
//	private String key = "";
//    private WebDriver driver;
//    private DesiredCapabilities capabillities;
//    private UITestHelper uiTestHelper = new UITestHelper();
//    private WebdriverTestHelper helper = new WebdriverTestHelper();
//    private SauceTunnelManager sauceTunnelManager = new SauceConnectTwoManager();
//    
//    
//    @BeforeClass
//    @Parameters({"url","user","key"})
//    public void beforeClass(String url, String user, String key) throws IOException{
//    	this.appUrl = url;
//    	this.user = user;
//    	this.key = key;
//    	saucelabsConnect();
//    }
//    
//    @BeforeMethod
//    @Parameters({ "browser", "version", "platform" })
//	public void beforeMethod(String browser, String version, String platform) throws Exception {
//        capabillities = uiTestHelper.getDesiredCapability(browser, platform, version);
//        this.driver = new RemoteWebDriver(
//					  new URL("http://"+user+":"+key+"@ondemand.saucelabs.com:80/wd/hub"),
//					  capabillities);
//        driver.navigate().to(appUrl+"/backbone/index.html");
//    }
//
//    @Test
//	public void testNavigate() throws Exception {
//
//    	String mail = helper.generateString(5, "text")+"@hotmail.com";
//		String pass = "123";
//		String tarea = "Correr integration-test";
//    	
//		WebDriverWait wait = new WebDriverWait(driver, 50);
//		wait.until(ExpectedConditions.elementToBeClickable(By.id("createAcount")));
//		driver.findElement(By.xpath("//*[@id='createAcount']")).click();
//		wait.until(ExpectedConditions.elementToBeClickable(By.id("newaccount")));
//		
//		driver.findElement(By.id("inputEmail")).sendKeys(mail);
//		driver.findElement(By.id("inputPassword")).sendKeys(pass);
// 		driver.findElement(By.id("inputPasswordConfirmation")).sendKeys(pass);
//		driver.findElement(By.id("newaccount")).click();
//		wait.until(ExpectedConditions.textToBePresentInElement(By.id("confirmation"), "Please confirm the registration by email that we sent you"));
//		
//		uiTestHelper.waitMoment(String.valueOf(10000));
//		//mock mail
//		Iterator emailIter =  fakeMailServer.getServer().getReceivedEmail();
//		String bodyMail = null;
//		while (emailIter.hasNext()){
//			 SmtpMessage email =  (SmtpMessage) emailIter.next();
//			if(email.getBody().contains(mail)){
//				bodyMail = email.getBody();
//			}
//		}
//
//        int x = bodyMail.indexOf("#token");
//        int y = bodyMail.indexOf(">http://")-1;
//        
//        String tokenUrl = bodyMail.substring(x,y).replace("=", "");
//        
//		driver.navigate().to(appUrl+"/backbone/index.html"+tokenUrl);
//		
//		driver.get(appUrl+"/backbone/index.html"+tokenUrl);
//		wait.until(ExpectedConditions.elementToBeClickable(By.id("loginButton")));
//		driver.findElement(By.id("inputEmail")).sendKeys(mail);
//		driver.findElement(By.id("inputPassword")).sendKeys(pass);
//		
//		driver.findElement(By.id("loginButton")).click();
//		
//		wait.until(ExpectedConditions.elementToBeClickable(By.id("createtask")));
//   		driver.findElement(By.id("createtask")).click();
//		
//		wait.until(ExpectedConditions.elementToBeClickable(By.id("inputTitle")));
//		driver.findElement(By.id("inputTitle")).sendKeys(tarea);
//		
//		Select selectStatus = new Select(driver.findElement(By.id("status")));
//		selectStatus.selectByIndex(1);
//		
//		Select selectType = new Select(driver.findElement(By.id("type")));
//		selectType.selectByIndex(1);
//		
//		driver.findElement(By.id("createnewtask")).click();
//		
//		wait.until(ExpectedConditions.elementToBeClickable(By.id("createtask")));
//    }
//
//    @AfterMethod(alwaysRun = true)
//	public void afterMethod(ITestResult testResult) throws Exception {
//        String jobID = ((RemoteWebDriver)driver).getSessionId().toString();
//        SauceREST client = new SauceREST(user, key);
//        Map<String, Object>sauceJob = new HashMap<String, Object>();
//        sauceJob.put("name", "Test method: "+testResult.getMethod().getMethodName());
//        if(testResult.isSuccess()) {
//        	
//            client.jobPassed(jobID);
//        } else {
//            client.jobFailed(jobID);
//        }
//        client.updateJobInfo(jobID, sauceJob);
//        sauceTunnelManager.closeTunnelsForPlan(user, null);
//        driver.quit();
//    }
//    
//	private void saucelabsConnect() throws IOException  {
//    	final SauceOnDemandAuthentication c = new SauceOnDemandAuthentication();
//        System.out.println("Starting a tunnel");
//        
//        c.setUsername(user);
//        c.setAccessKey(key);
//        Authenticator.setDefault(
//                new Authenticator() {
//                    public PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(
//                                c.getUsername(), c.getAccessKey().toCharArray());
//                    }
//                }
//        );            
//        sauceTunnelManager.openConnection(c.getUsername(), c.getAccessKey(), 4445, null, null, null);
//    }
//    
    
}