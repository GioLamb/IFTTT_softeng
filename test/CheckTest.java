import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import javafx.embed.swing.JFXPanel;
import static org.junit.jupiter.api.Assertions.*;

class CheckTest {


    @BeforeAll
    public static void initJFX() {
        System.setProperty("javafx.headless", "true");
        new JFXPanel(); // Inizializza JavaFX
    }

    @Test
    void run() {
        Platform.runLater(() -> {
            RuleManager rm = RuleManager.getInstance();
            Check check=rm.getCheck();
            assertNotNull(check.isAlive());
        });
    }

    @Test
    void stopThread() {
        Platform.runLater(() -> {
            RuleManager rm = RuleManager.getInstance();
            rm.getCheck().stopThread();
            assertEquals(rm.getCheck().getStatus(),false);
        });
    }
}