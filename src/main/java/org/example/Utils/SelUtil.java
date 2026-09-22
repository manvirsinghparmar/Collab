package org.example.Utils;

import org.example.Base.TestBase;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class SelUtil extends TestBase implements WebDriver, JavascriptExecutor {

    public static void initialize(WebDriver driver) {
        DriverManager.setDriver(driver);
        DriverManager.setWait(new WebDriverWait(driver, Duration.ofSeconds(10)));
    }


    public static void click(WebElement element) {
        DriverManager.getWait().until(driver -> {
            try {
                if (element.isDisplayed() && element.isEnabled()) {
                    element.click();
                    return true;
                }
                return false;
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                return false;
            }
        });
    }

    public static void click(By locator) {
        DriverManager.getWait().until(driver -> {
            try {
                WebElement element = driver.findElement(locator);
                if (element.isDisplayed() && element.isEnabled()) {
                    element.click();
                    return true;
                }
                return false;
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                return false;
            }
        });
    }

    public static String getText(WebElement element) {
        DriverManager.getWait().until(driver -> element.isDisplayed());
        return element.getText();
    }

    public static void acceptAlert() {
        DriverManager.getWait().until(driver -> {
            try {
                DriverManager.getDriver().switchTo().alert();
                return true;
            } catch (Exception e) {
                return false;
            }
        });
        DriverManager.getDriver().switchTo().alert().accept();
    }

    public static String getTextFromAlert() {
        DriverManager.getWait().until(driver -> {
            try {
                DriverManager.getDriver().switchTo().alert();
                return true;
            } catch (Exception e) {
                return false;
            }
        });
        return DriverManager.getDriver().switchTo().alert().getText();
    }

    @Override
    public void get(String url) {

    }

    @Override
    public @Nullable String getCurrentUrl() {
        return "";
    }

    @Override
    public @Nullable String getTitle() {
        return "";
    }

    @Override
    public List<WebElement> findElements(By by) {
        return List.of();
    }

    @Override
    public WebElement findElement(By by) {
        return null;
    }

    @Override
    public @Nullable String getPageSource() {
        return "";
    }

    @Override
    public void close() {

    }

    @Override
    public void quit() {

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
