package pages;

import org.example.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utilities.WaitUtils;

public class DashboardPage extends BaseTest {

    public DashboardPage() {
        PageFactory.initElements(wd, this);
    }

    @FindBy(xpath = "(//a[@class='oxd-main-menu-item'])[2]")
    private WebElement pimLinkFromDashboard;

    @FindBy(xpath = "//span[text()='Leave']")
    private WebElement leaveMenu;

    public PimPage clickOnPIMLink() {
        WaitUtils.waitForElementClickable(pimLinkFromDashboard).click();
        return new PimPage(wd);
    }

    public LeavePage clickOnLeaveMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(leaveMenu));
        leaveMenu.click();
        return new LeavePage();
    }

}