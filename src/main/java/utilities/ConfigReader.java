package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Properties;
import java.util.function.Function;

public final class ConfigReader {

    private final String loginUrl;
    private final String dashboardUrl;
    private final String username;
    private final String password;

    private static class Holder {
        private static final ConfigReader INSTANCE = new ConfigReader(
                loadProperties(), System::getProperty, System::getenv);
    }

    public static ConfigReader getInstance() {
        return Holder.INSTANCE;
    }

    // Separate resolution from file loading so validation can run without a browser.
    ConfigReader(Properties defaults, Function<String, String> systemProperties,
                 Function<String, String> environment) {
        URI base = parseUri("app.base.url",
                required("app.base.url", defaults, systemProperties, environment).trim());
        if (!("https".equalsIgnoreCase(base.getScheme()) || "http".equalsIgnoreCase(base.getScheme()))
                || base.getHost() == null || base.getRawUserInfo() != null
                || base.getRawQuery() != null || base.getRawFragment() != null
                || !(base.getRawPath().isEmpty() || base.getRawPath().equals("/"))) {
            throw invalid("app.base.url", "expected an absolute HTTP(S) URL containing only the host and optional port");
        }

        loginUrl = pageUrl(base, "app.login.path", defaults, systemProperties, environment);
        dashboardUrl = pageUrl(base, "app.dashboard.path", defaults, systemProperties, environment);
        username = required("login.username", defaults, systemProperties, environment);
        password = required("login.password", defaults, systemProperties, environment);
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = ConfigReader.class.getResourceAsStream("/config.properties")) {
            if (input == null) {
                throw new IllegalStateException("Missing config.properties on the classpath");
            }
            properties.load(new InputStreamReader(input, StandardCharsets.UTF_8));
            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read config.properties", e);
        }
    }

    private static String required(String key, Properties defaults,
                                   Function<String, String> systemProperties,
                                   Function<String, String> environment) {
        String value = systemProperties.apply(key);
        if (value == null) {
            value = environment.apply(key.replace('.', '_').toUpperCase(Locale.ROOT));
        }
        if (value == null) {
            value = defaults.getProperty(key);
        }
        if (value == null || value.isBlank()) {
            throw invalid(key, "a nonblank value is required");
        }
        return value;
    }

    private static String pageUrl(URI base, String key, Properties defaults,
                                  Function<String, String> systemProperties,
                                  Function<String, String> environment) {
        URI path = parseUri(key, required(key, defaults, systemProperties, environment).trim());
        if (path.isAbsolute() || path.getRawAuthority() != null
                || path.getRawPath() == null || !path.getRawPath().startsWith("/")
                || path.getRawQuery() != null || path.getRawFragment() != null) {
            throw invalid(key, "expected a path starting with / without a host, query, or fragment");
        }
        return base.resolve(path).toString();
    }

    private static URI parseUri(String key, String value) {
        try {
            return new URI(value);
        } catch (URISyntaxException e) {
            // Do not attach the exception: its message can contain the supplied value.
            throw invalid(key, "invalid URL or path syntax");
        }
    }

    private static IllegalArgumentException invalid(String key, String reason) {
        return new IllegalArgumentException("Invalid configuration '" + key + "': " + reason);
    }

    public String loginUrl() {
        return loginUrl;
    }

    public String dashboardUrl() {
        return dashboardUrl;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }
}
