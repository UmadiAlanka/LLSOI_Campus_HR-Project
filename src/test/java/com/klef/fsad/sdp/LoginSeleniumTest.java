package com.klef.fsad.sdp;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginSeleniumTest {

    @Test
    void loginTest() throws InterruptedException {

        // Setup ChromeDriver
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();

        // Maximize Browser
        driver.manage().window().maximize();

        // Open Login Page
        driver.get("http://localhost:3000");

        // Wait for page load
        Thread.sleep(3000);

        // Find Username Input
        WebElement username = driver.findElement(
                By.cssSelector("input[type='text']")
        );

        // Wait for manual login
        Thread.sleep(100000);

        password.sendKeys("1234");

        // Find Login Button
        WebElement loginButton = driver.findElement(
                By.cssSelector("button[type='submit']")
        );

        // Click Login
        loginButton.click();

        // Wait after login
        Thread.sleep(5000);

        // Get Current URL
        String currentUrl = driver.getCurrentUrl();

        System.out.println("Current URL : " + currentUrl);

        // Validate Login Success
        boolean result =
                currentUrl.contains("admin-dashboard")
                        ||
                        currentUrl.contains("dashboard")
                        ||
                        currentUrl.contains("employees");

        assertTrue(result);

        System.out.println("LOGIN TEST PASSED");

        // Close Browser
        driver.quit();
    }
}