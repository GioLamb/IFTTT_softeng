import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class RuleManagerTest {


    private RuleManager ruleManager;
    private ObservableList<Rule> rules;
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
            rm.addRule("Rule1", "Promemoria", "TriggerTime", "Content","Content2", null,LocalTime.of(12, 0),true,0,0,0,false, true, false, LocalDateTime.of(2023, 12,2,17,30,00,00));
            assertNotNull(rm.getRules().get(0));
        });
    }

    @Test
    void getRules() {
        Platform.runLater(() -> {
            RuleManager rm = RuleManager.getInstance();
            rm.addRule("Rule1", "Promemoria", "TriggerTime", "Content", "Content2", null,LocalTime.of(12, 0),true,0,0,0,false, true, false, LocalDateTime.of(2023, 12,2,17,30,00,00));
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

    @BeforeEach
    void setUp() {
        ruleManager = RuleManager.getInstance();
        rules = ruleManager.getRules();
        rules.clear(); // Assicuriamoci che la lista delle regole sia vuota prima di ogni test
    }

    @Test
    void removeRule_shouldRemoveRuleFromList() {
        Platform.runLater(() -> {
            // Creiamo una regola da aggiungere e rimuovere
            Rule rule = new Rule("Regola 1", "Promemoria", "TriggerTime", "TestContent", "Content2", null,LocalTime.of(12, 0), true, 0, 0, 0, false, true, false, LocalDateTime.of(2023, 12,2,17,30,00,00));
            ruleManager.addRule(rule.getNameRule().get(), rule.getNameAction().get(), rule.getNameTrigger().get(), rule.getActionContent().get(), rule.getActionContent2(), rule.getContent3(), LocalTime.parse(rule.getTriggerContent().get()), rule.getOneTime(), rule.getSleepDays(), rule.getSleepHours(), rule.getSleepMinutes(), rule.getRecurrent(), true, rule.getRepeat(), rule.getNowPlusSleep());

            // Verifichiamo che la regola sia stata aggiunta correttamente
            assertTrue(rules.contains(rule));

            // Rimuoviamo la regola
            ruleManager.removeRule(rule);

            // Verifichiamo che la regola sia stata rimossa correttamente
            assertFalse(rules.contains(rule));
        });
    }

    @Test
    void removeRule_shouldDoNothingForNonexistentRule() {
        Platform.runLater(() -> {
            // Creiamo una regola, ma non la aggiungiamo alla lista
            Rule rule = new Rule("Regola 1", "Promemoria", "TriggerTime", "TestContent", "TestContent2", null,LocalTime.of(12, 0), true, 0, 0, 0, false, true, false, LocalDateTime.of(2023, 12,2,17,30,00,00));

            // Verifichiamo che la regola non sia presente nella lista
            assertFalse(rules.contains(rule));

            // Tentiamo di rimuovere la regola
            ruleManager.removeRule(rule);

            // Verifichiamo che la lista non sia stata modificata (la regola non esisteva)
            assertFalse(rules.contains(rule));
        });
    }
}