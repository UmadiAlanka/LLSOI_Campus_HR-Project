package com.klef.fsad.sdp;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ForgotPasswordSeleniumTest {

    @Test
    void forgotPasswordTest() {

        // Automatically setup ChromeDriver
        WebDriverManager.chromedriver().setup();

        // Open Chrome Browser
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();

        // Open Forgot Password Page
        driver.get("http://localhost:3000/forgot-password");

        // Wait Helper
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        // Email Field
        WebElement email =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.cssSelector("input[type='email']")
                        )
                );

        // Enter Email
        email.sendKeys("anduniinduja@gmail.com");

        // Submit Button
        WebElement button =
                driver.findElement(
                        By.cssSelector("button[type='submit']")
                );

        // Click Button
        button.click();

        // Wait for Success Message
        WebElement success =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//*[contains(text(),'OTP Code Sent')]")
                        )
                );

        // Validation
        assertTrue(success.isDisplayed());

        System.out.println("FORGOT PASSWORD TEST PASSED");

        // Close Browser
        driver.quit();
    }
}