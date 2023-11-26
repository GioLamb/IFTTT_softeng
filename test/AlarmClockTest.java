import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AlarmClockTest {
    // CAMBIARE IL PATH AUDIO PRIMA DI FARE I TEST
    private static final String TEST_AUDIO_FILE_PATH = "C:\\Users\\Giovanni\\Downloads\\Effetto-Sonoro-Campanello-Casa.wav";
    private AlarmClock alarmClock;

    @BeforeAll
    public static void initJFX() {
        System.setProperty("javafx.headless", "true");
        new JFXPanel(); // Inizializza JavaFX
    }

    @BeforeEach
    public void setUp() {
        Platform.runLater(()->{
            alarmClock = new AlarmClock(TEST_AUDIO_FILE_PATH);
        });
    }

    @AfterEach
    public void tearDown() {
        Platform.runLater(()->{
            alarmClock.onActionClose();
        });
    }

    @Test
    public void testExecute() {
        Platform.runLater(()->{
            alarmClock.execute();
            assertTrue(alarmClock.getDialogStage().isShowing());
        });
        // Assuming dialogStage.show() triggers the execution
        // You may need to modify this based on your actual implementation
    }

    @Test
    public void testOnActionClose() {
        Platform.runLater(()->{
            assertFalse(alarmClock.getDialogStage().isShowing());

            alarmClock.execute();
            assertTrue(alarmClock.getDialogStage().isShowing());

            alarmClock.onActionClose();
            assertFalse(alarmClock.getDialogStage().isShowing());
        });
    }

    @Test
    public void testGetName() {
        Platform.runLater(()->{
            assertEquals("Sveglia", alarmClock.getName());
        });
    }

    @Test
    public void testGetContent() {
        Platform.runLater(()->{
            assertEquals(TEST_AUDIO_FILE_PATH, alarmClock.getContent());
        });
    }
}
