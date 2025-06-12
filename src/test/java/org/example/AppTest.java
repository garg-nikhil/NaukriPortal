package org.example;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;


public class AppTest {

    WebDriver driver;

    @Test
    public void updateNaukri(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless=new"); // for Chrome 109+
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        //options.addArguments("--disable-gpu");
        //options.addArguments("--remote-allow-origins=*"); // Optional but safe
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
        //driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get("https://login.naukri.com/nLogin/Login.php");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            Files.copy(screenshot.toPath(), Paths.get("screenshot.png"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        driver.findElement(By.xpath("//*[@id='usernameField']")).sendKeys("nikhilgarg855@gmail.com");
        System.out.println("Entered Username");
        driver.findElement(By.xpath("//*[@id='passwordField']")).sendKeys("");
        System.out.println("Entered Password");
        System.out.println(driver.getTitle());
        driver.findElement(By.xpath("//button[text()='Login']")).click();
        System.out.println(driver.getTitle());
        System.out.println("Hit login");
        
        driver.findElement(By.xpath("//*[text()='View']")).click();
        driver.findElement(By.xpath("//*[text()='Resume headline']/following-sibling::span[text()='editOneTheme']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Save']")));
        driver.findElement(By.xpath("//*[text()='Save']")).click();
        driver.quit();
    }
}
