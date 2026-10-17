
package org.orangehrm.assertions;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.testng.asserts.SoftAssert;

import static org.hamcrest.Matchers.containsString;

public final class AssertionsUtil {

    public static final SoftAssert softAssert = new SoftAssert();

    // Prevent unnecessary object creation patterns.
    // Create one instance per test instead.

    public void assertEquals(
            Object actual, Object expected, String message) {
        softAssert.assertEquals(actual, expected, message);
    }

    public void assertTrue(boolean condition, String message) {
        softAssert.assertTrue(condition, message);
    }

    public void assertFalse(boolean condition, String message) {
        softAssert.assertFalse(condition, message);
    }

    public void assertNotNull(Object actual, String message) {
        softAssert.assertNotNull(actual, message);
    }

    public void assertNull(Object actual, String message) {
        softAssert.assertNull(actual, message);
    }

    public void assertContains(
            String actual, String expectedSubstring, String message) {
        assertThat(actual, containsString(expectedSubstring), message);
    }

    public <T> void assertThat(
            T actual, Matcher<? super T> matcher, String message) {
        try {
            MatcherAssert.assertThat(message, actual, matcher);
        } catch (AssertionError failure) {
            softAssert.fail(failure.getMessage(), failure);
        }
    }

    public static void assertAll() {
        softAssert.assertAll();
    }
}
