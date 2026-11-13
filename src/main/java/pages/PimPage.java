package pages;

import basePackage.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utilities.SelUtils;
import utilities.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class PimPage extends BaseTest {

    public PimPage() {
        PageFactory.initElements(wd, this);
    }

    public String pimPageUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/pim/viewEmployeeList";
    @FindBy(xpath = "(//div[@role='listbox']//div[@role='option'])[1]")
    private WebElement firstNameHint;

    @FindBy(xpath = "//div[@class='oxd-table-body']")
    private WebElement resultsTable;

    @FindBy(xpath = "(//div[@class='oxd-table-body']"
            + "//i[contains(@class,'bi-pencil-fill')])[1]")
    private WebElement firstEditButton;

    @FindBy(xpath = "(//div[@class='oxd-table-body']//i[contains(@class,'bi-trash')])[1]")
    private WebElement deleteButton;

    @FindBy(xpath = "//*[@role='dialog']//button[normalize-space()='Yes, Delete']")
    private WebElement deleteRecordButton;


    @FindBy(xpath = "(//input[@placeholder=\"Type for hints...\"])[1]")
    private WebElement employeeNameInputTextBox;

    @FindBy(xpath = "//label[normalize-space()='Employee Id']/parent::div/following-sibling::div//input")
    private WebElement employeeIdInput;

    @FindBy(xpath = "//button[@type=\"submit\"]")
    private WebElement submitButton;

    @FindBy(xpath = "//button[@type='reset']")
    private WebElement resetButton;

    @FindBy(xpath="//a[text()='Add Employee']")
    private WebElement addEmployeeLink;

    @FindBy(css = ".oxd-toast-content--success")
    private WebElement successMessage;

    @FindBy(xpath = "//span[contains(normalize-space(), 'Records Found')"
            + " or contains(normalize-space(), 'Record Found')]")
    private WebElement recordsFoundMessage;

    @FindBy(xpath = "//a[text()='Reports']")
    private WebElement reportsLink;

    public List<String> getResultRows() {
        SelUtils.waitForElementVisible(recordsFoundMessage);
        return wd.findElements(By.cssSelector(".oxd-table-body .oxd-table-row"))
                .stream().map(row -> StringUtils.normalizeWhitespace(row.getText())).collect(Collectors.toList());
    }

    public String getRecordsFoundMessage() {
        return SelUtils.getText(recordsFoundMessage);
    }

    public String getEmployeeNameFilter() {
        SelUtils.waitForElementVisible(employeeNameInputTextBox);
        return employeeNameInputTextBox.getDomProperty("value");
    }

    public String getSuccessMessage() {
        return SelUtils.getText(successMessage);
    }

   public AddEmployeePage clickAddEmployeeLink() {
       SelUtils.clickElement(addEmployeeLink);
      return new AddEmployeePage();
   }

    public void clickResetButton() {
        SelUtils.clickElement(resetButton);
        SelUtils.getWait().until(
                ExpectedConditions.domPropertyToBe(employeeNameInputTextBox, "value", ""));
    }

    public void enterEmployeeName(String employeeName) {
        SelUtils.clickElement(employeeNameInputTextBox);
        employeeNameInputTextBox.clear();
        employeeNameInputTextBox.sendKeys(employeeName);
        selectFirstNameHint();
    }

    public void enterEmployeeId(String employeeId) {
        SelUtils.clearAndSendKeys(employeeIdInput, employeeId);
    }

    /** Deletes only the exact generated employee, checking both ID and name. */
    public boolean deleteCreatedEmployee(String employeeId, String firstName, String lastName) {
        enterEmployeeId(employeeId);
        clickSubmitButton();
        List<WebElement> rows = wd.findElements(By.cssSelector(".oxd-table-body .oxd-table-row"));
        if (rows.isEmpty()) {
            return false;
        }
        if (rows.size() != 1) {
            throw new IllegalStateException("Refusing to delete multiple employees for a generated ID");
        }
        WebElement row = rows.get(0);
        List<WebElement> cells = row.findElements(By.cssSelector(".oxd-table-cell"));
        if (cells.size() < 4 || !employeeId.equals(cells.get(1).getText().trim())
                || !firstName.equals(cells.get(2).getText().trim())
                || !lastName.equals(cells.get(3).getText().trim())) {
            throw new IllegalStateException("Refusing to delete an employee with a different identity");
        }
        SelUtils.clickElement(row.findElement(By.cssSelector("button:has(.bi-trash)")));
        clickDeleteRecordButton();
        SelUtils.getWait().until(ExpectedConditions.invisibilityOf(deleteRecordButton));
        SelUtils.getWait().until(ExpectedConditions.numberOfElementsToBe(
                By.cssSelector(".oxd-table-body .oxd-table-row"), 0));
        return true;
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
        SelUtils.getWait().until(
                ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".oxd-loading-spinner")));
        SelUtils.waitForElementVisible(recordsFoundMessage);
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



    public ReportsPage clickReportsLink() {
        SelUtils.clickElement(reportsLink);
        SelUtils.waitForUrlContains("/pim/viewDefinedPredefinedReports");
        return new ReportsPage();
    }
}
