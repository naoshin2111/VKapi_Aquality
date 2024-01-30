package pages;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import model.PhotoSaveResponse;
import org.openqa.selenium.By;

public class MyProfilePage extends Form {

    private static final String POST_X_PATH = "//div[contains(@id, '%d')]";
    private static final String POST_TEXT_X_PATH = "//div[contains(@id, '%d')]//div[@class='wall_text']";
    private static final String PHOTO_X_PATH = "//div[contains(@id,'wpt%s_%d')]//a[contains(@href,'%s')]";
    private static final String SHOW_NEXT_COMMENT_X_PATH = "//div[contains(@id, '%d')]//span[contains(@class, 'js-replies_next_label')]";
    private static final String COMMENT_X_PATH = "//div[contains(@id, '%d')]";
    private static final String LIKE_BUTTON_X_PATH = "//div[@class='like_wrap _like_wall%s_%d ']//div[@class='PostButtonReactions__icon ']//*[name()='svg']";
    private static final String POST_DELETED_X_PATH = "//div[contains(@id, 'wpt%s_%d')]";

    public MyProfilePage() {
        super(By.id("main_feed"), "My Profile Page");
    }

    public boolean isPostPresentById(int postId) {
        ILabel postLabel = getElementFactory().getLabel(By.xpath(String.format(POST_X_PATH, postId)), "Post with ID");
        return postLabel.state().waitForDisplayed();
    }

    public String getPostTextEdited(int postId) {
        ILabel postTextLabel = getElementFactory().getLabel(By.xpath(String.format(POST_TEXT_X_PATH, postId)), "Post Text");
        System.out.println(postTextLabel.getText());
        postTextLabel.state().waitForDisplayed();
        return postTextLabel.getText();
    }

    public boolean isPhotoPresentInPost(int postId, PhotoSaveResponse photoSaveResponse) {
        ILabel photoElement = getElementFactory().getLabel(By.xpath(String.format(PHOTO_X_PATH, photoSaveResponse.getOwnerId(), postId, photoSaveResponse.getPhotoId())), "Post Photo");
        return photoElement.state().waitForDisplayed();
    }

    public void showNextComment(int postId) {
        ILink showNextCommentButton = getElementFactory().getLink(By.xpath(String.format(SHOW_NEXT_COMMENT_X_PATH, postId)), "Show Next Comment");
        showNextCommentButton.state().waitForDisplayed();
        showNextCommentButton.click();
    }

    public boolean isCommentPresentByCommentId( int commentId) {
        ILabel commentLabel = getElementFactory().getLabel(By.xpath(String.format(COMMENT_X_PATH, commentId)), "Comment Text");
        return commentLabel.state().waitForDisplayed();
    }

    public void likePost(String ownerId, int postId) {
        IButton likeButton = getElementFactory().getButton(By.xpath(String.format(LIKE_BUTTON_X_PATH, ownerId, postId)), "Like Button");
        likeButton.state().isDisplayed();
        likeButton.click();
    }

    public boolean isPostDeleted(String ownerId, int postId) {
        ILabel postLabel = getElementFactory().getLabel(By.xpath(String.format(POST_DELETED_X_PATH, ownerId, postId)), "Post with ID");
        return postLabel.state().waitForNotDisplayed();
    }
}
