import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DisplayMessage extends FactoryAction implements Action {

    Alert alert;
    private String message;

    public DisplayMessage(String message) {
        this.message=message;
        this.alert = new Alert(Alert.AlertType.INFORMATION);
        this.alert.setHeaderText(null);
        this.alert.setContentText(message);
    }

    @Override
    public void execute() {
        //mostra l'alert fin quando l'utente non interagisce con esso
        alert.show();
        onActionClose();
    }

    @Override
    public void onActionClose() {
        //chiusura alert con il clic del bottone OK dell'alert
        //o con il click del bottone di chiusura dell'alert
        alert.setOnCloseRequest(event -> {
            if (alert.getResult() == ButtonType.CLOSE || alert.getResult() == ButtonType.OK) {
                alert.close();
            }
        });
    }

    @Override
    public String getName() {
        return "Promemoria";
    }

    @Override
    public String getContent() {
        return message;
    }
}