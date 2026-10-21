package utilities;

public class Utils {

    public static void sleep(int secs){
        try {
            Thread.sleep(secs * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted during thread sleep", e);
        }
    }
}
