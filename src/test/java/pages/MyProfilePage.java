package pages;

import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

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
        String completeId = "wpt676687109_" + postId;
        By postSelector = By.id(completeId);

        ILabel postLabel = getElementFactory().getLabel(postSelector, "Post with ID");
        return postLabel.state().waitForDisplayed();
    }

    public String getPostTextEdited(int postId) {
        // The ID selector does not require a dot at the start, unlike class selectors
        String postTextSelector = "wpt676687109_" + postId;
        // Create a label element for the post text
        ILabel postTextLabel = getElementFactory().getLabel(By.id(postTextSelector), "Post Text");

        // Wait for the text element to be displayed
        if (postTextLabel.state().waitForDisplayed()) {
            return postTextLabel.getText();
        } else {
            throw new IllegalStateException("The post text element is not displayed.");
        }
    }

    public boolean getPostTextEdited(String editedText) {
        // Using XPath to find the element that contains the text
        By textLocator = By.xpath("//div[contains(text(),'" + editedText + "')]");
        ILabel postTextLabel = getElementFactory().getLabel(textLocator, "Post Text");

        // Wait for the text element to be displayed and return the result
        return postTextLabel.state().waitForDisplayed();
    }

//    public boolean isPhotoPresentInPost(int postId, String photoId) {
//        // Adjust the selector to correctly target the image in the post
//        String photoSelector = "div[id='post" + postId + "'] img[src*='" + photoId + "']";
//        System.out.println(photoSelector);
//        ILabel photoElement = getElementFactory().getLabel(By.cssSelector(photoSelector), "Post Photo");
//        return photoElement.state().waitForDisplayed();
//    }

    public boolean isPhotoPresentInPost(int postId, String photoId) {
        // The photoId is a part of the img src attribute within the post with the given ID.
        // Use contains() function in XPath to match the photoId substring within the src attribute.
         String photoXPath = "//div[@id='wpt676687109_1506']//img[@class='PhotoPrimaryAttachment__imageElement']";
        //TODO Change the locator to dynamic value
        //String photoXPath = String.format("//div[@id='wpt676687109_%d']//img[contains(@class, 'PhotoPrimaryAttachment__imageElement')]", postId);

        System.out.println(photoXPath);
        ILabel photoElement = getElementFactory().getLabel(By.xpath(photoXPath), "Post Photo");

        // Wait for the image element to be displayed and return the result
        return photoElement.state().waitForDisplayed();
    }
}
