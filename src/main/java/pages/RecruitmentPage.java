package pages;

import basePackage.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utilities.SelUtils;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

public class RecruitmentPage extends BaseTest {

    private static final By LOADING_INDICATORS = By.cssSelector(".oxd-loading-spinner, .oxd-form-loader");
    private static final By RESULTS_BODY = By.cssSelector(".oxd-table-body");

    public RecruitmentPage() {
        PageFactory.initElements(wd, this);
    }

    @FindBy(xpath = "//h5[normalize-space()='Candidates']")
    private WebElement candidatesHeading;

    @FindBy(xpath = "//label[normalize-space()='Keywords']/parent::div/following-sibling::div//input")
    private WebElement keywordsInput;

    @FindBy(xpath = "//button[@type='submit' and normalize-space()='Search']")
    private WebElement searchButton;

    @FindBy(xpath = "//button[normalize-space()='Reset']")
    private WebElement resetButton;

    @FindBy(css = ".orangehrm-horizontal-padding > span")
    private WebElement recordsFoundMessage;

    @FindBy(css = ".oxd-table-body .oxd-table-row")
    private List<WebElement> resultRows;

    // Resolve the Candidate column from its heading instead of hardcoding a column number.
    @FindBy(xpath = "//div[contains(@class,'oxd-table-body')]//div[@role='row']/div[@role='cell']"
            + "[position() = count(//div[@role='columnheader'][text()[normalize-space()='Candidate']]"
            + "/preceding-sibling::div) + 1]")
    private List<WebElement> candidateNameCells;

    @FindBy(xpath = "//label[normalize-space()='Candidate Name']/parent::div/following-sibling::div//input")
    private WebElement candidateNameInput;

    @FindBy(css = "[role='listbox'] [role='option']")
    private List<WebElement> candidateSuggestions;

    @FindBy(xpath = "//nav[@aria-label='Topbar Menu']//a[normalize-space()='Candidates']")
    private WebElement candidatesTab;

    @FindBy(xpath = "//button[normalize-space()='Add']")
    private WebElement addCandidateButton;

    @FindBy(css = "input[name='firstName']")
    private WebElement firstNameInput;

    @FindBy(css = "input[name='lastName']")
    private WebElement lastNameInput;

    @FindBy(xpath = "//label[normalize-space()='Email']/parent::div/following-sibling::div//input")
    private WebElement emailInput;

    @FindBy(xpath = "//button[@type='submit' and normalize-space()='Save']")
    private WebElement saveCandidateButton;

    @FindBy(xpath = "//h6[normalize-space()='Candidate Profile']")
    private WebElement candidateProfileHeading;

    @FindBy(css = ".oxd-toast-content--success")
    private WebElement successNotification;

    @FindBy(xpath = "//*[@role='dialog']//button[normalize-space()='Yes, Delete']")
    private WebElement confirmDeleteButton;

    public RecruitmentPage waitForCandidatesPage() {
        SelUtils.waitForUrlContains("/recruitment/viewCandidates");
        SelUtils.waitForElementVisible(candidatesHeading);
        waitForResults();
        return this;
    }

    public void clickCandidatesTab() {
        SelUtils.clickElement(candidatesTab);
        waitForCandidatesPage();
    }

    public List<String> getCandidateNames() {
        waitForResults();
        return candidateNameCells.stream().map(cell -> normalizeName(cell.getText()))
                .collect(Collectors.toList());
    }

    public void enterCandidateName(String fullName) {
        // Full-name queries miss records with a blank middle name in the demo autocomplete.
        SelUtils.clearAndSendKeys(candidateNameInput, normalizeName(fullName).split(" ")[0]);
        WebElement suggestion = SelUtils.getWait().until(ExpectedConditions.refreshed(driver ->
                candidateSuggestions.stream().filter(WebElement::isDisplayed)
                        .filter(option -> normalizeName(option.getText()).equals(normalizeName(fullName)))
                        .findFirst().orElse(null)));
        // Duplicate display names are valid; any exact-name suggestion satisfies a name search.
        SelUtils.clickElement(suggestion);
        SelUtils.getWait().until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("[role='listbox']")));
    }

    public void clickAddCandidate() {
        SelUtils.clickElement(addCandidateButton);
        SelUtils.getWait().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h6[normalize-space()='Add Candidate']")));
    }

    public void enterFirstName(String firstName) {
        SelUtils.clearAndSendKeys(firstNameInput, firstName);
    }

    public void enterLastName(String lastName) {
        SelUtils.clearAndSendKeys(lastNameInput, lastName);
    }

    public void enterEmail(String email) {
        SelUtils.clearAndSendKeys(emailInput, email);
    }

    public String saveCandidate() {
        SelUtils.clickElement(saveCandidateButton);
        String message = SelUtils.getText(successNotification);
        waitForCandidateProfile();
        return message;
    }

    public String getFirstNameValue() {
        return firstNameInput.getDomProperty("value");
    }

    public String getLastNameValue() {
        return lastNameInput.getDomProperty("value");
    }

    public String getEmailValue() {
        return emailInput.getDomProperty("value");
    }

    public String getCandidateId() {
        waitForCandidateProfile();
        String path = URI.create(wd.getCurrentUrl()).getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }

    /** Removes only the generated test record, after verifying its saved profile ID. */
    public void deleteCreatedCandidate(String candidateId, String fullName) {
        clickCandidatesTab();
        enterCandidateName(fullName);
        clickSearchButton();
        SelUtils.clickElement(findCandidateRow(fullName).findElement(By.cssSelector("button:has(.bi-eye-fill)")));
        if (!getCandidateId().equals(candidateId)) {
            throw new IllegalStateException("Refusing to delete a candidate with a different saved ID");
        }

        clickCandidatesTab();
        enterCandidateName(fullName);
        clickSearchButton();
        SelUtils.clickElement(findCandidateRow(fullName).findElement(By.cssSelector("button:has(.bi-trash)")));
        SelUtils.clickElement(confirmDeleteButton);
        SelUtils.getWait().until(ExpectedConditions.invisibilityOf(confirmDeleteButton));
        waitForResults();
        SelUtils.getWait().until(ExpectedConditions.numberOfElementsToBe(
                By.cssSelector(".oxd-table-body .oxd-table-row"), 0));
    }

    public void enterKeywords(String keywords) {
        SelUtils.clearAndSendKeys(keywordsInput, keywords);
    }

    public String getKeywordsValue() {
        SelUtils.waitForElementVisible(keywordsInput);
        return keywordsInput.getDomProperty("value");
    }

    public void clickSearchButton() {
        SelUtils.clickElement(searchButton);
        waitForResults();
    }

    public void clickResetButton() {
        SelUtils.clickElement(resetButton);
        waitForResults();
    }

    public String getRecordsFoundMessage() {
        waitForResults();
        return SelUtils.getText(recordsFoundMessage);
    }

    public int getResultRowCount() {
        waitForResults();
        return resultRows.size();
    }

    private WebElement findCandidateRow(String fullName) {
        List<WebElement> matches = candidateNameCells.stream()
                .filter(cell -> normalizeName(cell.getText()).equals(normalizeName(fullName)))
                .collect(Collectors.toList());
        if (matches.size() != 1) {
            throw new IllegalStateException("Expected one generated candidate named " + fullName
                    + ", found " + matches.size());
        }
        return matches.get(0).findElement(By.xpath("parent::div[@role='row']"));
    }

    private void waitForCandidateProfile() {
        SelUtils.getWait().until(ExpectedConditions.urlMatches("/recruitment/addCandidate/[0-9]+(?:[?#].*)?$"));
        SelUtils.waitForElementVisible(candidateProfileHeading);
        SelUtils.getWait().until(ExpectedConditions.refreshed(driver ->
                driver.findElements(LOADING_INDICATORS).stream().noneMatch(WebElement::isDisplayed)
                        && !firstNameInput.isEnabled()));
    }

    private String normalizeName(String name) {
        return name.trim().replaceAll("\\s+", " ");
    }

    private void waitForResults() {
        SelUtils.getWait().until(ExpectedConditions.refreshed(driver -> {
            boolean loading = driver.findElements(LOADING_INDICATORS).stream()
                    .anyMatch(WebElement::isDisplayed);
            return !loading && recordsFoundMessage.isDisplayed()
                    && driver.findElement(RESULTS_BODY).isDisplayed();
        }));
    }
}
