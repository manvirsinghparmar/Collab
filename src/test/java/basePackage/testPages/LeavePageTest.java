package basePackage.testPages;


import basePackage.base.BaseTest;
import basePackage.support.EmployeeFixture;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.DashboardPage;
import pages.LeavePage;
import pages.LoginPage;
import org.testng.annotations.Test;
import hamcrestAssertion.HamcrestSoftAssertions;

import static org.hamcrest.Matchers.containsString;

import java.time.LocalDate;
import java.time.ZoneId;

public class LeavePageTest extends BaseTest {

    DashboardPage dashboardPage;
    LeavePage leavePage;
    LoginPage loginPage;
    private EmployeeFixture employee;

    @BeforeMethod
    public void setUp() {
        employee = new EmployeeFixture();
        initialization();
        loginPage = new LoginPage();
    }

    @Test
    public void validIfThereAreNOLeavesPendingApproval() {
        try (HamcrestSoftAssertions hmAssert = new HamcrestSoftAssertions()) {
            dashboardPage = loginPage.clickOnlogin(config.username(), config.password());
            employee.create(dashboardPage.clickOnPIMLink());
            leavePage = dashboardPage.clickOnLeaveMenu();
            LocalDate today = LocalDate.now(ZoneId.of("America/Toronto"));
            leavePage.enterFromDate(today.withDayOfYear(1).toString());
            leavePage.enterToDate(today.withMonth(12).withDayOfMonth(31).toString());
            leavePage.enterEmployeeName(employee.firstName(), employee.fullName());
            leavePage.clickSearchButton();
            hmAssert.assertThat("There are leaves pending approval.",
                    leavePage.getRecordsFoundMessage(), containsString("No Records Found"));
        }
    }

    @Test
    public void applyLeave() {
        try (HamcrestSoftAssertions hmAssert = new HamcrestSoftAssertions()) {
            dashboardPage = loginPage.clickOnlogin(config.username(), config.password());
            leavePage = dashboardPage.clickOnLeaveMenu();
            leavePage.clickApplyButton();
            leavePage.selectLeaveType("CAN - Vacation");
            leavePage.enterFromDate("2026-10-23");
            leavePage.enterToDate("2026-10-23");
            leavePage.clickApplyButtonForLeave();
            hmAssert.assertThat("Leave application was not successful.",
                    leavePage.getMessagePrompt(), containsString("Success"));
        }
    }

    @Test
    public void applyLeaveForMoreDaysThanAvailable() {
        try (HamcrestSoftAssertions hmAssert = new HamcrestSoftAssertions()) {
            dashboardPage = loginPage.clickOnlogin(config.username(), config.password());
            leavePage = dashboardPage.clickOnLeaveMenu();
            leavePage.clickApplyButton();
            leavePage.selectLeaveType("CAN - Vacation");
            leavePage.enterFromDate("2026-10-23");
            leavePage.enterToDate("2026-11-30");
            leavePage.clickApplyButtonForLeave();
            hmAssert.assertThat("Error message for insufficient leave balance was not displayed.",
                    leavePage.getMessagePrompt(), containsString("Leave Balance Exceeded"));
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        try {
            employee.close();
        } finally {
            super.teardown();
        }
    }
}
