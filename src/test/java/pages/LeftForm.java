package pages;

import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import static aquality.selenium.browser.AqualityServices.getElementFactory;

public class LeftForm extends Form {

    private final ILink myProfileLink = getElementFactory().getLink(By.id("l_pr"), "My Profile");

    public LeftForm() {
        super(By.xpath(""), "Left Form Page");
    }

    public void openMyProfile() {
        myProfileLink.state().waitForDisplayed();
        myProfileLink.click();
    }
}
