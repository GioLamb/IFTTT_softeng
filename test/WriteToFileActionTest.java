import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class WriteToFileActionTest {

    // Percorso del file di test e contenuto di test
    private static final String TEST_FILE_PATH = "/Users/vivi/Documents/Text.txt";
    private static final String TEST_CONTENT = "Ciao";
    private WriteToFileAction writeToFileAction;

    // Inizializza JavaFX prima di eseguire i test
    @BeforeAll
    public static void initJFX() {
        System.setProperty("javafx.headless", "true");
        new JFXPanel();
    }

    // Esegui prima di ogni singolo test
    @BeforeEach
    void setUp() {
        // Crea un'istanza di WriteToFileAction con il percorso e il contenuto di test
        writeToFileAction = new WriteToFileAction(TEST_FILE_PATH, TEST_CONTENT);
    }

    // Testa l'esecuzione dell'azione di scrittura nel file
    @Test
    void testExecute() {
        // Esegui l'azione
        writeToFileAction.execute();

        // Verifica che il file sia stato creato con successo
        File file = new File(TEST_FILE_PATH);
        assertTrue(file.exists());

        // Leggi il contenuto del file e verifica che corrisponda al contenuto di test
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String fileContent = reader.readLine();
            assertEquals(TEST_CONTENT, fileContent);
        } catch (IOException e) {
            fail("Exception should not be thrown");
        }
    }

    // Testa il metodo getContent1() che restituisce il percorso del file
    @Test
    void testGetContent1() {
        // Crea un'istanza di WriteToFileAction e verifica che il percorso sia corretto
        WriteToFileAction writeToFileAction = new WriteToFileAction(TEST_FILE_PATH, TEST_CONTENT);
        assertEquals(TEST_FILE_PATH, writeToFileAction.getContent1());
    }

    // Testa il metodo getContent2() che restituisce il contenuto da scrivere nel file
    @Test
    void testGetContent2() {
        // Crea un'istanza di WriteToFileAction e verifica che il contenuto sia corretto
        WriteToFileAction writeToFileAction = new WriteToFileAction(TEST_FILE_PATH, TEST_CONTENT);
        assertEquals(TEST_CONTENT, writeToFileAction.getContent2());
    }

    // Testa il metodo onActionClose() senza generare eccezioni
    @Test
    void testOnActionClose() {
        // Verifica che il metodo non generi eccezioni
        assertDoesNotThrow(() -> writeToFileAction.onActionClose());
    }
}
