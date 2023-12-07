import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteFileActionTest {

    @BeforeAll
    public static void initJFX() {
        System.setProperty("javafx.headless", "true");
        new JFXPanel(); // Inizializza JavaFX
    }

    @Test
    public void testDeleteFileActionGetName() {
        Platform.runLater(()-> {
            // INSERIRE UN PERCORSO VALIDO PRIMA DI ESGUIRE IL TEST
            String fileToDelete = "C:\\Users\\Utente\\Desktop\\prova.txt";

            DeleteFileAction action = new DeleteFileAction(fileToDelete);

            // Testa il nome dell'azione
            assertEquals("DeleteFileAction", action.getName());
        });
    }

    @Test
    public void testDeleteFileActionGetContent() {
        Platform.runLater(()-> {
            // INSERIRE UN PERCORSO VALIDO PRIMA DI ESGUIRE IL TEST
            String fileToDelete = "C:\\Users\\Utente\\Desktop\\prova.txt";

            DeleteFileAction action = new DeleteFileAction(fileToDelete);

            // Testa il contenuto dell'azione
            String content = action.getContent1();
            assertNotNull(content);
            assertEquals(fileToDelete, content);
        });
    }

    @Test
    public void testDeleteFileActionFileExists() {
        Platform.runLater(()-> {
            // INSERIRE UN PERCORSO VALIDO PRIMA DI ESGUIRE IL TEST
            String fileToDelete = "C:\\Users\\Utente\\Desktop\\prova.txt";

            // Crea un file per il test
            createFileForTest(fileToDelete);

            DeleteFileAction action = new DeleteFileAction(fileToDelete);

            // Testa l'esecuzione dell'azione (eliminazione del file)
            action.execute();

            // Verifica se il file è stato eliminato correttamente
            File deletedFile = new File(fileToDelete);
            assertFalse(deletedFile.exists(), "Il file non è stato eliminato correttamente");
        });
    }

    @Test
    public void testDeleteFileActionFileNotExist() {
        Platform.runLater(()-> {
            // INSERIRE UN PERCORSO NON VALIDO PRIMA DI ESGUIRE IL TEST
            String nonExistentFile = "C:\\percorso\\del\\tuo\\file_non_esistente.txt";

            DeleteFileAction action = new DeleteFileAction(nonExistentFile);

            // Testa l'esecuzione dell'azione (eliminazione del file inesistente)
            action.execute();

            // Verifica se l'alert di errore viene visualizzato quando il file non esiste
            assertTrue(action.alertError.isShowing(), "L'alert di errore non è stato visualizzato per il file inesistente");
        });
    }

    @Test
    public void testDeleteFileActionDirectoryInsteadOfFile() {
        Platform.runLater(()-> {
            // INSERIRE UN PERCORSO VALIDO PRIMA DI ESGUIRE IL TEST
            String directoryPath = "C:\\Users\\Utente\\Desktop\\Destinazione_copia_del_file";

            DeleteFileAction action = new DeleteFileAction(directoryPath);

            // Testa l'esecuzione dell'azione (eliminazione di una directory invece di un file)
            action.execute();

            // Verifica se l'alert di errore viene visualizzato quando il percorso è una directory
            assertTrue(action.alertError.isShowing(), "L'alert di errore non è stato visualizzato per una directory invece di un file");
        });
    }

    private void createFileForTest(String filePath) {
        Path file = Paths.get(filePath);
        try {
            Files.createFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}