import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;


class MainTest {
    @Disabled("Test is temporarily disabled")
    @Test
    @Timeout(value = 22)
    void mainTimeoutTest() {
        String[] args = {};
        try {
            Main.main(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
