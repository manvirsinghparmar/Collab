package utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ArtifactManager {

    private static final String ARTIFACTS_DIR_PROPERTY = "framework.artifacts.dir";
    private static final String DEFAULT_ARTIFACTS_DIR = "target/artifacts";
    private static final DateTimeFormatter FILE_TIME =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    private ArtifactManager() {
    }

    public static Path screenshotsFailedDirectory() {
        return createDirectoryIfNeeded(getArtifactsDirectory().resolve("screenshots").resolve("failed"));
    }

    public static Path screenshotsPassedDirectory() {
        return createDirectoryIfNeeded(getArtifactsDirectory().resolve("screenshots").resolve("passed"));
    }

    public static Path logsDirectory() {
        return createDirectoryIfNeeded(getArtifactsDirectory().resolve("logs"));
    }

    public static Path createReportDirectory() {
        Path reports = createDirectoryIfNeeded(getArtifactsDirectory().resolve("reports"));
        try {
            return Files.createTempDirectory(reports, LocalDateTime.now().format(FILE_TIME) + "_");
        } catch (IOException e) {
            throw new IllegalStateException("Unable to create report directory: " + reports, e);
        }
    }

    public static Path failedScreenshotPath(String testClass, String testMethod) {
        return buildScreenshotPath(screenshotsFailedDirectory(), testClass, testMethod);
    }

    public static Path successScreenshotPath(String testClass, String testMethod) {
        return buildScreenshotPath(screenshotsPassedDirectory(), testClass, testMethod);
    }

    private static Path buildScreenshotPath(Path directory, String testClass, String testMethod) {
        String timestamp = LocalDateTime.now().format(FILE_TIME);
        String fileName = sanitize(testClass) + "_" + sanitize(testMethod) + "_" + timestamp
                + "_t" + Thread.currentThread().getId() + ".png";
        return directory.resolve(fileName);
    }

    private static Path getArtifactsDirectory() {
        String configured = System.getProperty(ARTIFACTS_DIR_PROPERTY, DEFAULT_ARTIFACTS_DIR).trim();
        if (configured.isEmpty()) {
            configured = DEFAULT_ARTIFACTS_DIR;
        }
        return createDirectoryIfNeeded(Paths.get(configured));
    }

    private static Path createDirectoryIfNeeded(Path path) {
        try {
            Files.createDirectories(path);
            return path;
        } catch (IOException e) {
            throw new IllegalStateException("Unable to create artifact directory: " + path, e);
        }
    }

    private static String sanitize(String value) {
        return value.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
