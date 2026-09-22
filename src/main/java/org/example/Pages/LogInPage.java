package org.example.Pages;

import org.example.Base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LogInPage extends TestBase {

    public LogInPage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "input-email")
    WebElement emailInput;

    @FindBy(id = "input-password")
    WebElement passwordInput;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement loginButton;

    public MyAccountPage login(String email, String password) {
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        loginButton.submit();
        return new MyAccountPage();
    }

}
