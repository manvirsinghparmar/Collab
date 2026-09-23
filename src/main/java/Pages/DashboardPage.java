package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class DashboardPage {

    private WebDriver wd;

    public DashboardPage(WebDriver wd) {
        PageFactory.initElements(wd, this);
    }

    //Elements and methods for DashboardPage can be added here

}