import static org.junit.jupiter.api.Assertions.*;
import javafx.embed.swing.JFXPanel;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;

public class RuleTest {
    @BeforeAll
    public static void initJFX() {
        new JFXPanel(); // Inizializza JavaFX
    }
    @Test
    void testRuleCreation() {
        Platform.runLater(() -> {
            Rule rule = new Rule("Rule1", "Promemoria", "TriggerTime", "Content", LocalTime.of(12, 0));
            assertNotNull(rule);
            assertEquals("Rule1", rule.getNameRule());
            assertEquals("Promemoria", rule.getNameAction());
            assertEquals("TriggerTime", rule.getNameTrigger());
            assertEquals("Content", rule.getActionContent());
            assertEquals("12:00", rule.getTriggerContent()); // Assuming time is formatted as "HH:mm"
        });
    }

    @Test
    void testGetAction() {
        Platform.runLater(() -> {
            Rule rule = new Rule("Rule1", "Promemoria", "TriggerTime", "Content", LocalTime.of(12, 0));
            assertNotNull(rule.getAction());
        });
    }

    @Test
    void testGetTrigger() {
        Platform.runLater(() -> {
            Rule rule = new Rule("Rule1", "Promemoria", "TriggerTime", "Content", LocalTime.of(12, 0));
            assertNotNull(rule.getTrigger());
        });
        // Add more specific assertions about the Trigger if needed
    }

    @Test
    void testToString() {
        Platform.runLater(() -> {
            Rule rule = new Rule("Rule1", "Promemoria", "TriggerTime", "Content", LocalTime.of(12, 0));
            assertEquals("Rule1,Action1," + rule.getAction() + ",Trigger1," + rule.getTrigger(), rule.toString());

        });
    }

    @Test
    void state(){
        Platform.runLater(()->{
            Rule rule = new Rule("Rule1", "Promemoria", "TriggerTime", "Content", LocalTime.of(12, 0));
            assertEquals(true, rule.getState());
            rule.setState(false);
            assertEquals(false, rule.getState());
        });
    }
}
