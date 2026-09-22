package org.example.Config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BrowserConfiguration {

    public static WebDriver createDriver(String defaultBrowser, boolean incognito) {

        switch (defaultBrowser.toLowerCase()) {

            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();

                if (incognito) {
                    chromeOptions.addArguments("--incognito");
                }
                return new ChromeDriver(chromeOptions);

            case "firefox":
                return new FirefoxDriver();

            case "edge":
                return new EdgeDriver();

            default:
                throw new IllegalArgumentException(
                        "Unsupported browser: " + defaultBrowser
                );
        }
    }


}
