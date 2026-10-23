package basePackage.testPages;

import basePackage.base.BaseTest;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PimPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import hamcrestAssertion.HamcrestSoftAssertions;

import static org.hamcrest.Matchers.containsString;

public class PersonalDetailsPageTest extends BaseTest {
    private LoginPage loginPage;


    @BeforeMethod
    public void launch() {
        initialization();
        loginPage = new LoginPage();
    }


    @Test
    @Parameters("empName")
    public void validateUserIsAbleToLandOnPersonalDetailsPageAfterClickingEditButton(@Optional("Charles") String empName) {
        DashboardPage dashboard = loginPage.clickOnlogin(config.username(), config.password());
        PimPage pimPage = dashboard.clickOnPIMLink();
        pimPage.enterEmployeeName(empName);
        pimPage.clickSubmitButton();
        pimPage.clickEditButton();
        String actualUrl = wd.getCurrentUrl();
        try (HamcrestSoftAssertions softly = new HamcrestSoftAssertions()) {
            softly.assertThat("Editing an employee should open Personal Details", actualUrl,
                    containsString("/pim/viewPersonalDetails/empNumber/"));
        }


    }

    @AfterMethod
    public void quit() {
        teardown();
    }

}
