package basePackage.testPages;

import basePackage.base.BaseTest;
import basePackage.support.EmployeeFixture;
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
    private EmployeeFixture employee;

    @BeforeMethod
    public void launch() {
        employee = new EmployeeFixture();
        initialization();
        loginPage = new LoginPage();

    }

    @Test(priority = 1)
    @Parameters("empName")
    public void validateUserIsAbleToSearchForEmployeeWithValidEmpName(@Optional("") String empName) {

        DashboardPage dashboardPage = loginPage.clickOnlogin(config.username(), config.password());
        PimPage pimPage = dashboardPage.clickOnPIMLink();
        if (empName.isBlank()) {
            employee.create(pimPage);
            pimPage = employee.openEmployeeList();
            empName = employee.fullName();
        }
        pimPage.enterEmployeeName(empName);
        pimPage.clickSubmitButton();

        try (HamcrestSoftAssertions softly = new HamcrestSoftAssertions()) {
            softly.assertThat("Employee search should return a matching employee",
                    pimPage.getResultRows(), hasItem(containsStringIgnoringCase(empName)));
        }
    }


    @Test(priority = 2)
    @Parameters("empName")
    public void validateUserIsAbleToEditEmpDetails(@Optional("") String empName) {

        try (HamcrestSoftAssertions softly = new HamcrestSoftAssertions()) {
            DashboardPage dashboardPage = loginPage.clickOnlogin(config.username(), config.password());
            PimPage pimPage = dashboardPage.clickOnPIMLink();
            if (empName.isBlank()) {
                employee.create(pimPage);
                pimPage = employee.openEmployeeList();
                empName = employee.fullName();
            }
            pimPage.enterEmployeeName(empName);
            pimPage.clickSubmitButton();
            pimPage.clickEditButton();
            softly.assertThat("Expected Personal Details URL", wd.getCurrentUrl(),
                    containsString("/pim/viewPersonalDetails/empNumber/"));
        }

    }

    @Test
    @Parameters("empName")
    public void validateUserIsAbleToDeleteEmpDetails(@Optional("") String empName) {
        employee = new EmployeeFixture(empName);

        DashboardPage dashboardPage = loginPage.clickOnlogin(config.username(), config.password());
        PimPage pimPage = dashboardPage.clickOnPIMLink();
        employee.create(pimPage);

        try (HamcrestSoftAssertions hmAssert = new HamcrestSoftAssertions()) {
            hmAssert.assertThat("The generated employee should be deleted", employee.delete(), equalTo(true));
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

    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {
        try {
            employee.close();
        } finally {
            teardown();
        }
    }


}
