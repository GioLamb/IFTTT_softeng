/*import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class WriteToFileActionTest {

    @BeforeAll
    public static void initJFX() {
        new JFXPanel();
    }

    @Test
    void writeToFile_shouldWriteContentToFile() {
        Platform.runLater(()->{
            // Arrange
            String filePath = "test_file.txt";
            String content = "Hello, JUnit!";
            WriteToFileAction writeToFileAction = new WriteToFileAction(filePath, content);

            // Act
            writeToFileAction.writeToFile(content, filePath);

            // Assert
            String fileContent = readFromFile(filePath);
            assertEquals(content, fileContent);
        });
    }

    @Test
    void writeToFile_shouldHandleIOException() {
        Platform.runLater(()->{
            // Arrange
            String filePath = "invalid_path/test_file.txt";
            String content = "Hello, JUnit!";
            WriteToFileAction writeToFileAction = new WriteToFileAction(filePath, content);

            // Act and Assert
            assertThrows(IOException.class, () -> writeToFileAction.writeToFile(content, filePath));
        });
    }

    private String readFromFile(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
 */
