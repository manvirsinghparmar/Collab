package pages;

import org.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.SelUtils;

public class ContactDetailsPage extends BaseTest {

    public ContactDetailsPage() {
        PageFactory.initElements(wd, this);
    }

    @FindBy(xpath = "//label[normalize-space()='Street 1']"
            + "/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement street1Input;

    @FindBy(xpath = "//label[normalize-space()='Street 2']"
            + "/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement street2Input;

    @FindBy(xpath = "//label[normalize-space()='City']"
            + "/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement cityInput;

    @FindBy(xpath = "//label[normalize-space()='State/Province']"
            + "/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement stateInput;

    @FindBy(xpath = "//label[normalize-space()='Zip/Postal Code']"
            + "/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement zipCodeInputTextBox;

    @FindBy(xpath = "//label[normalize-space()='Country']"
            + "/ancestor::div[contains(@class,'oxd-input-group')]"
            + "//div[contains(@class,'oxd-select-text')]")
    private WebElement countryDropdown;

    @FindBy(xpath = "//label[normalize-space()='Work Email']"
            + "/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement workEmailInput;

    @FindBy(xpath = "//label[normalize-space()='Mobile']"
            + "/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement mobilePhoneInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//div[@class=\"oxd-toast-content oxd-toast-content--success\"]")
    private WebElement successMessageOnUpdatingContactDetails;


    public void enterStreet1(String street1) {
        SelUtils.clickElement(street1Input);
        SelUtils.clearAndSendKeys(street1Input, street1);
    }


    public void enterStreet2(String street2) {
        SelUtils.clickElement(street2Input);
        SelUtils.clearAndSendKeys(street2Input, street2);
    }

    public void enterCity(String city) {
        SelUtils.clickElement(cityInput);
        SelUtils.clearAndSendKeys(cityInput, city);
    }

    public void enterState(String state) {
        SelUtils.clickElement(stateInput);
        SelUtils.clearAndSendKeys(stateInput, state);
    }

    public void enterZipCode(String zipCode) {
        SelUtils.clickElement(zipCodeInputTextBox);
        SelUtils.clearAndSendKeys(zipCodeInputTextBox, zipCode);
    }


    public void selectCountry(String country) {
        SelUtils.clickElement(countryDropdown);
        SelUtils.clickElement(wd.findElement(By.xpath("//div[@role='listbox']//span[normalize-space()='" + country + "']"))
        );
    }

    public void enterMobilePhone(String mobilePhone) {
        SelUtils.clickElement(mobilePhoneInput);
        SelUtils.clearAndSendKeys(mobilePhoneInput, mobilePhone);
    }

    public void enterWorkEmail(String workEmail) {
        SelUtils.clickElement(workEmailInput);
        SelUtils.clearAndSendKeys(workEmailInput, workEmail);
    }

    public void clickSaveButton() {
        SelUtils.clickElement(saveButton);
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
        return SelUtils.getText(successMessageOnUpdatingContactDetails);
    }


}
