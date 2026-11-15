package utilities;

import org.openqa.selenium.WebDriver;

/**
 * Stores the active WebDriver per test thread.
 */
public final class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static void setDriver(WebDriver driver) {
        DRIVER.set(driver);
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public WebDriver requireDriver() {
        WebDriver driver = getDriver();
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized for the current thread.");
        }
        return driver;
    }

    public static void clear() {
        DRIVER.remove();
    }
}
