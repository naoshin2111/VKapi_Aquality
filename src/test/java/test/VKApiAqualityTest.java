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

public class VKApiAqualityTest extends BaseTest{
    @Test
    public void testLoginAndNavigateToProfile() {
        getBrowser().goTo(EnvironmentConfig.getUrl());
        //TODO Add an assertion

        //Authorize Login
        LoginPage loginPage = new LoginPage();
        PasswordPage passwordPage = loginPage.enterPhoneEmail();
        passwordPage.enterPassword();
        //TODO Add a "Soft Assert"

        //Go to My Profile Page
        MyProfilePage myProfilePage = new MyProfilePage();
        myProfilePage.openMyProfile();
        Assert.assertTrue(myProfilePage.isAt(), "Failed to navigate to the My Profile page.");

        //Create Post and response the post ID
        VKApiUtils apiUtils = new VKApiUtils();
        String randomText = RandomUtils.generateRandomText(100);
        PostResponse postResponse = apiUtils.createPost(randomText);
        Assert.assertNotNull(postResponse, "The API response for post creation is null");
        int postId = postResponse.getPostId();
        Assert.assertTrue(postId > 0, "The post ID should be greater than 0");

        // Check for the presence of the post on the wall
        boolean isPostPresent = myProfilePage.isPostPresentById(postId);
        Assert.assertTrue(isPostPresent, "The post with the ID " + postId + " was not found on the profile wall.");

        // Get the upload server URL
        PhotoServerResponse PhotoServerResponse = apiUtils.uploadPhotoToServer();

        String imagePath = TestDataConfig.getImagePath();
        PhotoWallResponse PhotoWallResponse = apiUtils.uploadPhotoToWall(imagePath, PhotoServerResponse.getUpload_url());
        // TODO -  Assert photo upload wall properties

        PhotoSaveRespnse photoSaveRespnse = apiUtils.savePhotoToWall(PhotoWallResponse);
        String attachment = "photo" + photoSaveRespnse.getOwnerId()+ "_" + photoSaveRespnse.getPhotoId();
        System.out.println(attachment);
        String newText = "Edited post text " + RandomUtils.generateRandomText(10);
        PostResponse editPostResponse = apiUtils.editPostWithPhoto(postId, newText, attachment);
        // TODO -  Assert postEdit wall properties

        // Check if the edited post text appears on the page
        boolean isEditedTextPresent = myProfilePage.getPostTextEdited(newText);
        Assert.assertTrue(isEditedTextPresent, "The edited post text '" + newText + "' was not found on the page.");

        // Assert the photo is uploaded to the post using the photo ID obtained after saving the photo
        boolean isPhotoUploaded = myProfilePage.isPhotoPresentInPost(postId, photoSaveRespnse.getPhotoId(), photoSaveRespnse.getOwnerId());
        Assert.assertTrue(isPhotoUploaded, "The photo was not uploaded to the post.");

        // Create a comment on the post
        String commentText = RandomUtils.generateRandomText(100);
        CommentResponse commentResponse = apiUtils.addCommentToPost(postResponse.getPostId(), commentText);
        Assert.assertNotNull(commentResponse, "The API response for comment creation is null");

        int commentId = commentResponse.getCommentId();
        Assert.assertTrue(commentId > 0, "The comment ID should be greater than 0");

        String ownerId = photoSaveRespnse.getOwnerId();

        //Click on "Show new comment"
        boolean isShowCommentButtonClicked = myProfilePage.showNextComment(ownerId, postId);
        Assert.assertTrue(isShowCommentButtonClicked, "Failed to click 'Show next comment' button.");

        // Verify the comment through UI without refreshing the page
        boolean isCommentAdded = myProfilePage.isCommentPresentByCommentId(ownerId, commentId);
        Assert.assertTrue(isCommentAdded, "The comment with text '" + commentText + "' was not found on the post in the UI.");

        // Like the post
        myProfilePage.likePost(ownerId, postId);

        // Find user who likes the post
        LikeResponse LikeResponse = apiUtils.checkLikedByUser(ownerId, "post", postId);
        Assert.assertEquals(LikeResponse.getLiked(), 1, "The owner did not like their own post.");

        //Delete the post and assert deletion was successful
        PostDeleteResponse postDeleteResponse = apiUtils.deletePost(postResponse.getPostId());
        Assert.assertTrue(postDeleteResponse.isDeleted(), "The post was not deleted.");

        //Check whether the post is deleted through UI
        boolean isPostDeleted = myProfilePage.isPostDeleted(ownerId, postId);
        Assert.assertTrue(isPostDeleted, "The post was not deleted (still visible on the page).");
    }

}
