package pages;

import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import config.TestUserConfig;
import org.openqa.selenium.By;

public class PasswordPage extends Form {
    private final ITextBox txtBoxPassword = getElementFactory().getTextBox(By.name("password"), "Password");
    private final ITextBox btnLogin = getElementFactory().getTextBox(By.className("vkuiButton"), "Login Button");

    public PasswordPage() {
        super(By.xpath("//form[@action='/login/password']"), "Password Page");
    }

    public void enterPassword() {
        String password = TestUserConfig.getPassword();
        txtBoxPassword.clearAndType(password);
        btnLogin.click();
    }
}
