package test;

import aquality.selenium.browser.AqualityServices;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterMethod;
import static aquality.selenium.browser.AqualityServices.getBrowser;

public abstract class BaseTest {

    @BeforeSuite
    public void setupRestAssured() {
        RestAssured.filters(new RequestLoggingFilter(),
                new ResponseLoggingFilter());
    }

    @AfterMethod
    public void tearDown() {
        if (AqualityServices.isBrowserStarted()) {
            getBrowser().quit();
        }
    }
}
