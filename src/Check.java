import javafx.application.Platform;

import java.time.LocalTime;
import java.util.Iterator;

public class Check extends Thread {
    private RuleManager rm;
    private Boolean isRunning;
    public Check() {
        isRunning = true;
    }

    public void run() { // metodo che si esegue all'avvio del thread
        while (isRunning) { // fin quando è in corso
            rm = RuleManager.getInstance(); // preleviamo l'istanza di RuleManager

            Platform.runLater(() -> { // questo metodo ci permette di eseguire il contenuto del thread in un tempo futuro
                Iterator<Rule> iterator = rm.getRules().iterator(); // creiamo un iteratore per la collezione di regole
                while (iterator.hasNext()) { // fin quando esiste un oggetto Rule
                    Rule rule = iterator.next(); // lo preleviamo
                    if (rule.getState()) {
                        if (rule.getTrigger() instanceof TriggerTime) { // preleviamo il trigger
                            if ((((TriggerTime) rule.getTrigger()).isTimeToTrigger(LocalTime.now()))) { // controlliamo se il trigger sia attivo
                                Platform.runLater(()->{
                                    rule.getAction().execute(); // eseguiamo l'azione annessa
                                    rule.setState(false); // imposta lo stato della regola appena eseguita su false
                                });
                            }
                        }
                    }
                }
            });

            try {
                // Attendiamo un secondo prima di far ripartire l'esecuzione
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopThread(){
        isRunning = false;
    }

    public Boolean getStatus(){return isRunning;}
}