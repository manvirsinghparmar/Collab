package basePackage.testPages;

import basePackage.base.BaseTest;
import pages.DashboardPage;
import pages.LoginPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import hamcrestAssertion.HamcrestSoftAssertions;
import utilities.SelUtils;

import static org.hamcrest.Matchers.equalTo;

public class LoginPageTest extends BaseTest {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    @BeforeMethod
    public void launch() {
        initialization();
        loginPage = new LoginPage();

    }

    @Test
    public void validateUserIsAbleToLoginWithValidCredentials() {

        try (HamcrestSoftAssertions softly = new HamcrestSoftAssertions()) {
            DashboardPage dashboardPage = loginPage.clickOnlogin(config.username(), config.password());
            SelUtils.waitForUrlContains(dashboardPage.DashboardUrl);
            softly.assertThat("Login should open the dashboard", wd.getCurrentUrl(),
                    equalTo(dashboardPage.DashboardUrl));
        }
    }

    @Test
    public void validateUserIsNotAbleToLoginWithInvalidCredential() {

        LoginPage loginPage = new LoginPage();
        loginPage.clickOnlogin("Admin", "admin1234");
    }

    @AfterMethod
    public void closeBrowser() {
        teardown();
    }
}
