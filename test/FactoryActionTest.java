import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Classe di test per la FactoryAction
public class FactoryActionTest {

    // Metodo eseguito una sola volta prima di tutti i test per inizializzare JavaFX
    @BeforeAll
    public static void initJFX(){
        new JFXPanel();
    }

    // Test per verificare la corretta creazione di azioni concrete tramite la FactoryAction
    @Test
    public void testCreateConcreteAction() {

        // Test per la creazione di un'azione di visualizzazione messaggio
        Platform.runLater(() -> {
            // Creazione di un'azione di visualizzazione messaggio
            Action displayMessage = new FactoryAction().createConcreteAction("Promemoria", "Test Message");
            // Verifica che l'azione non sia nulla
            assertNotNull(displayMessage);
            // Verifica che l'azione sia un'istanza di DisplayMessage
            assertTrue(displayMessage instanceof DisplayMessage);
            // Verifica che il nome dell'azione sia corretto
            assertEquals("Promemoria", displayMessage.getName());
            // Verifica che il contenuto dell'azione sia corretto
            assertEquals("Test Message", displayMessage.getContent());
        });

        // Test per la creazione di un'azione di sveglia
        Platform.runLater(() -> {
            // Creazione di un'azione di sveglia
            Action alarmClock = new FactoryAction().createConcreteAction("Sveglia", "audioFilePath");
            // Verifica che l'azione non sia nulla
            assertNotNull(alarmClock);
            // Verifica che l'azione sia un'istanza di AlarmClock
            assertTrue(alarmClock instanceof AlarmClock);
            // Verifica che il nome dell'azione sia corretto
            assertEquals("Sveglia", alarmClock.getName());
            // Verifica che il contenuto dell'azione sia corretto
            assertEquals("audioFilePath", alarmClock.getContent());
        });

        // Test per verificare il lancio di un'eccezione nel caso di un'azione non valida
        assertThrows(IllegalArgumentException.class, () ->
                // Tentativo di creare un'azione con un tipo non valido
                new FactoryAction().createConcreteAction("InvalidAction", "content")
        );
    }
}
