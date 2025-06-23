package com.surrender;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Action {

	private WebDriverWait driverWait = null;
	private WebDriver driver = null;
	private long timeOutInSeconds = 5;
	
	private Action(WebDriver driver) 
	{ 
		this.driver = driver;
		this.driverWait = new WebDriverWait(this.driver, timeOutInSeconds);
	}	

	public void setTimeOutWait(long timeOutInSeconds) {
		this.timeOutInSeconds = timeOutInSeconds;
		this.driverWait = new WebDriverWait(this.driver, timeOutInSeconds);
	}
	
	public static Action getInstance(WebDriver driver) {
		return new Action(driver);
	}
	
	public WebElement waitForElementPresent(By locator, long timeInSeconds) {
		WebDriverWait wait = new WebDriverWait(this.driver, timeInSeconds);
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	public WebElement waitForElementPresent(By locator) {
		return driverWait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	public WebElement waitForElementVisible(WebElement element, long timeInSeconds) {
		WebDriverWait wait = new WebDriverWait(this.driver, timeInSeconds);
		return wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public WebElement waitForElementVisible(WebElement element) {
		return driverWait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public WebElement waitForElementClickable(WebElement element, long timeInSeconds) {
		WebDriverWait wait = new WebDriverWait(this.driver, timeInSeconds);
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public WebElement waitForElementClickable(WebElement element) {
		return driverWait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public boolean waitForElementInvisible(WebElement element, long timeInSeconds) {
		WebDriverWait wait = new WebDriverWait(this.driver, timeInSeconds);
		return wait.until(ExpectedConditions.invisibilityOf(element));
	}
	
	public boolean waitForElementInvisible(WebElement element) {
		return driverWait.until(ExpectedConditions.invisibilityOf(element));
	}
	
	public void takeScreenshot(String fileName) throws FileNotFoundException, IOException, HeadlessException, AWTException {
		Float qualityValue = 1.0f;

		BufferedImage img = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		ImageWriter writer = ImageIO.getImageWritersByFormatName("png").next();
		ImageWriteParam param = writer.getDefaultWriteParam();

		if (param.canWriteCompressed()) {
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			String[] type = param.getCompressionTypes();

			param.setCompressionType(type[0]);
			param.setCompressionQuality(qualityValue);
		}
		
		if(!new File(Constants.IMAGES_EVIDENCES_DIR).exists()) {
			new File(Constants.IMAGES_EVIDENCES_DIR).mkdirs();
		}

		String filePath = Constants.IMAGES_EVIDENCES_DIR + "/" + fileName;
		
		writer.setOutput(new FileImageOutputStream(new File(filePath)));
		writer.write(null, new IIOImage(img, null, null), param);
	}
}
