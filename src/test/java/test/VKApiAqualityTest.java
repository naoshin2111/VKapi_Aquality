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
        //Get Browser
        //BrowserUtils.waitForPageLoad(EnvironmentConfig.getUrl(), By.xpath("//div[@class=\"IndexPageContent__content\"]"));
        getBrowser().goTo(EnvironmentConfig.getUrl());

        //Authorize Login
        LoginPage loginPage = new LoginPage();
        PasswordPage passwordPage = loginPage.enterPhoneEmail();
        passwordPage.enterPassword();

        //Go to My Profile Page
        MyProfilePage myProfilePage = new MyProfilePage();
        myProfilePage.openMyProfile();
        Assert.assertTrue(myProfilePage.isAt(), "Failed to navigate to the My Profile page.");

        //Create Post and response the post ID
        VKApiUtils apiUtils = new VKApiUtils();
        String randomText = RandomUtils.generateRandomText(100);
        PostResponse postResponse = apiUtils.createPost(randomText);
        System.out.println(postResponse);
        Assert.assertNotNull(postResponse, "The API response for post creation is null");
        int postId = postResponse.getPost_id();
        Assert.assertTrue(postId > 0, "The post ID should be greater than 0");

        // Check for the presence of the post on the wall

        boolean isPostPresent = myProfilePage.isPostPresentById(postId);
        Assert.assertTrue(isPostPresent, "The post with the ID " + postId + " was not found on the profile wall.");
    }
}
