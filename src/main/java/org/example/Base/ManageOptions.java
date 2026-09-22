package org.example.Base;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class ManageOptions {

    private ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--headless=new");
        return options;
    }

    private EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--incognito");
        options.addArguments("--headless=new");
        return options;
    }

    private FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--incognito");
        options.addArguments("--headless=new");
        return options;
    }

    public Capabilities getOptions(String browser) {

        switch (browser) {
            case "chrome":
                return getChromeOptions();
            case "edge":
                return getEdgeOptions();
            case "firefox":
                return getFirefoxOptions();
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }
}
