package pages;

import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class NewsfeedPage extends Form {

    private final LeftForm leftForm;

    public NewsfeedPage() {
        super(By.xpath("//div[@id='feed_rmenu']"), "News Feed Page");
        leftForm = new LeftForm();
    }

    public LeftForm getLeftForm(){
        return leftForm;
    }
}
