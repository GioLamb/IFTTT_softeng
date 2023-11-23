import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;

public class FXMLDocumentController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableColumn<?, String> actionContentView;

    @FXML
    private TableColumn<?, String> actionView;

    @FXML
    private Button newRuleButton;

    @FXML
    private TableColumn<?, String> ruleNameView;

    @FXML
    private TableColumn<?, String> triggerContentView;

    @FXML
    private TableColumn<?, String> triggerView;

    @FXML
    private ComboBox actionSelector;

    @FXML
    private Label audioLabel;

    @FXML
    private Button buttonAudio;

    @FXML
    private TextField hourSelector;

    @FXML
    private TextField messageField;

    @FXML
    private Label messageLabel;

    @FXML
    private TextField minutesSelector;

    @FXML
    private Button submitButton;

    // Questo metodo ci permette di poter cambiare la scena caricando un diverso file FXML
    public void switchRuleMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ruleselector.fxml")); // carichiamo l'FXML della sezione per creare le regole
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Questo metodo ci permette di poter cambiare la scena caricando un diverso file FXML
    public void switchMainPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainpage.fxml")); // carichiamo l'FXML della mainpage
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Metodo che ci permette di scegliere un file audio
    @FXML
    void selectAudio(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        // impostiamo al FileChooser dei filtri in modo tale da essere sicuri di scegliere un file corretto.
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Audio files (*.mp3, wav)","*.mp3", "*.wav");
        fileChooser.getExtensionFilters().add(filter);

        File file = fileChooser.showOpenDialog(stage);
        if (file!=null){
            buttonAudio.setText(file.getAbsolutePath()); // cambiamo il testo del bottone con il path del file audio
        }
    }

    @FXML
    void submit(ActionEvent event) {
        Integer hours = Integer.parseInt(hourSelector.getCharacters().toString());
        Integer minutes = Integer.parseInt(minutesSelector.getCharacters().toString());
        if(hours >= 0 && hours < 24 && minutes >= 0 && minutes < 60){ // eseguiamo un controllo per verificare la correttezza delle ore e dei minuti
            LocalTime time = LocalTime.of(hours, minutes, 0); // creiamo l'oggetto LocalTime da associare alla azione
        }
        else{
            Alert a = new Alert(Alert.AlertType.ERROR,"L'orario inserito non è corretto. Riprovare");
            a.show();
        }

        if (actionSelector.getSelectionModel().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Non è stata inserita alcuna azione. Si prega di selezionarne una.");
            a.show();
        }

        if (!buttonAudio.isDisable() && buttonAudio.textProperty().getValue().equals("Seleziona file audio")) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Non è stata inserita alcuna traccia audio. Si prega di selezionarne una.");
            a.show();
        }

    }

    // metodo per accedere alla sezione di creazione delle regole
    @FXML
    void newRule(ActionEvent event) {
        try {
            switchRuleMenu(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
    }

    // Metodo per ritornare alla mainpage
    @FXML
    void cancel(ActionEvent event) {
        try {
            switchMainPage(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
    }

    // metodo per il controllo degli input nel campo delle ore
    public void checkHours(KeyEvent event) {
        try {
            if (!event.getCharacter().matches("[0-9]")) { // se l'input non fa match con un carattere compreso tra [0-9]
                event.consume(); // marchiamo l'evento come consumato
                hourSelector.backward(); // il cursore va indietro
                hourSelector.deleteNextChar(); // cancelliamo il carattere di input inserito precedentemente
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // metodo per il controllo degli input nel campo delle ore
    public void checkMinutes(KeyEvent event) {
        try {
            if (!event.getCharacter().matches("[0-9]")) { // se l'input non fa match con un carattere compreso tra [0-9]
                event.consume(); // marchiamo l'evento come consumato
                minutesSelector.backward(); // il cursore va indietro
                minutesSelector.deleteNextChar(); // cancelliamo il carattere di input inserito precedentemente
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // metodo per cambiare dinamicamente i campi in base alla azione selezionata
    public void changeContentAction(ActionEvent actionEvent) {
        try{
            switch (actionSelector.getValue().toString()){
                case "Sveglia":
                    messageField.setDisable(true); // disattiviamo il messageField per la scrittura del messsaggio
                    messageLabel.setDisable(true); // disattiviamo il messageLabel
                    audioLabel.setDisable(false); // attiviamo l'audioLabel
                    buttonAudio.setDisable(false); // attiviamo il buttonAudio per la selezione del file audio
                    break;

                case "Promemoria":
                    messageField.setDisable(false); // attiviamo il messageField per la scrittura del messsaggio
                    messageLabel.setDisable(false); // attiviamo il messageLabel
                    audioLabel.setDisable(true); // disattiviamo l'audioLabel
                    buttonAudio.setDisable(true); // disattiviamo il buttonAudio per la selezione del file audio
                    break;
            };
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}