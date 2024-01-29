package pages;

import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import config.TestUserConfig;

public class LoginPage extends Form {
    private final ITextBox txtBoxPhoneEmail = getElementFactory().getTextBox(By.xpath("//input[@id='index_email']"), "Phone or Email");
    private final ITextBox ContinueButton = getElementFactory().getTextBox(By.cssSelector("button[type='submit'] span[class='FlatButton__in']"), "Continue Button");

    public LoginPage() {
        super(By.xpath("//div[@id='index_rcolumn']"), "Login Page");
    }

    public PasswordPage enterPhone(String login) {
        txtBoxPhoneEmail.clearAndType(login);
        ContinueButton.click();
        return new PasswordPage();
    }
}
