package utilities;

import org.example.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WaitUtils extends BaseTest {

    private WaitUtils() {
        // Utility class
    }

    public static WebElement waitForElementClickable(WebElement element) {
        return getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    private static WebDriverWait getWait() {
        return new WebDriverWait(wd, Duration.ofSeconds(15));
    }


    public static List<WebElement> waitForElementsPresent(By element){
        return getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(element));
    }

    public static boolean waitForElementPresent(By element) {
        return getWait().until(ExpectedConditions.presenceOfElementLocated(element)).isDisplayed();
    }

    public static boolean waitForUrlContains(String partialUrl) {
        return getWait().until(ExpectedConditions.urlContains(partialUrl));
    }

    public static boolean waitForElementVisible(WebElement element) {
        return getWait().until(ExpectedConditions.visibilityOf(element)).isDisplayed();
    }

    public static boolean waitForElementsVisible(List<WebElement> elements) {
        return getWait().until(ExpectedConditions.visibilityOfAllElements(elements)).size() > 0;
    }

    //public static boolean waitForElementNotVisible(WebElement element) {
       // return getWait().until(ExpectedConditions.invisibilityOf(element));
    //}

    public static String waitForPageSource() {
        return getWait().until(wd -> {
            String pageSource = wd.getPageSource();
            return pageSource != null && !pageSource.isEmpty()
                    ? pageSource
                    : null;
        });
    }
}
