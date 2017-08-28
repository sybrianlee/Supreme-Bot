package com.supreme.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ConfirmationPage extends Page {

    public ConfirmationPage(WebDriver driver) {
        super(driver);
    }

    public boolean success() {
        return isElementPresent(By.linkText("Success"));
    }
}
