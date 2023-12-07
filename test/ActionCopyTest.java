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

public class ActionCopyTest {

    @BeforeAll
    public static void initJFX() {
        System.setProperty("javafx.headless", "true");
        new JFXPanel(); // Inizializza JavaFX
    }

    @Test
    public void testActionCopyGetName() {
        Platform.runLater(()-> {
            // INSERIRE PERCORSI CHE SONO VALIDI PRIMA DI ESGUIRE IL TEST
            String fileToCopy = "C:\\Users\\Utente\\Desktop\\prova.txt";
            String destinationDirectory = "C:\\Users\\Utente\\Desktop\\Destinazione_copia_del_file";

            ActionCopy action = new ActionCopy(fileToCopy, destinationDirectory);

            // Testa il nome dell'azione
            assertEquals("ActionCopy", action.getName());
        });
    }

    @Test public void testActionCopyGetContent(){
        Platform.runLater(()-> {
            // INSERIRE PERCORSI CHE SONO VALIDI PRIMA DI ESGUIRE IL TEST
            String fileToCopy = "C:\\Users\\Utente\\Desktop\\prova.txt";
            String destinationDirectory = "C:\\Users\\Utente\\Desktop\\Destinazione_copia_del_file";

            ActionCopy action = new ActionCopy(fileToCopy, destinationDirectory);

            // Testa il contenuto dell'azione
            String content1 = action.getContent1();
            String content2 = action.getContent2();
            assertNotNull(content1);
            assertNotNull(content2);
            assertEquals(fileToCopy, content1);
            assertEquals(destinationDirectory, content2);
        });
    }

    @Test
    public void testActionCopyFileExistsAndValidDirectory() {
        Platform.runLater(()-> {
            // INSERIRE PERCORSI CHE SONO VALIDI PRIMA DI ESGUIRE IL TEST
            String fileToCopy = "C:\\Users\\Utente\\Desktop\\prova.txt";
            String destinationDirectory = "C:\\Users\\Utente\\Desktop\\Destinazione_copia_del_file";

            ActionCopy action = new ActionCopy(fileToCopy, destinationDirectory);

            // Testa l'esecuzione dell'azione (copia del file)
            action.execute();

            // Verifica se il file è stato copiato correttamente
            File copiedFile = new File(destinationDirectory, new File(fileToCopy).getName());
            assertTrue(copiedFile.exists(), "Il file non è stato copiato correttamente");
        });
   }

    @Test
    public void testActionCopyFileNotExistsAndValidDirectory() {
        Platform.runLater(()-> {
            // INSERIRE UN PERCORSO NON VALIDO PER IL FILE
            // INSERIRE UN PERCORSO VALIDO PER LA DIRECTORY
            String nonExistentFile = "C:\\percorso\\del\\tuo\\file_non_esistente.txt";
            String destinationDirectory = "C:\\Users\\Utente\\Desktop\\Destinazione_copia_del_file";

            ActionCopy action = new ActionCopy(nonExistentFile, destinationDirectory);

            // Testa l'esecuzione dell'azione (copia del file inesistente)
            action.execute();

            // Verifica se l'alert di errore viene visualizzato quando il file non esiste
            assertTrue(action.alertError.isShowing(), "L'alert di errore non è stato visualizzato per il file inesistente");

            // Verifica che il file non sia stato erroneamente copiato
            File copiedFile = new File(destinationDirectory, new File(nonExistentFile).getName());
            assertFalse(copiedFile.exists(), "Il file inesistente è stato erroneamente copiato");
        });
    }

    @Test
    public void testActionCopyFileExistsAndInvalidDirectory() {
        Platform.runLater(()-> {
            // INSERIRE UN PERCORSO VALIDO PER IL FILE
            // INSERIRE UN PERCORSO NON VALIDO PER LA DIRECTORY
            String fileToCopy = "C:\\Users\\Utente\\Desktop\\prova.txt";
            String invalidDirectory = "C:\\percorso\\della\\directory_non_valida";

            ActionCopy action = new ActionCopy(fileToCopy, invalidDirectory);

            // Testa l'esecuzione dell'azione (copia del file con directory non valida)
            action.execute();

            // Verifica se l'alert di errore viene visualizzato quando la directory non è valida
            assertTrue(action.alertError.isShowing(), "L'alert di errore non è stato visualizzato per la directory non valida");

            // Verifica che il file non sia stato erroneamente copiato
            File copiedFile = new File(invalidDirectory, new File(fileToCopy).getName());
            assertFalse(copiedFile.exists(), "Il file è stato erroneamente copiato con una directory non valida");
        });
    }

    @Test
    public void testActionCopyFileNotExistsAndInvalidDirectory() {
        Platform.runLater(()-> {
            // INSERIRE UN PERCORSO NON VALIDO PER IL FILE
            // INSERIRE UN PERCORSO NON VALIDO PER LA DIRECTORY
            String nonExistentFile = "C:\\percorso\\del\\tuo\\file_non_valido.txt";
            String invalidDirectory = "C:\\percorso\\della\\directory_non_valida";

            ActionCopy action = new ActionCopy(nonExistentFile, invalidDirectory);

            // Testa l'esecuzione dell'azione (copia del file e directory non validi)
            action.execute();

            // Verifica se l'alert di errore viene visualizzato quando sia il file che la directory non sono validi
            assertTrue(action.alertError.isShowing(), "L'alert di errore non è stato visualizzato per il file e la directory non validi");

            // Verifica che il file non sia stato erroneamente copiato
            File copiedFile = new File(invalidDirectory, new File(nonExistentFile).getName());
            assertFalse(copiedFile.exists(), "Il file è stato erroneamente copiato con un file o directory non validi");
        });
    }

    @Test
    public void testFileExistsInDirectory() {
        Platform.runLater(()-> {
            // INSERIRE PERCORSI CHE SONO VALIDI PRIMA DI ESGUIRE IL TEST
            String fileToCopy = "C:\\Users\\Utente\\Desktop\\prova.txt";
            String destinationDirectory = "C:\\Users\\Utente\\Desktop\\Destinazione_copia_del_file";

            // Crea un file con lo stesso nome nella directory di destinazione
            createFileInDirectory(destinationDirectory, new File(fileToCopy).getName());

            ActionCopy action = new ActionCopy(fileToCopy, destinationDirectory);

            // Testa l'esecuzione dell'azione (copia del file con file esistente nella directory)
            action.execute();

            // Verifica se l'alert di errore viene visualizzato quando il file esiste già nella directory di destinazione
            assertTrue(action.alertError.isShowing(), "L'alert di errore non è stato visualizzato per il file esistente nella directory");

            // Verifica che il file non sia stato erroneamente copiato
            File copiedFile = new File(destinationDirectory, new File(fileToCopy).getName());
            assertFalse(copiedFile.exists(), "Il file è stato erroneamente copiato con un file esistente nella directory");
        });
    }

    //crea nella directory di destinazione un file con lo stesso nome del file che deve essere copiato
    private void createFileInDirectory(String directoryPath, String fileName) {
        Path filePath = Paths.get(directoryPath, fileName);
        try {
            Files.createFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
