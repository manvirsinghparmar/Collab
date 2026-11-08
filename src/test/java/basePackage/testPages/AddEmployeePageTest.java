package basePackage.testPages;

import basePackage.base.BaseTest;
import basePackage.support.EmployeeFixture;
import hamcrestAssertion.HamcrestSoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.PimPage;
import pages.DashboardPage;
import pages.LoginPage;

import static org.hamcrest.Matchers.is;

public class AddEmployeePageTest extends BaseTest {
    private LoginPage loginPage;
    private EmployeeFixture employee;


    @BeforeMethod
    public void launch() {
        employee = new EmployeeFixture();
        initialization();
        loginPage = new LoginPage();
    }


    @Test
    public void testAddEmployee() {
        DashboardPage dashboard = loginPage.clickOnlogin(config.username(), config.password());
        PimPage pimPage = dashboard.clickOnPIMLink();
        employee.create(pimPage);
        try (HamcrestSoftAssertions hmAssert = new HamcrestSoftAssertions()) {
            hmAssert.assertThat("Adding an employee should display a success message",
                    employee.savedSuccessfully(), is(true));
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
