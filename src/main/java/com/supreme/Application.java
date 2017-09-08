package com.supreme;

import com.supreme.SupremeItem.Section;
import com.supreme.SupremeItem.Size;
import com.supreme.pages.CheckoutPage;
import com.supreme.pages.ConfirmationPage;
import com.supreme.pages.ProductPage;
import com.supreme.pages.SectionPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        // item to buy
        Section section = Section.JACKETS;
        String name = "Wool Overcoat";
        int colorIndex = 1;
        Size size = Size.MEDIUM;
        SupremeItem supremeItem = new SupremeItem(section, name, colorIndex, size);

        // set up
        log.info("Starting Bot");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        WebDriver driver = new ChromeDriver(options);
        
        // go to store section
        log.info("Item to buy: {}", supremeItem);
        SectionPage sectionPage = new SectionPage(driver, supremeItem.getSection());

        // select item
        log.info("Selecting item keyword: {}", supremeItem.getName());
        ProductPage productPage = sectionPage.findAndClickItem(supremeItem);
        long start = System.nanoTime();

        // select size
        if (supremeItem.getSize() != null) {
            log.info("Selecting size {}", supremeItem.getSize().getValue());
            productPage.selectSize(supremeItem.getSize());
        }

        // add to cart
        log.info("Adding to cart");
        productPage.addToCart();

        // checkout
        log.info("Checking out");
        CheckoutPage checkoutPage = productPage.checkout();

        // fill information
        log.info("Filling in information");
        checkoutPage.fillInfo();

        // process payment
        log.info("Submitting order");
        checkoutPage.processPayment();

        // captcha
        if (checkoutPage.captchaPresent()) {
            log.info("Waiting for captcha to be solved");
            checkoutPage.waitUntilCaptchaSolved();
        }

        // finish
        ConfirmationPage confirmationPage = new ConfirmationPage(driver);
        log.info(confirmationPage.success() ? "Success!" : "Fail");
        log.info("Time Elapsed: {}s", (System.nanoTime() - start) / 1000000000.0);
    }
}
