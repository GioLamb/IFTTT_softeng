import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainpage.fxml")));
        Scene scene1 = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("IFTTT_softeng");
        stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("icon.png")));
        stage.setScene(scene1);
        stage.show();
    }
}
