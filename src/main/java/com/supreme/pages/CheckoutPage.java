package com.supreme.pages;

import com.supreme.utils.JsonUtil;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class CheckoutPage extends Page {

    // billing info field locators
    private static final By sNameLocator = By.id("order_billing_name");
    private static final By sEmailLocator = By.id("order_email");
    private static final By sTelephoneLocator = By.id("order_tel");
    private static final By sAddressLocator = By.name("order[billing_address]");
    private static final By sAddress2Locator = By.name("order[billing_address_2]");
    private static final By sZipLocator = By.id("order_billing_zip");

    // credit card info field locators
    private static final By sCCNumberLocator = By.xpath("//div[@id='card_details']/div[1]/input");
    private static final By sCCMonthLocator = By.xpath("//div[@id='card_details']/div[2]/select[1]");
    private static final By sCCYearLocator = By.xpath("//div[@id='card_details']/div[2]/select[2]");
    private static final By sCCPinLocator = By.xpath("//div[@id='card_details']/div[3]/input");
    private static final By sTermsLocator = By.className("terms");

    private static final By sProcessPaymentButtonLocator = By.name("commit");
    private static final By sCaptchaLocator = By.xpath("/html/body/div[2]");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void fillInfo() {
        JSONObject infoJSON = JsonUtil.getJSONResource("info.json");

        // billing/shipping information
        findElement(sNameLocator).sendKeys((String) infoJSON.get("name"));
        findElement(sEmailLocator).sendKeys((String) infoJSON.get("email"));
        findElement(sTelephoneLocator).click();
        findElement(sTelephoneLocator).sendKeys((String) infoJSON.get("tel"));
        findElement(sAddressLocator).sendKeys((String) infoJSON.get("address"));
        findElement(sAddress2Locator).sendKeys((String) infoJSON.get("address2"));
        findElement(sZipLocator).sendKeys((String) infoJSON.get("zip"));

        // credit card information
        String ccNum = (String) infoJSON.get("number");
        for (int i = 0; i < ccNum.length(); i++) {
            findElement(sCCNumberLocator).sendKeys(String.valueOf(ccNum.charAt(i)));
        }
        Select monthSelect = new Select(findElement(sCCMonthLocator));
        monthSelect.selectByVisibleText((String) infoJSON.get("expMonth"));
        Select yearSelect = new Select(findElement(sCCYearLocator));
        yearSelect.selectByVisibleText((String) infoJSON.get("expYear"));
        findElement(sCCPinLocator).sendKeys((String) infoJSON.get("CVV"));
        findElement(sTermsLocator).click();
    }

    public ConfirmationPage processPayment() {
        findElement(sProcessPaymentButtonLocator).click();
        return new ConfirmationPage(driver);
    }

    public boolean captchaPresent() {
        return findElementDOM(sCaptchaLocator).getCssValue("visibility").equals("visible");
    }

    public void waitUntilCaptchaSolved() {
        waitUntilInvisible(sCaptchaLocator);
    }
}
