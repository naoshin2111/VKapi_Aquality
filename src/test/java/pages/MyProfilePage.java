package pages;

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
        // Implementation depends on what uniquely identifies the "My Profile" page.
        // You may need to update the locator to a unique element on the "My Profile" page.
        return myProfileLink.state().isDisplayed();
    }
}
