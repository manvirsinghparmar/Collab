package pages;

import basePackage.base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.SelUtils;

public class ReportsPage extends BaseTest {

    public ReportsPage() {
        PageFactory.initElements(wd, this);
    }

    @FindBy(xpath = "//input[contains(@placeholder, 'Type for hints...')]")
    private WebElement reportNameInputTextBox;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement searchButton;

    @FindBy(xpath = "(//button[@type='button'])[9]")
    private WebElement reportButton;

    public void enterReportName(String reportName) {
        SelUtils.clickElement(reportNameInputTextBox);
        SelUtils.clearAndSendKeys(reportNameInputTextBox, reportName);
    }

    public void clickSearchButton() {
        SelUtils.clickElement(searchButton);
    }

    public void clickReportButton() {
        SelUtils.clickElement(reportButton);
    }

    public void getReport(String reportName) {
        enterReportName(reportName);
        clickSearchButton();
        clickReportButton();
    }


}
