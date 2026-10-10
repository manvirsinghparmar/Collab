package browser;

import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

public class BrowserUtilTest {

    @Test
    public void usesChromeWhenNoOverrideIsProvided() {
        Assert.assertEquals(BrowserUtil.resolveBrowser(key -> null, key -> null), Browser.CHROME);
    }

    @Test
    public void systemPropertyOverridesEnvironment() {
        Assert.assertEquals(BrowserUtil.resolveBrowser(
                Map.of("browser", " firefox ")::get, Map.of("BROWSER", "EDGE")::get), Browser.FIREFOX);
    }

    @DataProvider
    public Object[][] supportedBrowsers() {
        return new Object[][] {
                {"chrome", Browser.CHROME},
                {"Firefox", Browser.FIREFOX},
                {" EDGE ", Browser.EDGE}
        };
    }

    @Test(dataProvider = "supportedBrowsers")
    public void environmentOverridesDefault(String value, Browser expected) {
        Assert.assertEquals(BrowserUtil.resolveBrowser(key -> null, Map.of("BROWSER", value)::get), expected);
    }

    @DataProvider
    public Object[][] invalidOverrides() {
        return new Object[][] {{""}, {" "}, {"SAFARI"}};
    }

    @Test(dataProvider = "invalidOverrides")
    public void invalidSystemPropertyDoesNotFallBack(String value) {
        IllegalArgumentException error = Assert.expectThrows(IllegalArgumentException.class,
                () -> BrowserUtil.resolveBrowser(Map.of("browser", value)::get, Map.of("BROWSER", "EDGE")::get));
        Assert.assertTrue(error.getMessage().contains("CHROME, FIREFOX, or EDGE"));
    }

    @Test(dataProvider = "invalidOverrides")
    public void invalidEnvironmentDoesNotFallBack(String value) {
        Assert.expectThrows(IllegalArgumentException.class,
                () -> BrowserUtil.resolveBrowser(key -> null, Map.of("BROWSER", value)::get));
    }

    @Test
    public void nullExplicitBrowserFailsBeforeDriverSetup() {
        Assert.expectThrows(IllegalArgumentException.class, () -> BrowserUtil.createDriver(null));
    }

    @Test
    public void chromeOptionsPreserveAutofillPreferenceAndUseFreshInstances() {
        ChromeOptions first = BrowserUtil.chromeOptions();
        ChromeOptions second = BrowserUtil.chromeOptions();
        Assert.assertNotSame(first, second);
        Assert.assertEquals(first.getBrowserName(), "chrome");
        Map<?, ?> settings = (Map<?, ?>) second.asMap().get("goog:chromeOptions");
        Assert.assertEquals(settings.get("prefs"), Map.of("autofill.profile_enabled", false));
        Assert.assertTrue(((java.util.List<?>) settings.get("args")).isEmpty(), "Chrome should remain visible");
        first.addArguments("--headless=new");
        Assert.assertNotEquals(first.asMap(), second.asMap(), "Options must not be shared between launches");
    }

    @Test
    public void firefoxAndEdgeUseFreshDefaultOptions() {
        Assert.assertNotSame(BrowserUtil.firefoxOptions(), BrowserUtil.firefoxOptions());
        Assert.assertNotSame(BrowserUtil.edgeOptions(), BrowserUtil.edgeOptions());
        Assert.assertEquals(BrowserUtil.firefoxOptions().getBrowserName(), "firefox");
        Assert.assertEquals(BrowserUtil.edgeOptions().getBrowserName(), "MicrosoftEdge");
    }
}
