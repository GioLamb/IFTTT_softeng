import javafx.application.Platform;

import java.time.LocalDateTime;
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
                    if (rule.getState().get()) {
                        if (rule.getTrigger() instanceof TriggerTime) { // preleviamo il trigger
                            //caso in cui la regola che può essere eseguita una sola volta ed il trigger è verificato
                            if ((((TriggerTime) rule.getTrigger()).isTimeToTrigger(LocalTime.now())) && (rule.getOneTime())) {
                                // eseguiamo l'azione annessa
                                rule.getAction().execute();
                                rule.changeState();
                            }
                            //Caso in cui la regola può essere rieseguita dopo un periodo di sleep.
                            //Se il trigger è attivo e la regola può essere eseguita(prima volta)
                            //oppure può essere eseguita nuovamente(casi successivi alla prima esecuzione) dopo un periodo di sleep,
                            //allora viene eseguita
                            else if (((((TriggerTime) rule.getTrigger()).isTimeToTrigger(LocalTime.now())) && rule.getRecurrent()) && ((rule.getRepeat()))) {
                                rule.getAction().execute(); // eseguiamo l'azione annessa
                                rule.setNow(LocalDateTime.now());
                                //calcoliamo dopo quanto tempo la regola può essere eseguita nuovamente
                                rule.setNowPlusSleep(LocalDateTime.now().plusDays(rule.getSleepDays()).plusHours(rule.getSleepHours()).plusMinutes(rule.getSleepMinutes()));
                                rule.setRepeat(false);
                            }
                            //Caso in cui la regola può essere rieseguita dopo un periodo di sleep.
                            //Questo è il caso in cui bisogna anche verificare, oltre al trigger, se è passato il periodo di sleep
                            //dall'ultima esecuzione della regola
                            else if (rule.getRecurrent() && !(rule.getRepeat())) {
                                rule.setNow(LocalDateTime.now());
                                //se è passato il periodo di sleep e il trigger è attivo, viene eseguita l'azione
                                if (rule.getNow().isAfter(rule.getNowPlusSleep()) && (((TriggerTime) rule.getTrigger()).isTimeToTrigger(LocalTime.now()))) {
                                    rule.getAction().execute();
                                    rule.setNowPlusSleep(LocalDateTime.now().plusDays(rule.getSleepDays()).plusHours(rule.getSleepHours()).plusMinutes(rule.getSleepMinutes()));
                                    //se è passato il periodo di sleep ma il trigger non è attivo, allora impostiamo
                                    //che la regola può essere eseguita nuovamente
                                }else  if (rule.getNow().isAfter(rule.getNowPlusSleep())) {
                                    rule.setRepeat(true);
                                }
                            }
                        }
                        if (rule.getTrigger() instanceof TriggerDayOfWeek) { // preleviamo il trigger
                            //caso in cui la regola che può essere eseguita una sola volta ed il trigger è verificato
                            if ((((TriggerDayOfWeek) rule.getTrigger()).isTimeToTrigger(LocalDateTime.now())) && (rule.getOneTime())) {
                                // eseguiamo l'azione annessa
                                rule.getAction().execute();
                                rule.changeState();
                            }
                            //Caso in cui la regola può essere rieseguita dopo un periodo di sleep.
                            //Se il trigger è attivo e la regola può essere eseguita(prima volta)
                            //oppure può essere eseguita nuovamente(casi successivi alla prima esecuzione) dopo un periodo di sleep,
                            //allora viene eseguita
                            else if (((((TriggerDayOfWeek) rule.getTrigger()).isTimeToTrigger(LocalDateTime.now())) && rule.getRecurrent()) && ((rule.getRepeat()))) {
                                rule.getAction().execute(); // eseguiamo l'azione annessa
                                rule.setNow(LocalDateTime.now());
                                //calcoliamo dopo quanto tempo la regola può essere eseguita nuovamente
                                rule.setNowPlusSleep(LocalDateTime.now().plusDays(rule.getSleepDays()).plusHours(rule.getSleepHours()).plusMinutes(rule.getSleepMinutes()));
                                rule.setRepeat(false);
                            }
                            //Caso in cui la regola può essere rieseguita dopo un periodo di sleep.
                            //Questo è il caso in cui bisogna anche verificare, oltre al trigger, se è passato il periodo di sleep
                            //dall'ultima esecuzione della regola
                            else if (rule.getRecurrent() && !(rule.getRepeat())) {
                                rule.setNow(LocalDateTime.now());
                                //se è passato il periodo di sleep e il trigger è attivo, viene eseguita l'azione
                                if (rule.getNow().isAfter(rule.getNowPlusSleep()) && (((TriggerDayOfWeek) rule.getTrigger()).isTimeToTrigger(LocalDateTime.now()))) {
                                    rule.getAction().execute();
                                    rule.setNowPlusSleep(LocalDateTime.now().plusDays(rule.getSleepDays()).plusHours(rule.getSleepHours()).plusMinutes(rule.getSleepMinutes()));
                                    //se è passato il periodo di sleep ma il trigger non è attivo, allora impostiamo
                                    //che la regola può essere eseguita nuovamente
                                }else  if (rule.getNow().isAfter(rule.getNowPlusSleep())) {
                                    rule.setRepeat(true);
                                }
                            }
                        }
                        if (rule.getTrigger() instanceof TriggerDayOfMonth) { // preleviamo il trigger
                            //caso in cui la regola che può essere eseguita una sola volta ed il trigger è verificato
                            if ((((TriggerDayOfMonth) rule.getTrigger()).isTimeToTrigger(LocalDateTime.now())) && (rule.getOneTime())) {
                                // eseguiamo l'azione annessa
                                rule.getAction().execute();
                                rule.changeState();
                            }
                            //Caso in cui la regola può essere rieseguita dopo un periodo di sleep.
                            //Se il trigger è attivo e la regola può essere eseguita(prima volta)
                            //oppure può essere eseguita nuovamente(casi successivi alla prima esecuzione) dopo un periodo di sleep,
                            //allora viene eseguita
                            else if (((((TriggerDayOfMonth) rule.getTrigger()).isTimeToTrigger(LocalDateTime.now())) && rule.getRecurrent()) && ((rule.getRepeat()))) {
                                rule.getAction().execute(); // eseguiamo l'azione annessa
                                rule.setNow(LocalDateTime.now());
                                //calcoliamo dopo quanto tempo la regola può essere eseguita nuovamente
                                rule.setNowPlusSleep(LocalDateTime.now().plusDays(rule.getSleepDays()).plusHours(rule.getSleepHours()).plusMinutes(rule.getSleepMinutes()));
                                rule.setRepeat(false);
                            }
                            //Caso in cui la regola può essere rieseguita dopo un periodo di sleep.
                            //Questo è il caso in cui bisogna anche verificare, oltre al trigger, se è passato il periodo di sleep
                            //dall'ultima esecuzione della regola
                            else if (rule.getRecurrent() && !(rule.getRepeat())) {
                                rule.setNow(LocalDateTime.now());
                                //se è passato il periodo di sleep e il trigger è attivo, viene eseguita l'azione
                                if (rule.getNow().isAfter(rule.getNowPlusSleep()) && (((TriggerDayOfMonth) rule.getTrigger()).isTimeToTrigger(LocalDateTime.now()))) {
                                    rule.getAction().execute();
                                    rule.setNowPlusSleep(LocalDateTime.now().plusDays(rule.getSleepDays()).plusHours(rule.getSleepHours()).plusMinutes(rule.getSleepMinutes()));
                                    //se è passato il periodo di sleep ma il trigger non è attivo, allora impostiamo
                                    //che la regola può essere eseguita nuovamente
                                }else  if (rule.getNow().isAfter(rule.getNowPlusSleep())) {
                                    rule.setRepeat(true);
                                }
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