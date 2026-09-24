package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.WaitUtils;

import java.time.Duration;

public class LoginPage {


    public LoginPage(WebDriver wd) {
        this.wd = wd;
        this.wait = new WebDriverWait(wd, Duration.ofSeconds(20));
        PageFactory.initElements(wd, this);
    }

    private WebDriver wd;
    private WebDriverWait wait;

    @FindBy(xpath = "//input[@name='username']")
    private WebElement usernameInputTextBox;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement passwordInputTextBox;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;



    public void enterEmailTextBox(String emailID) {
        WaitUtils.waitForElementClickable(usernameInputTextBox);
        usernameInputTextBox.sendKeys(emailID);
    }

    public void enterPasswordTexBox(String password) {
        WaitUtils.waitForElementClickable(passwordInputTextBox);
        passwordInputTextBox.sendKeys(password);
    }

    public DashboardPage clickOnlogin(String email, String pwd) {

        enterEmailTextBox(email);
        enterPasswordTexBox(pwd);
        WaitUtils.waitForElementClickable(loginButton);

        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();

        return new DashboardPage(wd);
    }
}