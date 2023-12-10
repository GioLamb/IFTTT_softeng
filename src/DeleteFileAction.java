import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeleteFileAction extends  FactoryAction implements Action{

    private Alert alertConfirm;
    private Alert alertError;
    private final String file;
    private final File fileToDelete;

    public DeleteFileAction (String file){
        this.file = file;
        this.fileToDelete = new File(file);
        //Platform.runLater(()-> {      //da utilizzare solo quando si esegue DeleteFileActionTest

            //Alert per notificare l'utente dell'esecuzione dell'azione
            this.alertConfirm = new Alert(Alert.AlertType.INFORMATION);
            this.alertConfirm.setTitle("AZIONE ESEGUITA");
            this.alertConfirm.setHeaderText(null);
            this.alertConfirm.setContentText("Il file " + file + " è stato eliminato");

            //Alert per notificare l'utente quando l'azione non viene eseguita correttamente
            this.alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setContentText("Il file " + file + " non esiste o non è un file regolare (directory)");
        //});
    }
    @Override
    public String getName() {
        return "DeleteFileAction";
    }

    @Override
    public String getContent1() {
        return file;
    }
    @Override
    public String getContent2() {
        return null;
    }

    @Override
    public void execute() {
        //Platform.runLater(()-> {      //da utilizzare solo quando si esegue DeleteFileActionTest

            // Verifica se il file esiste e se il file è un file regolare(non una directory)
            if (fileToDelete.exists() && fileToDelete.isFile()) {
                try {
                    // Creazione dell'oggetto Path per il file
                    Path fileSource = Paths.get(file);
                    // Eliminazione del file
                    Files.delete(fileSource);
                    alertConfirm.show();
                    alertConfirm.setOnCloseRequest(event -> onActionClose());
                } catch (IOException e) {
                    System.out.println("Si è verificato un errore durante la cancellazione del file.");
                    e.printStackTrace();
                }
            } else {
                alertError.show();
                alertError.setOnCloseRequest(event -> onActionClose());
            }
        //});
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



