package test;

import config.EnvironmentConfig;
import config.TestDataConfig;
import config.TestUserConfig;
import models.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MyProfilePage;
import pages.NewsfeedPage;
import pages.PasswordPage;
import utils.RandomUtils;
import utils.VkApiUtils;
import static aquality.selenium.browser.AqualityServices.getBrowser;

public class VKApiAqualityTest extends BaseTest{

    private static final int POST_TEXT = 100;
    private static final int EDIT_POST_TEXT = 10;
    private static final String PHOTO_ATTACHMENT = "photo%s_%s";
    private LoginPage loginPage;
    private PasswordPage passwordPage;
    private NewsfeedPage newsfeedPage;
    private MyProfilePage myProfilePage;
    private VkApiUtils apiUtils;

    @Test
    public void VKApitest() {

        getBrowser().goTo(EnvironmentConfig.getUrl());

        String login = TestUserConfig.getLogin();
        loginPage = new LoginPage();
        loginPage.enterPhone(login);

        String password = TestUserConfig.getPassword();
        passwordPage = new PasswordPage();
        passwordPage.enterPassword(password);

        newsfeedPage = new NewsfeedPage();
        myProfilePage = new MyProfilePage();
        newsfeedPage.getLeftForm().openMyProfile();

        String randomText = RandomUtils.generateRandomText(POST_TEXT);
        apiUtils = new VkApiUtils();
        PostResponse postResponse = apiUtils.createPost(randomText);

        int postId = postResponse.getPostId();
        boolean isPostPresent = myProfilePage.isPostPresentById(postId);
        Assert.assertTrue(isPostPresent, "The post with the ID " + postId + " was not found on the profile wall.");

        PhotoServerResponse PhotoServerResponse = apiUtils.uploadPhotoToServer();

        String imagePath = TestDataConfig.getImagePath();
        PhotoWallResponse photoWallResponse = apiUtils.uploadPhotoToWall(imagePath, PhotoServerResponse.getUploadUrl());

        PhotoSaveResponse photoSaveResponse = apiUtils.savePhotoToWall(photoWallResponse);

        String attachment = String.format(PHOTO_ATTACHMENT, photoSaveResponse.getOwnerId(), photoSaveResponse.getPhotoId());

        String newText = RandomUtils.generateRandomText(EDIT_POST_TEXT);
        apiUtils.editPostWithPhoto(postId, newText, attachment);

        String editedPostText = myProfilePage.getPostText(postId);
        Assert.assertEquals(editedPostText, newText,"The edited post text '" + newText + "' was not found on the page.");

        boolean isPhotoUploaded = myProfilePage.isPhotoPresentInPost(postId, photoSaveResponse);
        Assert.assertTrue(isPhotoUploaded, "The photo was not uploaded to the post.");

        String commentText = RandomUtils.generateRandomText(100);
        CommentResponse commentResponse = apiUtils.addCommentToPost(postId, commentText);

        myProfilePage.showNextComment(postId);

        boolean isCommentAdded = myProfilePage.isCommentPresentByCommentId(commentResponse.getCommentId());
        Assert.assertTrue(isCommentAdded, "The comment with text '" + commentText + "' was not found on the post in the UI.");

        myProfilePage.likePost(postId);
        LikeResponse LikeResponse = apiUtils.checkLikedByUser(photoSaveResponse.getOwnerId(), "post", postId);
        Assert.assertEquals(LikeResponse.getLiked(), 1, "The owner did not like their own post.");

        apiUtils.deletePost(postId);

        boolean isPostDeleted = myProfilePage.isPostDeleted(postId);
        Assert.assertTrue(isPostDeleted, "The post was not deleted (still visible on the page).");
    }
}
