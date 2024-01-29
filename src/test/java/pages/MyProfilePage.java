package pages;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class MyProfilePage extends Form {

    private final ILink MyProfileLink = getElementFactory().getLink(By.xpath("//span[contains(text(),'My profile')]"), "My Profile");



    public MyProfilePage() {
        super(By.id("main_feed"), "My Profile Page");
    }

    public void openMyProfile() {
        MyProfileLink.state().waitForDisplayed();
        MyProfileLink.click();
    }

    public boolean isPostPresentById(int postId) {
        String postXPath = String.format("//div[contains(@id, '%d')]", postId);
        By postSelector = By.xpath(postXPath);
        ILabel postLabel = getElementFactory().getLabel(By.xpath(String.format("//div[contains(@id, '%d')]", postId)), "Post with ID");

        return postLabel.state().waitForDisplayed();
    }

    public boolean getPostTextEdited(String editedText) {
        By textLocator = By.xpath("//div[contains(text(),'" + editedText + "')]");
        ILabel postTextLabel = getElementFactory().getLabel(textLocator, "Post Text");

        return postTextLabel.state().waitForDisplayed();
    }

    public boolean isPhotoPresentInPost(int postId, String photoId, String ownerId) {
        String photoXPath = String.format("//div[contains(@id,'wpt%s_%d')]//a[contains(@href,'%s')]", ownerId, postId, photoId);
        ILabel photoElement = getElementFactory().getLabel(By.xpath(photoXPath), "Post Photo");

        return photoElement.state().waitForDisplayed();
    }

    public void showNextComment(int postId) {
        String showNextCommentXPath = String.format("//div[contains(@id, '%d')]//span[contains(@class, 'js-replies_next_label')]", postId);
        By showNextCommentLocator = By.xpath(showNextCommentXPath);
        ILink showNextCommentButton = getElementFactory().getLink(showNextCommentLocator, "Show Next Comment");

        showNextCommentButton.state().waitForDisplayed();
        showNextCommentButton.click();
    }

    public boolean isCommentPresentByCommentId( int commentId) {
        String commentXPath = String.format("//div[contains(@id, '%d')]", commentId);
        ILabel commentLabel = getElementFactory().getLabel(By.xpath(commentXPath), "Comment Text");

        return commentLabel.state().waitForDisplayed();
    }

    public void likePost(String ownerId, int postId) {
        String likeButtonXPath = String.format("//div[@class='like_wrap _like_wall%s_%d ']//div[@class='PostButtonReactions__icon ']//*[name()='svg']", ownerId, postId);
        IButton likeButton = getElementFactory().getButton(By.xpath(likeButtonXPath), "Like Button");

        likeButton.state().isDisplayed();
        likeButton.click();
    }

    public boolean isPostDeleted(String ownerId, int postId) {
        String postXPath = String.format("//div[contains(@id, 'wpt%s_%d')]", ownerId, postId);
        By postSelector = By.xpath(postXPath);
        ILabel postLabel = getElementFactory().getLabel(postSelector, "Post with ID");

        return postLabel.state().waitForNotDisplayed();
    }
}
