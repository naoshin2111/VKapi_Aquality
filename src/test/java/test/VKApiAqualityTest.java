package test;

import config.EnvironmentConfig;
import config.TestDataConfig;
import model.PhotoSave;
import model.PhotoUploadServer;
import model.PhotoUploadWall;
import model.Post;
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
        Post post = apiUtils.createPost(randomText);
        Assert.assertNotNull(post, "The API response for post creation is null");
        int postId = post.getPostId();
        Assert.assertTrue(postId > 0, "The post ID should be greater than 0");


        // Check for the presence of the post on the wall
        boolean isPostPresent = myProfilePage.isPostPresentById(postId);
        Assert.assertTrue(isPostPresent, "The post with the ID " + postId + " was not found on the profile wall.");

        // Get the upload server URL
        PhotoUploadServer photoUploadServer = apiUtils.getWallUploadServer();


        String imagePath = TestDataConfig.getImagePath();
        PhotoUploadWall photoUploadWall = apiUtils.uploadPhotoToWall(imagePath, photoUploadServer.getUpload_url());
        // TODO -  Assert photo upload wall properties
        //photoName = "photo" + ownerId + "_" + photoId

        PhotoSave photoSave = apiUtils.saveWallPhoto(photoUploadWall);
        String attachment = "photo" + photoSave.getOwnerId()+ "_" + photoSave.getPhotoId();
        String newText = "Edited post text " + RandomUtils.generateRandomText(10);
        Post editPost = apiUtils.editPostWithPhoto(postId, newText, attachment);
        // TODO -  Assert postEdit wall properties

        // Check if the edited post text appears on the page
        //Assert.assertEquals(myProfilePage.getPostTextEdited(postId), newText, "The post text was not updated on the page.");

        boolean isEditedTextPresent = myProfilePage.getPostTextEdited(newText);
        Assert.assertTrue(isEditedTextPresent, "The edited post text '" + newText + "' was not found on the page.");

        // Check if the uploaded photo appears on the page
        //Assert.assertTrue(myProfilePage.isPhotoPresentInPost(postId, photoSave.getPhotoId()), "The photo was not attached to the post on the page.");

        // Assert the photo is uploaded to the post
//        boolean isPhotoUploaded = myProfilePage.isPhotoUploadedToPost(postId);
//        Assert.assertTrue(isPhotoUploaded, "The photo was not uploaded to the post.");

        // Assert the photo is uploaded to the post using the photo ID obtained after saving the photo
        boolean isPhotoUploaded = myProfilePage.isPhotoPresentInPost(postId, photoSave.getPhotoId());
        Assert.assertTrue(isPhotoUploaded, "The photo was not uploaded to the post.");

    }
}
