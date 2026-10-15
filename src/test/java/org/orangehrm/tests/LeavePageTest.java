package org.orangehrm.tests;


import org.orangehrm.bastest.BaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.orangehrm.pages.DashboardPage;
import org.orangehrm.pages.LeavePage;
import org.orangehrm.pages.LoginPage;
import org.testng.annotations.Test;

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
        dashboardPage = loginPage.clickOnlogin(config.username(), config.password());
        leavePage = dashboardPage.clickOnLeaveMenu();
        leavePage.enterFromDate("2026-09-23");
        leavePage.enterToDate("2026-12-31");
        leavePage.enterEmployeeName("Demo", "Demo Open Source");
        leavePage.clickSearchButton();
        softAssert.assertTrue(leavePage.getRecordsFoundMessage().contains("No Records Found"), "There are leaves pending approval.");
    }

    @Test
    public void applyLeave() {
        dashboardPage = loginPage.clickOnlogin(config.username(), config.password());
        leavePage = dashboardPage.clickOnLeaveMenu();
        leavePage.clickApplyButton();
        leavePage.selectLeaveType("CAN - Vacation");
        leavePage.enterFromDate("2026-10-23");
        leavePage.enterToDate("2026-10-23");
        leavePage.clickApplyButtonForLeave();
        softAssert.assertTrue(leavePage.getMessagePrompt().contains("Success"), "Leave application was not successful.");
    }

    @Test
    public void applyLeaveForMoreDaysThanAvailable() {
        dashboardPage = loginPage.clickOnlogin(config.username(), config.password());
        leavePage = dashboardPage.clickOnLeaveMenu();
        leavePage.clickApplyButton();
        leavePage.selectLeaveType("CAN - Vacation");
        leavePage.enterFromDate("2026-10-23");
        leavePage.enterToDate("2026-11-30");
        leavePage.clickApplyButtonForLeave();
        softAssert.assertTrue(leavePage.getMessagePrompt().contains("Leave Balance Exceeded"), "Error message for insufficient leave balance was not displayed.");
    }

    @AfterMethod
    public void tearDown() {
        super.teardown();
    }
}
