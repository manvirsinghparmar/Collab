package pages;

import org.example.BaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.WaitUtils;

public class DashboardPage extends BaseTest {

    public DashboardPage(WebDriver wd) {
        PageFactory.initElements(wd, this);
    }

    @FindBy(xpath = "(//a[@class='oxd-main-menu-item'])[2]")
    private WebElement pimLink;

    public PimPage clickOnPIMLink() {
        WaitUtils.waitForElementClickable(pimLink).click();
        return new PimPage(wd);
    }



}