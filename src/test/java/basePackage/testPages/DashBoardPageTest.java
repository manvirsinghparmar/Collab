package basePackage.testPages;

import basePackage.base.BaseTest;
import hamcrestAssertion.HamcrestSoftAssertions;
import pages.DashboardPage;
import pages.LoginPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.SelUtils;

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
        SelUtils.waitForUrlContains("/pim/viewEmployeeList");

        try (HamcrestSoftAssertions softly = new HamcrestSoftAssertions()) {
            softly.assertThat("PIM link should open the employee list",
                    getDriver().getCurrentUrl(), containsString("/pim/viewEmployeeList"));
        }

    }


    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {
        teardown();
    }
}
