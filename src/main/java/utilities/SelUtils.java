package utilities;

import org.example.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class SelUtils extends BaseTest implements WebDriver, JavascriptExecutor {

    private static final int WAIT_TIME = 15;

    private SelUtils() {
        // Utility class
    }

    private static WebDriverWait getWait() {
        return new WebDriverWait(wd, Duration.ofSeconds(WAIT_TIME));
    }

    // ==================== Wait Methods ====================

    public static WebElement waitForElementClickable(WebElement element) {
        return getWait().until(
                ExpectedConditions.elementToBeClickable(element)
        );
    }

    public static boolean waitForElementVisible(WebElement element) {
        return getWait()
                .until(ExpectedConditions.visibilityOf(element))
                .isDisplayed();
    }

    public static List<WebElement> waitForElementsVisible(List<WebElement> elements) {
        return getWait()
                .until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    public static List<WebElement> waitForElementsPresent(By locator) {
        return getWait()
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public static boolean waitForElementPresent(By locator) {
        return getWait()
                .until(ExpectedConditions.presenceOfElementLocated(locator)) != null;
    }

    public static boolean waitForUrlContains(String partialUrl) {
        return getWait()
                .until(ExpectedConditions.urlContains(partialUrl));
    }

    public static String waitForPageSource() {
        return getWait().until(driver -> {
            String pageSource = driver.getPageSource();
            return pageSource != null && !pageSource.isEmpty()
                    ? pageSource
                    : null;
        });
    }

    // ==================== Element Actions ====================

    public static void clickElement(WebElement element) {

        waitForElementVisible(element);

        try {
            scrollToBottom();
            waitForElementClickable(element).click();

        } catch (TimeoutException |
                 ElementClickInterceptedException |
                 StaleElementReferenceException e) {

            ((JavascriptExecutor) wd)
                    .executeScript("arguments[0].click();", element);
        }
    }

    public static void sendKeys(WebElement element, String text) {

        waitForElementVisible(element);

        element.clear();
        element.sendKeys(text);
    }

    public static void clearAndSendKeys(WebElement element, String text) {

        waitForElementVisible(element);

        element.clear();
        element.sendKeys(text);
    }

    public static void enterTextIntoInputBox(WebElement element, String text) {

        waitForElementVisible(element);

        element.sendKeys(text);
    }

    public static void scrollToBottom() {

        ((JavascriptExecutor) wd)
                .executeScript(
                        "window.scrollTo(0, document.body.scrollHeight);"
                );
    }

    // ==================== WebDriver Methods ====================

    @Override
    public void get(String url) {
        waitForUrlContains(url);
    }

    @Override
    public String getCurrentUrl() {
        return wd.getCurrentUrl();
    }

    @Override
    public String getTitle() {
        return wd.getTitle();
    }

    @Override
    public List<WebElement> findElements(By element) {
        return waitForElementsPresent(element);
    }

    @Override
    public WebElement findElement(By element) {
        return waitForElementsPresent(element).get(0);
    }

    @Override
    public String getPageSource() {
        return waitForPageSource();
    }

    @Override
    public void close() {
        wd.close();
    }

    @Override
    public void quit() {
        wd.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return wd.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        return wd.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return wd.switchTo();
    }

    @Override
    public Navigation navigate() {
        return wd.navigate();
    }

    @Override
    public Options manage() {
        return wd.manage();
    }

    @Override
    public Object executeScript(String script, Object... args) {
        return ((JavascriptExecutor) wd).executeScript(script, args);
    }

    @Override
    public Object executeAsyncScript(String script, Object... args) {
        return ((JavascriptExecutor) wd).executeAsyncScript(script, args);
    }
}