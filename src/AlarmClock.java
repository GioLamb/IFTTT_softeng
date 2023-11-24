import javafx.stage.Stage;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AlarmClock implements Action{
    private String audioFilePath;
    private Stage dialogStage;
    private Clip clip;

    public AlarmClock(String audioFilePath) {
        this.audioFilePath = audioFilePath;
    }

    @Override
    public void execute() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(audioFilePath));
            //Creo un oggetto clip per riprodurre audioInputStream
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            //Avvia la riproduzione continua dell'audio
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            dialogStage = new Stage();
            dialogStage.setTitle("Riproduzione Audio");
            //Riproduzione dell'audio interrotta dalla chiusura
            //della finestra di dialogo da parte dell'utente
            dialogStage.setOnCloseRequest(event -> onActionClose());
            dialogStage.showAndWait();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActionClose() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
        if (dialogStage != null) {
            dialogStage.close();
        }
    }

}