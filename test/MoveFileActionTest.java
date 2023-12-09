import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class MoveFileActionTest {

    //PERCORSO VALIDO DEL FILE
    String fileToMove = "C:\\Users\\Utente\\Desktop\\prova.txt";

    //PERCORSO VALIDO DELLA DIRECTORY
    String destinationDirectory = "C:\\Users\\Utente\\Desktop\\Destinazione_copia_del_file";

    //PERCORSO NON VALIDO DEL FILE
    String nonExistentFile = "C:\\percorso\\del\\tuo\\file_non_valido.txt";

    //PERCORSO NON VALIDO DELLA DIRECTORY
    String invalidDirectory = "C:\\percorso\\della\\directory_non_valida";

    MoveFileAction action1 = new MoveFileAction(fileToMove, destinationDirectory);
    MoveFileAction action2 = new MoveFileAction(nonExistentFile, destinationDirectory);
    MoveFileAction action3 = new MoveFileAction(fileToMove, invalidDirectory);
    MoveFileAction action4 = new MoveFileAction(nonExistentFile, invalidDirectory);

    @BeforeAll
    public static void initJFX() {
        System.setProperty("javafx.headless", "true");
        new JFXPanel(); // Inizializza JavaFX
    }


    @Test
    public void testFileMoveFileGetName() {

        // Testa il nome dell'azione
        assertEquals("MoveFileAction", action1.getName());
        assertEquals("MoveFileAction", action2.getName());
        assertEquals("MoveFileAction", action3.getName());
        assertEquals("MoveFileAction", action4.getName());
    }

    @Test public void testFileMoveFileGetContent(){

        // Testa il contenuto dell'azione
        assertNotNull(action1.getContent1());
        assertNotNull(action1.getContent2());
        assertEquals(fileToMove, action1.getContent1());
        assertEquals(destinationDirectory, action1.getContent2());

        assertNotNull(action2.getContent1());
        assertNotNull(action2.getContent2());
        assertEquals(nonExistentFile, action2.getContent1());
        assertEquals(destinationDirectory, action2.getContent2());

        assertNotNull(action3.getContent1());
        assertNotNull(action3.getContent2());
        assertEquals(fileToMove, action3.getContent1());
        assertEquals(invalidDirectory, action3.getContent2());

        assertNotNull(action4.getContent1());
        assertNotNull(action4.getContent2());
        assertEquals(nonExistentFile, action4.getContent1());
        assertEquals(invalidDirectory, action4.getContent2());
    }

    @Test
    public void testFileMoveFileExistsAndValidDirectory() {

        // Testa l'esecuzione dell'azione (spostamento file esistente in una directory valida)
        action1.execute();

        // Verifica se il file è stato spostato correttamente
        File movedFile = new File(destinationDirectory, new File(fileToMove).getName());
        assertTrue(movedFile.exists(), "Il file non è stato spostato correttamente");

        // Verifica se il file originale non esiste più nella posizione originale
        assertFalse(new File(fileToMove).exists(), "Il file originale esiste ancora nella posizione originale");
    }


    @Test
    public void testFileMoveFileNotExistAndValidDirectory() {

        // Testa l'esecuzione dell'azione (spostamento file inesistente in una directory valida)
        action2.execute();

        // Verifica che il file non sia stato spostato
        File movedFile = new File(destinationDirectory, new File(nonExistentFile).getName());
        assertFalse(movedFile.exists(), "Il file inesistente è stato erroneamente spostato");
    }
    @Test
    public void testFileMoveFileExistsAndInvalidDirectory() {

        // Testa l'esecuzione dell'azione (spostamento del file in una directory non valida)
        action3.execute();

        // Verifica che il file non sia stato spostato
        File movedFile = new File(invalidDirectory, new File(fileToMove).getName());
        assertFalse(movedFile.exists(), "Il file è stato erroneamente spostato in una directory non valida");
    }

    @Test
    public void testFileMoveFileNotExistAndInvalidDirectory() {

        // Testa l'esecuzione dell'azione (spostamento del file inesistente in una directory non valida)
        action4.execute();

        // Verifica che il file non sia stato erroneamente spostato
        File movedFile = new File(invalidDirectory, new File(nonExistentFile).getName());
        assertFalse(movedFile.exists(), "Il file è stato erroneamente spostato con un file o directory non validi");

    }

    @Test
    public void testFileAlreadyExistsInDirectory() {

        // Creazione di un file con lo stesso nome nella directory di destinazione
        createFileInDirectory(destinationDirectory, new File(fileToMove).getName());

        MoveFileAction action = new MoveFileAction(fileToMove, destinationDirectory);

        // Testa l'esecuzione dell'azione (spostamento del file con file con lo steso nome esistente nella directory)
        action1.execute();

        // Verifica se il file è stato spostato correttamente
        File movedFile = new File(destinationDirectory, new File(fileToMove).getName());
        assertTrue(movedFile.exists(), "Il file non è stato spostato correttamente");

        // Verifica se il file originale non esiste più nella posizione originale
        assertFalse(new File(fileToMove).exists(), "Il file originale esiste ancora nella posizione originale");


    }

    //Creazione nella directory di destinazione un file con lo stesso nome del file che deve essere spostato
    private void createFileInDirectory(String directoryPath, String fileName) {
        Path filePath = Paths.get(directoryPath, fileName);
        try {
            Files.createFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

