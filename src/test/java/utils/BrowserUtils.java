package utils;

import aquality.selenium.browser.AqualityServices;
import constant.CommonConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BrowserUtils {

    public static void waitForPageLoad(String url, By pageLoadIndicator) {
        WebDriver driver = AqualityServices.getBrowser().getDriver();
        driver.get(url);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(CommonConstants.COMMON_WAIT_TIME));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(pageLoadIndicator));
        } catch (Exception e) {
            throw new RuntimeException("Page did not load properly or the element was not found: " + e.getMessage());
        }
    }
}
