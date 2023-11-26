import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

class DisplayMessageTest {

    @BeforeAll
    public static void initJFX() {
        System.setProperty("javafx.headless", "true");
        new JFXPanel(); // Inizializza JavaFX
    }

    @Test
    void execute_shouldShowAlertAndCloseOnActionClose() {
        Platform.runLater(()->{
            String message = "Test Message";
            DisplayMessage displayMessage = new DisplayMessage(message);

            // Mock di Alert per verificare se il metodo show() viene chiamato
            Alert mockAlert = new Alert(Alert.AlertType.INFORMATION);
            mockAlert.setHeaderText(null);
            mockAlert.setContentText(message);
            mockAlert.initOwner(null);

            displayMessage.alert = mockAlert;

            // Chiamare il metodo execute
            displayMessage.execute();

            // Verificare se il metodo show() è stato chiamato
            assertTrue(mockAlert.isShowing());

            // Simulare la chiusura dell'alert
            mockAlert.setResult(ButtonType.OK);

            // Verificare che l'alert sia stato chiuso
            assertFalse(mockAlert.isShowing());
        });
    }

    @Test
    void onActionClose_shouldCloseAlertOnOK() {
        Platform.runLater(()->{
            String message = "Test Message";
            DisplayMessage displayMessage = new DisplayMessage(message);

            // Mock di Alert per verificare se il metodo close() viene chiamato
            Alert mockAlert = new Alert(Alert.AlertType.INFORMATION);
            mockAlert.setHeaderText(null);
            mockAlert.setContentText(message);
            mockAlert.initOwner(null);

            displayMessage.alert = mockAlert;

            // Chiamare il metodo onActionClose
            displayMessage.onActionClose();

            // Simulare la chiusura dell'alert cliccando su OK
            mockAlert.setResult(ButtonType.OK);

            // Verificare che il metodo close() sia stato chiamato
            assertFalse(mockAlert.isShowing());
        });
    }

    // Aggiungi altri test a seconda delle esigenze
}
