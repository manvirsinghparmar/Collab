package basePackage.testPages;

import basePackage.base.BaseTest;
import hamcrestAssertion.HamcrestSoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AddEmployeePage;
import pages.PimPage;
import pages.DashboardPage;
import pages.LoginPage;

import static org.hamcrest.Matchers.is;

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
        DashboardPage dashboard = loginPage.clickOnlogin(config.username(), config.password());
        PimPage pimPage = dashboard.clickOnPIMLink();
        addEmployeePage = pimPage.clickAddEmployeeLink();
        addEmployeePage.addEmployee("Ruchi", "Chaudhary", "Ravish");
        try (HamcrestSoftAssertions softly = new HamcrestSoftAssertions()) {
            softly.assertThat("Adding an employee should display a success message",
                    addEmployeePage.isSuccessMessageVisible(), is(true));
        }
    }

    @AfterMethod
    public void quit() {
        teardown();
    }

}
