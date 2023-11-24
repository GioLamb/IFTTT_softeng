import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TriggerTime implements Trigger {
    private LocalTime triggerTime;
    private boolean isTriggered;

    public TriggerTime(LocalTime time) {
        this.triggerTime = time;
        this.isTriggered = false;
    }

    public boolean isTimeToTrigger(LocalTime currentTime) {
        return currentTime.compareTo(triggerTime) >= 0;
    }

    @Override
    public void execute() {
        while (!this.isTriggered) {
            LocalTime currentTime = LocalTime.now();
            if (isTimeToTrigger(currentTime) && !isTriggered) {
                synchronized (this) {
                    if (!this.isTriggered) {
                        this.isTriggered = true;
                        // executeAction(); // Chiamare questo metodo se è necessario eseguire un'azione correlata all'attivazione.
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

}
