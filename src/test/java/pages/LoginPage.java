package pages;

import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import config.TestUserConfig;

public class LoginPage extends Form {
    private final ITextBox txtBoxPhoneEmail = getElementFactory().getTextBox(By.name("login"), "Phone or Email");
    private final ITextBox btnContinue = getElementFactory().getTextBox(By.cssSelector("button[type='submit'] span[class='FlatButton__in']"), "Continue Button");

    public LoginPage() {
        super(By.xpath("//div[@class=\"VkIdForm\"]"), "Login Page");
    }

    public PasswordPage enterPhoneEmail() {
        String login = TestUserConfig.getLogin();
        txtBoxPhoneEmail.clearAndType(login);
        btnContinue.click();
        return new PasswordPage();
    }
}
