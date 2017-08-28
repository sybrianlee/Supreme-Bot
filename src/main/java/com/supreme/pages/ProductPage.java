package com.supreme.pages;

import com.supreme.SupremeItem.Size;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;

public class ProductPage extends Page {

    private static final Logger log = LoggerFactory.getLogger(ProductPage.class);

    public ProductPage(WebDriver driver) {
        super(driver);
    }

//    public void selectColor(String itemColor) {
//        try {
//            WebElement detailSection = findElement(By.id("details"));
//            WebElement colorSection = detailSection.findElement(By.className("styles"));
//            List<WebElement> colorOptions = findElements(colorSection, By.tagName("li"));
//
//            for (WebElement color : colorOptions) {
//                WebElement colorSelect = color.findElement(By.tagName("a"));
//                if (colorSelect.getAttribute("data-style-name").equals(itemColor)) {
//                    colorSelect.click();
//                }
//            }
//        } catch (NoSuchElementException | TimeoutException e) {
//            log.error("Could not find color {}", itemColor);
//            throw e;
//        }
//    }

    public void selectSize(Size size) {
        try {
            Select select = new Select(findElement(By.id("s")));
            select.selectByVisibleText(size.getValue());
        } catch (NoSuchElementException | TimeoutException e) {
            log.error("Could not find size {}", size.getValue());
            throw e;
        }
    }

    public void addToCart() {
        try {
            WebElement addSection = findElement(By.id("add-remove-buttons"));
            WebElement addToCartButton = addSection.findElement(By.name("commit"));
            addToCartButton.click();
        } catch (NoSuchElementException | TimeoutException e) {
            log.error("Could not add to cart");
            throw e;
        }
    }

    public CheckoutPage checkout() {
        try {
            WebElement cartSection = findElement(By.id("cart"));
            WebElement checkoutButton = cartSection.findElement(By.linkText("checkout now"));
            checkoutButton.click();
            return new CheckoutPage(driver);
        } catch (NoSuchElementException | TimeoutException e) {
            log.error("Could not checkout");
            throw e;
        }
    }
}
