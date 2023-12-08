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
    private File FileToWrite;
    Alert alertError;

    public WriteToFileAction(String filePath, String content) {
        this.FileToWrite = new File(filePath);
        this.filePath = filePath;
        this.content = content;
        this.alertConfirm = new Alert(Alert.AlertType.INFORMATION);
        this.alertConfirm.setTitle("AZIONE ESEGUITA");
        this.alertConfirm.setHeaderText(null);
        this.alertConfirm.setContentText("Il contenuto è stato scritto nel file " +filePath);
        this.alertError = new Alert(Alert.AlertType.ERROR);
        alertError.setContentText("Il file non esiste o non è un file regolare (directory)");
    }

    @Override
    public String getName() {
        return "WriteToFileAction";
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
        if(FileToWrite.exists() && FileToWrite.isFile()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(content);
                alertConfirm.show();
                alertConfirm.setOnCloseRequest(event -> onActionClose());
            } catch (IOException e) {
                System.out.println("Si è verificato un errore durante la scrittura del contenuto nel file.");
                e.printStackTrace();
            }
        }else {
            alertError.show();
            alertError.setOnCloseRequest(event -> onActionClose());
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

