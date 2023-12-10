import javafx.stage.Stage;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AlarmClock extends FactoryAction implements Action{
    private Stage dialogStage;
    private Clip clip;
    private final String audioFilePath;
    AudioInputStream audioInputStream;
    public AlarmClock(String audioFilePath) {
        this.audioFilePath=audioFilePath;
        dialogStage = new Stage();
        dialogStage.setTitle("Riproduzione Audio");
    }

    @Override
    public void execute() {
        dialogStage.show();
        try {
            this.audioInputStream = AudioSystem.getAudioInputStream(new File(audioFilePath));
            //clip per permettere la riproduzione di audioInputStream
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | NullPointerException | LineUnavailableException | SecurityException | IllegalArgumentException | IllegalStateException e) {
            e.printStackTrace();
        }
        //Avvia la riproduzione continua dell'audio
        clip.loop(Clip.LOOP_CONTINUOUSLY);

        //Riproduzione dell'audio interrotta dalla chiusura
        //della finestra di dialogo da parte dell'utente
        dialogStage.setOnCloseRequest(event -> onActionClose());
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
    @Override
    public String getName() {
        return "Sveglia";
    }

    @Override
    public String getContent1() {
        return audioFilePath;
    }
    @Override
    public String getContent2() {
        return null;
    }
}