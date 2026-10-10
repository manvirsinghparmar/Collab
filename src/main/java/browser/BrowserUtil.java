package browser;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

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
            throw new IllegalArgumentException("Browser must be CHROME, FIREFOX, or EDGE");
        }
        return switch (browser) {
            case CHROME -> {
                WebDriverManager.chromedriver().setup();
                yield new ChromeDriver(chromeOptions());
            }
            case FIREFOX -> {
                WebDriverManager.firefoxdriver().setup();
                yield new FirefoxDriver(firefoxOptions());
            }
            case EDGE -> {
                WebDriverManager.edgedriver().setup();
                yield new EdgeDriver(edgeOptions());
            }
        };
    }

    public static Browser resolveBrowser() {
        return resolveBrowser(System::getProperty, System::getenv);
    }

    static Browser resolveBrowser(Function<String, String> systemProperties,
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
            throw new IllegalArgumentException("Invalid browser override: expected CHROME, FIREFOX, or EDGE");
        }
    }

    static ChromeOptions chromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", Map.of("autofill.profile_enabled", false));
        return options;
    }

    static FirefoxOptions firefoxOptions() {
        return new FirefoxOptions();
    }

    static EdgeOptions edgeOptions() {
        return new EdgeOptions();
    }
}
