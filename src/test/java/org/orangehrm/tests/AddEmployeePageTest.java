package org.orangehrm.tests;

import org.orangehrm.bastest.BaseTest;
import org.orangehrm.pages.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddEmployeePageTest extends BaseTest {
    private LoginPage loginPage;
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
    }

    @AfterMethod
    public void quit() {
        teardown();
    }

}
