import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFileAction extends FactoryAction implements Action {
    // Percorso del file e contenuto da scrivere nel file
    private final String filePath;
    private final String content;

    // Alert per la conferma e l'errore durante l'esecuzione
    private  Alert alertConfirm;
    Alert alertError;

    // Costruttore della classe
    public WriteToFileAction(String filePath, String content) {
        this.filePath = filePath;
        this.content = content;

        // Inizializzazione degli alert all'interno del thread JavaFX
        Platform.runLater(() -> {
            // Alert per la conferma
            this.alertConfirm = new Alert(Alert.AlertType.INFORMATION);
            this.alertConfirm.setTitle("AZIONE ESEGUITA");
            this.alertConfirm.setHeaderText(null);
            this.alertConfirm.setContentText("Il contenuto è stato scritto nel file " + filePath);

            // Alert per l'errore
            this.alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setContentText("Il file non esiste o non è un file regolare (directory)");
        });
    }

    // Metodo per ottenere il nome dell'azione
    @Override
    public String getName() {
        return "WriteToFileAction";
    }

    // Metodi per ottenere il contenuto del percorso del file e il contenuto da scrivere
    @Override
    public String getContent1() {
        return filePath;
    }

    @Override
    public String getContent2() {
        return content;
    }

    // Metodo per eseguire l'azione di scrittura nel file
    @Override
    public void execute() {
        Platform.runLater(() -> {
            // Creazione di un oggetto File con il percorso specificato
            File file = new File(filePath);

            // Verifica se il file esiste ed è un file regolare
            if (file.exists() && file.isFile()) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                    // Scrittura del contenuto nel file
                    writer.write(content);

                    // Mostra l'alert di conferma e gestisci la chiusura dell'alert
                    alertConfirm.show();
                    alertConfirm.setOnCloseRequest(event -> onActionClose());
                } catch (IOException e) {
                    // Gestione dell'errore di scrittura nel file
                    System.out.println("Si è verificato un errore durante la scrittura del contenuto nel file.");
                    e.printStackTrace();
                }
            } else {
                // Mostra l'alert di errore e gestisci la chiusura dell'alert
                alertError.show();
                alertError.setOnCloseRequest(event -> onActionClose());
            }
        });
    }

    // Metodo chiamato alla chiusura dell'azione
    @Override
    public void onActionClose() {
        Platform.runLater(() -> {
            // Chiusura dell'alert di conferma con il clic del bottone OK o di chiusura
            alertConfirm.setOnCloseRequest(event -> {
                if (alertConfirm.getResult() == ButtonType.CLOSE || alertConfirm.getResult() == ButtonType.OK) {
                    alertConfirm.close();
                }
            });
        });

        Platform.runLater(() -> {
            // Chiusura dell'alert di errore con il clic del bottone OK o di chiusura
            alertError.setOnCloseRequest(event -> {
                if (alertError.getResult() == ButtonType.CLOSE || alertError.getResult() == ButtonType.OK) {
                    alertError.close();
                }
            });
        });
    }
}
