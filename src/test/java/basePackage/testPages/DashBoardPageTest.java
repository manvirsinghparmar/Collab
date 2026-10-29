package basePackage.testPages;

import basePackage.base.BaseTest;
import hamcrestAssertion.HamcrestSoftAssertions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.DashboardPage;
import pages.LoginPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.containsString;

public class DashBoardPageTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void launch() {
        initialization();
        loginPage = new LoginPage();

    }


    @Test
    public void validateUserIsAbleToClickOnPIMLinkOnDashboard() {

        DashboardPage dashboard =
                loginPage.clickOnlogin(config.username(), config.password());

        dashboard.clickOnPIMLink();
        wait.until(ExpectedConditions.urlContains("/pim/viewEmployeeList"));

        try (HamcrestSoftAssertions softly = new HamcrestSoftAssertions()) {
            softly.assertThat("PIM link should open the employee list",
                    wd.getCurrentUrl(), containsString("/pim/viewEmployeeList"));
        }

    }
    @Test
    public void validateUserIsAbleToClickOnMyInfoLinkOnDashboard(){

    }



    @AfterMethod
    public void closeBrowser() {
        teardown();
    }
}
