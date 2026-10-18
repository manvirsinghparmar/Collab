package org.orangehrm.pages;

import org.orangehrm.basetest.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.orangehrm.utilities.SelUtils;

public class AddEmployeePage extends BaseTest {

    public AddEmployeePage() {
        PageFactory.initElements(wd, this);
    }

    @FindBy(xpath = "//input[@name='firstName']")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@name='middleName']")
    private WebElement middleNameInput;

    @FindBy(xpath = "//input[@name='lastName']")
    private WebElement lastNameInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//div[@class=\"oxd-toast-content oxd-toast-content--success\"]")
    private WebElement successMessageOnAddingNewEmployee;

    public void enterFirstName(String firstName) {
        SelUtils.clickElement(firstNameInput);
        SelUtils.clearAndSendKeys(firstNameInput, firstName);
    }

    public void enterMiddleName(String middleName) {
        SelUtils.clickElement(middleNameInput);
        SelUtils.clearAndSendKeys(middleNameInput, middleName);
    }

    public void enterLastName(String lastName) {
        SelUtils.clickElement(lastNameInput);
        SelUtils.clearAndSendKeys(lastNameInput, lastName);
    }

    public void clickSaveButton() {
        SelUtils.clickElement(saveButton);
    }

    public void addEmployee(String firstName, String middleName, String lastName) {
        enterFirstName(firstName);
        enterMiddleName(middleName);
        enterLastName(lastName);
        clickSaveButton();
    }

    public boolean isSuccessMessageVisible() {

        return SelUtils.waitForElementVisible(successMessageOnAddingNewEmployee);    }

}

