import javafx.scene.control.Alert;

public class DisplayMessage implements Action{
    private String message;

    private Alert alert;

    public DisplayMessage(String message){
        this.message = message;
    }

    @Override
    public void execute(){
        //crea la finestra di dialogo in cui viene mostrato il messaggio di promemoria
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Promemoria");
        alert.setHeaderText(null);
        alert.setContentText(message);
    }
    @Override
    public void onActionClose() {
        //chiusura tramite interazione dell'utente
        alert.showAndWait();
    }
}

