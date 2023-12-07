import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFileAction extends FactoryAction implements Action {
    private String filePath;
    private String content;
    private Alert alertConfirm;
    Alert alertError;

    public WriteToFileAction(String filePath, String content) {
        this.filePath = filePath;
        this.content = content;
        this.alertConfirm = new Alert(Alert.AlertType.INFORMATION);
        this.alertConfirm.setTitle("AZIONE ESEGUITA");
        this.alertConfirm.setHeaderText(null);
        this.alertConfirm.setContentText("Il contenuto è stato scritto nel file");
        this.alertError = new Alert(Alert.AlertType.ERROR);
        alertError.setContentText("Il file non esiste o non è un file regolare (directory)");
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getContent1() {
        return null;
    }

    @Override
    public String getContent2() {
        return null;
    }



    @Override
    public void execute() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActionClose() {
        Platform.runLater(() -> {
            //chiusura alert con il clic del bottone OK dell'alert
            //o con il click del bottone di chiusura dell'alert
            alertConfirm.setOnCloseRequest(event -> {
                if (alertConfirm.getResult() == ButtonType.CLOSE || alertConfirm.getResult() == ButtonType.OK) {
                    alertConfirm.close();
                }
            });
        });
        Platform.runLater(() -> {
            //chiusura alert con il clic del bottone OK dell'alert
            //o con il click del bottone di chiusura dell'alert
            alertError.setOnCloseRequest(event -> {
                if (alertError.getResult() == ButtonType.CLOSE || alertError.getResult() == ButtonType.OK) {
                    alertError.close();
                }
            });
        });
    }
}

