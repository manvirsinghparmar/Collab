package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {

    private WebDriver wd;
    private WebDriverWait wait;


    public DashboardPage(WebDriver wd) {
        this.wd = wd;
        this.wait = new WebDriverWait(wd, Duration.ofSeconds(20));
        PageFactory.initElements(wd, this);
    }

    @FindBy(xpath = "//span[text()='Leave']")
    private WebElement leaveMenu;

    public LeavePage clickOnLeaveMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(leaveMenu));
        leaveMenu.click();
        return new LeavePage(wd);
    }

}