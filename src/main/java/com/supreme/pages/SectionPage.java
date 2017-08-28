package com.supreme.pages;

import com.supreme.SupremeItem.Section;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SectionPage extends Page {

    private static final Logger log = LoggerFactory.getLogger(SectionPage.class);

    public SectionPage(WebDriver driver, Section section) {
        super(driver);
        driver.get(String.format("http://www.supremenewyork.com/shop/all/%s", section.getName()));
    }

    public ProductPage findAndClickItem(String itemName, int colorIndex) {
        try {
            findElements(By.partialLinkText(itemName)).get(colorIndex).click();
            return new ProductPage(driver);
        } catch (NoSuchElementException | TimeoutException e) {
            log.error("Could not find item {}", itemName);
            throw e;
        } catch (IndexOutOfBoundsException e) {
            log.error("Color index greater than available colors");
            throw e;
        }
    }
}
