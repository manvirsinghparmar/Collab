package utilities;

import basePackage.base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.testng.ITestResult;

public final class FrameworkLogger {

    private static final Logger LOGGER =
            LogManager.getLogger(BaseTest.class);

    private FrameworkLogger() {
    }

    public static void setLoggingContextFromTestResult(
            ITestResult result) {

        ThreadContext.put(
                "suite", result.getTestContext().getSuite().getName());

        ThreadContext.put(
                "testClass",
                result.getTestClass().getRealClass().getSimpleName());

        ThreadContext.put(
                "testMethod", result.getMethod().getMethodName());

        ThreadContext.put(
                "threadId",
                String.valueOf(Thread.currentThread().getId()));
    }

    public static void clearTestContext() {
        ThreadContext.clearAll();
    }

    public static void testStart(String message) {
        LOGGER.info(message);
    }

    public static void testPass(String message) {
        LOGGER.info(message);
    }

    public static void testSkip(String message) {
        LOGGER.warn(message);
    }

    public static void testFail(String message, Throwable error) {
        if (error == null) {
            LOGGER.error(message);
        } else {
            LOGGER.error(message, error);
        }
    }

    public static void screenshotSaved(String message) {
        LOGGER.info(message);
    }

    public static void stepInfo(String message) {
        LOGGER.info(message);
    }

    public static void debugInfo(String message) {
        LOGGER.debug(message);
    }

    public static void warn(String message) {
        LOGGER.warn(message);
    }
}