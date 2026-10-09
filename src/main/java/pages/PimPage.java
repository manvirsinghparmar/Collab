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

    public PimPage() {
        PageFactory.initElements(wd, this);
    }

    @FindBy(xpath = "(//div[@role='listbox']//div[@role='option'])[1]")
    private WebElement firstNameHint;

    @FindBy(xpath = "//div[@class='oxd-table-body']")
    private WebElement resultsTable;

    @FindBy(xpath = "(//div[@class='oxd-table-body']"
            + "//i[contains(@class,'bi-pencil-fill')])[1]")
    private WebElement firstEditButton;

    @FindBy(xpath = "(//div[@class='oxd-table-body']//i[contains(@class,'bi-trash')])[1]")
    private WebElement deleteButton;

    @FindBy(xpath = "(//button[@type='button'])[9]")
    private WebElement deleteRecordButton;


    @FindBy(xpath = "(//input[@placeholder=\"Type for hints...\"])[1]")
    private WebElement employeeNameInputTextBox;

    @FindBy(xpath = "//button[@type=\"submit\"]")
    private WebElement submitButton;

    @FindBy(xpath = "//button[@type='reset']")
    private WebElement resetButton;

    @FindBy(xpath="//a[text()='Add Employee']")
    private WebElement addEmployeeLink;

    public AddEmployeePage clickAddEmployeeLink() {
        SelUtils.clickElement(addEmployeeLink);
        return new AddEmployeePage();
    }

    public void clickResetButton() {
        SelUtils.clickElement(resetButton);
    }

    public void enterEmployeeName(String employeeName) {
        SelUtils.clickElement(employeeNameInputTextBox);
        employeeNameInputTextBox.clear();
        employeeNameInputTextBox.sendKeys(employeeName);
        selectFirstNameHint();
    }

    private void selectFirstNameHint() {
        try {
            SelUtils.clickElement(firstNameHint);
        } catch (TimeoutException e) {
            // No hints offered (e.g. a partial name); fall back to the raw text.
        }
    }

    public void clickSubmitButton() {
        SelUtils.clickElement(submitButton);
        SelUtils.waitForElementVisible(resultsTable);
    }

    public void scrollToEditButton() {
        SelUtils.scrollIntoView(firstEditButton);
    }

    public PersonalDetailsPage clickEditButton() {
        SelUtils.clickElement(firstEditButton);
        SelUtils.waitForUrlContains("/pim/viewPersonalDetails/empNumber/");
        return new PersonalDetailsPage();
    }

    public void clickDeleteButton() {
        SelUtils.clickElement(deleteButton);
    }

    public void clickDeleteRecordButton() {
        SelUtils.clickElement(deleteRecordButton);
    }



}
