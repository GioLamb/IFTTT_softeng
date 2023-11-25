import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class RuleManagerTest {
    RuleManager rm1 = RuleManager.getInstance();
    RuleManager rm2 = RuleManager.getInstance();
    @Test
    void getInstance() {
        assertEquals(rm1, rm2);
    }
}