package com.nokri;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

import java.time.Duration;

public class UpdateNokriProfile {
    private WebDriver driver;
    private WebDriverWait wait;
    
    private final String EMAIL = "tusharsjadhav60@gmail.com";
    private final String PASSWORD = "JayHanuman@2000";
    private final String RESUME_PATH = "E:\\Tushar_Jadhav_QA.pdf"; 

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.naukri.com/nlogin/login?URL=https://www.naukri.com/mnjuser/homepage");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("usernameField"))).sendKeys(EMAIL);
        driver.findElement(By.id("passwordField")).sendKeys(PASSWORD);
        driver.findElement(By.xpath("//button[contains(text(),'Login')]")).click();
        wait.until(ExpectedConditions.urlContains("homepage")); 
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/main/div/div/div[3]/div/div[3]/div[2]/a"))).click();
    }

    @Test(priority = 1)
    public void updateResume() {
        WebElement parentDiv = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"lazyAttachCV\"]/div/div[2]/div[2]/div/div[2]/div[1]/div/section")));
        parentDiv.click(); 
        WebElement uploadButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='file']")));
        uploadButton.sendKeys(RESUME_PATH);  
        System.out.println("Resume updated successfully!");
    }

    @Test(priority = 2)
    public void updateProfile() throws InterruptedException {
    	WebElement uploadButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"root\"]/div/div/span/div/div/div/div/div/div[1]/div[1]/div/div/div/div[2]/div[1]/div/div[1]/em")));
        uploadButton.click();
        
        WebElement element = driver.findElement(By.id("saveBasicDetailsBtn"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(500);
        WebElement saveButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("saveBasicDetailsBtn")));
        saveButton.click();
        
       
        Thread.sleep(2000);
        String expected = "Today";
        WebElement element1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='root']/div/div/span/div/div/div/div/div/div[1]/div[1]/div/div/div/div[2]/div[1]/div/div[2]/div[2]/span/span")));
        String actual = element1.getText();
        Assert.assertEquals(actual, expected, "Text mismatch!");
        System.out.println("Profile updated successfully!");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

   
    
}
