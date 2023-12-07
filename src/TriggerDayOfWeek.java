import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class TriggerDayOfWeek implements Trigger{
    // Variabili d'istanza per memorizzare il giorno target, l'orario di trigger e lo stato del trigger
    //private DayOfWeek targetDay;
    private DayOfWeek dayOfWeek;
    //private LocalTime triggerTime;
    private boolean isTriggered;

    // Costruttore della classe, inizializza le variabili d'istanza con i valori passati come parametri
    public TriggerDayOfWeek(int day) {
        if(day < 1 || day > 7){
            throw new IllegalArgumentException("Giorno non compreso nella settimana: " + day);
        }
        this.dayOfWeek = DayOfWeek.of(day);
        this.isTriggered = false;
    }

    public boolean isTimeToTrigger(LocalDateTime currentDateTime) {
        // Confronta solo i giorni della settimana senza considerare l'orario
        return currentDateTime.getDayOfWeek().equals(dayOfWeek);
    }


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
