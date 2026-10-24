package pages;

import basePackage.base.BaseTest;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.SelUtils;


public class DashboardPage extends BaseTest {

    public String dashboardUrl = config.dashboardUrl();

    public DashboardPage() {
        PageFactory.initElements(wd, this);
    }

    @FindBy(xpath = "(//a[@class='oxd-main-menu-item'])[2]")
    private WebElement pimLinkFromDashboard;

    @FindBy(xpath = "//span[text()='Leave']")
    private WebElement leaveMenu;

    @FindBy(css = "a[href$='/recruitment/viewRecruitmentModule']")
    private WebElement recruitmentMenu;

    public PimPage clickOnPIMLink() {
        SelUtils.clickElement(pimLinkFromDashboard);
        return new PimPage();
    }

    public LeavePage clickOnLeaveMenu() {
        SelUtils.clickElement(leaveMenu);
        return new LeavePage();
    }

    public RecruitmentPage clickOnRecruitmentMenu() {
        SelUtils.clickElement(recruitmentMenu);
        return new RecruitmentPage().waitForCandidatesPage();
    }

}
