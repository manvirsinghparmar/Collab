package pages;

import basePackage.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utilities.SelUtils;

import java.time.Duration;
import java.util.List;

public class LeavePage extends BaseTest {

    private static final By RESULTS_BODY = By.cssSelector(".oxd-table-body");

    public LeavePage() {
        PageFactory.initElements(getDriver(), this);
    }

    @FindBy(xpath = "//label[text()='From Date']/parent::div/following-sibling::div//input")
    private WebElement fromDateField;

    @FindBy(xpath = "//label[text()='To Date']/parent::div/following-sibling::div//input")
    private WebElement toDateField;

    @FindBy(xpath = "(//div[@class='oxd-select-text-input'])[1]")
    private WebElement leaveStatusDropdown;

    @FindBy(xpath = "(//div[@class='oxd-select-text-input'])[2]")
    private WebElement leaveTypeDropdown;

    @FindBy(css = "input[placeholder='Type for hints...']")
    private WebElement employeeNameField;

    @FindBy(xpath = "//button[text()=' Search ']")
    private WebElement searchButtonForLeaveList;

    @FindBy(css = ".oxd-table-body .oxd-table-row")
    private List<WebElement> tableRows;

    @FindBy(css = "span[class='oxd-text oxd-text--span']")
    private WebElement recordsFoundMessage;

    @FindBy(xpath = "//a[text()='Apply']")
    private WebElement applyButton;

    @FindBy(css = ".oxd-select-text")
    private WebElement leaveTypeDropdownUnderApplyLeave;

    @FindBy(xpath = "//div[@role='listbox']//span")
    private List<WebElement> leaveTypeOptions;

    @FindBy(xpath = "//div[@role='listbox']//span[text()='CAN - Vacation']")
    private WebElement vacationLeave;

    @FindBy(xpath = "(//input[@placeholder='yyyy-dd-mm'])[1]")
    private WebElement fromDateUnderApplyLeave;

    @FindBy(xpath = "(//input[@placeholder='yyyy-dd-mm'])[2]")
    private WebElement toDateUnderApplyLeave;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement applyButtonForLeave;

    @FindBy(css = ".oxd-toast-content")
    private WebElement messagePrompt;


    public void enterFromDate(String fromDate) {
        SelUtils.enterDate(fromDateField, fromDate);
    }

    public void enterToDate(String toDate) {
        SelUtils.enterDate(toDateField, toDate);
    }

    public void enterEmployeeName(String employeeFirstName, String employeeFullName) {
        By option = By.xpath("//div[@role='listbox']//span[normalize-space()='" + employeeFullName + "']");
        queryEmployeeName(employeeFirstName);
        WebElement suggestion;
        try {
            suggestion = SelUtils.getWait().withTimeout(Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(option));
        } catch (TimeoutException e) {
            // The demo can dismiss the autocomplete while a matching employee exists.
            queryEmployeeName(employeeFirstName);
            suggestion = SelUtils.getWait().until(ExpectedConditions.elementToBeClickable(option));
        }
        SelUtils.clickElement(suggestion);
    }

    private void queryEmployeeName(String employeeFirstName) {
        SelUtils.clickElement(employeeNameField);
        SelUtils.clearAndSendKeys(employeeNameField, employeeFirstName);
    }

    public void clickSearchButton() {
        WebElement previousResults = SelUtils.getWait().until(ExpectedConditions.presenceOfElementLocated(RESULTS_BODY));
        SelUtils.clickElement(searchButtonForLeaveList);
        SelUtils.getWait().until(ExpectedConditions.stalenessOf(previousResults));
        SelUtils.getWait().until(ExpectedConditions.presenceOfElementLocated(RESULTS_BODY));
        SelUtils.waitForElementVisible(recordsFoundMessage);
    }

    public boolean isLeaveListTableEmpty() {
        return tableRows.isEmpty();
    }

    public String getRecordsFoundMessage() {
        return SelUtils.getText(recordsFoundMessage);
    }

    public void clickApplyButton() {
        SelUtils.clickElement(applyButton);
    }

    public void selectLeaveType(String leaveType) {
        SelUtils.waitForElementClickable(leaveTypeDropdownUnderApplyLeave);
        leaveTypeDropdownUnderApplyLeave.click();
        for (WebElement option : leaveTypeOptions) {
            if (option.getText().equalsIgnoreCase(leaveType)) {
                SelUtils.waitForElementClickable(option);
                option.click();
                break;
            }
        }
    }

    public void enterFromDateUnderApplyLeave(String fromDate) {
        SelUtils.enterDate(fromDateUnderApplyLeave, fromDate);
    }

    public void enterToDateUnderApplyLeave(String toDate) {
        SelUtils.enterDate(toDateUnderApplyLeave, toDate);
    }

    public void clickApplyButtonForLeave() {
        SelUtils.clickElement(applyButtonForLeave);
    }

    public String getMessagePrompt() {
        return SelUtils.getText(messagePrompt);
    }

}
