package pages;

import org.example.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utilities.SelUtils;

public class DashboardPage extends BaseTest {

    public String DashboardUrl = config.dashboardUrl();

    public DashboardPage() {
        PageFactory.initElements(wd, this);
    }

    @FindBy(xpath = "(//a[@class='oxd-main-menu-item'])[2]")
    private WebElement pimLinkFromDashboard;

    @FindBy(xpath = "//span[text()='Leave']")
    private WebElement leaveMenu;

    public PimPage clickOnPIMLink() {
        SelUtils.clickElement(pimLinkFromDashboard);
        return new PimPage();
    }

    public LeavePage clickOnLeaveMenu() {
        SelUtils.clickElement(leaveMenu);
        return new LeavePage();
    }

}
