package org.example.Pages;

import org.example.Base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterAccountPage extends TestBase {

    public RegisterAccountPage() {

        PageFactory.initElements(driver, this);

    }

    @FindBy(id = "input-firstname")
    WebElement firstNameInputField;

    @FindBy(id = "input-lastname")
    WebElement lastNameInputField;

    @FindBy(id = "input-email")
    WebElement emailInputField;

    @FindBy(id = "input-password")
    WebElement passwordInputField;

    @FindBy(css = "input[name='agree']")
    WebElement privacyPolicyCheckbox;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement continueButton;

    public void clickRegisterButton() {
        continueButton.click();
    }

    public void checkPrivacyPolicyCheckbox() {
        if (!privacyPolicyCheckbox.isSelected()) {
            privacyPolicyCheckbox.click();
        }
    }

    public void registerAccount(String firstName, String lastName, String email, String password) {
        firstNameInputField.sendKeys(firstName);
        lastNameInputField.sendKeys(lastName);
        emailInputField.sendKeys(email);
        passwordInputField.sendKeys(password);
        checkPrivacyPolicyCheckbox();
        clickRegisterButton();
    }

}
