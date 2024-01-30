package test;

import config.EnvironmentConfig;
import config.TestDataConfig;
import config.TestUserConfig;
import model.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MyProfilePage;
import pages.NewsfeedPage;
import pages.PasswordPage;
import utils.RandomUtils;
import utils.VKApiUtils;
import static aquality.selenium.browser.AqualityServices.getBrowser;

public class VKApiAqualityTest extends BaseTest{

    private static final int POST_TEXT = 100;
    private static final int EDIT_POST_TEXT = 10;
    private LoginPage loginPage;
    private PasswordPage passwordPage;
    private MyProfilePage myProfilePage;

    private NewsfeedPage newsfeedPage;

    @Test
    public void VKApitest() {

        getBrowser().goTo(EnvironmentConfig.getUrl());

        loginPage = new LoginPage();
        String login = TestUserConfig.getLogin();

        passwordPage = loginPage.enterPhone(login);
        passwordPage.enterPassword();
        newsfeedPage = new NewsfeedPage();
        newsfeedPage.openMyProfile();
        myProfilePage = new MyProfilePage();

        VKApiUtils apiUtils = new VKApiUtils();
        String randomText = RandomUtils.generateRandomText(POST_TEXT);
        PostResponse postResponse = apiUtils.createPost(randomText);

        boolean isPostPresent = myProfilePage.isPostPresentById(postResponse.getPostId());
        Assert.assertTrue(isPostPresent, "The post with the ID " + postResponse.getPostId() + " was not found on the profile wall.");

        PhotoServerResponse PhotoServerResponse = apiUtils.uploadPhotoToServer();

        String imagePath = TestDataConfig.getImagePath();
        PhotoWallResponse photoWallResponse = apiUtils.uploadPhotoToWall(imagePath, PhotoServerResponse.getUploadUrl());
        Assert.assertFalse(photoWallResponse.getPhoto().isEmpty(), "Photo string is empty after uploading to the wall.");

        PhotoSaveResponse photoSaveResponse = apiUtils.savePhotoToWall(photoWallResponse);

        String attachment = "photo" + photoSaveResponse.getOwnerId()+ "_" + photoSaveResponse.getPhotoId();
        String newText = RandomUtils.generateRandomText(EDIT_POST_TEXT);
        PostResponse editPostResponse = apiUtils.editPostWithPhoto(postResponse.getPostId(), newText, attachment);

        String editedPostText = myProfilePage.getPostTextEdited(postResponse.getPostId());
        Assert.assertEquals(editedPostText, newText,"The edited post text '" + newText + "' was not found on the page.");

        boolean isPhotoUploaded = myProfilePage.isPhotoPresentInPost(postResponse.getPostId(), photoSaveResponse);
        Assert.assertTrue(isPhotoUploaded, "The photo was not uploaded to the post.");

        String commentText = RandomUtils.generateRandomText(100);
        CommentResponse commentResponse = apiUtils.addCommentToPost(postResponse.getPostId(), commentText);

        myProfilePage.showNextComment(postResponse.getPostId());

        boolean isCommentAdded = myProfilePage.isCommentPresentByCommentId(commentResponse.getCommentId());
        Assert.assertTrue(isCommentAdded, "The comment with text '" + commentText + "' was not found on the post in the UI.");

        myProfilePage.likePost(photoSaveResponse.getOwnerId(), postResponse.getPostId());
        LikeResponse LikeResponse = apiUtils.checkLikedByUser(photoSaveResponse.getOwnerId(), "post", postResponse.getPostId());
        Assert.assertEquals(LikeResponse.getLiked(), 1, "The owner did not like their own post.");

        PostDeleteResponse postDeleteResponse = apiUtils.deletePost(postResponse.getPostId());
        Assert.assertEquals(postDeleteResponse.getResponse(), 1, "The post was not deleted.");

        boolean isPostDeleted = myProfilePage.isPostDeleted(photoSaveResponse.getOwnerId(), postResponse.getPostId());
        Assert.assertTrue(isPostDeleted, "The post was not deleted (still visible on the page).");
    }
}
