package pages;

import org.example.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.SelUtils;

public class PimPage extends BaseTest {

    private static final By FIRST_NAME_HINT =
            By.xpath("(//div[@role='listbox']//div[@role='option'])[1]");

    private static final By RESULTS_TABLE =
            By.xpath("//div[@class='oxd-table-body']");

    private static final By FIRST_EDIT_BUTTON =
            By.xpath("(//div[@class='oxd-table-body']"
                    + "//i[contains(@class,'bi-pencil-fill')])[1]");

    public PimPage(WebDriver wd) {
        PageFactory.initElements(wd, this);
    }

    @FindBy(xpath = "(//input[@placeholder=\"Type for hints...\"])[1]")
    private WebElement employeeNameInputTextBox;

    @FindBy(xpath = "//button[@type=\"submit\"]")
    private WebElement submitButton;

    public void enterEmployeeName(String employeeName) {
        SelUtils.clickElement(employeeNameInputTextBox);
        employeeNameInputTextBox.clear();
        employeeNameInputTextBox.sendKeys(employeeName);
        selectFirstNameHint();
    }

    /**
     * The employee name field is an autocomplete: unless a hint is picked the
     * field is flagged "Invalid" and the search never returns any rows.
     */
    private void selectFirstNameHint() {
        try {
            SelUtils.clickElement(SelUtils.findElement(FIRST_NAME_HINT));
        } catch (TimeoutException e) {
            // No hints offered (e.g. a partial name); fall back to the raw text.
        }
    }

    public void clickSubmitButton() {
        SelUtils.clickElement(submitButton);
        SelUtils.findElement(RESULTS_TABLE);
    }

    public void scrollToEditButton() {
        SelUtils.scrollIntoView(SelUtils.findElement(FIRST_EDIT_BUTTON));
    }

    public PersonalDetailsPage clickEditButton() {
        SelUtils.clickElement(SelUtils.findElement(FIRST_EDIT_BUTTON));
        SelUtils.waitForUrlContains("/pim/viewPersonalDetails/empNumber/");
        return new PersonalDetailsPage(wd);
    }


}
