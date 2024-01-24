package test;

import config.EnvironmentConfig;
import model.Post;
import model.PostResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MyProfilePage;
import pages.PasswordPage;
import utils.RandomUtils;
import utils.VKApiUtils;

import static aquality.selenium.browser.AqualityServices.getBrowser;

public class VKApiAqualityTest {
    @Test
    public void testLoginAndNavigateToProfile() {
        //BrowserUtils.waitForPageLoad(EnvironmentConfig.getUrl(), By.xpath("//div[@class=\"IndexPageContent__content\"]"));
        getBrowser().goTo(EnvironmentConfig.getUrl());

        LoginPage loginPage = new LoginPage();
        PasswordPage passwordPage = loginPage.enterPhoneEmail();
        passwordPage.enterPassword();

        MyProfilePage myProfilePage = new MyProfilePage();
        myProfilePage.openMyProfile();
        Assert.assertTrue(myProfilePage.isAt(), "Failed to navigate to the My Profile page.");

        VKApiUtils apiUtils = new VKApiUtils();
        String randomText = RandomUtils.generateRandomText(100);
        PostResponse postResponse = apiUtils.createPost(randomText);
        System.out.println(postResponse);
        Assert.assertNotNull(postResponse, "The API response for post creation is null");


        //Assert.assertTrue(postId > 0, "The post ID should be greater than 0");

    }
}
