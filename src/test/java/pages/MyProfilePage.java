package pages;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import constant.CommonConstants;
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
        System.out.println(completeId);
        By postSelector = By.id(completeId);

        ILabel postLabel = getElementFactory().getLabel(postSelector, "Post with ID");
        return postLabel.state().waitForDisplayed();
    }
}
