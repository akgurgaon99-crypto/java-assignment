package com.amazon.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;

public class AmazonTest {

    @Test
    public void testiPhone() {
        setupAndRun("iPhone");
    }

    @Test
    public void testGalaxy() {
        setupAndRun("Galaxy");
    }

    public void setupAndRun(String product) {
        // Chrome options for stability
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        
        WebDriver driver = new ChromeDriver(options);
        runTask(driver, product);
    }

    public void runTask(WebDriver driver, String product) {
        // Wait object (10 seconds max wait)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        try {
            driver.get("https://www.amazon.in");
            driver.manage().window().maximize();

            // 1. Wait for Search Box and Search
            WebElement searchBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("twotabsearchtextbox")));
            searchBox.sendKeys(product);
            searchBox.submit();
            
            // 2. Wait for Add to Cart Button and Click
            By cartLocator = By.xpath("//button[@name='submit.addToCart']");
            WebElement addToCartBtn = wait.until(ExpectedConditions.presenceOfElementLocated(cartLocator));
            js.executeScript("arguments[0].click();", addToCartBtn);
            
            System.out.println(product + " added to cart successfully!");

            // 2. Wait for Price and Print
            WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("a-price-whole")));
            String price = priceElement.getText();
            System.out.println(product + " Price: " + price);


        } catch (Exception e) {
            System.out.println("Error during " + product + " test: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}
