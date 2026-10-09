package pages;

import org.example.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utilities.SelUtils;

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
        SelUtils.waitForElementVisible(fromDateField);
        fromDateField.clear();
        fromDateField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        fromDateField.sendKeys(fromDate);
        fromDateField.sendKeys(Keys.TAB);
    }

    public void enterToDate(String toDate) {
        SelUtils.waitForElementVisible(toDateField);
        toDateField.clear();
        toDateField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        toDateField.sendKeys(toDate);
        toDateField.sendKeys(Keys.TAB);
    }

    public void enterEmployeeName(String employeeFirstName, String employeeFullName) {
        SelUtils.waitForElementVisible(employeeNameField);
        employeeNameField.sendKeys(employeeFirstName);
        By option = By.xpath("//div[@role='listbox']//span[normalize-space()='" + employeeFullName + "']");
        SelUtils.getWait().until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public void clickSearchButton() {
        SelUtils.waitForElementClickable(searchButtonForLeaveList);
        searchButtonForLeaveList.click();
    }

    public boolean isLeaveListTableEmpty() {
        return tableRows.isEmpty();
    }

    public String getRecordsFoundMessage() {
        SelUtils.waitForElementVisible(recordsFoundMessage);
        return recordsFoundMessage.getText();
    }

    public void clickApplyButton() {
        SelUtils.waitForElementClickable(applyButton);
        applyButton.click();
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
        SelUtils.waitForElementVisible(fromDateUnderApplyLeave);
        fromDateUnderApplyLeave.clear();
        fromDateUnderApplyLeave.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        fromDateUnderApplyLeave.sendKeys(fromDate);
        fromDateUnderApplyLeave.sendKeys(Keys.TAB);
    }

    public void enterToDateUnderApplyLeave(String toDate) {
        SelUtils.waitForElementVisible(toDateUnderApplyLeave);
        toDateUnderApplyLeave.clear();
        toDateUnderApplyLeave.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        toDateUnderApplyLeave.sendKeys(toDate);
        toDateUnderApplyLeave.sendKeys(Keys.TAB);
    }

    public void clickApplyButtonForLeave() {
        SelUtils.waitForElementClickable(applyButtonForLeave);
        applyButtonForLeave.click();
    }

    public String getMessagePrompt() {
        SelUtils.waitForElementVisible(messagePrompt);
        return messagePrompt.getText();
    }

}