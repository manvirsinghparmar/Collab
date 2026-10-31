package pages;

import basePackage.base.BaseTest;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.SelUtils;
import java.util.List;

public class ReportsPage extends BaseTest {

    public ReportsPage() {
        PageFactory.initElements(wd, this);
    }

    @FindBy(xpath = "//input[contains(@placeholder, 'Type for hints...')]")
    private WebElement reportNameInputTextBox;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement reportSearchButton;

    @FindBy(xpath = "(//button[@type='button'])[9]")
    private WebElement reportButtonInRecords;

    @FindBy(css = ".oxd-table-body .bi-pencil-fill")
    private WebElement recordEditButton;

    @FindBy(xpath = "//input[@placeholder='Type here ...']")
    private WebElement reportNameEditInputTextBox;

    @FindBy(css = "div.oxd-select-wrapper > div.oxd-select-text > div > i.bi-caret-down-fill")
    private List<WebElement> dropdownIcons;

    @FindBy(xpath = "(//button[@type='button'])[5]")
    private WebElement displayFieldAddBtn;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement reportSaveBtn;

    @FindBy(xpath = "//div[@class=\"oxd-toast-content oxd-toast-content--success\"]")
    private WebElement successMessageOnUpdatingContactDetails;

    @FindBy(css = ".oxd-select-option")
    private List<WebElement> dropdownOptions;

    public String getSuccessMessageText() {
        return SelUtils.getText(successMessageOnUpdatingContactDetails);
    }

    public void enterReportName(String reportName) {
        SelUtils.clickElement(reportNameInputTextBox);
        SelUtils.sendKeys(reportNameInputTextBox, reportName);
        reportNameInputTextBox.sendKeys(Keys.ARROW_DOWN);
        reportNameInputTextBox.sendKeys(Keys.ENTER);
    }

    public void clickSearchButton() {
        SelUtils.clickElement(reportSearchButton);
    }

    public void clickReportButton() {
        SelUtils.clickElement(reportButtonInRecords);
    }

    public void clickEditButton() {
        SelUtils.clickElement(recordEditButton);
    }

    public void getReport(String reportName) {
        enterReportName(reportName);
        clickSearchButton();
        clickReportButton();
    }

    public void clickEditReportButton(String reportName) {
        enterReportName(reportName);
        clickSearchButton();
        clickEditButton();

    }

    public void editReportName(String reportName) {
        SelUtils.clickElement(reportNameEditInputTextBox);
        reportNameEditInputTextBox.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE, reportName);
        SelUtils.getWait().until(webDriver ->
                reportName.equals(reportNameEditInputTextBox.getDomProperty("value")));
    }

    public void clickDropdownIcon(int index) {
        if (index >= 0 && index < dropdownIcons.size()) {
            SelUtils.clickElement(dropdownIcons.get(index));
        } else {
            throw new IndexOutOfBoundsException("Invalid index for dropdown icon: " + index);
        }
    }

    public void selectOptionFromDropdown(int optionPosition) {

        if (optionPosition < 0 || optionPosition >= dropdownOptions.size()) {
            throw new IndexOutOfBoundsException(
                    "Invalid option position: " + optionPosition
            );
        }

        SelUtils.clickElement(dropdownOptions.get(optionPosition));
    }

    public void clickDisplayFieldAddButton() {
        SelUtils.clickElement(displayFieldAddBtn);
    }

    public void clickRecordSaveButton() {
        SelUtils.clickElement(reportSaveBtn);
    }
}
