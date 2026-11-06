package basePackage.testPages;

import basePackage.base.BaseTest;
import hamcrestAssertion.HamcrestSoftAssertions;
import org.testng.annotations.*;
import pages.*;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class MyInfoContactDetailsPageTest extends BaseTest {
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private MyInfoContactDetailsPage myInfoContactDetailsPage;


    @BeforeMethod
    public void launch() {
        initialization();
        loginPage = new LoginPage();
    }

    @AfterMethod(alwaysRun = true)
    public void quit() {
        teardown();
    }

    @Test
    public void validateContactDetailsHeadingIsDisplayed() {
        try (HamcrestSoftAssertions hmAssert = new HamcrestSoftAssertions()) {
            LoginPage login = new LoginPage();
            DashboardPage dashboard = login.clickOnlogin(config.username(), config.password());
            MyInfoPage myInfoPage = dashboard.clickOnMyInfoLink();
            MyInfoContactDetailsPage myInfoContactDetailsPage = myInfoPage.clickContactDetailsMyInfoTab();
            hmAssert.assertThat("Contact Details heading should be displayed",
                    myInfoContactDetailsPage.isContactDetailsHeadingDisplayed(), containsString("Contact Details"));
        }
    }

    @Test
    @Parameters("empName")
    public void validateUserIsAbleToUpdateEmployeeContactDetails(@Optional("Amelia") String empName) throws InterruptedException {
        try (HamcrestSoftAssertions hmAssert = new HamcrestSoftAssertions()) {
            LoginPage login = new LoginPage();
            dashboardPage = login.clickOnlogin(config.username(), config.password());
            MyInfoPage myInfoPage = dashboardPage.clickOnMyInfoLink();
            myInfoContactDetailsPage = myInfoPage.clickContactDetailsMyInfoTab();
            myInfoContactDetailsPage.fillContactDetailsAndSave("123", "Williams", "Buffalo", "New York", "USA101", "United States", "amelia@gmail.com", "0987654321");
            hmAssert.assertThat("Contact details should be saved successfully",
                    myInfoContactDetailsPage.getSuccessMessage(), containsString("Success"));
        }
    }

    @Test
    public void validateUserIsAbleToUpdateEmployeeContactDetailsWithInvalidEmail() throws InterruptedException {
        try (HamcrestSoftAssertions hmAssert = new HamcrestSoftAssertions()) {
            LoginPage login = new LoginPage();
            dashboardPage = login.clickOnlogin(config.username(), config.password());
            MyInfoPage myInfoPage = dashboardPage.clickOnMyInfoLink();
            myInfoContactDetailsPage = myInfoPage.clickContactDetailsMyInfoTab();
            myInfoContactDetailsPage.fillContactDetailsAndSave("123", "Williams", "Buffalo", "New York", "USA101", "United States", "invalid-email", "0987654321");
            hmAssert.assertThat("Error message should be displayed for invalid email",
                    myInfoContactDetailsPage.getErrorMessageForInvalidEmail(), equalTo("Expected format: admin@example.com"));
        }
    }


}
