package platform;

import org.openqa.selenium.Platform;

/**
 * Detects the local operating system, not the platform of a remote browser session.
 */
public final class PlatformUtil {

    private PlatformUtil() {
    }

    /**
     * @throws UnsupportedOperationException if the local operating system is not Windows, macOS, or Linux
     */
    public static Platforms getCurrentPlatform() {
        Platform platform = Platform.getCurrent();
        if (platform.is(Platform.MAC)) {
            return Platforms.MAC;
        }
        if (platform.is(Platform.WINDOWS)) {
            return Platforms.WINDOWS;
        }
        if (platform.is(Platform.LINUX)) {
            return Platforms.LINUX;
        }
        throw new UnsupportedOperationException(
                "Unsupported local operating system: " + System.getProperty("os.name")
                        + ". Supported platforms are WINDOWS, MAC, and LINUX.");
    }

    public static boolean isMac() {
        return getCurrentPlatform() == Platforms.MAC;
    }

    public static boolean isWindows() {
        return getCurrentPlatform() == Platforms.WINDOWS;
    }

    public static boolean isLinux() {
        return getCurrentPlatform() == Platforms.LINUX;
    }
}
