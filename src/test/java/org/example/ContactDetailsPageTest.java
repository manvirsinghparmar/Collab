package org.example;

import pages.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ContactDetailsPageTest extends BaseTest {

    private PersonalDetailsPage personalDetailsPage;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private PimPage pimPage;
    private ContactDetailsPage contactDetailsPage;



    @BeforeMethod
    public void launch() {
        initialization();
        loginPage=new LoginPage(wd);
    }

    @AfterMethod
    public void quit() {
        teardown();
    }


    @Test
    @Parameters("empName")
    public void validateUserIsAbleToUpdateEmployeeContactDetails(@Optional("Ashley") String empName) throws InterruptedException {
        LoginPage login = new LoginPage(wd);
        DashboardPage dashboard = login.clickOnlogin("Admin", "admin123");
        PimPage pimPage = dashboard.clickOnPIMLink();
        pimPage.enterEmployeeName(empName);
        pimPage.clickSubmitButton();
        personalDetailsPage = pimPage.clickEditButton();
        contactDetailsPage = personalDetailsPage.clickcontactdetailslink();
        contactDetailsPage.fillContactDetailsAndSave("123", "Williams", "Buffalo", "New York", "USA101", "United States", "abcd@gmail.com", "0987654321");
        softAssert.assertTrue(contactDetailsPage.getSuccessMessage().contains("Success"));
        softAssert.assertAll();
    }
}
