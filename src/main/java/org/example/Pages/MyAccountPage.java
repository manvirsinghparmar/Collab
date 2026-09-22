package org.example.Pages;

import org.example.Base.TestBase;
import org.openqa.selenium.support.PageFactory;

public class MyAccountPage extends TestBase {

    public MyAccountPage() {
        PageFactory.initElements(driver, this);
    }

    public String getTitle() {
        return driver.getTitle();
    }

}
