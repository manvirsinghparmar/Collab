package pages;

import org.example.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utilities.WaitUtils;

import java.util.List;

public class LeavePage extends BaseTest {


    public LeavePage() {
        PageFactory.initElements(wd, this);
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
    private WebElement searchButton;

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
        WaitUtils.waitForElementVisible(fromDateField);
        fromDateField.clear();
        fromDateField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        fromDateField.sendKeys(fromDate);
        fromDateField.sendKeys(Keys.TAB);
    }

    public void enterToDate(String toDate) {
        WaitUtils.waitForElementVisible(toDateField);
        toDateField.clear();
        toDateField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        toDateField.sendKeys(toDate);
        toDateField.sendKeys(Keys.TAB);
    }

    public void enterEmployeeName(String employeeFirstName, String employeeFullName) {
        WaitUtils.waitForElementVisible(employeeNameField);
        employeeNameField.sendKeys(employeeFirstName);
        By option = By.xpath("//div[@role='listbox']//span[normalize-space()='" + employeeFullName + "']");
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public void clickSearchButton() {
        WaitUtils.waitForElementClickable(searchButton);
        searchButton.click();
    }

    public boolean isTableEmpty() {
        return tableRows.isEmpty();
    }

    public String getRecordsFoundMessage() {
        WaitUtils.waitForElementVisible(recordsFoundMessage);
        return recordsFoundMessage.getText();
    }

    public void clickApplyButton() {
        WaitUtils.waitForElementClickable(applyButton);
        applyButton.click();
    }

    public void selectLeaveType(String leaveType) {
        WaitUtils.waitForElementClickable(leaveTypeDropdownUnderApplyLeave);
        leaveTypeDropdownUnderApplyLeave.click();
        for (WebElement option : leaveTypeOptions) {
            if (option.getText().equalsIgnoreCase(leaveType)) {
                WaitUtils.waitForElementClickable(option);
                option.click();
                break;
            }
        }
    }

    public void enterFromDateUnderApplyLeave(String fromDate) {
        WaitUtils.waitForElementVisible(fromDateUnderApplyLeave);
        fromDateUnderApplyLeave.clear();
        fromDateUnderApplyLeave.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        fromDateUnderApplyLeave.sendKeys(fromDate);
        fromDateUnderApplyLeave.sendKeys(Keys.TAB);
    }

    public void enterToDateUnderApplyLeave(String toDate) {
        WaitUtils.waitForElementVisible(toDateUnderApplyLeave);
        toDateUnderApplyLeave.clear();
        toDateUnderApplyLeave.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        toDateUnderApplyLeave.sendKeys(toDate);
        toDateUnderApplyLeave.sendKeys(Keys.TAB);
    }

    public void clickApplyButtonForLeave() {
        WaitUtils.waitForElementClickable(applyButtonForLeave);
        applyButtonForLeave.click();
    }

    public String getMessagePrompt() {
        WaitUtils.waitForElementVisible(messagePrompt);
        return messagePrompt.getText();
    }

}