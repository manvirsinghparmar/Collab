package org.example.Utils;

import org.example.Base.TestBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

public class WebDriverEvents extends TestBase implements WebDriverListener {

    @Override
    public void beforeAccept(Alert alert) {
        logger.info("Before accepting Alert");
    }

    @Override
    public void afterAccept(Alert alert) {
        logger.info("Accepted Alert");
    }

    @Override
    public void beforeDismiss(Alert alert) {
        logger.info("Before dismissing Alert");
    }

    @Override
    public void afterDismiss(Alert alert) {
        logger.info("Dismissed Alert");
    }

    @Override
    public void beforeGet(WebDriver driver, String url) {
        logger.info("Navigating to: " + url);
    }

    @Override
    public void afterGet(WebDriver driver, String url) {
        logger.info("Navigated to: " + url);
    }

    @Override
    public void beforeBack(WebDriver.Navigation navigation) {
        logger.info("Navigating back");
    }

    @Override
    public void beforeRefresh(WebDriver.Navigation navigation) {
        logger.info("Page Refreshing");
    }

    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        logger.info("Finding element using " + locator);
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        logger.info("Found element using " + locator);
    }

    @Override
    public void beforeClick(WebElement element) {
        try {
            logger.info("Clicking on " + element.getText());
        } catch (Exception e) {
            logger.info("Clicking on element");
        }
    }

    @Override
    public void afterClick(WebElement element) {
        logger.info("Clicked on element");
    }

    @Override
    public void beforeGetText(WebElement element) {
        logger.info("Getting text from element");
    }

    @Override
    public void afterGetText(WebElement element, String result) {
        logger.info("Got text from element: " + result);
    }
}
