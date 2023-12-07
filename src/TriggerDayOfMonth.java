import java.time.LocalDateTime;

public class TriggerDayOfMonth implements Trigger{
    private LocalDateTime dayOfMonth;
    private boolean isTriggered;

    public TriggerDayOfMonth(LocalDateTime dayM) {
        /*
        if (dayOfMonth < 1 || dayOfMonth > 31) {
            throw new IllegalArgumentException("Il giorno del mese deve essere compreso tra 1 e 31.");
        }
        this.dayOfMonth = dayOfMonth;
        */
        dayOfMonth = dayM;
        this.isTriggered = false;
    }

    @Override
    public void execute() {
        while(!Thread.interrupted() && !this.isTriggered) {
            int currentDayOfMonth = LocalDateTime.now().getDayOfMonth();

            if(currentDayOfMonth == dayOfMonth.getDayOfMonth() && !isTriggered) {
                synchronized (this) {
                    if(!this.isTriggered) {
                        this.isTriggered = true;
                    }
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    public boolean isTimeToTrigger(LocalDateTime currentDateTime) {
        // Confronta solo il giorno del mese senza considerare l'orario
        return !isTriggered && currentDateTime.getDayOfMonth() == dayOfMonth.getDayOfMonth();
    }
    */

    public boolean isTimeToTrigger(LocalDateTime currentDateTime) {
        // Confronta solo il giorno del mese senza considerare l'orario
        return !isTriggered && currentDateTime.getDayOfMonth() == dayOfMonth.getDayOfMonth() &&
                !currentDateTime.isBefore(LocalDateTime.now()); // Aggiunto questo controllo
    }

    public boolean isTriggered() {
        return isTriggered;
    }

    public LocalDateTime getDayOfMonth() {return dayOfMonth;}
}

