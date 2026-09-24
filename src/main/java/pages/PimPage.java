package pages;

import org.example.BaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.SelUtils;
import utilities.WaitUtils;

public class PimPage extends BaseTest {

    public PimPage(WebDriver wd) {
        PageFactory.initElements(wd, this);
    }

    @FindBy(xpath = "(//input[@placeholder=\"Type for hints...\"])[1]")
    private WebElement employeeNameInput;

    @FindBy(xpath = "//button[@type=\"submit\"]")
    private WebElement submitButton;

    @FindBy(xpath = "//button[@type=\"button\"]//i[@class=\"oxd-icon bi-pencil-fill\"]")
    private WebElement editButton;

    public void enterEmployeeName(String employeeName) {
        WaitUtils.waitForElementClickable(employeeNameInput);
        employeeNameInput.sendKeys(employeeName);
    }

    public void clickSubmitButton() {
        WaitUtils.waitForElementClickable(submitButton);
        submitButton.click();
    }

    public void scrollToEditButton() {
        SelUtils.scrollIntoView(editButton);
    }

    public PersonalDetailsPage clickEditButton() {
        WaitUtils.waitForElementClickable(editButton);
        editButton.click();
        return new PersonalDetailsPage(wd);
    }




}
