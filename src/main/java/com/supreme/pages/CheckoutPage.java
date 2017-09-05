package com.supreme.pages;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;

public class CheckoutPage extends Page {

    private static final Logger log = LoggerFactory.getLogger(CheckoutPage.class);

    // information field locators
    private static final By sNameLocator = By.id("order_billing_name");
    private static final By sEmailLocator = By.id("order_email");
    private static final By sTelephoneLocator = By.id("order_tel");
    private static final By sAddressLocator = By.name("order[billing_address]");
    private static final By sAddress2Locator = By.name("order[billing_address_2]");
    private static final By sZipLocator = By.id("order_billing_zip");

    // credit card information locators
    private static final By sCCNumberLocator = By.xpath("//div[@id='card_details']/div[1]/input");
    private static final By sCCMonthLocator = By.xpath("//div[@id='card_details']/div[2]/select[1]");
    private static final By sCCYearLocator = By.xpath("//div[@id='card_details']/div[2]/select[2]");
    private static final By sCCPinLocator = By.xpath("//div[@id='card_details']/div[3]/input");
    private static final By sTermsLocator = By.className("terms");

    // cart footer
    private static final By sCartFooterLocator = By.id("cart-footer");
    private static final By sProcessPaymentButtonLocator = By.name("commit");

    // captcha
    private static final By sCaptchaLocator = By.xpath("/html/body/div[2]");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void fillInfo() {
        JSONObject jsonObject = null;
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("info.json")));
            jsonObject = (JSONObject) obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        try {
            // billing/shipping information
            findElement(sNameLocator).sendKeys((String) jsonObject.get("name"));
            findElement(sEmailLocator).sendKeys((String) jsonObject.get("email"));
            findElement(sTelephoneLocator).click();
            findElement(sTelephoneLocator).sendKeys((String) jsonObject.get("tel"));
            findElement(sAddressLocator).sendKeys((String) jsonObject.get("address"));
            findElement(sAddress2Locator).sendKeys((String) jsonObject.get("address2"));
            findElement(sZipLocator).sendKeys((String) jsonObject.get("zip"));

            // credit card information
            String ccNum = (String) jsonObject.get("number");
            for (int i = 0; i < ccNum.length(); i++) {
                findElement(sCCNumberLocator).sendKeys(String.valueOf(ccNum.charAt(i)));
            }
            Select monthSelect = new Select(findElement(sCCMonthLocator));
            monthSelect.selectByVisibleText((String) jsonObject.get("expMonth"));
            Select yearSelect = new Select(findElement(sCCYearLocator));
            yearSelect.selectByVisibleText((String) jsonObject.get("expYear"));
            findElement(sCCPinLocator).sendKeys((String) jsonObject.get("CVV"));
            findElement(sTermsLocator).click();
        } catch (NoSuchElementException | TimeoutException e) {
            log.error("Could not fill in information");
            throw e;
        }
    }

    public ConfirmationPage processPayment() {
        try {
            WebElement cartFooter = findElement(sCartFooterLocator);
            WebElement processPaymentButton = cartFooter.findElement(sProcessPaymentButtonLocator);
            processPaymentButton.click();
            return new ConfirmationPage(driver);
        } catch (NoSuchElementException | TimeoutException e) {
            log.error("Could not click process payment button");
            throw e;
        }
    }

    public boolean captchaPresent() {
        return findElementDOM(sCaptchaLocator).getCssValue("visibility").equals("visible");
    }

    public void waitUntilCaptchaSolved() {
        waitUntilInvisible(sCaptchaLocator);
    }
}
