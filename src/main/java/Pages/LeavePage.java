package Pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class LeavePage {

    private WebDriver wd;
    private WebDriverWait wait;


    public LeavePage(WebDriver wd) {
        this.wd = wd;
        this.wait = new WebDriverWait(wd, Duration.ofSeconds(20));
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

    public void enterFromDate(String fromDate) {
        wait.until(ExpectedConditions.visibilityOf(fromDateField));
        fromDateField.clear();
        fromDateField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        fromDateField.sendKeys(fromDate);
        fromDateField.sendKeys(Keys.TAB);
    }

    public void enterToDate(String toDate) {
        wait.until(ExpectedConditions.visibilityOf(toDateField));
        toDateField.clear();
        toDateField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        toDateField.sendKeys(toDate);
        toDateField.sendKeys(Keys.TAB);
    }

    public void enterEmployeeName(String employeeFirstName) {
        wait.until(ExpectedConditions.visibilityOf(employeeNameField));
        employeeNameField.sendKeys(employeeFirstName);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while waiting after entering employee name", e);
        }

        new Actions(wd).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
    }

    public void clickSearchButton() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
    }

    public boolean isTableEmpty() {
        return tableRows.isEmpty();
    }

}