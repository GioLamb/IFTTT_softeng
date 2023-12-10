import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class TriggerDayOfWeek implements Trigger{
    // Variabili d'istanza per memorizzare il giorno target, e lo stato del trigger
    private DayOfWeek dayOfWeek;
    private boolean isTriggered;

    public TriggerDayOfWeek(int day) {
        this.dayOfWeek = DayOfWeek.of(day);
        this.isTriggered = false;
    }

    //Il metodo isTimeToTrigger, confronta il giorno inserito dall'utente al momento della registrazione della regola
    //con il giorno corrente. Restituisce true se sono uguali, false se invece non sono uguali.
    public boolean isTimeToTrigger(LocalDateTime currentDateTime) {
        // Confronta solo i giorni della settimana senza considerare l'orario
        return currentDateTime.getDayOfWeek().equals(dayOfWeek);
    }

    //execute(), provvede ad effettuare un controllo riguardo alla possibilità che il trigger sia già attivo, altrimenti
    //provvede ad attivarlo verificando prima che sia il giorno adatto (ossia quello richiesto).
    @Override
    public void execute() {
        // Ciclo while che continua finché il trigger non è attivato
        while (!this.isTriggered) {
            // Ottiene il giorno della settimana corrente
            DayOfWeek currentDay = LocalDateTime.now().getDayOfWeek();
            // Verifica se è il giorno di attivare il trigger e se non è già stato attivato
            if (currentDay == dayOfWeek && !isTriggered) {
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


    public boolean isTriggered() {return isTriggered;}
    public DayOfWeek getDayOfWeek() {return dayOfWeek;}
}
