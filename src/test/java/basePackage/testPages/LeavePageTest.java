package basePackage.testPages;


import basePackage.base.BaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.DashboardPage;
import pages.LeavePage;
import pages.LoginPage;
import org.testng.annotations.Test;
import hamcrestAssertion.HamcrestSoftAssertions;

import static org.hamcrest.Matchers.containsString;

public class LeavePageTest extends BaseTest {

    DashboardPage dashboardPage;
    LeavePage leavePage;
    LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        initialization();
        loginPage = new LoginPage();
    }

    @Test
    public void validIfThereAreNOLeavesPendingApproval() {
        try (HamcrestSoftAssertions hmAssert = new HamcrestSoftAssertions()) {
            dashboardPage = loginPage.clickOnlogin(config.username(), config.password());
            leavePage = dashboardPage.clickOnLeaveMenu();
            leavePage.enterFromDate("2026-09-23");
            leavePage.enterToDate("2026-12-31");
            leavePage.enterEmployeeName("Demo", "Demo Open Source");
            leavePage.clickSearchButton();
            hmAssert.assertThat("There are leaves pending approval.",
                    leavePage.getRecordsFoundMessage(), containsString("No Records Found"));
        }
    }

    @Test
    public void applyLeave() {
        try (HamcrestSoftAssertions softly = new HamcrestSoftAssertions()) {
            dashboardPage = loginPage.clickOnlogin(config.username(), config.password());
            leavePage = dashboardPage.clickOnLeaveMenu();
            leavePage.clickApplyButton();
            leavePage.selectLeaveType("CAN - Vacation");
            leavePage.enterFromDate("2026-10-23");
            leavePage.enterToDate("2026-10-23");
            leavePage.clickApplyButtonForLeave();
            softly.assertThat("Leave application was not successful.",
                    leavePage.getMessagePrompt(), containsString("Success"));
        }
    }

    @Test
    public void applyLeaveForMoreDaysThanAvailable() {
        try (HamcrestSoftAssertions softly = new HamcrestSoftAssertions()) {
            dashboardPage = loginPage.clickOnlogin(config.username(), config.password());
            leavePage = dashboardPage.clickOnLeaveMenu();
            leavePage.clickApplyButton();
            leavePage.selectLeaveType("CAN - Vacation");
            leavePage.enterFromDate("2026-10-23");
            leavePage.enterToDate("2026-11-30");
            leavePage.clickApplyButtonForLeave();
            softly.assertThat("Error message for insufficient leave balance was not displayed.",
                    leavePage.getMessagePrompt(), containsString("Leave Balance Exceeded"));
        }
    }

    @AfterMethod
    public void tearDown() {
        super.teardown();
    }
}
