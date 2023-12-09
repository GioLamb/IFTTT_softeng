import java.time.LocalDateTime;

public class TriggerDayOfMonth implements Trigger{
    private final LocalDateTime dayOfMonth;
    private boolean isTriggered;

    public TriggerDayOfMonth(LocalDateTime dayM) {
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

