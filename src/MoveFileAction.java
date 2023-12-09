import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class MoveFileAction extends  FactoryAction implements Action {
    private final Alert alertConfirm;
    Alert alertError;
    private final String file;
    private final String directory;
    private final File fileToMove;
    private final File targetDirectory;

    public MoveFileAction (String file, String directory) {
        this.file = file;
        this.directory = directory;
        this.fileToMove = new File(file);
        this.targetDirectory = new File(directory);
        this.alertConfirm = new Alert(Alert.AlertType.INFORMATION);
        this.alertConfirm.setTitle("AZIONE ESEGUITA");
        this.alertConfirm.setHeaderText(null);
        this.alertConfirm.setContentText("Il file" + file + "è stato spostato nella directory" + directory);
        this.alertError = new Alert(Alert.AlertType.ERROR);
        alertError.setContentText("Il file non esiste o la directory di destinazione non è valida.");
    }

    @Override
    public String getName() {
        return "MoveFileAction";
    }

    @Override
    public String getContent1() {
        return file;
    }

    @Override
    public String getContent2() {
        return directory;
    }

    @Override
    public void execute() {
        // Verifica se il file esiste e se la directory di destinazione è una directory valida
        if (fileToMove.exists() && targetDirectory.isDirectory()) {
            try {
                // Creazione degli oggetti Path per il file e la directory
                Path fileSource = Paths.get(file);
                Path fileDestination = Paths.get(directory, fileToMove.getName());

                // Sposta il file nella directory di destinazione
                // Se nella directory esiste già un file con lo stesso nome, viene sovrascritto dal file che si sta spostando
                Files.move(fileSource, fileDestination, StandardCopyOption.REPLACE_EXISTING);
                alertConfirm.show();
                alertConfirm.setOnCloseRequest(event -> onActionClose());
            } catch (IOException e) {
                System.out.println("Si è verificato un errore durante lo spostamento del file.");
                e.printStackTrace();
            }
        } else {
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


