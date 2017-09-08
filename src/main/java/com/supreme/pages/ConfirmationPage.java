package com.supreme.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ConfirmationPage extends Page {

    private static final By sSuccessMessageLocator = By.partialLinkText("Your order has been successfully");

    public ConfirmationPage(WebDriver driver) {
        super(driver);
    }

    public boolean success() {
        return isElementPresent(sSuccessMessageLocator);
    }
}
