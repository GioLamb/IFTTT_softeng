import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javax.xml.namespace.QName;
import java.util.Optional;

public class DisplayMessage extends FactoryAction implements Action {
    private String message;

    private final Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public DisplayMessage(String message) {
        super("Promemoria", message);
    }

    @Override
    public void execute() {
        //crea la finestra di dialogo in cui viene mostrato il messaggio di promemoria
        alert.setTitle("Promemoria");
        alert.setHeaderText(null);
        alert.setContentText(message);
        //mostra l'alert fin quando l'utente non interagisce con esso
        alert.showAndWait();
        onActionClose();
    }

    @Override
    public void onActionClose() {
        //chiusura alert con il clic del bottone OK dell'alert
        //o con il click del bottone di chiusura dell'alert
        Optional<ButtonType> result = Optional.ofNullable(alert.getResult());
        if (result.isEmpty() || result.get() == ButtonType.OK || result.get().getButtonData().isCancelButton()) {
            alert.close();
        }
    }
}

