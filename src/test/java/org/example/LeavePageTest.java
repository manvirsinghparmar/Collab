package org.example;


import Pages.DashboardPage;
import Pages.LeavePage;
import Pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LeavePageTest extends BaseTest {

    DashboardPage dashboardPage;
    LeavePage leavePage;
    LoginPage loginPage;

    @Test
    public void validIfThereAreNOLeavesPendingApproval(){
        loginPage = new LoginPage(wd);
        dashboardPage = loginPage.clickOnlogin("Admin", "admin123");
        leavePage = dashboardPage.clickOnLeaveMenu();
        leavePage.enterFromDate("2026-09-23");
        leavePage.enterToDate("2026-12-31");
        leavePage.enterEmployeeName("Amelia");
        leavePage.clickSearchButton();
        Assert.assertTrue(leavePage.isTableEmpty(), "There are leaves pending approval.");


    }





}
