package pages;

import org.example.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utilities.WaitUtils;

public class ContactDetailsPage extends BaseTest {
    public ContactDetailsPage(WebDriver wd) {
        PageFactory.initElements(wd, this);
    }

    @FindBy(xpath = "//input[@fdprocessedid='pjepde']")
    private WebElement street1Input;

    @FindBy(xpath = "//input[@fdprocessedid='5g0rz']")
    private WebElement street2Input;

    @FindBy(xpath = "//input[@fdprocessedid='xlmmf']")
    private WebElement cityInput;

    @FindBy(xpath = "//input[@fdprocessedid='halq0f']")
    private WebElement stateInput;

    @FindBy(xpath = "//input[@fdprocessedid='m7rj']")
    private WebElement zipCodeInputTextBox;

    @FindBy(xpath = "//div[text()='-- Select --']")
    private WebElement countryDropdown;

    @FindBy(xpath = "//input[@fdprocessedid=\"eg56o\"]")
    private WebElement workEmailInput;

    @FindBy(xpath = "//input[@fdprocessedid=\"tiizd\"]")
    private WebElement mobilePhoneInput;

    @FindBy(xpath = "//button[@fdprocessedid=\"fz3c1q\"]")
    private WebElement saveButton;

    @FindBy(xpath = "//div[@class=\"oxd-toast-content oxd-toast-content--success\"]")
    private WebElement successMessage;


    public void enterStreet1(String street1) {
        WaitUtils.waitForElementClickable(street1Input);
        street1Input.sendKeys(street1);
    }


    public void enterStreet2(String street2) {
        WaitUtils.waitForElementClickable(street2Input);
        street2Input.sendKeys(street2);
    }

    public void enterCity(String city) {
        WaitUtils.waitForElementClickable(cityInput);
        cityInput.sendKeys(city);
    }

    public void enterState(String state) {
        WaitUtils.waitForElementClickable(stateInput);
        stateInput.sendKeys(state);
    }

    public void enterZipCode(String zipCode) {
        WaitUtils.waitForElementClickable(zipCodeInputTextBox);
        zipCodeInputTextBox.sendKeys(zipCode);
    }



    public void selectCountry(String country) {
        countryDropdown.click();

        WebElement option = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[contains(@class,'oxd-select-option')]//span[normalize-space()='" + country + "']")
                )
        );

        option.click();
    }

public void enterMobilePhone(String mobilePhone) {
        WaitUtils.waitForElementClickable(mobilePhoneInput);
        mobilePhoneInput.sendKeys(mobilePhone);
    }

    public void enterWorkEmail(String workEmail) {
        WaitUtils.waitForElementClickable(workEmailInput);
        workEmailInput.sendKeys(workEmail);
    }

    public void clickSaveButton() {
        WaitUtils.waitForElementClickable(saveButton);
        saveButton.click();
    }

    public void fillContactDetailsAndSave(String street1, String street2, String city, String state, String zipCode, String country, String workEmail, String mobilePhone) {
        enterStreet1(street1);
        enterStreet2(street2);
        enterCity(city);
        enterState(state);
        enterZipCode(zipCode);
        selectCountry(country);
        enterWorkEmail(workEmail);
        enterMobilePhone(mobilePhone);
        clickSaveButton();
    }

    public String getSuccessMessage() {
        WaitUtils.waitForElementVisible(successMessage);
        return successMessage.getText();
    }


}
