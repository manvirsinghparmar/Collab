package basePackage.testPages;

import basePackage.base.BaseTest;
import hamcrestAssertion.HamcrestSoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.RecruitmentPage;
import utilities.SelUtils;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

public class RecruitmentPageTest extends BaseTest {

    private LoginPage loginPage;
    private RecruitmentPage recruitmentPage;
    private String createdCandidateId;
    private String createdCandidateName;

    @BeforeMethod
    public void launch() {
        createdCandidateId = null;
        initialization();
        loginPage = new LoginPage();
    }

    @Test
    public void validateCandidateSearchWithUnmatchedKeywordsAndReset() {
        DashboardPage dashboardPage = loginPage.clickOnlogin(config.username(), config.password());
        SelUtils.waitForUrlContains(config.dashboardUrl());
        RecruitmentPage recruitmentPage = dashboardPage.clickOnRecruitmentMenu();
        String unmatchedKeywords = "no-match-" + UUID.randomUUID();
        recruitmentPage.enterKeywords(unmatchedKeywords);
        assertThat("Keywords should contain the entered value", recruitmentPage.getKeywordsValue(), equalTo(unmatchedKeywords));
        recruitmentPage.clickSearchButton();
        try (HamcrestSoftAssertions hmAssert = new HamcrestSoftAssertions()) {
        hmAssert.assertThat("An unmatched keyword should show the empty-results message",
                recruitmentPage.getRecordsFoundMessage(), equalTo("No Records Found"));
        hmAssert.assertThat("An unmatched keyword should return no candidate rows",
                recruitmentPage.getResultRowCount(), equalTo(0));
        recruitmentPage.clickResetButton();
        hmAssert.assertThat("Reset should clear the Keywords field", recruitmentPage.getKeywordsValue(),
                equalTo(""));
        hmAssert.assertThat("Reset should restore candidate results", recruitmentPage.getResultRowCount(),
                greaterThan(0));
        }
    }

    @Test
    public void validateCandidateSearchWithExistingName() {
        DashboardPage dashboardPage = loginPage.clickOnlogin(config.username(), config.password());
        SelUtils.waitForUrlContains(config.dashboardUrl());
        RecruitmentPage recruitmentPage = dashboardPage.clickOnRecruitmentMenu();
        List<String> candidateNames = recruitmentPage.getCandidateNames();
        assertThat("An existing candidate is required for name search", candidateNames, not(empty()));
        String fullName = candidateNames.get(0);
        recruitmentPage.enterCandidateName(fullName);
        recruitmentPage.clickSearchButton();

        try (HamcrestSoftAssertions hmAssert = new HamcrestSoftAssertions()) {
            hmAssert.assertThat("Name search should return candidate records",
                    recruitmentPage.getResultRowCount(), greaterThan(0));
            hmAssert.assertThat("Every returned candidate should match the selected name",
                    recruitmentPage.getCandidateNames(), everyItem(equalTo(fullName)));
        }
    }

    @Test
    public void validateUserIsAbleToAddCandidate() {
        DashboardPage dashboardPage = loginPage.clickOnlogin(config.username(), config.password());
        SelUtils.waitForUrlContains(config.dashboardUrl());
        recruitmentPage = dashboardPage.clickOnRecruitmentMenu();
        String uniqueId = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
        String firstName = "Recruitment" + uniqueId;
        String lastName = "Automation";
        String email = "candidate." + uniqueId + "@example.com";
        createdCandidateName = firstName + " " + lastName;
        recruitmentPage.clickAddCandidate();
        recruitmentPage.waitForAddCandidatePage();
        recruitmentPage.enterFirstName(firstName);
        recruitmentPage.enterLastName(lastName);
        recruitmentPage.enterEmail(email);
        String successMessage = recruitmentPage.saveCandidate();
        createdCandidateId = recruitmentPage.getCandidateId();
        Reporter.log("Created demo candidate " + createdCandidateId + ": " + createdCandidateName, true);

        try (HamcrestSoftAssertions hmAssert = new HamcrestSoftAssertions()) {
            hmAssert.assertThat("Adding a candidate should show a success notification",
                    successMessage, containsString("Successfully Saved"));
            hmAssert.assertThat("Saved first name should match", recruitmentPage.getFirstNameValue(), equalTo(firstName));
            hmAssert.assertThat("Saved last name should match", recruitmentPage.getLastNameValue(), equalTo(lastName));
            hmAssert.assertThat("Saved email should match", recruitmentPage.getEmailValue(), equalTo(email));
            recruitmentPage.clickCandidatesTab();
            recruitmentPage.enterCandidateName(createdCandidateName);
            recruitmentPage.clickSearchButton();
            hmAssert.assertThat("The new candidate should appear in search results",
                    recruitmentPage.getCandidateNames(), hasItem(createdCandidateName));
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        try {
            if (createdCandidateId != null) {
                recruitmentPage.deleteCreatedCandidate(createdCandidateId, createdCandidateName);
            }
        } finally {
            if (wd != null) {
                super.teardown();
            }
        }
    }
}
