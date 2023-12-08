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

    private static final String TEST_FILE_PATH = "/Users/vivi/Documents/Test.txt";
    private static final String TEST_CONTENT = "Hello, JUnit 5!";
    private WriteToFileAction writeToFileAction;

    @BeforeAll
    public static void initJFX(){
        System.setProperty("javafx.headless", "true");
        new JFXPanel();
    }
    @BeforeEach
    void setUp() {
        Platform.runLater(()->{
            // Initialize the WriteToFileAction with test data
            writeToFileAction = new WriteToFileAction(TEST_FILE_PATH, TEST_CONTENT);
        });
    }

    @Test
    void testExecute() {
        Platform.runLater(()->{
            // Perform the action
            writeToFileAction.execute();

            // Verify that the file was created
            File file = new File(TEST_FILE_PATH);
            assertTrue(file.exists());

            // Verify the content of the file
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String fileContent = reader.readLine();
                assertEquals(TEST_CONTENT, fileContent);
            } catch (IOException e) {
                fail("Exception should not be thrown");
            }
        });
    }

    @Test
    void testGetContent1() {
        Platform.runLater(()->{
            // Ensure getContent1 returns the expected content
            assertEquals(null, writeToFileAction.getContent1());
        });
    }

    @Test
    void testGetContent2() {
        Platform.runLater(()->{
            // Ensure getContent2 returns null
            assertEquals(null, writeToFileAction.getContent2());
        });
    }

    @Test
    void testOnActionClose() {
        Platform.runLater(()->{
            // Ensure onActionClose does not throw any exceptions
            assertDoesNotThrow(() -> writeToFileAction.onActionClose());
        });
    }

    @AfterEach
    void tearDown() {
        // Clean up: delete the test file
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }
}
