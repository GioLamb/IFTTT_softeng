import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class FXMLDocumentControllerTest {

    private FXMLDocumentController controller;

    @BeforeAll
    public static void initJFX(){
        new JFXPanel();
    }

    @BeforeEach
    void setUp() {
        controller = new FXMLDocumentController();
    }

    @Test
    void testInitialize() {
        Platform.runLater(() -> {
            controller.initialize();
            assertNotNull(controller.getTableView());
            assertNotNull(controller.getTableView().getColumns());
            assertFalse(controller.getTableView().getColumns().isEmpty());
        });
    }

    @Test
    void testSwitchRuleMenu() {
        Platform.runLater(() -> {
            try {
                controller.switchRuleMenu(null);
                assertNotNull(controller.getScene());
                assertEquals("ruleselector.fxml", controller.getScene().getRoot().getId());
            } catch (IOException e) {
                fail("Exception not expected", e);
            }
        });
    }

    @Test
    void testSwitchMainPage() {
        Platform.runLater(() -> {
            try {
                controller.switchMainPage(null);
                assertNotNull(controller.getScene());
                assertEquals("mainpage.fxml", controller.getScene().getRoot().getId());
            } catch (IOException e) {
                fail("Exception not expected", e);
            }
        });
    }

    @Test
    void testSelectAudio() {
        Platform.runLater(() -> {
            controller.selectAudio();
            // Simulate file selection
            controller.getContent(); // Assuming getContent() returns the selected file path
            // Add assertions based on your application logic
        });
    }

    @Test
    void testSubmitWithValidData() {
        Platform.runLater(() -> {
            controller.getActionSelector().getItems().add("Sveglia");
            controller.getActionSelector().setValue("Sveglia");
            controller.getHourSelector().setText("10");
            controller.getMinutesSelector().setText("30");

            assertDoesNotThrow(() -> controller.submit(null));

            // Add assertions based on your application logic
        });
    }

    @Test
    void testStart() {
        Platform.runLater(() -> {
            assertDoesNotThrow(() -> controller.start(new Stage()));
            // Add assertions based on the expected behavior of the start() method
        });
    }

    @Test
    void testStop() {
        Platform.runLater(()->{
            assertDoesNotThrow(() -> controller.stop());
            // Add assertions based on the expected behavior of the stop() method
        });
    }

    // Caso di test per aggiungere una nuova regola quando l'elenco delle regole è inizialmente vuoto
    @Test
    void testNewRuleWhenRulesEmpty() {
        Platform.runLater(() -> {
            // Simulazione del comportamento di RuleManager
            RuleManager rm = RuleManager.getInstance();
            rm.getRules().clear(); // Elimina le regole esistenti

            // Aggiunta di una nuova regola e verifica del comportamento atteso
            assertDoesNotThrow(() -> controller.newRule(null)); // Tentativo di aggiungere una nuova regola
            assertNotNull(controller.getScene()); // Verifica che la scena non sia nulla
            assertEquals("ruleselector.fxml", controller.getScene().getRoot().getId()); // Verifica che il file FXML previsto sia caricato
        });
    }

    // Caso di test per aggiungere una nuova regola quando l'elenco delle regole non è vuoto, con tipo di azione "Promemoria"
    @Test
    void testNewRuleWhenRulesNotEmptyPromemoria() {
        Platform.runLater(() -> {
            // Simulazione del comportamento di RuleManager con una regola "Promemoria" esistente
            RuleManager rm = RuleManager.getInstance();
            rm.addRule("Test Rule", "Promemoria", "Test Trigger", "Test Content", LocalTime.now());

            // Aggiunta di una nuova regola e verifica del comportamento atteso
            assertDoesNotThrow(() -> controller.newRule(null)); // Tentativo di aggiungere una nuova regola
            assertNotNull(controller.getScene()); // Verifica che la scena non sia nulla
            assertEquals("ruleselector.fxml", controller.getScene().getRoot().getId()); // Verifica che il file FXML previsto sia caricato
        });
    }

    // Caso di test per aggiungere una nuova regola quando l'elenco delle regole non è vuoto, con tipo di azione "Sveglia"
    @Test
    void testNewRuleWhenRulesNotEmptySveglia() {
        Platform.runLater(() -> {
            // Simulazione del comportamento di RuleManager con una regola "Sveglia" esistente
            RuleManager rm = RuleManager.getInstance();
            rm.addRule("Test Rule", "Sveglia", "Test Trigger", "/Users/vivi/Downloads/Prova.wav", LocalTime.now());

            // Aggiunta di una nuova regola e verifica del comportamento atteso
            assertDoesNotThrow(() -> controller.newRule(null)); // Tentativo di aggiungere una nuova regola
            assertNotNull(controller.getScene()); // Verifica che la scena non sia nulla
            assertEquals("ruleselector.fxml", controller.getScene().getRoot().getId()); // Verifica che il file FXML previsto sia caricato
        });
    }

    @Test
    void testCancel() {
        Platform.runLater(()->{
            try {
                controller.cancel(null);
                // Add assertions based on the expected behavior of the cancel() method
            } catch (Exception e) {
                fail("Exception not expected", e);
            }
        });
    }

    @Test
    void testCheckHours() {
        Platform.runLater(() -> {
            // Utilizziamo il costruttore appropriato di KeyEvent
            KeyEvent event = new KeyEvent(
                    KeyEvent.KEY_TYPED,
                    "1",
                    "1",
                    KeyCode.UNDEFINED,  // Utilizziamo KeyCode.UNDEFINED per indicare che non è un tasto fisico
                    false,
                    false,
                    false,
                    false
            );
            controller.checkHours(event);
            // Aggiungere le asserzioni in base al comportamento atteso del metodo checkHours()
        });
    }

    @Test
    void testCheckMinutes() {
        Platform.runLater(()-> {
            // Utilizziamo il costruttore appropriato di KeyEvent
            KeyEvent event = new KeyEvent(
                    KeyEvent.KEY_TYPED,
                    "2",
                    "2",
                    KeyCode.UNDEFINED,  // Utilizziamo KeyCode.UNDEFINED per indicare che non è un tasto fisico
                    false,
                    false,
                    false,
                    false
            );
            controller.checkMinutes(event);
            // Aggiungere le asserzioni in base al comportamento atteso del metodo checkMinutes()
        });
    }

    @Test
    void testChangeContentActionForSveglia() {
        Platform.runLater(()->{
            // Mock ActionSelector value
            controller.getActionSelector().setValue("Sveglia");

            controller.changeContentAction();
            // Add assertions based on the expected behavior for the "Sveglia" case
        });
    }

    @Test
    void testChangeContentActionForPromemoria() {
        Platform.runLater(()->{
            // Mock ActionSelector value
            controller.getActionSelector().setValue("Promemoria");

            controller.changeContentAction();
            // Add assertions based on the expected behavior for the "Promemoria" case
        });
    }

    @Test
    void testDeleteRule() {
        Platform.runLater(()->{
            //Si crea una nuova regola da eliminare che aggiungiamo alla TableView
            FXMLDocumentController controller = new FXMLDocumentController();
            Rule ruleToDelete = new Rule("Regola 1", "Promemoria", "TriggerTime", "TestContent", LocalTime.now());
            controller.getTableView().getItems().add(ruleToDelete);

            //Chiama il metodo deleteRule per eliminare la regola
            controller.deleteRule(new ActionEvent());

            //Ci assicuriamo che la tableView non contenga più la regola,
            //implementando un Platform per far si che si legga la tabella correttamente
            Platform.runLater(() ->{
                assertFalse(controller.getTableView().getItems().contains(ruleToDelete), "La regola dovrebbe essere eliminata dalla TableView");
                assertNull(controller.getSelectedRuleToDelete(), "La regola selezionata dovrebbe essere reimpostata a null");
            });
        });
    }

    @Test
    void testDeleteRuleFromEmptyTableView() {
        //Ci assicuriamo che la TableView sia inizialmente vuota
        FXMLDocumentController controller = new FXMLDocumentController();

        //Chiamiamo deleteRule su una TableView vuota, cosa che non dovrebbe generare errori
        controller.deleteRule(new ActionEvent());

        //Ci assicuriamo che la TableView sia rimasta vuota dopo l'eliminazione,
        //oltre che assicurarci che la variabile relativa alla regola selezionata sia rimasta vuota
        assertTrue(controller.getTableView().getItems().isEmpty(), "La TableView dovrebbe rimanere vuota");
        assertNull(controller.getSelectedRuleToDelete(), "La regola selezionata dovrebbe essere null");
    }

    @Test
    void testDelete() {
        Platform.runLater(()->{
            //Crea una nuova regola da eliminare, e aggiungiamo la stessa alla
            //TableView del controller
            FXMLDocumentController controller = new FXMLDocumentController();
            Rule ruleToDelete = new Rule("Regola 1", "Promemoria", "TriggerTime", "TestContent", LocalTime.now());
            controller.getTableView().getItems().add(ruleToDelete);

            //Chiamiamo delete per eliminare la regola della TableView
            controller.delete(ruleToDelete);

            //Ci assicuriamo che la TableView sia vuota dopo l'eliminazione, e che la
            //variabile relativa alla regola selezionata sia vuota, dopo l'eliminazione
            //utilizziamo un Platoform, per attendere la corretta lettura della TableView
            //dopo l'eliminazione
            Platform.runLater(() -> {
                assertFalse(controller.getTableView().getItems().contains(ruleToDelete), "La regola dovrebbe essere eliminata dalla TableView");
                assertNull(controller.getSelectedRuleToDelete(), "La regola selezionata dovrebbe essere reimpostata a null");
            });
        });
    }

    @Test
    void testDeleteFromEmptyTableView() {
        Platform.runLater(()->{
            //Creiamo una nuova regola da eliminare
            FXMLDocumentController controller = new FXMLDocumentController();
            Rule ruleToDelete = new Rule("Regola 1", "Promemoria", "TriggerTime", "TestContent", LocalTime.now());

            //Chiamiamo delete per eliminare la regola della TableView (vuota)
            controller.delete(ruleToDelete);

            //Ci assicuriamo che la TableView sia vuota, e che lo sia anche la
            //variabile relativa alla regola selezionata
            assertTrue(controller.getTableView().getItems().isEmpty(), "La TableView dovrebbe rimanere vuota");
            assertNull(controller.getSelectedRuleToDelete(), "La regola selezionata dovrebbe essere null");
        });
    }

}