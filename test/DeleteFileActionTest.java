import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteFileActionTest {

    //PERCORSO VALIDO DEL FILE
    String fileToDelete = "C:\\Users\\Utente\\Desktop\\prova.txt";

    //PERCORSO NON VALIDO DEL FILE
    String nonExistentFile = "C:\\percorso\\del\\tuo\\file_non_valido.txt";

    // PERCORSO VALIDO DELLA DIRECTORY
    String directoryPath = "C:\\Users\\Utente\\Desktop\\Destinazione_copia_del_file";

    DeleteFileAction action1 = new DeleteFileAction(fileToDelete);
    DeleteFileAction action2 = new DeleteFileAction(nonExistentFile);
    DeleteFileAction action3 = new DeleteFileAction(directoryPath);


    @BeforeAll
    public static void initJFX() {
        System.setProperty("javafx.headless", "true");
        new JFXPanel(); // Inizializza JavaFX
    }


    @Test
    public void testFileMoveFileGetName() {

        // Testa il nome dell'azione
        assertEquals("DeleteFileAction", action1.getName());
        assertEquals("DeleteFileAction", action2.getName());
    }

    @Test public void testFileMoveFileGetContent(){

        // Testa il contenuto dell'azione
        assertNotNull(action1.getContent1());
        assertNull(action1.getContent2());
        assertEquals(fileToDelete, action1.getContent1());
        assertEquals(null, action1.getContent2());

        assertNotNull(action2.getContent1());
        assertNull(action2.getContent2());
        assertEquals(nonExistentFile, action2.getContent1());
        assertEquals(null, action2.getContent2());

    }

    @Test
    public void testDeleteFileActionFileExists() {

        // Testa l'esecuzione dell'azione (eliminazione del file esistente)
        action1.execute();

        // Verifica se il file è stato eliminato correttamente
        File deletedFile = new File(fileToDelete);
        assertFalse(deletedFile.exists(), "Il file non è stato eliminato correttamente");
    }

    @Test
    public void testDeleteFileActionFileNotExist() {

        // Testa l'esecuzione dell'azione (eliminazione del file inesistente)
        action2.execute();

        // Verifica che il file non sia stato eliminato
        File deletedFile = new File( new File(nonExistentFile).getName());
        assertFalse(deletedFile.exists(), "Il file è stato erroneamente eliminato");

    }

    @Test
    public void testDeleteFileActionDirectoryInsteadOfFile() {

        // Testa l'esecuzione dell'azione (eliminazione di una directory invece di un file)
        action3.execute();

        //verifica che la directory non sia stata eliminata
        File directory = new File(directoryPath);
        assertTrue(directory.exists(), "La directory è stata erroneamente eliminata");
    }
}