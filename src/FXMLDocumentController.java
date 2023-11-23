import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
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
import java.time.LocalDateTime;
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

    public void switchRuleMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ruleselector.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchMainPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainpage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void selectAudio(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Audio files (*.mp3, wav)","*.mp3", "*.wav");
        fileChooser.getExtensionFilters().add(filter);

        File file = fileChooser.showOpenDialog(stage);
        if (file!=null){
            buttonAudio.setText(file.getAbsolutePath());
        }
    }

    @FXML
    void submit(ActionEvent event) {
        Integer hours = Integer.parseInt(hourSelector.getCharacters().toString());
        Integer minutes = Integer.parseInt(minutesSelector.getCharacters().toString());
        if(hours >= 0 && hours < 24 && minutes >= 0 && minutes < 60){
            LocalTime time = LocalTime.of(hours, minutes, 0);
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

    @FXML
    void newRule(ActionEvent event) {
        try {
            switchRuleMenu(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
    }

    @FXML
    void cancel(ActionEvent event) {
        try {
            switchMainPage(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
    }

    public void checkHours(KeyEvent event) {
        try {
            if (!event.getCharacter().matches("[0-9]")) {
                event.consume();
                hourSelector.backward();
                hourSelector.deleteNextChar();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkMinutes(KeyEvent event) {
        try {
            if (!event.getCharacter().matches("[0-9]")) {
                event.consume();
                minutesSelector.backward();
                minutesSelector.deleteNextChar();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeContentAction(ActionEvent actionEvent) {
        try{
            switch (actionSelector.getValue().toString()){
                case "Sveglia":
                    messageField.setDisable(true);
                    messageLabel.setDisable(true);
                    audioLabel.setDisable(false);
                    buttonAudio.setDisable(false);
                    break;

                case "Promemoria":
                    messageField.setDisable(false);
                    messageLabel.setDisable(false);
                    audioLabel.setDisable(true);
                    buttonAudio.setDisable(true);
                    break;
            };
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}