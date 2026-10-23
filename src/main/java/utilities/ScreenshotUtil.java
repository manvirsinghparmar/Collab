package utilities;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ScreenshotUtil {

    private ScreenshotUtil() {
    }

    public static Path captureFailureScreenshot(WebDriver driver, ITestResult result) {
        String className = result.getTestClass().getRealClass().getSimpleName();
        String methodName = result.getMethod().getMethodName();
        Path target = ArtifactManager.failedScreenshotPath(className, methodName);
        capture(driver, target);
        return target;
    }


    public static void capture(WebDriver driver, Path targetFile) {
        if (!(driver instanceof TakesScreenshot)) {
            throw new IllegalStateException("Current WebDriver does not support screenshots.");
        }
        byte[] image = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        try {
            Files.write(targetFile, image);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to write screenshot: " + targetFile, e);
        }
    }
}
