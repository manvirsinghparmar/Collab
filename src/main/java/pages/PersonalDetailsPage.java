package pages;

import org.example.BaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.WaitUtils;

public class PersonalDetailsPage extends BaseTest {

    public PersonalDetailsPage(WebDriver wd) {
        PageFactory.initElements(wd, this);
    }

    @FindBy(xpath = "//a[text()='Contact Details']")
    private WebElement contactDetailsLink;

    public ContactDetailsPage clickcontactdetailslink() {
        WaitUtils.waitForElementClickable(contactDetailsLink).click();
        return new ContactDetailsPage(wd);
    }


}
