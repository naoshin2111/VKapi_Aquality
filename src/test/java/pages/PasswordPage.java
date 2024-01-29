package pages;

import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import config.TestUserConfig;
import org.openqa.selenium.By;

public class PasswordPage extends Form {
    private final ITextBox TextBoxPassword = getElementFactory().getTextBox(By.name("password"), "Password");
    private final ITextBox ButtonLogin = getElementFactory().getTextBox(By.className("vkuiButton"), "Login Button");

    public PasswordPage() {
        super(By.xpath("//form[@action='/login/password']"), "Password Page");
    }

    public void enterPassword() {
        String password = TestUserConfig.getPassword();
        TextBoxPassword.clearAndType(password);
        ButtonLogin.click();
    }

    public boolean isPasswordEnteredCorrectly() {
        return TextBoxPassword.getValue().equals(TestUserConfig.getPassword());
    }
}
