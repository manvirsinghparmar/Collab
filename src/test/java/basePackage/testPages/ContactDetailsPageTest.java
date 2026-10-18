package basePackage.testPages;

import basePackage.base.BaseTest;
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
    private DashboardPage dashboardPage;
    private PimPage pimPage;
    private ContactDetailsPage contactDetailsPage;



    @BeforeMethod
    public void launch() {
        initialization();
        loginPage = new LoginPage();
    }

    @AfterMethod
    public void quit() {
        teardown();
    }


    @Test
    @Parameters("empName")
    public void validateUserIsAbleToUpdateEmployeeContactDetails(@Optional("Amelia") String empName) throws InterruptedException {
        try (HamcrestSoftAssertions softly = new HamcrestSoftAssertions()) {
            LoginPage login = new LoginPage();
            DashboardPage dashboard = login.clickOnlogin(config.username(), config.password());
            PimPage pimPage = dashboard.clickOnPIMLink();
            pimPage.enterEmployeeName(empName);
            pimPage.clickSubmitButton();
            personalDetailsPage = pimPage.clickEditButton();
            contactDetailsPage = personalDetailsPage.clickcontactdetailslink();
            contactDetailsPage.fillContactDetailsAndSave("123", "Williams", "Buffalo", "New York", "USA101", "United States", "amelia@gmail.com", "0987654321");
            softly.assertThat("Contact details should be saved successfully",
                    contactDetailsPage.getSuccessMessage(), containsString("Success"));
        }
    }
}
