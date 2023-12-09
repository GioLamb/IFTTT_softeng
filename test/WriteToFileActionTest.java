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

    private static final String TEST_FILE_PATH = "/Users/vivi/Documents/Text.txt";
    private static final String TEST_CONTENT = "Ciao";
    private WriteToFileAction writeToFileAction;

    @BeforeAll
    public static void initJFX(){
        System.setProperty("javafx.headless", "true");
        new JFXPanel();
    }
    @BeforeEach
    void setUp() {
        writeToFileAction = new WriteToFileAction(TEST_FILE_PATH, TEST_CONTENT);
    }

    @Test
    void testExecute() {
        writeToFileAction.execute();


        File file = new File(TEST_FILE_PATH);

        assertTrue(file.exists());


        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String fileContent = reader.readLine();
            assertEquals(TEST_CONTENT, fileContent);
        } catch (IOException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    void testGetContent1() {
        WriteToFileAction writeToFileAction = new WriteToFileAction(TEST_FILE_PATH, TEST_CONTENT);
        assertEquals(TEST_FILE_PATH, writeToFileAction.getContent1());
    }

    @Test
    void testGetContent2() {
        WriteToFileAction writeToFileAction = new WriteToFileAction(TEST_FILE_PATH, TEST_CONTENT);
        assertEquals(TEST_CONTENT, writeToFileAction.getContent2());
    }

    @Test
    void testOnActionClose() {
        assertDoesNotThrow(() -> writeToFileAction.onActionClose());
    }
}
