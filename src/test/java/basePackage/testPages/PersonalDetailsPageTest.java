<<<<<<<< HEAD:src/test/java/org/orangehrm/tests/PersonalDetailsPageTest.java
package org.orangehrm.tests;

import org.orangehrm.assertions.AssertionsUtil;
import org.orangehrm.basetest.BaseTest;
import org.orangehrm.pages.DashboardPage;
import org.orangehrm.pages.LoginPage;
import org.orangehrm.pages.PimPage;
========
package basePackage.testPages;

import basePackage.base.BaseTest;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PersonalDetailsPage;
import pages.PimPage;
>>>>>>>> origin/master:src/test/java/basePackage/testPages/PersonalDetailsPageTest.java
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import hamcrestAssertion.HamcrestSoftAssertions;

import static org.hamcrest.Matchers.containsString;

public class PersonalDetailsPageTest extends BaseTest {
    private LoginPage loginPage;


    @BeforeMethod
    public void launch() {
        initialization();
        loginPage = new LoginPage();
    }


    @Test
    @Parameters("empName")
    public void validateUserIsAbleToLandOnPersonalDetailsPageAfterClickingEditButton(@Optional("Charles") String empName) {
        DashboardPage dashboard = loginPage.clickOnlogin(config.username(), config.password());
        PimPage pimPage = dashboard.clickOnPIMLink();
        pimPage.enterEmployeeName(empName);
        pimPage.clickSubmitButton();
        pimPage.clickEditButton();
        String actualUrl = wd.getCurrentUrl();
        AssertionsUtil.softAssert.assertTrue(actualUrl.contains("/pim/viewPersonalDetails/empNumber/"),
                "User did not land on Personal Details page. Actual URL: " + actualUrl);


    }

    @AfterMethod
    public void quit() {
        teardown();
    }

<<<<<<<< HEAD:src/test/java/org/orangehrm/tests/PersonalDetailsPageTest.java
========

    @Test
    @Parameters("empName")
    public void validateUserIsAbleToLandOnPersonalDetailsPageAfterClickingEditButton(@Optional("Charles") String empName) {
        try (HamcrestSoftAssertions softly = new HamcrestSoftAssertions()) {
            LoginPage login = new LoginPage();
            DashboardPage dashboard = login.clickOnlogin(config.username(), config.password());
            PimPage pimPage = dashboard.clickOnPIMLink();
            pimPage.enterEmployeeName(empName);
            pimPage.clickSubmitButton();
            pimPage.clickEditButton();
            String actualUrl = wd.getCurrentUrl();
            softly.assertThat("User did not land on Personal Details page", actualUrl,
                    containsString("/pim/viewPersonalDetails/empNumber/"));
        }

    }

>>>>>>>> origin/master:src/test/java/basePackage/testPages/PersonalDetailsPageTest.java
}
