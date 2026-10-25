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

    @BeforeMethod
    public void launch() {
        initialization();
        loginPage = new LoginPage();

    }

    @Test
    public void validateUserIsAbleToLoginWithValidCredentials() {

        try (HamcrestSoftAssertions softly = new HamcrestSoftAssertions()) {
            DashboardPage dashboardPage = loginPage.clickOnlogin(config.username(), config.password());
            SelUtils.waitForUrlContains(config.dashboardUrl());
            softly.assertThat("Login should open the dashboard", wd.getCurrentUrl(),
                    equalTo(config.dashboardUrl()));
        }
    }

    @Test
    public void validateUserIsNotAbleToLoginWithInvalidCredential() {

        loginPage.clickOnlogin(config.username(), "admin1234");
        try (HamcrestSoftAssertions softly = new HamcrestSoftAssertions()) {
            softly.assertThat("Invalid credentials should display an error",
                    loginPage.getLoginErrorMessage(), equalTo("Invalid credentials"));
            softly.assertThat("Invalid login should remain on the login page",
                    wd.getCurrentUrl(), equalTo(config.loginUrl()));
        }
    }

    @AfterMethod
    public void closeBrowser() {
        teardown();
    }
}