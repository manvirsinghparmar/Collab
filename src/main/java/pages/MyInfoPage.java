package pages;

import basePackage.base.BaseTest;
import listeners.WebDriverEventListener;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utilities.SelUtils;

import java.util.ArrayList;
import java.util.List;


public class MyInfoPage extends BaseTest {

    public MyInfoPage() {
        PageFactory.initElements(wd, this);
    }

    @FindBy(xpath = "//a[@href='/web/index.php/pim/viewMyDetails']")
    private WebElement myInfoLink;

    @FindBy(css = ".orangehrm-tabs-wrapper a")
    private List<WebElement> myInfoTabs;

    @FindBy(xpath = "//h6[normalize-space()='Personal Details']")
    private WebElement personalDetailsHeading;

    @FindBy(css = ".oxd-date-input input")
    private List<WebElement> calendarInput;

    @FindBy(xpath = "//div[@class='oxd-select-wrapper']")
    private List<WebElement> dropDowns;

    @FindBy(xpath = "//button[@type='submit']")
    private List<WebElement> personalDetailsSaveButton;

    @FindBy(xpath = "//div[@class=\"oxd-toast-content oxd-toast-content--success\"]")
    private WebElement successMessageOnUpdatingPersonalDetails;

    public MyInfoPage clickMyInfoLink() {
        SelUtils.clickElement(myInfoLink);
        return this;
    }

    public MyInfoPage clickPersonalDetailsSaveButton(int buttonIndex) {
        SelUtils.clickElement(personalDetailsSaveButton.get(buttonIndex - 1));
        return this;
    }

    public MyInfoPage clickTab(MyInfo tab) {
        String tabName = tab.getDisplayName();
        WebElement matchingTab = SelUtils.getWait()
                .withMessage("My Info tab not found: " + tabName)
                .until(driver -> {
                    for (WebElement element : myInfoTabs) {
                        if (element.isDisplayed()
                                && element.getText().trim().equalsIgnoreCase(tabName)) {
                            return element;
                        }
                    }
                    return null;
                });
        SelUtils.clickElement(matchingTab);
        return this;
    }

    public boolean isTabDisplayed(MyInfo tab) {
        String tabName = tab.getDisplayName();
        SelUtils.waitForElementsVisible(myInfoTabs);

        for (WebElement element : myInfoTabs) {
            if (element.getText().trim().equalsIgnoreCase(tabName)) {
                return element.isDisplayed();
            }
        }

        return false;
    }

    public boolean isPersonalDetailsPageDisplayed() {
        return SelUtils.waitForElementVisible(personalDetailsHeading);
    }

    public void selectDateFromCalendar(int calendarIndex, String date) {
        if (calendarIndex < 1) {
            throw new IllegalArgumentException("Calendar index must be at least 1: " + calendarIndex);
        }
        WebElement calendar = SelUtils.getWait()
                .withMessage("Date input not available at calendar index: " + calendarIndex)
                .until(driver -> {
                    List<WebElement> inputs = new ArrayList<>(calendarInput);
                    if (inputs.size() < calendarIndex) {
                        return null;
                    }
                    WebElement input = inputs.get(calendarIndex - 1);
                    return input.isDisplayed() && input.isEnabled() ? input : null;
                });
        SelUtils.enterDate(calendar, date);
    }

    public void selectOptionFromDropdown(int dropdownIndex, String option) {
        if (dropdownIndex < 1) {
            throw new IllegalArgumentException("Dropdown index must be at least 1: " + dropdownIndex);
        }
        SelUtils.getWait().until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector(".oxd-form-loader")));
        WebElement dropdown = SelUtils.getWait()
                .withMessage("Dropdown not available at index: " + dropdownIndex)
                .until(driver -> {
                    List<WebElement> dropdowns = new ArrayList<>(dropDowns);
                    if (dropdowns.size() < dropdownIndex) {
                        return null;
                    }
                    WebElement element = dropdowns.get(dropdownIndex - 1);
                    return element.isDisplayed() && element.isEnabled() ? element : null;
                });
        WebElement selectedText = dropdown.findElement(By.cssSelector(".oxd-select-text-input"));
        SelUtils.clickElement(dropdown.findElement(By.cssSelector(".oxd-select-text")));
        WebElement optionElement = SelUtils.getWait()
                .withMessage("Option not found in dropdown: " + option)
                .until(driver -> {
                    List<WebElement> options = dropdown.findElements(
                            By.cssSelector("[role='listbox'] [role='option']"));
                    for (WebElement opt : options) {
                        if (opt.isDisplayed() && opt.getText().trim().equalsIgnoreCase(option)) {
                            return opt;
                        }
                    }
                    return null;
                });
        SelUtils.clickElement(optionElement);
        SelUtils.getWait()
                .withMessage("Dropdown selection did not update to: " + option)
                .until(driver -> selectedText.getText().trim().equalsIgnoreCase(option));
    }


    public String getSuccessMessage() {
        return SelUtils.getText(successMessageOnUpdatingPersonalDetails);
    }
}
