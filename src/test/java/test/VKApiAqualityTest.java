package test;

import config.EnvironmentConfig;
import config.TestDataConfig;
import model.*;
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
    public void testLoginAndNavigateToProfile() throws InterruptedException {
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

        PhotoSave photoSave = apiUtils.saveWallPhoto(photoUploadWall);
        String attachment = "photo" + photoSave.getOwnerId()+ "_" + photoSave.getPhotoId();
        System.out.println(attachment);
        String newText = "Edited post text " + RandomUtils.generateRandomText(10);
        Post editPost = apiUtils.editPostWithPhoto(postId, newText, attachment);
        // TODO -  Assert postEdit wall properties

        // Check if the edited post text appears on the page
        boolean isEditedTextPresent = myProfilePage.getPostTextEdited(newText);
        Assert.assertTrue(isEditedTextPresent, "The edited post text '" + newText + "' was not found on the page.");

        // Assert the photo is uploaded to the post using the photo ID obtained after saving the photo
        boolean isPhotoUploaded = myProfilePage.isPhotoPresentInPost(postId, photoSave.getPhotoId(), photoSave.getOwnerId());
        Assert.assertTrue(isPhotoUploaded, "The photo was not uploaded to the post.");


        // Create a comment on the post
        String commentText = RandomUtils.generateRandomText(100);
        Comment comment = apiUtils.addCommentToPost(post.getPostId(), commentText);
        Assert.assertNotNull(comment, "The API response for comment creation is null");

        int commentId = comment.getCommentId();
        Assert.assertTrue(commentId > 0, "The comment ID should be greater than 0");

        String ownerId = photoSave.getOwnerId();

        //Click on "Show new comment"
        boolean isShowCommentButtonClicked = myProfilePage.showNextComment(ownerId, postId);
        Assert.assertTrue(isShowCommentButtonClicked, "Failed to click 'Show next comment' button.");

        // Verify the comment through UI without refreshing the page
        boolean isCommentAdded = myProfilePage.isCommentPresentByCommentId(ownerId, commentId);
        Assert.assertTrue(isCommentAdded, "The comment with text '" + commentText + "' was not found on the post in the UI.");
//
        // Like the post
//        myProfilePage.likePost(postId);

//        // Delete the post and assert deletion was successful
//        DeleteResponse deleteResponse = apiUtils.deletePost(post.getPostId());
//        Assert.assertTrue(deleteResponse.isDeleted(), "The post was not deleted.");
//
//        //Check whether the post is deleted through UI
//        boolean isPostDeleted = myProfilePage.isPostDeleted(ownerId, postId);
//        Assert.assertTrue(isPostDeleted, "The post was not deleted (still visible on the page).");
    }

}
