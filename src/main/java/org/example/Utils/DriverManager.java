package org.example.Utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> waitThreadLocal = new ThreadLocal<>();

    private DriverManager() {
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    public static WebDriverWait getWait() {
        return waitThreadLocal.get();
    }

    public static void setWait(WebDriverWait wait) {
        waitThreadLocal.set(wait);
    }

    public static void unload() {
        driverThreadLocal.remove();
        waitThreadLocal.remove();
    }
}
