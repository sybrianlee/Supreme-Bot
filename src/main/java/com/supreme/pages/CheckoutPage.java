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
            findElement(By.id("order_billing_name")).sendKeys((String) jsonObject.get("name"));
            findElement(By.id("order_email")).sendKeys((String) jsonObject.get("email"));
            findElement(By.id("order_tel")).click();
            findElement(By.id("order_tel")).sendKeys((String) jsonObject.get("tel"));
            findElement(By.name("order[billing_address]")).sendKeys((String) jsonObject.get("address"));
            findElement(By.name("order[billing_address_2]")).sendKeys((String) jsonObject.get("address2"));
            findElement(By.id("order_billing_zip")).sendKeys((String) jsonObject.get("zip"));

            // credit card information
            String ccNum = (String) jsonObject.get("number");
            for (int i = 0; i < ccNum.length(); i++) {
                findElement(By.id("cnb")).sendKeys(String.valueOf(ccNum.charAt(i)));
            }
            Select monthSelect = new Select(findElement(By.id("credit_card_month")));
            monthSelect.selectByVisibleText((String) jsonObject.get("expMonth"));
            Select yearSelect = new Select(findElement(By.id("credit_card_year")));
            yearSelect.selectByVisibleText((String) jsonObject.get("expYear"));
            findElement(By.id("vval")).sendKeys((String) jsonObject.get("CVV"));
            findElement(By.className("terms")).click();
        } catch (NoSuchElementException | TimeoutException e) {
            log.error("Could not fill in information");
            throw e;
        }
    }

    public ConfirmationPage processPayment() {
        try {
            WebElement paySection = findElement(By.id("pay"));
            WebElement processPaymentButton = paySection.findElement(By.name("commit"));
            processPaymentButton.click();
            return new ConfirmationPage(driver);
        } catch (NoSuchElementException | TimeoutException e) {
            log.error("Could not click process payment button");
            throw e;
        }
    }

    public boolean captchaPresent() {
        return findElementDOM(By.xpath("/html/body/div[2]")).getCssValue("visibility").equals("visible");
    }

    public void waitUntilCaptchaSolved() {
        waitUntilInvisible(By.xpath("/html/body/div[2]"));
    }
}
