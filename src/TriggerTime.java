import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

//Definizione della classe TriggerTime che estende FactoryTrigger e implementa Trigger
public class TriggerTime extends FactoryTrigger implements Trigger {
    //Variabili d'istanza private per memorizzare l'orario del trigger e lo stato del trigger
    private LocalTime triggerTime;
    private boolean isTriggered;

    //Costruttore della classe TriggerTime che accetta un orario come parametro
    public TriggerTime(LocalTime time) {
        //Chiama il costruttore della classe madre (FactoryTrigger) con il nome del trigger e l'orario
        triggerTime = time;
        //Inizializza la variabile d'istanza isTriggered a false
        this.isTriggered = false;
    }

    // Metodo per verificare se è il momento di attivare il trigger in base all'orario corrente
    // Confronta l'orario corrente con l'orario del trigger
    // Restituisce true se è il momento di attivare il trigger
    public boolean isTimeToTrigger(LocalTime currentTime) {
        // Restituisce true se l'orario corrente è dopo l'orario di trigger
        // OPPURE se l'orario corrente è uguale all'orario di trigger.
        return currentTime.isAfter(triggerTime) || currentTime.equals(triggerTime);
    }


    // Implementazione del metodo execute dell'interfaccia Trigger
    @Override
    public void execute() {
        // Ciclo while che continua finché il trigger non è attivato
        while (!this.isTriggered) {
            // Ottiene l'orario corrente
            LocalTime currentTime = LocalTime.now();
            // Verifica se è il momento di attivare il trigger e se non è già stato attivato
            if (isTimeToTrigger(currentTime.truncatedTo(ChronoUnit.MINUTES)) && !isTriggered) {
                // Sincronizza l'accesso a isTriggered per evitare race condition
                synchronized (this) {
                    if (!this.isTriggered) {
                        // Imposta lo stato del trigger su attivato
                        this.isTriggered = true;
                    }
                }
            }
            // Attende 1000 millisecondi prima di eseguire la prossima iterazione
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Gestisce l'eccezione d'interruzione del thread
                e.printStackTrace();
            }
        }
    }

    public LocalTime getTime() {
        return triggerTime;
    }
}
