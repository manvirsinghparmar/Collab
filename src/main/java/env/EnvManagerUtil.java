package env;

import java.util.Locale;

public final class EnvManagerUtil {

    private EnvManagerUtil() {
    }

    public static Environment resolveEnvironment(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Invalid configuration 'app.env': a nonblank value is required");
        }
        try {
            return Environment.valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid configuration 'app.env': expected DEV, STAGE, or PROD");
        }
    }
}
