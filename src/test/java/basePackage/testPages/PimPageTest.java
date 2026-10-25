package basePackage.testPages;

import basePackage.base.BaseTest;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PimPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import hamcrestAssertion.HamcrestSoftAssertions;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

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

        DashboardPage dashboardPage = loginPage.clickOnlogin(config.username(), config.password());
        PimPage pimPage = dashboardPage.clickOnPIMLink();
        pimPage.enterEmployeeName(empName);
        pimPage.clickSubmitButton();

        try (HamcrestSoftAssertions softly = new HamcrestSoftAssertions()) {
            softly.assertThat("Employee search should return a matching employee",
                    pimPage.getResultRows(), hasItem(containsStringIgnoringCase(empName)));
        }
    }


    @Test(priority = 2)
    @Parameters("empName")
    public void validateUserIsAbleToEditEmpDetails(@Optional("Charles") String empName) {

        try (HamcrestSoftAssertions softly = new HamcrestSoftAssertions()) {
            DashboardPage dashboardPage = loginPage.clickOnlogin(config.username(), config.password());
            PimPage pimPage = dashboardPage.clickOnPIMLink();
            pimPage.enterEmployeeName(empName);
            pimPage.clickSubmitButton();
            pimPage.clickEditButton();
            softly.assertThat("Expected Personal Details URL", wd.getCurrentUrl(),
                    containsString("/pim/viewPersonalDetails/empNumber/"));
        }

    }

    @Test
    @Parameters("empName")
    public void validateUserIsAbleToDeleteEmpDetails(@Optional("bala") String empName) {

        DashboardPage dashboardPage = loginPage.clickOnlogin(config.username(), config.password());
        PimPage pimPage = dashboardPage.clickOnPIMLink();
        pimPage.enterEmployeeName(empName);
        pimPage.clickSubmitButton();
        pimPage.clickDeleteButton();
        pimPage.clickDeleteRecordButton();

        try (HamcrestSoftAssertions softly = new HamcrestSoftAssertions()) {
            softly.assertThat("Deleting an employee should display a success message",
                    pimPage.getSuccessMessage(), containsString("Successfully Deleted"));
        }
    }

    @Test
    @Parameters("empName")
    public void validateUserIsAbleToSearchForEmployeeDoesNotExistAndReset(
            @Optional("NoSuchEmployee987654321") String empName) {

        DashboardPage dashboardPage = loginPage.clickOnlogin(config.username(), config.password());
        PimPage pimPage = dashboardPage.clickOnPIMLink();
        pimPage.enterEmployeeName(empName);
        pimPage.clickSubmitButton();
        try (HamcrestSoftAssertions softly = new HamcrestSoftAssertions()) {
            softly.assertThat("An unknown employee should return no records",
                    pimPage.getRecordsFoundMessage(), equalTo("No Records Found"));
            softly.assertThat("An unknown employee should leave the results empty",
                    pimPage.getResultRows(), empty());
            pimPage.clickResetButton();
            softly.assertThat("Reset should clear the employee name filter",
                    pimPage.getEmployeeNameFilter(), equalTo(""));
        }

    }

    @Test
    public void validateUserIsAbleToClickOnReportsLink() {

        DashboardPage dashboardPage = loginPage.clickOnlogin(config.username(), config.password());
        PimPage pimPage = dashboardPage.clickOnPIMLink();
        pimPage.clickReportsLink();

        try (HamcrestSoftAssertions softly = new HamcrestSoftAssertions()) {
            softly.assertThat("Clicking on Reports link should navigate to the Reports page",
                    wd.getCurrentUrl(), containsString("/pim/viewDefinedPredefinedReports"));
        }
    }

    @AfterMethod
    public void closeBrowser() {
        teardown();
    }


}
