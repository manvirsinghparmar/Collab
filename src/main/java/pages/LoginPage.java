package pages;

import org.example.BaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.SelUtils;
public class LoginPage extends BaseTest {

    private WebDriver wd;

    public LoginPage(WebDriver wd) {
        this.wd = wd;
        PageFactory.initElements(wd, this);
    }



    @FindBy(xpath = "//input[@name='username']")
    private WebElement usernameInputTextBox;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement passwordInputTextBox;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;



    public void enterEmailTextBox(String emailID) {
        SelUtils.enterTextIntoInputBox(usernameInputTextBox, emailID);
    }

    public void enterPasswordTexBox(String password) {
        SelUtils.enterTextIntoInputBox(passwordInputTextBox, password);
    }

    public DashboardPage clickOnlogin(String email, String pwd) {

        enterEmailTextBox(email);
        enterPasswordTexBox(pwd);
        SelUtils.clickElement(loginButton);

        return new DashboardPage(wd);
    }
}