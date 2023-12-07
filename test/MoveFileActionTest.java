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

public class MoveFileActionTest {

    @BeforeAll
    public static void initJFX() {
        System.setProperty("javafx.headless", "true");
        new JFXPanel(); // Inizializza JavaFX
    }

    @Test
    public void testMoveFileActionGetName() {
        Platform.runLater(()-> {
            // INSERIRE PERCORSI CHE SONO VALIDI PRIMA DI ESGUIRE IL TEST
            String fileToCopy = "C:\\Users\\Utente\\Desktop\\prova.txt";
            String destinationDirectory = "C:\\Users\\Utente\\Desktop\\Destinazione_copia_del_file";

            MoveFileAction action = new MoveFileAction( fileToCopy, destinationDirectory);

            // Testa il nome dell'azione
            assertEquals("MoveFileAction", action.getName());
        });
    }

    @Test public void testMoveFileActionGetContent(){
        Platform.runLater(()-> {
            // INSERIRE PERCORSI CHE SONO VALIDI PRIMA DI ESGUIRE IL TEST
            String fileToMove = "C:\\Users\\Utente\\Desktop\\prova.txt";
            String destinationDirectory = "C:\\Users\\Utente\\Desktop\\Destinazione_copia_del_file";

            MoveFileAction action = new MoveFileAction(fileToMove, destinationDirectory);

            // Testa il contenuto dell'azione
            String content1 = action.getContent1();
            String content2 = action.getContent2();
            assertNotNull(content1);
            assertNotNull(content2);
            assertEquals(fileToMove, content1);
            assertEquals(destinationDirectory, content2);
        });
    }

    @Test
    public void testFileMoveFileExistsAndValidDirectory() {
        Platform.runLater(()-> {
            // INSERIRE PERCORSI CHE SONO VALIDI NEL SISTEMA PRIMA DI ESGUIRE IL TEST
            String fileToMove = "C:\\Users\\Utente\\Desktop\\prova.txt";
            String destinationDirectory = "C:\\Users\\Utente\\Desktop\\Destinazione_copia_del_file";

            MoveFileAction action = new MoveFileAction(fileToMove, destinationDirectory);

            // Testa l'esecuzione dell'azione (spostamento del file)
            action.execute();

            // Verifica se il file è stato spostato correttamente
            File movedFile = new File(destinationDirectory, new File(fileToMove).getName());
            assertTrue(movedFile.exists(), "Il file non è stato spostato correttamente");

            // Verifica se il file originale non esiste più nella posizione originale
            assertFalse(new File(fileToMove).exists(), "Il file originale esiste ancora nella posizione originale");
        });
    }


        @Test
        public void testFileMoveFileNotExistAndValidDirectory() {
            Platform.runLater(()-> {
                // INSERIRE UN PERCORSO NON VALIDO PER IL FILE
                //INSERIRE UN PERCORSO VALIDO PER LA DIRECTORY
                String nonExistentFile = "C:\\percorso\\del\\tuo\\file_non_esistente.txt";
                String destinationDirectory = "C:\\Users\\Utente\\Desktop\\Destinazione_copia_del_file";

                MoveFileAction action = new MoveFileAction(nonExistentFile, destinationDirectory);

                // Testa l'esecuzione dell'azione (spostamento del file inesistente)
                action.execute();

                // Verifica se l'alert di errore viene visualizzato quando il file non esiste
                assertTrue(action.alertError.isShowing(), "L'alert di errore non è stato visualizzato per il file inesistente");

                // Verifica che il file non sia stato spostato
                File movedFile = new File(destinationDirectory, new File(nonExistentFile).getName());
                assertFalse(movedFile.exists(), "Il file inesistente è stato erroneamente spostato");
            });
        }
    @Test
    public void testFileMoveFileExistsAndInvalidDirectory() {
        Platform.runLater(()-> {
            // INSERIRE UN PERCORSO VALIDO PER IL FILE
            // INSERIRE UN PERCORSO NON VALIDO PER LA DIRECTORY
            String fileToMove = "C:\\Users\\Utente\\Desktop\\prova.txt";
            String invalidDirectory = "C:\\percorso\\della\\directory_non_valida";

            MoveFileAction action = new MoveFileAction(fileToMove, invalidDirectory);

            // Testa l'esecuzione dell'azione (spostamento del file con directory non valida)
            action.execute();

            // Verifica se l'alert di errore viene visualizzato quando la directory non è valida
            assertTrue(action.alertError.isShowing(), "L'alert di errore non è stato visualizzato per la directory non valida");

            // Verifica che il file non sia stato spostato
            File movedFile = new File(invalidDirectory, new File(fileToMove).getName());
            assertFalse(movedFile.exists(), "Il file è stato erroneamente spostato in una directory non valida");
        });
    }

    @Test
    public void testFileMoveFileNotExistAndInvalidDirectory() {
        Platform.runLater(()-> {
            // INSERIRE UN PERCORSO NON VALIDO PER IL FILE
            // INSERIRE UN PERCORSO NON VALIDO PER LA DIRECTORY
            String nonExistentFile = "C:\\percorso\\del\\tuo\\file_non_valido.txt";
            String invalidDirectory = "C:\\percorso\\della\\directory_non_valida";

            MoveFileAction action = new MoveFileAction(nonExistentFile, invalidDirectory);

            // Testa l'esecuzione dell'azione (spostamento del file e directory non validi)
            action.execute();

            // Verifica se l'alert di errore viene visualizzato quando sia il file che la directory non sono validi
            assertTrue(action.alertError.isShowing(), "L'alert di errore non è stato visualizzato per il file e la directory non validi");

            // Verifica che il file non sia stato erroneamente spostato
            File movedFile = new File(invalidDirectory, new File(nonExistentFile).getName());
            assertFalse(movedFile.exists(), "Il file è stato erroneamente spostato con un file o directory non validi");
        });
    }

    @Test
    public void testFileAlreadyExistsInDirectory() {
        Platform.runLater(()-> {
        // INSERIRE PERCORSI CHE SONO VALIDI PRIMA DI ESGUIRE IL TEST
        String fileToMove = "C:\\Users\\Utente\\Desktop\\prova.txt";
        String destinationDirectory = "C:\\Users\\Utente\\Desktop\\Destinazione_copia_del_file";

        // Crea un file con lo stesso nome nella directory di destinazione
        createFileInDirectory(destinationDirectory, new File(fileToMove).getName());

        MoveFileAction action = new MoveFileAction(fileToMove, destinationDirectory);

        // Testa l'esecuzione dell'azione (spostamento del file con file esistente nella directory)
        action.execute();

        // Verifica se l'alert di errore viene visualizzato quando il file esiste già nella directory di destinazione
        assertTrue(action.alertError.isShowing(), "L'alert di errore non è stato visualizzato per il file esistente nella directory");

        // Verifica che il file non sia stato erroneamente spostato
        File movedFile = new File(destinationDirectory, new File(fileToMove).getName());
        assertFalse(movedFile.exists(), "Il file è stato erroneamente spostato con un file esistente nella directory");
        });
    }

    //crea nella directory di destinazione un file con lo stesso nome del file che deve essere spostato
    private void createFileInDirectory(String directoryPath, String fileName) {
        Path filePath = Paths.get(directoryPath, fileName);
        try {
            Files.createFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

