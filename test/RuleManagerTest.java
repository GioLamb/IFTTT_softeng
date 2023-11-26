import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class RuleManagerTest {
    @BeforeAll
    public static void initJFX() {
        new JFXPanel(); // Inizializza JavaFX
    }
    @Test
    void getInstance() {
        Platform.runLater(() -> {
            RuleManager rm = RuleManager.getInstance();
            assertNotNull(rm);
            assertEquals(rm, RuleManager.getInstance());
        });
    }

    @Test
    void addRule() {
        Platform.runLater(() -> {
            RuleManager rm = RuleManager.getInstance();
            rm.addRule("Rule1", "Promemoria", "TriggerTime", "Content", LocalTime.of(12, 0));
            assertNotNull(rm.getRules().get(0));
        });
    }

    @Test
    void getRules() {
        Platform.runLater(() -> {
            RuleManager rm = RuleManager.getInstance();
            rm.addRule("Rule1", "Promemoria", "TriggerTime", "Content", LocalTime.of(12, 0));
            assertNotNull(rm.getRules().get(0));
        });
    }

    @Test
    void check() {
        Platform.runLater(() -> {
            RuleManager rm = RuleManager.getInstance();
            assertNotNull(rm.getCheck());
        });
    }

    @Test
    void getCheck() {
        Platform.runLater(() -> {
            RuleManager rm = RuleManager.getInstance();
            assertNotNull(rm.getCheck());
        });
    }
}