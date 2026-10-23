package listeners;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;
import utilities.FrameworkLogger;

public class WebDriverEventListener implements WebDriverListener {

    @Override
    public void beforeGet(WebDriver driver, String url) {
        FrameworkLogger.stepInfo("Navigating to: " + url);
    }

    @Override
    public void afterGet(WebDriver driver, String url) {
        FrameworkLogger.stepInfo("Navigation completed: " + url);
    }

    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        FrameworkLogger.debugInfo(
                "Finding Element using " + locator);
    }

    @Override
    public void afterFindElement(
            WebDriver driver, By locator, WebElement result) {

        FrameworkLogger.debugInfo(
                "Found Element using " + locator);
    }

    @Override
    public void beforeClick(WebElement element) {
        FrameworkLogger.stepInfo(
                "Clicking on " + getElementDescription(element));
    }

    @Override
    public void afterClick(WebElement element) {
        FrameworkLogger.stepInfo(
                "Clicked on " + getElementDescription(element));
    }

    @Override
    public void beforeQuit(WebDriver driver) {
        FrameworkLogger.stepInfo("Closing browser session");
    }

    @Override
    public void beforeSendKeys(
            WebElement element, CharSequence... keysToSend) {

        FrameworkLogger.stepInfo(
                "Entering text into "
                        + getElementDescription(element)
                        + " [length=" + getInputLength(keysToSend) + "]");
    }

    private int getInputLength(CharSequence[] keysToSend) {
        if (keysToSend == null) {
            return 0;
        }

        int length = 0;

        for (CharSequence keys : keysToSend) {
            if (keys != null) {
                length += keys.length();
            }
        }

        return length;
    }

    private String getElementDescription(WebElement element) {
        try {
            return String.valueOf(element);
        } catch (RuntimeException e) {
            return "unknown-element";
        }
    }
}