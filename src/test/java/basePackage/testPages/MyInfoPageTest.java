package basePackage.testPages;

import basePackage.base.BaseTest;
import hamcrestAssertion.HamcrestSoftAssertions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;

public class MyInfoPageTest extends BaseTest {
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private MyInfoPage myInfoPage;
    private PimPage pimPage;

    @BeforeMethod
    public void launch() {
        initialization();
        loginPage = new LoginPage();
    }


    @Test
    public void validateMyInfoTabsAreDisplayed() {
        dashboardPage = loginPage.clickOnlogin(config.username(), config.password());
        myInfoPage = dashboardPage.clickOnMyInfoLink();
        for (MyInfo tab : MyInfo.values()) {
            try (HamcrestSoftAssertions hamcrestAssert = new HamcrestSoftAssertions()) {

                hamcrestAssert.assertThat(
                        "Tab should be displayed: " + tab.getDisplayName(),
                        myInfoPage.isTabDisplayed(tab), is(true)
                );

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    public void validateNavigationToPersonalDetails() {
        dashboardPage = loginPage.clickOnlogin(config.username(), config.password());

        myInfoPage = dashboardPage.clickOnMyInfoLink()
                .clickMyInfoTabs(MyInfo.PERSONAL_DETAILS);
        try {
            HamcrestSoftAssertions hamcrestAssert = new HamcrestSoftAssertions();

            hamcrestAssert.assertThat(
                    "Personal Details page should be displayed",
                    myInfoPage.isPersonalDetailsPageDisplayed(), is(true)
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void validateUserIsAbleToUpdatePersonalDetails() {
        dashboardPage = loginPage.clickOnlogin(config.username(), config.password());
        myInfoPage = dashboardPage.clickOnMyInfoLink()
                .clickMyInfoTabs(MyInfo.PERSONAL_DETAILS);

        myInfoPage.selectDateFromCalendar(1, "2030-30-10");
        myInfoPage.selectOptionFromDropdown(2, "Married");
        myInfoPage.clickPersonalDetailsSaveButton(1);
        try {

            HamcrestSoftAssertions hamcrestAssert = new HamcrestSoftAssertions();

            hamcrestAssert.assertThat("Contact details should be saved successfully",
                    myInfoPage.getSuccessMessage(), containsString("Success"));

            hamcrestAssert.assertThat(
                    "Personal Details page should be displayed",
                    myInfoPage.isPersonalDetailsPageDisplayed(), is(true)
            );


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void validateUserIsAbleToDownloadProfileImage() {
        dashboardPage = loginPage.clickOnlogin(config.username(), config.password());
        myInfoPage = dashboardPage.clickOnMyInfoLink()
                .clickMyInfoTabs(MyInfo.PERSONAL_DETAILS);
        myInfoPage.clickDownloadImageButton();
        try {
            HamcrestSoftAssertions hamcrestAssert = new HamcrestSoftAssertions();
            hamcrestAssert.assertThat(
                    "Profile image should be downloaded successfully",
                    myInfoPage.isProfileImageDownloaded("test.png"), is(true)
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void validateUserIsAbleToUploadProfileImage() {
        dashboardPage = loginPage.clickOnlogin(config.username(), config.password());
        myInfoPage = dashboardPage.clickOnMyInfoLink()
                .clickMyInfoTabs(MyInfo.PERSONAL_DETAILS);
        myInfoPage.clickEditRecordButton();
        myInfoPage.uploadProfileImage("profile.jpg");
        myInfoPage.clickPersonalDetailsSaveButton(2);
        try {
            HamcrestSoftAssertions hamcrestAssert = new HamcrestSoftAssertions();
            hamcrestAssert.assertThat(
                    "Profile image should be uploaded successfully",
                    myInfoPage.isProfileImageUploaded("profile.jpg"), is(true)
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

