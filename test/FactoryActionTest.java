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
            Action displayMessage = new FactoryAction().createConcreteAction("Promemoria", "Test Message", null);
            // Verifica che l'azione non sia nulla
            assertNotNull(displayMessage);
            // Verifica che l'azione sia un'istanza di DisplayMessage
            assertTrue(displayMessage instanceof DisplayMessage);
            // Verifica che il nome dell'azione sia corretto
            assertEquals("Promemoria", displayMessage.getName());
            // Verifica che il contenuto dell'azione sia corretto
            assertEquals("Test Message", displayMessage.getContent1());
            assertNull(displayMessage.getContent2());
        });

        // Test per la creazione di un'azione di sveglia
        Platform.runLater(() -> {
            // Creazione di un'azione di sveglia
            Action alarmClock = new FactoryAction().createConcreteAction("Sveglia", "audioFilePath", null);
            // Verifica che l'azione non sia nulla
            assertNotNull(alarmClock);
            // Verifica che l'azione sia un'istanza di AlarmClock
            assertTrue(alarmClock instanceof AlarmClock);
            // Verifica che il nome dell'azione sia corretto
            assertEquals("Sveglia", alarmClock.getName());
            // Verifica che il contenuto dell'azione sia corretto
            assertEquals("audioFilePath", alarmClock.getContent1());
            assertNull(alarmClock.getContent2());
        });

        // Test per la creazione di un'azione di copia di un file
        Platform.runLater(() -> {
            // Creazione di un'azione di sveglia
            Action copyFile = new FactoryAction().createConcreteAction("Copia un file", "File", "Directory");
            // Verifica che l'azione non sia nulla
            assertNotNull(copyFile);
            // Verifica che l'azione sia un'istanza di AlarmClock
            assertTrue(copyFile instanceof ActionCopy);
            // Verifica che il nome dell'azione sia corretto
            assertEquals("Copia un file", copyFile.getName());
            // Verifica che il contenuto dell'azione sia corretto
            assertEquals("File", copyFile.getContent1());
            assertEquals("Directory", copyFile.getContent2());
        });

        // Test per la creazione di un'azione di spostamento di un file
        Platform.runLater(() -> {
            // Creazione di un'azione di sveglia
            Action moveFile = new FactoryAction().createConcreteAction("Sposta un file", "File", "Directory");
            // Verifica che l'azione non sia nulla
            assertNotNull(moveFile);
            // Verifica che l'azione sia un'istanza di AlarmClock
            assertTrue(moveFile instanceof ActionCopy);
            // Verifica che il nome dell'azione sia corretto
            assertEquals("Sposta un file", moveFile.getName());
            // Verifica che il contenuto dell'azione sia corretto
            assertEquals("File", moveFile.getContent1());
            assertEquals("Directory", moveFile.getContent2());
        });

        // Test per la creazione di un'azione di eliminazione di un file
        Platform.runLater(() -> {
            // Creazione di un'azione di sveglia
            Action deleteFile = new FactoryAction().createConcreteAction("Elimina un file", "File", null);
            // Verifica che l'azione non sia nulla
            assertNotNull(deleteFile);
            // Verifica che l'azione sia un'istanza di AlarmClock
            assertTrue(deleteFile instanceof ActionCopy);
            // Verifica che il nome dell'azione sia corretto
            assertEquals("Elimina un file", deleteFile.getName());
            // Verifica che il contenuto dell'azione sia corretto
            assertEquals("File", deleteFile.getContent1());
            assertNull(deleteFile.getContent2());
        });


        // Test per verificare il lancio di un'eccezione nel caso di un'azione non valida
        assertThrows(IllegalArgumentException.class, () ->
                // Tentativo di creare un'azione con un tipo non valido
                new FactoryAction().createConcreteAction("InvalidAction", "content","content2")
        );
    }
}
