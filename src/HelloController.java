import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HelloController {

    @FXML
    private Button btn;

    @FXML
    private void initialize() {
        // Opzioni di inizializzazione, se necessario
    }

    @FXML
    private void sayHello(ActionEvent event) {
        System.out.println("Ciao, mondo!");
    }
}

