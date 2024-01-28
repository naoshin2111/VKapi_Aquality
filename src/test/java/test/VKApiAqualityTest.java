package test;

import config.EnvironmentConfig;
import config.TestDataConfig;
import model.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import pages.MyProfilePage;
import pages.PasswordPage;
import utils.RandomUtils;
import utils.VKApiUtils;
import static aquality.selenium.browser.AqualityServices.getBrowser;

public class VKApiAqualityTest extends BaseTest{

    private final SoftAssert softAssert = new SoftAssert();

    @Test
    public void VKApitest() {

        getBrowser().goTo(EnvironmentConfig.getUrl());
        Assert.assertTrue(getBrowser().getCurrentUrl().contains(EnvironmentConfig.getUrl()), "The browser did not navigate to the expected URL.");

        LoginPage loginPage = new LoginPage();
        PasswordPage passwordPage = loginPage.enterPhone();
        softAssert.assertTrue(loginPage.isPhoneNumberEnteredCorrectly(), "Phone/Email was not entered correctly.");

        passwordPage.enterPassword();
        softAssert.assertTrue(passwordPage.isPasswordEnteredCorrectly(), "Password was not entered correctly.");

        softAssert.assertAll();

        MyProfilePage myProfilePage = new MyProfilePage();
        myProfilePage.openMyProfile();
        Assert.assertTrue(myProfilePage.isAt(), "Failed to navigate to the My Profile page.");

        VKApiUtils apiUtils = new VKApiUtils();
        String randomText = RandomUtils.generateRandomText(100);
        PostResponse postResponse = apiUtils.createPost(randomText);
        Assert.assertNotNull(postResponse, "The API response for post creation is null");

        int postId = postResponse.getPostId();

        boolean isPostPresent = myProfilePage.isPostPresentById(postId);
        Assert.assertTrue(isPostPresent, "The post with the ID " + postId + " was not found on the profile wall.");

        PhotoServerResponse PhotoServerResponse = apiUtils.uploadPhotoToServer();

        String imagePath = TestDataConfig.getImagePath();
        PhotoWallResponse photoWallResponse = apiUtils.uploadPhotoToWall(imagePath, PhotoServerResponse.getUpload_url());
        Assert.assertNotNull(photoWallResponse, "Failed to upload photo to the wall.");
        Assert.assertFalse(photoWallResponse.getPhoto().isEmpty(), "Photo string is empty after uploading to the wall.");

        PhotoSaveResponse photoSaveResponse = apiUtils.savePhotoToWall(photoWallResponse);
        Assert.assertNotNull(photoSaveResponse, "Failed to save photo to the wall.");
        Assert.assertNotNull(photoSaveResponse.getPhotoId(), "Photo ID is null after saving photo to the wall.");
        Assert.assertNotNull(photoSaveResponse.getOwnerId(), "Owner ID is null after saving photo to the wall.");

        String attachment = "photo" + photoSaveResponse.getOwnerId()+ "_" + photoSaveResponse.getPhotoId();
        String newText = "Edited post text " + RandomUtils.generateRandomText(10);
        PostResponse editPostResponse = apiUtils.editPostWithPhoto(postId, newText, attachment);
        Assert.assertNotNull(editPostResponse, "Failed to edit post.");
        Assert.assertEquals(editPostResponse.getPostId(), postId, "Edited post ID does not match the expected post ID.");

        boolean isEditedTextPresent = myProfilePage.getPostTextEdited(newText);
        Assert.assertTrue(isEditedTextPresent, "The edited post text '" + newText + "' was not found on the page.");

        boolean isPhotoUploaded = myProfilePage.isPhotoPresentInPost(postId, photoSaveResponse.getPhotoId(), photoSaveResponse.getOwnerId());
        Assert.assertTrue(isPhotoUploaded, "The photo was not uploaded to the post.");

        String commentText = RandomUtils.generateRandomText(100);
        CommentResponse commentResponse = apiUtils.addCommentToPost(postResponse.getPostId(), commentText);
        Assert.assertNotNull(commentResponse, "The API response for comment creation is null");

        int commentId = commentResponse.getCommentId();
        String ownerId = photoSaveResponse.getOwnerId();

        boolean isShowCommentButtonClicked = myProfilePage.showNextComment(ownerId, postId);
        Assert.assertTrue(isShowCommentButtonClicked, "Failed to click 'Show next comment' button.");

        boolean isCommentAdded = myProfilePage.isCommentPresentByCommentId(ownerId, commentId);
        Assert.assertTrue(isCommentAdded, "The comment with text '" + commentText + "' was not found on the post in the UI.");

        myProfilePage.likePost(ownerId, postId);
        LikeResponse LikeResponse = apiUtils.checkLikedByUser(ownerId, "post", postId);
        Assert.assertEquals(LikeResponse.getLiked(), 1, "The owner did not like their own post.");

        PostDeleteResponse postDeleteResponse = apiUtils.deletePost(postResponse.getPostId());
        Assert.assertTrue(postDeleteResponse.isDeleted(), "The post was not deleted.");

        boolean isPostDeleted = myProfilePage.isPostDeleted(ownerId, postId);
        Assert.assertTrue(isPostDeleted, "The post was not deleted (still visible on the page).");
    }
}
