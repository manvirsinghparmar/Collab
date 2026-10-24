package utilities;

public final class StringUtils {

    private StringUtils() {
    }

    public static String normalizeWhitespace(String value) {
        return value.trim().replaceAll("\\s+", " ");
    }
}
