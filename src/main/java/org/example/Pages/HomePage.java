package org.example.Pages;

import org.example.Base.TestBase;
import org.example.Utils.SelUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends TestBase {

    public HomePage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[text()='My Account']")
    private WebElement myAccountButton;

    @FindBy(xpath = "//a[text()='Login']")
    private WebElement loginButton;

    @FindBy(xpath = "//a[text()='Register']")
    private WebElement registerButton;

    public void clickMyAccountButton() {
        SelUtil.click(myAccountButton);
    }

    public LogInPage clickLoginButton() {
        SelUtil.click(loginButton);
        return new LogInPage();
    }

    public RegisterAccountPage clickRegisterButton() {
        SelUtil.click(registerButton);
        return new RegisterAccountPage();
    }

}
