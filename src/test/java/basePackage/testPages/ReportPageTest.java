package basePackage.testPages;

import basePackage.base.BaseTest;
import hamcrestAssertion.HamcrestSoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import static org.hamcrest.Matchers.containsString;

public class ReportPageTest extends BaseTest {
    private LoginPage loginPage;

    @BeforeMethod
    public void launch() {
        initialization();
        loginPage = new LoginPage();
    }

    @Test
    public void validateReportPageNavigation() {
        loginPage.clickOnlogin(config.username(), config.password())
                .clickOnPIMLink()
                .clickReportsLink();
    }

    @Test
    public void validateUserIsAbleToSearchForReport() {
        loginPage.clickOnlogin(config.username(), config.password())
                .clickOnPIMLink()
                .clickReportsLink()
                .getReport("All Employees");
        try {
            HamcrestSoftAssertions softly = new HamcrestSoftAssertions();

            softly.assertThat(
                    "Report search should return a matching report",
                    wd.getCurrentUrl(),
                    containsString("displayPredefinedReport")
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @AfterMethod
    public void quit() {
        teardown();
    }
}