package pages;

import org.example.BaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.SelUtils;

public class PersonalDetailsPage extends BaseTest {

    public PersonalDetailsPage() {
        PageFactory.initElements(wd, this);
    }

    @FindBy(xpath = "//a[text()='Contact Details']")
    private WebElement contactDetailsLink;

    public ContactDetailsPage clickcontactdetailslink() {
        SelUtils.clickElement(contactDetailsLink);
        return new ContactDetailsPage();
    }


}
