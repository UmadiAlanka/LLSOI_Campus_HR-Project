package com.klef.fsad.sdp;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddUserSeleniumTest {

    @Test
    void addUserTest() throws InterruptedException {

        // Setup ChromeDriver
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();

        // Maximize browser
        driver.manage().window().maximize();

        // Open Add User page
        driver.get(
                "http://localhost:3000/admin-dashboard/admin-manage-users/add-user"
        );

        // Wait for page load
        Thread.sleep(3000);

        // Fill UserName
        WebElement name =
                driver.findElement(By.name("name"));
        name.sendKeys("Test User");

        // Fill NIC
        WebElement nic =
                driver.findElement(By.name("nic"));
        nic.sendKeys("200274300746");

        // Fill Address
        WebElement address =
                driver.findElement(By.name("address"));
        address.sendKeys("Colombo");

        // Fill Contact Number
        WebElement contactNumber =
                driver.findElement(By.name("contactNumber"));
        contactNumber.sendKeys("0712345678");

        // Fill Job
        WebElement job =
                driver.findElement(By.name("job"));
        job.sendKeys("Lecturer");

        // Fill Username
        WebElement username =
                driver.findElement(By.name("username"));
        username.sendKeys("testuser123");

        // Fill Email
        WebElement email =
                driver.findElement(By.name("email"));
        email.sendKeys("testuser123@gmail.com");

        // Fill Password
        WebElement password =
                driver.findElement(By.name("password"));
        password.sendKeys("1234");

        // Fill Confirm Password
        WebElement confirmPassword =
                driver.findElement(By.name("confirmPassword"));
        confirmPassword.sendKeys("1234");

        // Click Register Button
        WebElement registerButton =
                driver.findElement(
                        By.cssSelector("button[type='submit']")
                );

        registerButton.click();

        // Wait after submit
        Thread.sleep(5000);

        // Validation
        String pageSource = driver.getPageSource();

        boolean result =
                pageSource.toLowerCase()
                        .contains("success");

        assertTrue(result);

        System.out.println(
                "ADD USER TEST PASSED"
        );

        // Close browser
        driver.quit();
    }
}