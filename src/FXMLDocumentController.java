import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Objects;


public class FXMLDocumentController extends Application{
    public TableColumn<Rule, String> ruleNameView = new TableColumn<>("Nome Regola");
    public TableColumn<Rule, String> actionView = new TableColumn<>("Nome Azione");
    public TableColumn<Rule, String> actionContentView = new TableColumn<>("Contenuto Azione");
    public TableColumn<Rule, String> triggerView = new TableColumn<>("Nome Trigger");
    public TableColumn<Rule, String> triggerContentView = new TableColumn<>("Contenuto Trigger");
    public TableColumn<Rule, Boolean> stateView = new TableColumn<>("Stato");
    public Button deleteButton;
    ContextMenu contextMenu = new ContextMenu();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableView<Rule> tableView = new TableView<>();

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
    private String content;
    private LocalTime time = LocalTime.of(0, 0);
    private Rule selectedRuleToDelete;

    private final RuleManager rm = RuleManager.getInstance();


    public static void main(String[] args) {
        launch(args);
    }


    @FXML
    public void initialize() { // durante l'inizializzazione del controller creiamo la tableview per visualizzare i dati
        tableView.setItems(rm.getRules());
        ruleNameView.setCellValueFactory(cellData -> cellData.getValue().getNameRule());
        actionView.setCellValueFactory(cellData -> cellData.getValue().getNameAction());
        actionContentView.setCellValueFactory(cellData -> cellData.getValue().getActionContent());
        triggerView.setCellValueFactory(cellData -> cellData.getValue().getNameTrigger());
        triggerContentView.setCellValueFactory(cellData -> cellData.getValue().getTriggerContent());
        stateView.setCellValueFactory(cellData -> cellData.getValue().getState());
        tableView.getColumns().addAll(ruleNameView, actionView, actionContentView, triggerView, triggerContentView, stateView);

        MenuItem active = new MenuItem("Attiva");
        MenuItem deactive = new MenuItem("Disattiva");
        active.setOnAction(event -> {
            Rule selectedData = tableView.getSelectionModel().getSelectedItem();
            selectedData.setState(true);
        });

        deactive.setOnAction(event -> {
            Rule selectedData = tableView.getSelectionModel().getSelectedItem();
            selectedData.setState(false);
        });
        contextMenu.getItems().addAll(active,deactive);

        tableView.setRowFactory(tv -> {
            TableRow<Rule> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    Rule selecctedRule = row.getItem();
                }
                if (!row.isEmpty() && event.getButton() == javafx.scene.input.MouseButton.SECONDARY) {
                    contextMenu.show(tableView, event.getScreenX(), event.getScreenY());
                }
            });
            return row;
        });
        //Listener-Handler utilizzata per poter abilitare il bottone di cancellazione della regola
        //quando viene fatto un click sinistro su una regola da voler cancellare
        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(event.getButton() == MouseButton.PRIMARY) {
                //Abilita il deleteButton quando si effettua un click sinistro
                //su una regola da voler cancellare
                deleteButton.setDisable(false);
            } else {
                //Altrimenti lo stesso bottone rimane disabilitato
                deleteButton.setDisable(true);
            }
        });
    }

    @Override // metodo per l'avvio del programma
    public void start(Stage stage) throws Exception {
        // carichiamo il documento FXML da cui iniziare e impostiamo la scena
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainpage.fxml")));
        Scene scene1 = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("IFTTT_softeng");
        stage.getIcons().add(new Image(Objects.requireNonNull(FXMLDocumentController.class.getResourceAsStream("icon.png"))));
        stage.setScene(scene1);
        stage.show();
    }

    @Override
    public void stop() throws Exception { // metodo per fermare il thread per il controllo alla chiusura del programma
        rm.getCheck().stopThread();
        super.stop();
    }

    // Questo metodo ci permette di poter cambiare la scena caricando un diverso file FXML
    public void switchRuleMenu(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ruleselector.fxml"))); // carichiamo l'FXML della sezione per creare le regole
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.scene = new Scene(this.root);
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    // Questo metodo ci permette di poter cambiare la scena caricando un diverso file FXML
    public void switchMainPage(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainpage.fxml"))); // carichiamo l'FXML della mainpage
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.scene = new Scene(this.root);
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    // Metodo che ci permette di scegliere un file audio
    @FXML
    void selectAudio() {
        FileChooser fileChooser = new FileChooser();
        // impostiamo al FileChooser dei filtri in modo tale da essere sicuri di scegliere un file corretto.
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Audio files (*.mp3, wav)", "*.mp3", "*.wav");
        fileChooser.getExtensionFilters().add(filter);

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            this.buttonAudio.setText(file.getAbsolutePath()); // cambiamo il testo del bottone con il path del file audio
            this.content = file.getAbsolutePath();
        }
    }

    // metodo per inviare tutti i dati raccolti in input al RuleManager
    @FXML
    void submit(ActionEvent event) {
        if(hourSelector.getCharacters().isEmpty() || minutesSelector.getCharacters().isEmpty()){
            Alert a = new Alert(Alert.AlertType.ERROR, "Attenzione si è verificato un errore, controlla di aver riempito tutti campi necessari");
            a.show();
            return;
        }
        int hours = Integer.parseInt(this.hourSelector.getCharacters().toString()); // convertiamo i valori in interi
        int minutes = Integer.parseInt(this.minutesSelector.getCharacters().toString());
        if (hours >= 0 && hours < 24 && minutes >= 0 && minutes < 60) { // eseguiamo un controllo per verificare la correttezza delle ore e dei minuti
            this.time = LocalTime.of(hours, minutes); // creiamo l'oggetto LocalTime da associare alla azione
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "L'orario inserito non è corretto.\n" + // inviamo un alert di errore
                    "Le ore devono essere comprese tra 0 e 23\n" +
                    "I minuti devono essere compresi tra 0 e 59.\n Riprovare");
            a.show();
            return;
        }

        if (this.actionSelector.getSelectionModel().isEmpty()) { // verifichiamo che il selettore non sia vuoto
            Alert a = new Alert(Alert.AlertType.ERROR, "Non è stata inserita alcuna azione. Si prega di selezionarne una.");// inviamo un alert di errore
            a.show();
            return;
        }

        if (!this.buttonAudio.isDisable() && this.buttonAudio.textProperty().getValue().equals("Seleziona file audio")) { // verifichiamo che il filechooser non sia vuoto
            Alert a = new Alert(Alert.AlertType.ERROR, "Non è stata inserita alcuna traccia audio. Si prega di selezionarne una.");// inviamo un alert di errore
            a.show();
            return;
        }

        if (this.actionSelector.getValue().toString().equals("Promemoria")) { // verifichiamo che sia selezionato il promemoria
            this.content = this.messageField.getText();
        }
        RuleManager rm = RuleManager.getInstance(); // accediamo al RuleManager e aggiungiamo la nuova regola
        rm.addRule("Regola #" + (rm.getRules().size() + 1), this.actionSelector.getValue().toString(), "TriggerTime", this.content, this.time);
        cancel(event);
    }

    // metodo per accedere alla sezione di creazione delle regole
    @FXML
    void newRule(ActionEvent event) {
        try{
            switchRuleMenu(event);
        }catch (Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR, e.toString());
            a.show();
        }
    }

    // Metodo per ritornare alla mainpage
    @FXML
    void cancel(ActionEvent event) {
        try {
            switchMainPage(event);
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR, e.toString());
            a.show();
        }
    }

    //Metodo per la cancellazione di una regola inserita precedentemente
    @FXML
    public void deleteRule(ActionEvent actionEvent) {
        //Verifica se una regola è stata selezionata
        Rule selectedData = tableView.getSelectionModel().getSelectedItem();
        if (selectedData != null) {
            selectedRuleToDelete = selectedData;
            //Chiama il metodo delete con la regola selezionata
            delete(selectedRuleToDelete);
            //Rimuovi la regola dalla TableView
            tableView.getItems().remove(selectedRuleToDelete);
            //Resetta la variabile
            selectedRuleToDelete = null;
        }
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
            Alert a = new Alert(Alert.AlertType.ERROR, e.toString());
            a.show();
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
            Alert a = new Alert(Alert.AlertType.ERROR, e.toString());
            a.show();
        }
    }

    // metodo per cambiare dinamicamente i campi in base alla azione selezionata
    public void changeContentAction() {
        try {
            switch (actionSelector.getValue().toString()) {
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
            }
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR, e.toString());
            a.show();
        }
    }

    //Metodo per l'eliminazione della regola
    public void delete(Rule selectedRuleToDelete) {
        //Rimuovi la regola dal RuleManager
        rm.removeRule(selectedRuleToDelete);
        //Aggiorna la TableView dopo l'eliminazione
        Platform.runLater(() -> tableView.refresh());
    }

    public Scene getScene() {
        return scene;
    }

    public TableView<Rule> getTableView() {
        return tableView;
    }

    public ComboBox getActionSelector() {
        return actionSelector;
    }

    public TextField getHourSelector() {
        return hourSelector;
    }

    public TextField getMinutesSelector() {
        return minutesSelector;
    }

    public String getContent() {
        return content;
    }

    public LocalTime getTime() {
        return time;
    }

    public Object getSelectedRuleToDelete() {
        return selectedRuleToDelete;
    }
}