import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;


public class MainClassTest {
    @Disabled("Test is temporarily disabled")
    @Test
    @Timeout(value = 22)
    public void mainClassTimeoutTest() {
        String[] args = {};
        try {
            Main.main(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
