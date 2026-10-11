package org.testPages;

import org.base.BaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;

public class AddEmployeePageTest extends BaseTest {
    private PersonalDetailsPage personalDetailsPage;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private PimPage pimPage;
    private ContactDetailsPage contactDetailsPage;
    private AddEmployeePage addEmployeePage;


    @BeforeMethod
    public void launch() {
        initialization();
        loginPage = new LoginPage();
    }


    @Test
    public void testAddEmployee() {
        addEmployeePage = new AddEmployeePage();
        DashboardPage dashboard = loginPage.clickOnlogin("Admin", "admin123");
        PimPage pimPage = dashboard.clickOnPIMLink();
        pimPage.clickAddEmployeeLink();
        addEmployeePage.addEmployee("Ruchi", "Chaudhary", "Ravish");
        softAssert.assertTrue(addEmployeePage.isSuccessMessageVisible(), "Success message is not visible after adding a new employee.");
        softAssert.assertAll();
    }

    @AfterMethod
    public void quit() {
        teardown();
    }

}
