package com.klef.fsad.sdp;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmptyLoginSeleniumTest {

    @Test
    void emptyLoginTest()
            throws InterruptedException {

        WebDriverManager.chromedriver().setup();

        WebDriver driver =
                new ChromeDriver();

        driver.manage().window().maximize();

        // Open login page
        driver.get("http://localhost:3000");

        Thread.sleep(3000);

        // Click login button without entering data
        driver.findElement(By.tagName("button"))
                .click();

        Thread.sleep(3000);

        // Get page source
        String pageSource =
                driver.getPageSource();

        // Check validation message
        boolean result =
                pageSource.contains("required")
                        ||
                        pageSource.contains("Please")
                        ||
                        pageSource.contains("Username")
                        ||
                        pageSource.contains("Password");

        assertTrue(result);

        System.out.println(
                "EMPTY LOGIN TEST PASSED"
        );

        driver.quit();
    }
}