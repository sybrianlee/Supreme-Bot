package com.supreme.pages;

import com.supreme.SupremeItem;
import com.supreme.SupremeItem.Section;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SectionPage extends Page {

    private static final Logger log = LoggerFactory.getLogger(SectionPage.class);

    private static final String STORE_URL = "http://www.supremenewyork.com/shop/all/";

    public SectionPage(WebDriver driver, Section section) {
        super(driver);
        driver.get(STORE_URL + section.getName());
    }

    public ProductPage findItem(SupremeItem supremeItem) {
        String name = supremeItem.getName();
        int colorIndex = supremeItem.getColorIndex();

        try {
            while (itemNotReleased(name)) {
                refresh();
            }
            driver.get(findElementsDOM(By.partialLinkText(name)).get(colorIndex).getAttribute("href"));
            return new ProductPage(driver);
        } catch (IndexOutOfBoundsException e) {
            log.error("Color index greater than available colors");
            throw e;
        }
    }

    private boolean itemNotReleased(String name) {
        return findElementsDOM(By.partialLinkText(name)).isEmpty();
    }
}
