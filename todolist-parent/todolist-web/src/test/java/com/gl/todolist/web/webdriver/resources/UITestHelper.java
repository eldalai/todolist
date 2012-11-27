package com.gl.todolist.web.webdriver.resources;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestNGMethod;
import org.testng.Reporter;

public class UITestHelper {

	public DesiredCapabilities getDesiredCapability(String browser, String plataform) {
		DesiredCapabilities capability = null;

		if (browser.equalsIgnoreCase("firefox")) {
			capability = DesiredCapabilities.firefox();
			capability.setBrowserName("firefox");
			capability.setPlatform(getPlatform( plataform, capability));
		}

		if (browser.equalsIgnoreCase("iexplore")) {
			capability = DesiredCapabilities.internetExplorer();
			capability.setBrowserName("iexplore");
			capability.setPlatform(getPlatform( plataform, capability));
		}

		if (browser.equalsIgnoreCase("chrome")) {
			capability = DesiredCapabilities.chrome();
			capability.setBrowserName("chrome");
			capability.setPlatform(getPlatform( plataform, capability));
		}
		capability.setJavascriptEnabled(true);
		return capability;
	}
	
	private Platform getPlatform( String plataform, DesiredCapabilities capability) {

		if (plataform.equalsIgnoreCase("windows")) {
			capability.setPlatform(org.openqa.selenium.Platform.WINDOWS);
		}							

		if (plataform.equalsIgnoreCase("android")) {
			capability.setPlatform(org.openqa.selenium.Platform.ANDROID);
		}

		if (plataform.equalsIgnoreCase("linux")) {
			capability.setPlatform(org.openqa.selenium.Platform.LINUX);
		}
		if(plataform.equals("vista")){
			capability.setPlatform(org.openqa.selenium.Platform.VISTA);
		}
		
		if (plataform.equalsIgnoreCase("mac")) {
			capability.setPlatform(org.openqa.selenium.Platform.MAC);
		}
		if(plataform.equals("any")){
			capability.setPlatform(org.openqa.selenium.Platform.ANY);
		}
		return capability.getPlatform();
	}

	public List<String> listWindows(WebDriver driver) {
		// Obtain ids of windows.
		List<String> windows = new ArrayList<String>();

		windows.clear();
		Set<String> windowsId = driver.getWindowHandles();
		Iterator<String> it = windowsId.iterator();
		while (it.hasNext()) {
			windows.add(it.next());
		}
		return windows;
	}

	public void waitMoment(String milliseconds){
		try{
			Thread.sleep(Long.parseLong(milliseconds));
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void takeScreenShoot(WebDriver driver, ITestNGMethod testMethod) throws Exception {
		WebDriver augmentedDriver = new Augmenter().augment(driver);
		File screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
		String nameScreenshot = testMethod.getXmlTest().getParameter("browser").toUpperCase() + "_" + testMethod.getTestClass().getRealClass().getSimpleName() + "_" + testMethod.getMethodName();
		String path = getPath(nameScreenshot);
		FileUtils.copyFile(screenshot, new File(path));
		Reporter.log("<a href=" + path + " target='_blank' >" + this.getFileName(nameScreenshot) + "</a>");
	}

	private String getFileName(String nameTest) throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy_hh.mm.ss");
		Date date = new Date();
		return dateFormat.format(date) + "_" + nameTest + ".png";
	}

	private String getPath(String nameTest) throws IOException {
		File directory = new File(".");
		// String newFileNamePath = directory.getCanonicalPath() +
		// "\\test-output\\screenShots\\" + getFileName(nameTest);
		String newFileNamePath = directory.getCanonicalPath() + "\\target\\surefire-reports\\screenShots\\" + getFileName(nameTest);
		return newFileNamePath;
	}

	public void maximizeWindow(WebDriver driver) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenResolution = new Dimension((int) toolkit.getScreenSize().getWidth() / 2, (int) toolkit.getScreenSize().getHeight() / 2);
		driver.manage().window().setSize(screenResolution);
		// driver.manage().window().setPosition(targetPosition)
	}
}
