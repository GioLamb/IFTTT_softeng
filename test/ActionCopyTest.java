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

    //PERCORSO VALIDO DEL FILE
    String fileToCopy = "C:\\Users\\Utente\\Desktop\\prova.txt";

    //PERCORSO VALIDO DELLA DIRECTORY
    String destinationDirectory = "C:\\Users\\Utente\\Desktop\\Destinazione_copia_del_file";

    //PERCORSO NON VALIDO DEL FILE
    String nonExistentFile = "C:\\percorso\\del\\tuo\\file_non_valido.txt";

    //PERCORSO NON VALIDO DELLA DIRECTORY
    String invalidDirectory = "C:\\percorso\\della\\directory_non_valida";

    ActionCopy action1 = new ActionCopy(fileToCopy, destinationDirectory);
    ActionCopy action2 = new ActionCopy(nonExistentFile, destinationDirectory);
    ActionCopy action3 = new ActionCopy(fileToCopy, invalidDirectory);
    ActionCopy action4 = new ActionCopy(nonExistentFile, invalidDirectory);

    @BeforeAll
    public static void initJFX() {
        System.setProperty("javafx.headless", "true");
        new JFXPanel(); // Inizializza JavaFX
    }

    @Test
    public void testActionCopyGetName() {

        // Testa il nome dell'azione
        assertEquals("ActionCopy", action1.getName());
        assertEquals("ActionCopy", action2.getName());
        assertEquals("ActionCopy", action3.getName());
        assertEquals("ActionCopy", action4.getName());
    }

    @Test public void testActionCopyGetContent(){

        // Testa il contenuto dell'azione
        assertNotNull(action1.getContent1());
        assertNotNull(action1.getContent2());
        assertEquals(fileToCopy, action1.getContent1());
        assertEquals(destinationDirectory, action1.getContent2());

        assertNotNull(action2.getContent1());
        assertNotNull(action2.getContent2());
        assertEquals(nonExistentFile, action2.getContent1());
        assertEquals(destinationDirectory, action2.getContent2());

        assertNotNull(action3.getContent1());
        assertNotNull(action3.getContent2());
        assertEquals(fileToCopy, action3.getContent1());
        assertEquals(invalidDirectory, action3.getContent2());

        assertNotNull(action4.getContent1());
        assertNotNull(action4.getContent2());
        assertEquals(nonExistentFile, action4.getContent1());
        assertEquals(invalidDirectory, action4.getContent2());
    }

    @Test
    public void testActionCopyFileExistsAndValidDirectory() {

        // Testa l'esecuzione dell'azione (file esistente e directory valida)
        action1.execute();

        // Verifica se il file è stato copiato correttamente
        File copiedFile = new File(destinationDirectory, new File(fileToCopy).getName());
        assertTrue(copiedFile.exists(), "Il file non è stato copiato correttamente");
    }

    @Test
    public void testActionCopyFileNotExistsAndValidDirectory() {

        // Testa l'esecuzione dell'azione (file inesistente e directory valida)
        action2.execute();

        // Verifica che il file non sia stato erroneamente copiato
        File copiedFile = new File(destinationDirectory, new File(nonExistentFile).getName());
        assertFalse(copiedFile.exists(), "Il file inesistente è stato erroneamente copiato");
    }

    @Test
    public void testActionCopyFileExistsAndInvalidDirectory() {

        // Testa l'esecuzione dell'azione (file esistente e directory non valida)
        action3.execute();

        // Verifica che il file non sia stato erroneamente copiato
        File copiedFile = new File(invalidDirectory, new File(fileToCopy).getName());
        assertFalse(copiedFile.exists(), "Il file è stato erroneamente copiato con una directory non valida");
    }

    @Test
    public void testActionCopyFileNotExistsAndInvalidDirectory() {

        // Testa l'esecuzione dell'azione (file inesistente e directory non valida)
        action4.execute();

        // Verifica che il file non sia stato erroneamente copiato
        File copiedFile = new File(invalidDirectory, new File(nonExistentFile).getName());
        assertFalse(copiedFile.exists(), "Il file è stato erroneamente copiato con un file o directory non validi");
    }

    @Test
    public void testFileExistsInDirectory() {

        // Crea un file con lo stesso nome nella directory di destinazione
        createFileInDirectory(destinationDirectory, new File(fileToCopy).getName());


        // Testa l'esecuzione dell'azione (copia del file con file con lo stesso nome esistente nella directory)
        action1.execute();

        // Verifica che il file non è stato copiato correttamente
        File copiedFile = new File(destinationDirectory, new File(fileToCopy).getName());
        assertTrue(copiedFile.exists(), "Il file non è stato copiato correttamente");
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
