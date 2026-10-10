package org.testPages;


import org.example.BaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.DashboardPage;
import pages.LeavePage;
import pages.LoginPage;
import org.testng.Assert;
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
        softAssert.assertAll();
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
        softAssert.assertAll();
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
        softAssert.assertAll();
    }

    @AfterMethod
    public void tearDown() {
        super.teardown();
    }
}
