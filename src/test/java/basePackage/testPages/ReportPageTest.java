package basePackage.testPages;

import basePackage.base.BaseTest;
import hamcrestAssertion.HamcrestSoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PimPage;
import pages.ReportsPage;

import static org.hamcrest.Matchers.containsString;

public class ReportPageTest extends BaseTest {
    private LoginPage loginPage;
    private ReportsPage reportsPage;
    private PimPage pimPage;
    private DashboardPage dashboardPage;

    @BeforeMethod
    public void launch() {
        initialization();
        loginPage = new LoginPage();
    }

    @Test
    public void validateReportPageNavigation() {
        loginPage.clickOnlogin(config.username(), config.password())
                .clickOnPIMLink()
                .clickReportsLink();
        try{
            HamcrestSoftAssertions hamcrestAssert = new HamcrestSoftAssertions();

            hamcrestAssert.assertThat(
                    "Report page navigation should be successful",
                    wd.getCurrentUrl(),
                    containsString("/pim/viewDefinedPredefinedReports")
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void validateUserIsAbleToSearchForReport() {
        loginPage.clickOnlogin(config.username(), config.password())
                .clickOnPIMLink()
                .clickReportsLink()
                .getReport("All Employees");
        try {
            HamcrestSoftAssertions hamcrestAssert = new HamcrestSoftAssertions();

            hamcrestAssert.assertThat(
                    "Report search should return a matching report",
                    wd.getCurrentUrl(),
                    containsString("displayPredefinedReport")
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    @Test
    public void validateUserIsAbleToEditAndSaveTheReport() {
        dashboardPage = loginPage.clickOnlogin(config.username(), config.password());

        pimPage = dashboardPage.clickOnPIMLink();
        reportsPage = pimPage.clickReportsLink();
        reportsPage.enterReportName("All Employees");
        reportsPage.clickSearchButton();
        reportsPage.clickEditReportButton("All Employees");
        reportsPage.editReportName("Sub Unit Hierarchy Report of Employees");
        reportsPage.clickDropdownIcon(2);
        reportsPage.selectOptionFromDropdown(2);
        reportsPage.clickDropdownIcon(3);
        reportsPage.selectOptionFromDropdown(4);
        reportsPage.clickDisplayFieldAddButton();
        reportsPage.clickRecordSaveButton();
        try {
            HamcrestSoftAssertions hamcrestAssert = new HamcrestSoftAssertions();
            hamcrestAssert.assertThat("Contact details should be saved successfully",
                    reportsPage.getSuccessMessageTxt(), containsString("Success"));
            hamcrestAssert.assertThat(
                    "Report edit should be successfull",
                    wd.getCurrentUrl(),
                    containsString("definePredefinedReport")
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    @AfterMethod
    public void quit() {
        teardown();
    }
}