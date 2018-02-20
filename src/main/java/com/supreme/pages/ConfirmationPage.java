package com.supreme.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ConfirmationPage extends Page {

    /*
     * Your order has been submitted on Oct 05 2017 at 11:00and will be processed within 3-5 days.
     * Once processing is completed, you will receive a shipping confirmation with the tracking number.
     */

    private static final By sConfirmationTextLocator = By.partialLinkText("Your order has been submitted");

    public ConfirmationPage(WebDriver driver) {
        super(driver);
    }

    public boolean success() {
        return isElementPresent(sConfirmationTextLocator);
    }
}
