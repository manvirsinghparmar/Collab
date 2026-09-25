package org.example;


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
    public void validIfThereAreNOLeavesPendingApproval(){
        dashboardPage = loginPage.clickOnlogin("Admin", "admin123");
        leavePage = dashboardPage.clickOnLeaveMenu();
        leavePage.enterFromDate("2026-09-23");
        leavePage.enterToDate("2026-12-31");
        leavePage.enterEmployeeName("Amelia");
        leavePage.clickSearchButton();
        Assert.assertTrue(leavePage.isTableEmpty(), "There are leaves pending approval.");
    }

    @AfterMethod
    public void tearDown() {
        super.teardown();
    }
}
