import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
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
            Rule rule = new Rule("Rule1", "Promemoria", "TriggerTime", "Content", LocalTime.of(12, 0),true,0,0,0,false);
            assertNotNull(rule);
            assertEquals("Rule1", rule.getNameRule());
            assertEquals("Promemoria", rule.getNameAction());
            assertEquals("TriggerTime", rule.getNameTrigger());
            assertEquals("Content", rule.getActionContent());
            assertEquals("12:00", rule.getTriggerContent()); // Assuming time is formatted as "HH:mm"
            assertEquals(true, rule.getOneTime());
            assertEquals(0, rule.getSleepDays());
            assertEquals(0, rule.getSleepHours());
            assertEquals(0 ,rule.getSleepMinutess());
            assertEquals(false, rule.getRecurrent());
        });
    }

    @Test
    void testGetAction() {
        Platform.runLater(() -> {
            Rule rule = new Rule("Rule1", "Promemoria", "TriggerTime", "Content", LocalTime.of(12, 0),true,0,0,0,false);
            assertNotNull(rule.getAction());
        });
    }

    @Test
    void testGetTrigger() {
        Platform.runLater(() -> {
            Rule rule = new Rule("Rule1", "Promemoria", "TriggerTime", "Content", LocalTime.of(12, 0),true,0,0,0,false);
            assertNotNull(rule.getTrigger());
        });
        // Add more specific assertions about the Trigger if needed
    }

    @Test
    void testToString() {
        Platform.runLater(() -> {
            Rule rule = new Rule("Rule1", "Promemoria", "TriggerTime", "Content", LocalTime.of(12, 0),true,0,0,0,false);
            assertEquals("Rule1,Action1," + rule.getAction() + ",Trigger1," + rule.getTrigger(), rule.toString());

        });
    }

    @Test
    void state(){
        Platform.runLater(()->{
            Rule rule = new Rule("Rule1", "Promemoria", "TriggerTime", "Content", LocalTime.of(12, 0),true,0,0,0,false);
            assertEquals(true, rule.getState().get());
            rule.setState(false);
            assertEquals(false, rule.getState().get());
        });
    }

    @Test
    void equals_shouldReturnTrueForEqualObjects() {
        Platform.runLater(() -> {
            // Creazione di due oggetti Rule con gli stessi attributi
            Rule rule1 = new Rule("Rule1", "Promemoria", "TriggerTime", "Content1", LocalTime.of(12, 0), true, 0, 0, 0, false);
            Rule rule2 = new Rule("Rule1", "Promemoria", "TriggerTime", "Content1", LocalTime.of(12, 0), true, 0, 0, 0, false);

            // Verifica che equals restituisca true
            assertTrue(rule1.equals(rule2));
        });
    }

    @Test
    void equals_shouldReturnFalseForDifferentObjects() {
        Platform.runLater(() -> {
            // Creazione di due oggetti Rule con attributi diversi
            Rule rule1 = new Rule("Rule1", "Promemoria", "TriggerTime", "Content1", LocalTime.of(12, 0), true, 0, 0, 0, false);
            Rule rule2 = new Rule("Rule2", "Promemoria", "TriggerTime", "Content2", LocalTime.of(14, 30), false, 0, 1, 0, true);

            // Verifica che equals restituisca false
            assertFalse(rule1.equals(rule2));
        });
    }

    @Test
    void equals_shouldReturnFalseForNullObject() {
        Platform.runLater(() -> {
            // Creazione di un oggetto Rule
            Rule rule = new Rule("Rule1", "Promemoria", "TriggerTime", "Content1", LocalTime.of(12, 0), true, 0, 0, 0, false);

            // Verifica che equals restituisca false quando confrontato con null
            assertFalse(rule.equals(null));
        });
    }

    @Test
    void equals_shouldReturnFalseForDifferentClass() {
        Platform.runLater(() -> {
            // Creazione di un oggetto Rule
            Rule rule = new Rule("Rule1", "Promemoria", "TriggerTime", "Content1", LocalTime.of(12, 0), true, 0, 0, 0, false);

            // Verifica che equals restituisca false quando confrontato con un oggetto di una classe diversa
            assertFalse(rule.equals("non è un oggetto Rule"));
        });
    }

    @Test
    void oneTime(){
        Platform.runLater(()->{
            Rule rule = new Rule("Rule1", "Promemoria", "TriggerTime", "Content", LocalTime.of(12, 0),true,0,0,0,false);
            assertEquals(true, rule.getOneTime());
            rule.setOneTime(false);
            assertEquals(false,rule.getOneTime());
        });
    }

    @Test
    void sleepDays(){
        Platform.runLater(()->{
            Rule rule = new Rule("Rule1", "Promemoria", "TriggerTime", "Content", LocalTime.of(12, 0),true,0,0,0,false);
            assertEquals(0, rule.getSleepDays());
        });
    }

    @Test
    void sleepHours(){
        Platform.runLater(()->{
            Rule rule = new Rule("Rule1", "Promemoria", "TriggerTime", "Content", LocalTime.of(12, 0),true,0,0,0,false);
            assertEquals(0, rule.getSleepHours());
        });
    }

    @Test
    void sleepMinutes(){
        Platform.runLater(()->{
            Rule rule = new Rule("Rule1", "Promemoria", "TriggerTime", "Content", LocalTime.of(12, 0),true,0,0,0,false);
            assertEquals(0, rule.getSleepMinutess());
        });
    }

    @Test
    void recurrent(){
        Platform.runLater(()->{
            Rule rule = new Rule("Rule1", "Promemoria", "TriggerTime", "Content", LocalTime.of(12, 0),true,0,0,0,false);
            assertEquals(false, rule.getRecurrent());
            rule.setRecurrent(true);
            assertEquals(true, rule.getRecurrent());
        });
    }

    @Test
    void repeat(){
        Platform.runLater(()->{
            Rule rule = new Rule("Rule1", "Promemoria", "TriggerTime", "Content", LocalTime.of(12, 0),true,0,0,0,false);
            assertEquals(false, rule.getRepeat());
            rule.setRepeat(true);
            assertEquals(true, rule.getOneTime());
        });
    }

    @Test
    void now(){
        Platform.runLater(()->{
            Rule rule = new Rule("Rule1", "Promemoria", "TriggerTime", "Content", LocalTime.of(12, 0),true,0,0,0,false);
            rule.getAction().execute();
            assertEquals(LocalDateTime.now(), rule.getNow());
            rule.setNow(LocalDateTime.now());
            assertEquals(LocalDateTime.now(), rule.getNow());
        });
    }

    @Test
    void nowPlusSleep(){
        Platform.runLater(()->{
            Rule rule = new Rule("Rule1", "Promemoria", "TriggerTime", "Content", LocalTime.of(12, 0),true,0,0,0,false);
            rule.getAction().execute();
            assertEquals(LocalDateTime.now().plusDays(0).plusHours(0).plusMinutes(0), rule.getNowPlusSleep());
            rule.setNowPlusSleep(LocalDateTime.now().plusDays(1).plusHours(1).plusMinutes(1));
            assertEquals(LocalDateTime.now().plusDays(1).plusHours(1).plusMinutes(1), rule.getNowPlusSleep());
        });
    }
}
