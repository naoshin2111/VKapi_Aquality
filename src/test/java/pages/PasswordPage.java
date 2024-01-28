package pages;

import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import config.TestUserConfig;
import org.openqa.selenium.By;

public class PasswordPage extends Form {
    private final ITextBox TEXT_BOX_PASSWORD = getElementFactory().getTextBox(By.name("password"), "Password");
    private final ITextBox BUTTON_LOGIN = getElementFactory().getTextBox(By.className("vkuiButton"), "Login Button");

    public PasswordPage() {
        super(By.xpath("//form[@action='/login/password']"), "Password Page");
    }

    public void enterPassword() {
        String password = TestUserConfig.getPassword();
        TEXT_BOX_PASSWORD.clearAndType(password);
        BUTTON_LOGIN.click();
    }
}
