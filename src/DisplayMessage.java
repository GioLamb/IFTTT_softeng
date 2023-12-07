import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DisplayMessage extends FactoryAction implements Action {

    private Alert alert;
    private String message;

    public DisplayMessage(String message) {
        this.message=message;
        Platform.runLater(() -> {
            this.alert = new Alert(Alert.AlertType.INFORMATION);
            this.alert.setTitle("Promemoria");
            this.alert.setHeaderText(null);
            this.alert.setContentText(message);
        });
    }

    @Override
    public void execute() {
        Platform.runLater(() -> {
            //mostra l'alert fin quando l'utente non interagisce con esso
            alert.show();
            alert.setOnCloseRequest(event -> onActionClose());
        });
    }

    @Override
    public void onActionClose() {
        Platform.runLater(() -> {
            //chiusura alert con il clic del bottone OK dell'alert
            //o con il click del bottone di chiusura dell'alert
            alert.setOnCloseRequest(event -> {
                if (alert.getResult() == ButtonType.CLOSE || alert.getResult() == ButtonType.OK) {
                    alert.close();
                }
            });
        });
    }

    @Override
    public String getName() {
        return "Promemoria";
    }

    @Override
    public String getContent1() {
        return message;
    }
    @Override
    public String getContent2() {
        return null;
    }
}