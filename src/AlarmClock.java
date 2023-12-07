import javafx.stage.Stage;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AlarmClock extends FactoryAction implements Action{
    private Stage dialogStage;
    private Clip clip;
    private String audioFilePath;
    public AlarmClock(String audioFilePath) {
        this.audioFilePath=audioFilePath;
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(audioFilePath));
            //clip per riprodurre audioInputStream
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            dialogStage = new Stage();
            dialogStage.setTitle("Riproduzione Audio");

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void execute() {
        dialogStage.show();
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

    public Stage getDialogStage(){
        return dialogStage;
    }
}