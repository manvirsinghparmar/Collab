package pages;

import org.example.BaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.SelUtils;

import java.time.Duration;

public class LoginPage extends BaseTest {


    public LoginPage() {
        PageFactory.initElements(wd, this);
    }


    @FindBy(xpath = "//input[@name='username']")
    private WebElement usernameInputTextBox;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement passwordInputTextBox;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;


    public void enterEmailTextBox(String emailID) {
        SelUtils.clickElement(usernameInputTextBox);
        usernameInputTextBox.sendKeys(emailID);
    }

    public void enterPasswordTexBox(String password) {
        SelUtils.clickElement(passwordInputTextBox);
        passwordInputTextBox.sendKeys(password);
    }

    public DashboardPage clickOnlogin(String email, String pwd) {

        enterEmailTextBox(email);
        enterPasswordTexBox(pwd);
        SelUtils.clickElement(loginButton);
        return new DashboardPage();

    }
}