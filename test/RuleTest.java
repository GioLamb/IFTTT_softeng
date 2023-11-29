import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void equals_shouldReturnTrueForEqualObjects() {
        // Creazione di due oggetti Rule con gli stessi attributi
        Rule rule1 = new Rule("Rule1", "Promemoria", "TriggerTime", "Content1", LocalTime.of(12, 0));
        Rule rule2 = new Rule("Rule1", "Promemoria", "TriggerTime", "Content1", LocalTime.of(12, 0));

        // Verifica che equals restituisca true
        assertTrue(rule1.equals(rule2));
    }

    @Test
    void equals_shouldReturnFalseForDifferentObjects() {
        // Creazione di due oggetti Rule con attributi diversi
        Rule rule1 = new Rule("Rule1", "Promemoria", "TriggerTime", "Content1", LocalTime.of(12, 0));
        Rule rule2 = new Rule("Rule2", "Promemoria", "TriggerTime", "Content2", LocalTime.of(14, 30));

        // Verifica che equals restituisca false
        assertFalse(rule1.equals(rule2));
    }

    @Test
    void equals_shouldReturnFalseForNullObject() {
        // Creazione di un oggetto Rule
        Rule rule = new Rule("Rule1", "Promemoria", "TriggerTime", "Content1", LocalTime.of(12, 0));

        // Verifica che equals restituisca false quando confrontato con null
        assertFalse(rule.equals(null));
    }

    @Test
    void equals_shouldReturnFalseForDifferentClass() {
        // Creazione di un oggetto Rule
        Rule rule = new Rule("Rule1", "Promemoria", "TriggerTime", "Content1", LocalTime.of(12, 0));

        // Verifica che equals restituisca false quando confrontato con un oggetto di una classe diversa
        assertFalse(rule.equals("non è un oggetto Rule"));
    }
}
