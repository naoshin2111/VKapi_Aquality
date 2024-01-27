package pages;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import java.time.Duration;

public class MyProfilePage extends Form {

    private final ILink myProfileLink = getElementFactory().getLink(By.cssSelector("li[id='l_pr'] a[class='LeftMenuItem-module__item--XMcN9']"), "My Profile");

    public MyProfilePage() {
        super(By.id("main_feed"), "My Profile Page");
    }

    public void openMyProfile() {
        myProfileLink.state().waitForDisplayed();
        myProfileLink.click();
    }

    public boolean isAt() {
        return myProfileLink.state().isDisplayed();
    }

    public boolean isPostPresentById(int postId) {

        String postXPath = String.format("//div[contains(@id, 'wpt676687109_%d')]", postId);
        By postSelector = By.xpath(postXPath);
        ILabel postLabel = getElementFactory().getLabel(postSelector, "Post with ID");

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

    public boolean showNextComment(String ownerId, int postId) {
        String showNextCommentXPath = String.format("//div[contains(@id, 'replies%s_%d')]//span[contains(@class, 'js-replies_next_label')]", ownerId, postId);
        By showNextCommentLocator = By.xpath(showNextCommentXPath);
        ILink showNextCommentButton = getElementFactory().getLink(showNextCommentLocator, "Show Next Comment");
        showNextCommentButton.state().waitForDisplayed();

        if (showNextCommentButton.state().isClickable()) {
            showNextCommentButton.click();

            return true;
        } else {
            return false;
        }
    }

    public boolean isCommentPresentByCommentId(String ownerId, int commentId) {
        String commentXPath = String.format("//div[contains(@id, 'post%s_%d')]", ownerId, commentId);
        ILabel commentLabel = getElementFactory().getLabel(By.xpath(commentXPath), "Comment Text");

        return commentLabel.state().waitForDisplayed();
    }

    public void likePost(String ownerId, int postId) {
        String likeButtonXPath = String.format("//div[@class='like_wrap _like_wall%s_%d ']//div[@class='PostButtonReactions__icon ']//*[name()='svg']", ownerId, postId);
        IButton likeButton = getElementFactory().getButton(By.xpath(likeButtonXPath), "Like Button");
        if (likeButton.state().isDisplayed()) {
            likeButton.click();
        } else {
            throw new IllegalStateException("Like button for post with ID " + postId + " is not displayed.");
        }
    }

    public boolean isPostDeleted(String ownerId, int postId) {
        String postXPath = String.format("//div[contains(@id, 'wpt%s_%d')]", ownerId, postId);
        By postSelector = By.xpath(postXPath);

        try {
            ILabel postLabel = getElementFactory().getLabel(postSelector, "Post with ID");
            return !postLabel.state().waitForDisplayed(Duration.ofSeconds(5));
        } catch (NoSuchElementException | TimeoutException e) {
            return true;
        }
    }
}
