package com.supreme.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.NoSuchElementException;

public abstract class Page {

    private static final Logger log = LoggerFactory.getLogger(Page.class);

    protected final WebDriver driver;
    protected final WebDriverWait mWait;
    protected final WebDriverWait mWait20;

    public Page(WebDriver driver) {
        this.driver = driver;
        mWait = new WebDriverWait(driver, 3);
        mWait20 = new WebDriverWait(driver, 20);
    }

    public WebElement findElement(By by) {
        try {
            return waitUntilVisible(by);
        } catch (NoSuchElementException | TimeoutException e) {
            log.error("Could not find element by {}", by.toString());
            throw e;
        }
    }

    public WebElement findElementDOM(By by) {
        try {
            return driver.findElement(by);
        } catch (NoSuchElementException | TimeoutException e) {
            log.error("Could not find element in DOM by {}", by.toString());
            throw e;
        }
    }

    public List<WebElement> findElementsDOM(By by) {
        try {
            return driver.findElements(by);
        } catch (NoSuchElementException | TimeoutException e) {
            log.error("Could not find elements in DOM by {}", by.toString());
            throw e;
        }
    }

    public boolean isElementPresent(By by) {
        try {
            findElement(by);
            return true;
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }

    public WebElement waitUntilVisible(By by) {
        return mWait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public boolean waitUntilInvisible(By by) {
        return mWait20.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public void refresh() {
        driver.navigate().refresh();
    }
}
