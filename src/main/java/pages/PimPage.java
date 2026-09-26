package pages;

import org.example.BaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.SelUtils;

public class PimPage extends BaseTest {

    public PimPage(WebDriver wd) {
        PageFactory.initElements(wd, this);
    }

    @FindBy(xpath = "(//input[@placeholder=\"Type for hints...\"])[1]")
    private WebElement employeeNameInputTextBox;

    @FindBy(xpath = "//button[@type=\"submit\"]")
    private WebElement submitButton;

    @FindBy(xpath = "(//button[contains(@class,'oxd-table-cell-action-space')])[1]")
    private WebElement editButton;

    public void enterEmployeeName(String employeeName) {
        WaitUtils.waitForElementClickable(employeeNameInputTextBox);
        employeeNameInputTextBox.sendKeys(employeeName);
    }

    public void clickSubmitButton() {
        WaitUtils.waitForElementClickable(submitButton);
        submitButton.click();
    }

    public void scrollToEditButton() {
        SelUtils.scrollToBottom();
    }

    public PersonalDetailsPage clickEditButton() {
        WaitUtils.waitForElementVisible(editButton);
        editButton.click();
        return new PersonalDetailsPage(wd);
    }




}
