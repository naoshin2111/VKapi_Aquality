package pages;

import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class NewsfeedPage extends Form {

    private final ILink MyProfileLink = getElementFactory().getLink(By.id("l_pr"), "My Profile");

    public NewsfeedPage() {
        super(By.xpath("//div[@id='feed_rmenu']"), "News Feed Page");
    }

    public void openMyProfile() {
        MyProfileLink.state().waitForDisplayed();
        MyProfileLink.click();
    }
}
