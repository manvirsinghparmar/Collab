<<<<<<<< HEAD:src/test/java/org/orangehrm/tests/PimPageTest.java
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
>>>>>>>> origin/master:src/test/java/basePackage/testPages/PimPageTest.java
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import hamcrestAssertion.HamcrestSoftAssertions;

import static org.hamcrest.Matchers.containsString;

public class PimPageTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void launch() {
        initialization();
        loginPage = new LoginPage();

    }

    @Test(priority = 1)
    @Parameters("empName")
    public void validateUserIsAbleToSearchForEmployeeWithValidEmpName(@Optional("Charles") String empName) {

        DashboardPage dashboardPage = loginPage.clickOnlogin("Admin", "admin123");
        PimPage pimPage = dashboardPage.clickOnPIMLink();
        pimPage.enterEmployeeName(empName);
        pimPage.clickSubmitButton();

    }


    @Test(priority = 2)
    @Parameters("empName")
    public void validateUserIsAbleToEditEmpDetails(@Optional("Charles") String empName) {

<<<<<<<< HEAD:src/test/java/org/orangehrm/tests/PimPageTest.java
        DashboardPage dashboardPage = loginPage.clickOnlogin("Admin", "admin123");
        PimPage pimPage = dashboardPage.clickOnPIMLink();
        pimPage.enterEmployeeName(empName);
        pimPage.clickSubmitButton();
        pimPage.clickEditButton();
        AssertionsUtil.softAssert.assertTrue(wd.getCurrentUrl().contains("/pim/viewPersonalDetails/empNumber/"), "Expected Personal Details URL, but actual URL was: " + wd.getCurrentUrl());
========
        try (HamcrestSoftAssertions softly = new HamcrestSoftAssertions()) {
            LoginPage loginPage = new LoginPage();
            DashboardPage dashboardPage = loginPage.clickOnlogin("Admin", "admin123");
            PimPage pimPage = dashboardPage.clickOnPIMLink();
            pimPage.enterEmployeeName(empName);
            pimPage.clickSubmitButton();
            pimPage.clickEditButton();
            softly.assertThat("Expected Personal Details URL", wd.getCurrentUrl(),
                    containsString("/pim/viewPersonalDetails/empNumber/"));
        }
>>>>>>>> origin/master:src/test/java/basePackage/testPages/PimPageTest.java

    }

    @Test
    @Parameters("empName")
    public void validateUserIsAbleToDeleteEmpDetails(@Optional("bala") String empName) {

        DashboardPage dashboardPage = loginPage.clickOnlogin("Admin", "admin123");
        PimPage pimPage = dashboardPage.clickOnPIMLink();
        pimPage.enterEmployeeName(empName);
        pimPage.clickSubmitButton();
        pimPage.clickDeleteButton();
        pimPage.clickDeleteRecordButton();

    }

    @Test
    @Parameters("empName")
    public void validateUserIsAbleToSearchForEmployeeDoesNotExistAndReset(@Optional("Ana") String empName) {

        DashboardPage dashboardPage = loginPage.clickOnlogin("Admin", "admin123");
        PimPage pimPage = dashboardPage.clickOnPIMLink();
        pimPage.enterEmployeeName(empName);
        pimPage.clickSubmitButton();
        pimPage.clickResetButton();


    }

    @AfterMethod
    public void closeBrowser() {
        teardown();
    }


}

