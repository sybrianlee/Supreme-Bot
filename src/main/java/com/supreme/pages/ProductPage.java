package com.supreme.pages;

import com.supreme.SupremeItem.Size;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class ProductPage extends Page {

    private static final By sSizeSelectLocator = By.id("s");
    private static final By sAddToCartButtonLocator = By.name("commit");
    private static final By sCheckoutButtonLocator = By.linkText("checkout now");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public void selectSize(Size size) {
        Select sizeSelect = new Select(findElement(sSizeSelectLocator));
        sizeSelect.selectByVisibleText(size.getValue());
    }

    public void addToCart() {
        findElement(sAddToCartButtonLocator).click();
    }

    public CheckoutPage checkout() {
        findElement(sCheckoutButtonLocator).click();
        return new CheckoutPage(driver);
    }
}
