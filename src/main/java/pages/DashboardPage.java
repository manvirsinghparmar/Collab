package pages;

import org.example.BaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DashboardPage extends BaseTest {

    public DashboardPage(WebDriver wd) {
        PageFactory.initElements(wd, this);
    }

    public static final String DASHBOARD_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index";

    @FindBy(xpath = "(//a[@class='oxd-main-menu-item'])[2]")
    private WebElement pimLinkFromDashboard;

    public PimPage clickOnPIMLink() {
        WaitUtils.waitForElementClickable(pimLinkFromDashboard).click();
        return new PimPage(wd);
    }



}