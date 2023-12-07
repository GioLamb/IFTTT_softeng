import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Scanner;


public class FXMLDocumentController extends Application {
    private final String filepath = System.getProperty("user.dir");
    public Label labelHour = new Label();
    public Label labelMinutes = new Label();
    private final File file = new File(filepath, "rules.txt");
    public Button fileButton = new Button();
    public TextField messageField2 = new TextField();
    public ComboBox comboWeek = new ComboBox<>();
    public ComboBox comboMonth = new ComboBox();
    public Button fileButton2 = new Button();
    private String nameTrigger;
    private String nameAction;
    public TableColumn<Rule, String> ruleNameView = new TableColumn<>("Nome Regola");
    public TableColumn<Rule, String> actionView = new TableColumn<>("Nome Azione");
    public TableColumn<Rule, String> actionContentView = new TableColumn<>("Contenuto Azione");
    public TableColumn<Rule, String> triggerView = new TableColumn<>("Nome Trigger");
    public TableColumn<Rule, String> triggerContentView = new TableColumn<>("Contenuto Trigger");
    public TableColumn<Rule, Boolean> stateView = new TableColumn<>("Stato");
    public TableColumn<Rule, String> plusSleepView = new TableColumn<>("Nuova ripetizione");
    public ContextMenu contextMenu = new ContextMenu();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableView<Rule> tableView = new TableView<>();

    @FXML
    private ComboBox actionSelector = new ComboBox<>();

    @FXML
    private Button buttonAudio = new Button();

    @FXML
    private TextField hourSelector = new TextField();

    @FXML
    private TextField messageField = new TextField();

    @FXML
    private TextField minutesSelector = new TextField();
    @FXML
    private CheckBox oneTimeSelector;

    @FXML
    private CheckBox recurrentSelector;

    @FXML
    private TextField sleepDaySelector = new TextField();

    @FXML
    private TextField sleepHourSelector = new TextField();

    @FXML
    private TextField sleepMinuteSelector = new TextField();

    @FXML
    private Label labelSleepDay;
    @FXML
    private Label labelSleepHour;
    @FXML
    private Label labelSleepMinute;
    @FXML
    Button deleteButton = new Button();
    @FXML
    private ComboBox triggerSelector = new ComboBox<>();
    @FXML
    private Button submitButton = new Button();
    @FXML
    private TextArea messageArea;

    private int sleepDays;
    private int sleepHours;
    private int sleepMinutes;
    private int hours;
    private int minutes;
    private String content;
    private String content2 = null;
    private Integer content3 = null;
    private LocalTime time = LocalTime.of(0, 0);
    private Rule selectedRuleToDelete;
    private Boolean repeat;
    private LocalDateTime nowPlusSleep;

    private final RuleManager rm = RuleManager.getInstance();


    public static void main(String[] args) {
        launch(args);
    }


    @FXML
    public void initialize() {
        // durante l'inizializzazione del controller creiamo la tableview per visualizzare i dati
        tableView.setItems(rm.getRules());
        ruleNameView.setCellValueFactory(cellData -> cellData.getValue().getNameRule());
        actionView.setCellValueFactory(cellData -> cellData.getValue().getNameAction());
        actionContentView.setCellValueFactory(cellData -> cellData.getValue().getActionContent());
        triggerView.setCellValueFactory(cellData -> cellData.getValue().getNameTrigger());
        triggerContentView.setCellValueFactory(cellData -> cellData.getValue().getTriggerContent());
        stateView.setCellValueFactory(cellData -> cellData.getValue().getState());
        plusSleepView.setCellValueFactory(cellData -> cellData.getValue().getNowPlusSleepFormat());
        tableView.getColumns().addAll(ruleNameView, actionView, actionContentView, triggerView, triggerContentView, stateView, plusSleepView);
        // impostiamo le scelte da poter effettuare con il tasto sinistro
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
        contextMenu.getItems().addAll(active, deactive);

        tableView.setRowFactory(tv -> {
            TableRow<Rule> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
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
            if (event.getButton() == MouseButton.PRIMARY) {
                //Abilita il deleteButton quando si effettua un click sinistro
                //su una regola da voler cancellare
                deleteButton.setDisable(false);
            } else {
                //Altrimenti lo stesso bottone rimane disabilitato
                deleteButton.setDisable(true);
            }
        });

        try { // creiamo dei bind per poter cambiare in maniera dinamica la visualizzazione degli elementi nella selezione delle regole
            BooleanBinding isDisplayMessageSelected = Bindings.createBooleanBinding(
                    () -> "Promemoria".equals(actionSelector.getValue()),
                    actionSelector.valueProperty()
            );
            BooleanBinding isClockSelected = Bindings.createBooleanBinding(
                    () -> "Sveglia".equals(this.actionSelector.getValue()),
                    this.actionSelector.valueProperty()
            );
            BooleanBinding isFileSelected = Bindings.createBooleanBinding(
                    () -> "Scrivi in un file".equals(this.actionSelector.getValue()),
                    this.actionSelector.valueProperty()
            );
            BooleanBinding isCopySelected = Bindings.createBooleanBinding(
                    () -> "Copia un file".equals(this.actionSelector.getValue()),
                    this.actionSelector.valueProperty()
            );
            BooleanBinding isMoveSelected = Bindings.createBooleanBinding(
                    () -> "Sposta un file".equals(this.actionSelector.getValue()),
                    this.actionSelector.valueProperty()
            );
            BooleanBinding isDeleteSelected = Bindings.createBooleanBinding(
                    () -> "Elimina un file".equals(this.actionSelector.getValue()),
                    this.actionSelector.valueProperty()
            );

            BooleanBinding isTriggerTimeSelected = Bindings.createBooleanBinding(
                    () -> "Orario della giornata".equals(this.triggerSelector.getValue()),
                    this.triggerSelector.valueProperty()
            );
            BooleanBinding isTriggerMonthSelected = Bindings.createBooleanBinding(
                    () -> "Giorno del mese".equals(this.triggerSelector.getValue()),
                    this.triggerSelector.valueProperty()
            );
            BooleanBinding isTriggerWeekSelected = Bindings.createBooleanBinding(
                    () -> "Giorno della settimana".equals(this.triggerSelector.getValue()),
                    this.triggerSelector.valueProperty()
            );
            // colleghiamo i bind agli elementi
            this.messageField.visibleProperty().bind(isDisplayMessageSelected);
            this.fileButton.visibleProperty().bind(isClockSelected.or(isFileSelected.or(isMoveSelected.or(isCopySelected.or(isDeleteSelected).or(isClockSelected)))));
            this.fileButton2.visibleProperty().bind(isMoveSelected.or(isCopySelected));
            this.hourSelector.visibleProperty().bind(isTriggerTimeSelected);
            this.minutesSelector.visibleProperty().bind(isTriggerTimeSelected);
            this.labelHour.visibleProperty().bind(isTriggerTimeSelected);
            this.labelMinutes.visibleProperty().bind(isTriggerTimeSelected);
            this.comboWeek.visibleProperty().bind(isTriggerWeekSelected);
            this.comboMonth.visibleProperty().bind(isTriggerMonthSelected);
            actionSelector.valueProperty().addListener((observable, oldValue, newValue) -> {
                // Aggiorna il testo del bottone in base alla selezione
                messageArea.setVisible(false);
                fileButton2.setText("Seleziona una cartella");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        if (file.createNewFile()) {
            return;
        }
        read();
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
    void selectFile() {
        FileChooser fileChooser = new FileChooser();
        switch (actionSelector.getValue().toString()){
            case "Sveglia":
                // impostiamo al FileChooser dei filtri in modo tale da essere sicuri di scegliere un file corretto.
                FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Audio files (*.wav)", "*.wav");
                fileChooser.getExtensionFilters().add(filter);

                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    this.fileButton.setText(file.getAbsolutePath()); // cambiamo il testo del bottone con il path del file
                    this.content = file.getAbsolutePath();
                }
                break;
            case "Scrivi in un file":
            case "Sposta un file":
            case "Copia un file":
            case "Elimina un file":
                FileChooser.ExtensionFilter filter1 = new FileChooser.ExtensionFilter("Text files(*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(filter1);

                File file1 = fileChooser.showOpenDialog(stage);
                if (file1 != null) {
                    this.fileButton.setText(file1.getAbsolutePath()); // cambiamo il testo del bottone con il path del file
                    this.content = file1.getAbsolutePath();
                }
                break;
        }
        messageArea.setVisible(true);
    }

    @FXML
    void selectFile2() {
        switch (actionSelector.getValue().toString()){
            case "Sposta un file":
            case "Copia un file":
                DirectoryChooser directoryChooser = new DirectoryChooser();
                java.io.File selectedDirectory = directoryChooser.showDialog(stage);
                if (selectedDirectory != null) {
                    this.fileButton2.setText(selectedDirectory.getAbsolutePath()); // cambiamo il testo del bottone con il path del file
                    this.content2 = selectedDirectory.getAbsolutePath();
                }
                break;
        }
    }
    // metodo per inviare tutti i dati raccolti in input al RuleManager
    @FXML
    void submit(ActionEvent event) {
        try {

            if (this.actionSelector.getSelectionModel().isEmpty()) { // verifichiamo che il selettore non sia vuoto
                Alert a = new Alert(Alert.AlertType.ERROR, "Non è stata inserita alcuna azione. Si prega di selezionarne una.");// inviamo un alert di errore
                a.show();
                return;
            }

            if (this.triggerSelector.getSelectionModel().isEmpty()) { // verifichiamo che il selettore non sia vuoto
                Alert a = new Alert(Alert.AlertType.ERROR, "Non è stato inserito alcun Trigger. Si prega di selezionarne uno.");// inviamo un alert di errore
                a.show();
                return;
            }

            switch (actionSelector.getValue().toString()) {
                case "Promemoria":
                    content = messageField.getText().toString();
                    nameAction = "Promemoria";
                    break;
                case "Sveglia":
                    nameAction = "Sveglia";
                    if (this.fileButton.isVisible() && this.fileButton.textProperty().getValue().equals("Seleziona un file")) { // verifichiamo che il filechooser non sia vuoto
                        Alert a = new Alert(Alert.AlertType.ERROR, "Non è stata inserita alcuna traccia audio. Si prega di selezionarne una.");// inviamo un alert di errore
                        a.show();
                        return;
                    }
                    content = fileButton.textProperty().getValue();
                    break;
                case "Scrivi in un file":
                    nameAction="Scrittura su File";
                    if (this.fileButton.isVisible() && this.fileButton.textProperty().getValue().equals("Seleziona un file")) { // verifichiamo che il filechooser non sia vuoto
                        Alert a = new Alert(Alert.AlertType.ERROR, "Non è stata inserito alcun file. Si prega di selezionarne uno.");// inviamo un alert di errore
                        a.show();
                        return;
                    }
                    content = messageArea.getText();
                    this.messageArea.setVisible(true);
                    WriteToFileAction writeToFileAction = new WriteToFileAction(fileButton.textProperty().getValue(), content);
                    writeToFileAction.execute();
                    this.messageArea.setVisible(false);
                    break;
                case "Elimina un file":
                    nameAction = "Elimina un file";
                    if (this.fileButton.isVisible() && this.fileButton.textProperty().getValue().equals("Seleziona un file")) { // verifichiamo che il filechooser non sia vuoto
                        Alert a = new Alert(Alert.AlertType.ERROR, "Non è stata inserito alcun file. Si prega di selezionarne uno.");// inviamo un alert di errore
                        a.show();
                        return;
                    }
                    content = fileButton.textProperty().getValue();
                    break;
                case "Copia un file":
                    nameAction = "Copia un file";
                    if (this.fileButton.isVisible() && this.fileButton.textProperty().getValue().equals("Seleziona un file")) { // verifichiamo che il filechooser non sia vuoto
                        Alert a = new Alert(Alert.AlertType.ERROR, "Non è stata inserito alcun file. Si prega di selezionarne uno.");// inviamo un alert di errore
                        a.show();
                        return;
                    }
                    if (this.fileButton2.isVisible() && this.fileButton2.textProperty().getValue().equals("Seleziona una cartella")) { // verifichiamo che il directorychooser non sia vuoto
                        Alert a = new Alert(Alert.AlertType.ERROR, "Non è stata inserito alcuna cartella. Si prega di selezionarne una.");// inviamo un alert di errore
                        a.show();
                        return;
                    }
                    content = fileButton.textProperty().getValue().toString(); // path del file
                    content2 = fileButton2.textProperty().getValue().toString(); // path della cartella
                    break;
                case "Sposta un file":
                    nameAction = "Sposta un file";
                    if (this.fileButton.isVisible() && this.fileButton.textProperty().getValue().equals("Seleziona un file")) { // verifichiamo che il filechooser non sia vuoto
                        Alert a = new Alert(Alert.AlertType.ERROR, "Non è stata inserito alcun file. Si prega di selezionarne uno.");// inviamo un alert di errore
                        a.show();
                        return;
                    }
                    if (this.fileButton2.isVisible() && this.fileButton2.textProperty().getValue().equals("Seleziona una cartella")) { // verifichiamo che il directorychooser non sia vuoto
                        Alert a = new Alert(Alert.AlertType.ERROR, "Non è stata inserito alcuna cartella. Si prega di selezionarne una.");// inviamo un alert di errore
                        a.show();
                        return;
                    }
                    content = fileButton.textProperty().getValue().toString(); // path del file
                    content2 = fileButton2.textProperty().getValue().toString(); // path della cartella

                    break;
            }

            switch (triggerSelector.getValue().toString()) {
                case "Orario della giornata":
                    nameTrigger = "TriggerTime";
                    if (this.hourSelector.getCharacters().isEmpty() || this.minutesSelector.getCharacters().isEmpty()) {
                        Alert a = new Alert(Alert.AlertType.ERROR, "Attenzione si è verificato un errore, controlla di aver riempito tutti campi necessari");
                        a.show();
                        return;
                    }
                    hours = Integer.parseInt(this.hourSelector.getCharacters().toString()); // convertiamo i valori in interi
                    minutes = Integer.parseInt(this.minutesSelector.getCharacters().toString());
                    if (hours >= 0 && hours < 24 && minutes >= 0 && minutes < 60) { // eseguiamo un controllo per verificare la correttezza delle ore e dei minuti
                        this.time = LocalTime.of(hours, minutes); // creiamo l'oggetto LocalTime da associare alla azione
                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR, "L'orario inserito non è corretto.\n" + // inviamo un alert di errore
                                "Le ore devono essere comprese tra 0 e 23\n" +
                                "I minuti devono essere compresi tra 0 e 59.\n Riprovare");
                        a.show();
                        return;
                    }
                    break;

                case "Giorno della settimana":
                    nameTrigger = "TriggerDayOfWeek";
                    switch (comboWeek.getValue().toString()) {
                        case "Domenica":
                            content3 = 1;
                            break;
                        case "Lunedì":
                            content3 = 2;
                            break;
                        case "Martedì":
                            content3 = 3;
                            break;
                        case "Mercoledì":
                            content3 = 4;
                            break;
                        case "Giovedì":
                            content3 = 5;
                            break;
                        case "Venerdì":
                            content3 = 6;
                            break;
                        case "Sabato":
                            content3 = 7;
                            break;
                    }
                    break;

                case "Giorno del mese":
                    nameTrigger = "TriggerDayOfMonth";
                    content3 = Integer.parseInt(comboMonth.getValue().toString());
                    break;
            }
                    if (!(this.oneTimeSelector.isSelected() || this.recurrentSelector.isSelected())) {
                        Alert a = new Alert(Alert.AlertType.ERROR, "Scegliere quante volte la regola deve essere riattivata");
                        a.show();
                        return;
                    }

                    if (this.recurrentSelector.isSelected()) {
                        if (this.sleepDaySelector.getText().isEmpty()) {
                            Alert a = new Alert(Alert.AlertType.ERROR, "Inserire un valore nel campo 'Giorni' ");// inviamo un alert di errore
                            a.show();
                            return;
                        } else if (this.sleepDaySelector.getText().charAt(0) == 'G') {
                            Alert a = new Alert(Alert.AlertType.ERROR, "Inserire un valore nel campo 'Giorni' ");// inviamo un alert di errore
                            a.show();
                            return;
                        } else if (this.sleepHourSelector.getText().isEmpty()) {
                            Alert a = new Alert(Alert.AlertType.ERROR, "Inserire un valore nel campo 'Ore' ");// inviamo un alert di errore
                            a.show();
                            return;
                        } else if (this.sleepHourSelector.getText().charAt(0) == 'O') {
                            Alert a = new Alert(Alert.AlertType.ERROR, "Inserire un valore nel campo 'Ore' ");// inviamo un alert di errore
                            a.show();
                            return;
                        } else if (this.sleepMinuteSelector.getText().isEmpty()) {
                            Alert a = new Alert(Alert.AlertType.ERROR, "Inserire un valore nel campo 'Minuti' ");// inviamo un alert di errore
                            a.show();
                            return;
                        } else if (this.sleepMinuteSelector.getText().charAt(0) == 'M') {
                            Alert a = new Alert(Alert.AlertType.ERROR, "Inserire un valore nel campo 'Minuti' ");// inviamo un alert di errore
                            a.show();
                            return;
                        } else {
                            sleepDays = Integer.parseInt(this.sleepDaySelector.getCharacters().toString());
                            sleepHours = Integer.parseInt(this.sleepHourSelector.getCharacters().toString()); // convertiamo i valori in interi
                            sleepMinutes = Integer.parseInt(this.sleepMinuteSelector.getCharacters().toString());
                            if (sleepHours >= 0 && sleepHours < 24 && sleepMinutes >= 0 && sleepMinutes < 60) { // eseguiamo un controllo per verificare la correttezza delle ore e dei minuti
                            } else {
                                Alert a = new Alert(Alert.AlertType.ERROR, "L'orario inserito non è corretto.\n" + // inviamo un alert di errore
                                        "Le ore devono essere comprese tra 0 e 23\n" +
                                        "I minuti devono essere compresi tra 0 e 59.\n Riprovare");
                                a.show();
                                return;
                            }
                        }
                        this.repeat = true;
                    } else {
                        this.sleepDays = 0;
                        this.sleepHours = 0;
                        this.sleepMinutes = 0;
                        this.repeat = false;
                    }
                    this.nowPlusSleep = LocalDateTime.of(LocalDate.now(), LocalTime.of(hours, minutes)).plusDays(sleepDays).plusHours(sleepHours).plusMinutes(sleepMinutes);
                    RuleManager rm = RuleManager.getInstance(); // accediamo al RuleManager e aggiungiamo la nuova regola
                    rm.addRule("Regola #" + (rm.getRules().size() + 1), nameAction, nameTrigger, this.content, content2, content3, this.time, this.oneTimeSelector.isSelected(), this.sleepDays, this.sleepHours, this.sleepMinutes, this.recurrentSelector.isSelected(), true, this.repeat, this.nowPlusSleep);
                    cancel(event);
            } catch(Exception e){
                e.printStackTrace();
            }
        }

    // metodo per accedere alla sezione di creazione delle regole
    @FXML
    void newRule(ActionEvent event) {
        try {
            switchRuleMenu(event);
        } catch (Exception e) {
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

    //Metodo per l'eliminazione della regola
    public void delete(Rule selectedRuleToDelete) {
        //Rimuovi la regola dal RuleManager
        rm.removeRule(selectedRuleToDelete);
        //Aggiorna la TableView dopo l'eliminazione
        Platform.runLater(() -> tableView.refresh());
    }

    public void oneTimeCheck() {
        if (oneTimeSelector.isSelected()) {
            if (recurrentSelector.isSelected()) {
                recurrentSelector.setSelected(false);
                sleepDaySelector.setVisible(false);
                sleepHourSelector.setVisible(false);
                sleepMinuteSelector.setVisible(false);
                labelSleepDay.setVisible(false);
                labelSleepHour.setVisible(false);
                labelSleepMinute.setVisible(false);
            }
        }
    }

    public void recurrentCheck() {
        if (recurrentSelector.isSelected()) {
            // Se la checkbox è selezionata, mostra i textfield
            sleepDaySelector.setVisible(true);
            sleepHourSelector.setVisible(true);
            sleepMinuteSelector.setVisible(true);
            labelSleepDay.setVisible(true);
            labelSleepHour.setVisible(true);
            labelSleepMinute.setVisible(true);
            if (oneTimeSelector.isSelected()) {
                oneTimeSelector.setSelected(false);
            }
        } else {
            // Altrimenti, nascondi i textfield
            sleepDaySelector.setVisible(false);
            sleepHourSelector.setVisible(false);
            sleepMinuteSelector.setVisible(false);
            labelSleepDay.setVisible(false);
            labelSleepHour.setVisible(false);
            labelSleepMinute.setVisible(false);
            sleepDaySelector.setText("");
            sleepHourSelector.setText("");
            sleepMinuteSelector.setText("");
        }
    }

    public void checkDayNumber(KeyEvent event) {
        try {
            if (!event.getCharacter().matches("[0-9]")) { // se l'input non fa match con un carattere compreso tra [0-9]
                event.consume(); // marchiamo l'evento come consumato
                sleepDaySelector.backward();
                sleepDaySelector.deleteNextChar();
            }
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR, e.toString());
            a.show();
        }
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


    public void write() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Scrivi i dati dall'ObservableList al TXT
            for (Rule item : rm.getRules()) {
                writer.write(item.getNameRule().get() + "\n" + item.getNameAction().get() + "\n" +
                        item.getNameTrigger().get() + "\n" + item.getActionContent().get() + "\n" + item.getActionContent2() +
                        "\n" + item.getTriggerContent().get() +"\n" + item.getContent3() + "\n" + item.getOneTime() +
                        "\n" + item.getSleepDays() + "\n" + item.getSleepHours() + "\n" + item.getSleepMinutes() +
                        "\n" + item.getRecurrent() + "\n" + item.getState().get() + "\n" + item.getRepeat() +
                        "\n" + item.getNowPlusSleep() + "\n\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void read() throws FileNotFoundException {
        Scanner sc = new Scanner(file);
        sc.useDelimiter("\n\n");   //sets the delimiter pattern
        while (sc.hasNext())  //returns a boolean value
        {
            String[] elements = sc.next().split("\n");
            if(elements[6].equals("null")){
                content3 = null;
            }
            else {
                Integer content3 = Integer.parseInt(elements[6]);
            }
            String[] hoursMinutes = elements[5].split(":");
            Integer h = Integer.parseInt(hoursMinutes[0]);
            Integer m = Integer.parseInt(hoursMinutes[1]);
            Integer sd = Integer.parseInt(elements[8]);
            Integer sh = Integer.parseInt(elements[9]);
            Integer sm = Integer.parseInt(elements[10]);
            Boolean oneTime = Boolean.parseBoolean(elements[7]);
            Boolean recurrent = Boolean.parseBoolean(elements[11]);
            Boolean state = Boolean.parseBoolean(elements[12]);
            Boolean repeat = Boolean.parseBoolean(elements[13]);
            nowPlusSleep = LocalDateTime.parse(elements[14]);
            // inseriamo il nuovo elemento
            rm.addRule(elements[0], elements[1], elements[2], elements[3], elements[4], content3, LocalTime.of(h, m), oneTime, sd, sh, sm, recurrent, state, repeat, nowPlusSleep);

        }
        sc.close();  //closes the scanner
    }

    @Override
    public void stop() {
        write();
        System.exit(0);
    }

    public CheckBox getOneTimeSelector() {
        return oneTimeSelector;
    }

    public CheckBox getRecurrentSelector() {
        return recurrentSelector;
    }

    public Node getSleepDaySelector() {
        return sleepDaySelector;
    }

    public Node getSleepHourSelector() {
        return sleepHourSelector;
    }

    public Node getSleepMinuteSelector() {
        return sleepMinuteSelector;
    }

    public Node getLabelSleepDay() {
        return labelSleepDay;
    }

    public Node getLabelSleepHour() {
        return labelSleepHour;
    }

    public Node getLabelSleepMinute() {
        return labelSleepMinute;
    }
}

