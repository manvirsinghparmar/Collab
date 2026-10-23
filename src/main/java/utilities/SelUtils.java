package utilities;

import basePackage.base.BaseTest;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import platform.PlatformUtil;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class SelUtils extends BaseTest implements WebDriver, JavascriptExecutor {
    private SelUtils() {
    }

    private static WebDriver driver() {
        WebDriver active = DriverManager.getDriver();
        if (active != null) {
            return active;
        }
        if (wd != null) {
            return wd;
        }
        throw new IllegalStateException("WebDriver is not initialized.");
    }

    public static WebDriverWait getWait() {
        return new WebDriverWait(driver(), Duration.ofSeconds(15));
    }

    public static WebElement waitForElementClickable(WebElement element) {
        return getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public static List<WebElement> waitForElementsPresent(By element) {
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

    public static String waitForPageSource() {
        return getWait().until(webDriver -> {
            String pageSource = webDriver.getPageSource();
            return pageSource != null && !pageSource.isEmpty()
                    ? pageSource
                    : null;
        });
    }

    public static void clickElement(WebElement element) {
        waitForElementVisible(element);
        try {
            scrollIntoView(element);
            waitForElementClickable(element).click();
        } catch (TimeoutException | ElementClickInterceptedException | StaleElementReferenceException e) {
            FrameworkLogger.stepInfo("Standard click failed, retrying with JavaScript click.");
            ((JavascriptExecutor) driver()).executeScript("arguments[0].click();", element);
        }
    }

    public static void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver())
                .executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", element);
    }

    @Override
    public void get(String url) {
        waitForUrlContains(url);
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

    public static void enterDate(WebElement element, String date) {
        clickElement(element);
        Keys modifier = PlatformUtil.isMac() ? Keys.COMMAND : Keys.CONTROL;
        element.sendKeys(Keys.chord(modifier, "a"), Keys.BACK_SPACE, date);
        // Verify replacement before Tab lets the application format the date on blur.
        getWait().until(ExpectedConditions.domPropertyToBe(element, "value", date));
        element.sendKeys(Keys.TAB);
    }

    public static void enterTextIntoInputBox(WebElement element, String text) {
        waitForElementVisible(element);
        element.sendKeys(text);
    }

    public static String getText(WebElement element) {
        waitForElementVisible(element);
        String text = element.getText();
        FrameworkLogger.stepInfo("Getting text on " + text);
        FrameworkLogger.stepInfo("Got text on " + text);
        return text;
    }

    @Override
    public @Nullable String getCurrentUrl() {
        return driver().getCurrentUrl();
    }

    @Override
    public @Nullable String getTitle() {
        return driver().getTitle();
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
    public @Nullable String getPageSource() {
        return waitForPageSource();
    }

    @Override
    public void close() {
        driver().close();
    }

    @Override
    public void quit() {
        driver().quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return Set.of();
    }

    @Override
    public String getWindowHandle() {
        return "";
    }

    @Override
    public TargetLocator switchTo() {
        return null;
    }

    @Override
    public Navigation navigate() {
        return null;
    }

    @Override
    public Options manage() {
        return null;
    }

    @Override
    public @Nullable Object executeScript(String script, @Nullable Object... args) {
        return null;
    }

    @Override
    public @Nullable Object executeAsyncScript(String script, @Nullable Object... args) {
        return null;
    }
}
