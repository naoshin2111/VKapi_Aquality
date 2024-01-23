package test;

import aquality.selenium.browser.AqualityServices;
import config.EnvironmentConfig;
import config.TestUserConfig;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.PasswordPage;
import utils.BrowserUtils;
import utils.ConfigReader;

import static aquality.selenium.browser.AqualityServices.getBrowser;

public class VKApiAqualityTest {
    @Test
    public void testLoginAndNavigateToProfile() {
        BrowserUtils.waitForPageLoad(EnvironmentConfig.getUrl(), By.xpath("//div[@class=\"IndexPageContent__content\"]"));
        //getBrowser().goTo(EnvironmentConfig.getUrl());

        LoginPage loginPage = new LoginPage();
        PasswordPage passwordPage = loginPage.enterPhoneEmail();
        //passwordPage.enterPassword();

    }
}
