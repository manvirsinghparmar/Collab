package utilities;

import org.example.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public final class SelUtils extends BaseTest {

    private static final int WAIT_TIME = 15;

    private SelUtils() {
        // Utility class
    }

    private static WebDriverWait getWait() {
        return new WebDriverWait(wd, Duration.ofSeconds(WAIT_TIME));
    }


    public static void clickElement(WebElement element) {

        try {
            getWait().until(ExpectedConditions.visibilityOf(element));

            scrollIntoView(element);

            getWait().until(
                    ExpectedConditions.elementToBeClickable(element)
            ).click();

        } catch (TimeoutException |
                 NoSuchElementException |
                 ElementClickInterceptedException |
                 StaleElementReferenceException e) {

            ((JavascriptExecutor) wd)
                    .executeScript("arguments[0].click();", element);
        }
    }


    public static void sendKeys(WebElement element, String text) {

        getWait().until(
                ExpectedConditions.visibilityOf(element)
        );

        element.clear();
        element.sendKeys(text);
    }

    public static void clearAndSendKeys(WebElement element, String text) {

        getWait().until(
                ExpectedConditions.visibilityOf(element)
        );

        element.clear();
        element.sendKeys(text);
    }

    public static void enterTextIntoInputBox(WebElement element, String text) {

        getWait().until(
                ExpectedConditions.visibilityOf(element)
        );

        element.sendKeys(text);
    }


    public static WebElement findElement(By locator) {

        return getWait().until(
                ExpectedConditions.presenceOfElementLocated(locator)
        );
    }

    public static List<WebElement> findElements(By locator) {

        return getWait().until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(locator)
        );
    }


    public static boolean isElementVisible(WebElement element) {

        return getWait().until(
                ExpectedConditions.visibilityOf(element)
        ).isDisplayed();
    }

    public static List<WebElement> waitForElementsVisible(
            List<WebElement> elements) {

        return getWait().until(
                ExpectedConditions.visibilityOfAllElements(elements)
        );


    }


    public static boolean waitForUrlContains(String partialUrl) {

        return getWait().until(
                ExpectedConditions.urlContains(partialUrl)
        );
    }


    public static boolean waitForElementNotVisible(WebElement element) {

        return getWait().until(
                ExpectedConditions.invisibilityOf(element)
        );
    }


    public static String waitForPageSource() {

        return getWait().until(driver -> {

            String pageSource = driver.getPageSource();

            return pageSource != null && !pageSource.isEmpty()
                    ? pageSource
                    : null;
        });
    }


    public static void scrollIntoView(WebElement element) {

        getWait().until(ExpectedConditions.visibilityOf(element));

        ((JavascriptExecutor) wd).executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'center'});",
                element
        );
    }
}