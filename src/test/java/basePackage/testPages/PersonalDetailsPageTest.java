package basePackage.testPages;

import basePackage.base.BaseTest;
import basePackage.support.EmployeeFixture;
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
    private EmployeeFixture employee;


    @BeforeMethod
    public void launch() {
        employee = new EmployeeFixture();
        initialization();
        loginPage = new LoginPage();
    }


    @Test
    @Parameters("empName")
    public void validateUserIsAbleToLandOnPersonalDetailsPageAfterClickingEditButton(@Optional("") String empName) {
        DashboardPage dashboard = loginPage.clickOnlogin(config.username(), config.password());
        PimPage pimPage = dashboard.clickOnPIMLink();
        if (empName.isBlank()) {
            employee.create(pimPage);
            pimPage = employee.openEmployeeList();
            empName = employee.fullName();
        }
        pimPage.enterEmployeeName(empName);
        pimPage.clickSubmitButton();
        pimPage.clickEditButton();
        String actualUrl = wd.getCurrentUrl();
        try (HamcrestSoftAssertions softly = new HamcrestSoftAssertions()) {
            softly.assertThat("Editing an employee should open Personal Details", actualUrl,
                    containsString("/pim/viewPersonalDetails/empNumber/"));
        }


    }

    @AfterMethod(alwaysRun = true)
    public void quit() {
        try {
            employee.close();
        } finally {
            teardown();
        }
    }

}
