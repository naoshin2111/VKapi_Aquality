package pages;

import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import config.TestUserConfig;

public class LoginPage extends Form {
    private final ITextBox TXT_BOX_PHONE_EMAIL = getElementFactory().getTextBox(By.name("login"), "Phone or Email");
    private final ITextBox CONTINUE_BUTTON = getElementFactory().getTextBox(By.cssSelector("button[type='submit'] span[class='FlatButton__in']"), "Continue Button");

    public LoginPage() {
        super(By.xpath("//div[@class=\"VkIdForm\"]"), "Login Page");
    }

    public PasswordPage enterPhone() {
        String login = TestUserConfig.getLogin();
        TXT_BOX_PHONE_EMAIL.clearAndType(login);
        CONTINUE_BUTTON.click();
        return new PasswordPage();
    }

    public boolean isPhoneNumberEnteredCorrectly() {
        return TXT_BOX_PHONE_EMAIL.getValue().equals(TestUserConfig.getLogin());
    }
}
