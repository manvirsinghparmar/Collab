package basePackage.testPages;

import basePackage.base.BaseTest;
import basePackage.support.EmployeeFixture;
import pages.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import hamcrestAssertion.HamcrestSoftAssertions;

import static org.hamcrest.Matchers.containsString;

public class ContactDetailsPageTest extends BaseTest {

    private PersonalDetailsPage personalDetailsPage;
    private LoginPage loginPage;
    private ContactDetailsPage contactDetailsPage;
    private EmployeeFixture employee;



    @BeforeMethod
    public void launch() {
        employee = new EmployeeFixture();
        initialization();
        loginPage = new LoginPage();
    }

    @AfterMethod(alwaysRun = true)
    public void quit() {
        try {
            employee.close();
        } finally {
            teardown();
        }
    }


    @Test
    @Parameters("empName")
    public void validateUserIsAbleToUpdateEmployeeContactDetails(@Optional("Amelia") String empName) throws InterruptedException {
        employee = new EmployeeFixture(empName);
        try (HamcrestSoftAssertions hamcrestAssert = new HamcrestSoftAssertions()) {
            LoginPage login = new LoginPage();
            DashboardPage dashboard = login.clickOnlogin(config.username(), config.password());
            PimPage pimPage = dashboard.clickOnPIMLink();
            employee.create(pimPage);
            pimPage = employee.openEmployeeList();
            pimPage.enterEmployeeName(employee.fullName());
            pimPage.clickSubmitButton();
            personalDetailsPage = pimPage.clickEditButton();
            contactDetailsPage = personalDetailsPage.clickcontactdetailslink();
            contactDetailsPage.fillContactDetailsAndSave("123", "Test Street", "Buffalo", "New York", "12345", "United States", employee.email(), "0987654321");
            hamcrestAssert.assertThat("Contact details should be saved successfully",
                    contactDetailsPage.getSuccessMessage(), containsString("Success"));
        }
    }
}
