<<<<<<<< HEAD:src/test/java/org/orangehrm/tests/LoginPageTest.java
package org.orangehrm.tests;

import org.orangehrm.basetest.BaseTest;
import org.orangehrm.pages.DashboardPage;
import org.orangehrm.pages.LoginPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.orangehrm.assertions.AssertionsUtil;
import org.orangehrm.pages.DashboardPage;
========
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
>>>>>>>> origin/master:src/test/java/basePackage/testPages/LoginPageTest.java

public class LoginPageTest extends BaseTest {

    private LoginPage loginPage;
    private AssertionsUtil assertionsUtil;
    private DashboardPage dashboardPage;

    @BeforeMethod
    public void launch() {
        initialization();
        loginPage = new LoginPage();
        assertionsUtil = new AssertionsUtil();

    }

    @Test
    public void validateUserIsAbleToLoginWithValidCredentials() {

<<<<<<<< HEAD:src/test/java/org/orangehrm/tests/LoginPageTest.java
        dashboardPage = loginPage.clickOnlogin("Admin", "admin123");
        assertionsUtil.assertContains(
                dashboardPage.getCurrentUrl(),
                dashboardPage.DashboardUrl,
                "User is not able to login with valid credentials");
========
        try (HamcrestSoftAssertions softly = new HamcrestSoftAssertions()) {
            DashboardPage dashboardPage = loginPage.clickOnlogin(config.username(), config.password());
            SelUtils.waitForUrlContains(dashboardPage.DashboardUrl);
            softly.assertThat("Login should open the dashboard", wd.getCurrentUrl(),
                    equalTo(dashboardPage.DashboardUrl));
        }
>>>>>>>> origin/master:src/test/java/basePackage/testPages/LoginPageTest.java
    }

    @Test
    public void validateUserIsNotAbleToLoginWithInvalidCredential() {

        loginPage.clickOnlogin("Admin", "admin1234");
    }

    @AfterMethod
    public void closeBrowser() {
        teardown();
    }
}
