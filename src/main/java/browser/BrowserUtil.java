package browser;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import listeners.WebDriverEventListener;
import platform.PlatformUtil;

import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

public final class BrowserUtil {

    private static final Browser DEFAULT_BROWSER = Browser.CHROME;

    private BrowserUtil() {
    }

    public static WebDriver createDriver() {
        return createDriver(resolveBrowser());
    }

    public static WebDriver createDriver(Browser browser) {
        if (browser == null) {
            throw new IllegalArgumentException("Browser must be CHROME, FIREFOX, EDGE, or SAFARI");
        }
        WebDriver rawDriver;
        switch (browser) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                rawDriver = new ChromeDriver(chromeOptions());
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                rawDriver = new FirefoxDriver(firefoxOptions());
                break;
            case EDGE:
                WebDriverManager.edgedriver().setup();
                rawDriver = new EdgeDriver(edgeOptions());
                break;
            case SAFARI:
                if (!PlatformUtil.isMac()) {
                    throw new UnsupportedOperationException(
                            "Safari is only supported on macOS. Select CHROME, FIREFOX, or EDGE on this operating system.");
                }
                rawDriver = new SafariDriver(safariOptions());
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        return new EventFiringDecorator(new WebDriverEventListener()).decorate(rawDriver);
    }

    public static Browser resolveBrowser() {
        return resolveBrowser(System::getProperty, System::getenv);
    }

    public static Browser resolveBrowser(Function<String, String> systemProperties,
                                         Function<String, String> environment) {
        String value = systemProperties.apply("browser");
        if (value == null) {
            value = environment.apply("BROWSER");
        }
        if (value == null) {
            return DEFAULT_BROWSER;
        }
        try {
            return Browser.valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid browser override: expected CHROME, FIREFOX, EDGE, or SAFARI");
        }
    }

    static ChromeOptions chromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", Map.of("autofill.profile_enabled", false));
        return options;
    }

    public static FirefoxOptions firefoxOptions() {
        return new FirefoxOptions();
    }

    static EdgeOptions edgeOptions() {
        return new EdgeOptions();
    }

    static SafariOptions safariOptions() {
        return new SafariOptions();
    }
}
