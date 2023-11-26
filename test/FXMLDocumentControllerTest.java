import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
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

    @Test
    void testNewRuleWhenRulesEmpty() {
        Platform.runLater(()->{
            // Mock RuleManager behavior
            RuleManager rm = RuleManager.getInstance();
            rm.getRules().clear();

            controller.newRule(null);
            // Add assertions based on the expected behavior when rules are empty
        });
    }

    @Test
    void testNewRuleWhenRulesNotEmpty() {
        Platform.runLater(()->{
            // Mock RuleManager behavior
            RuleManager rm = RuleManager.getInstance();
            rm.addRule("Test Rule", "Test Action", "Test Trigger", "Test Content", null);

            controller.newRule(null);
            // Add assertions based on the expected behavior when rules are not empty
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
}