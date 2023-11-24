import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalTime;

public class FactoryTrigger {
    private String nameTrigger;
    private LocalTime time;

    public FactoryTrigger(String nameTrigger, LocalTime time) {
        this.nameTrigger = nameTrigger;
        this.time = time;
    }

    public Trigger FactoryTrigger(String triggerType, LocalTime time){
        if(triggerType.equals("TriggerTime")){
            return new TriggerTime(time);
        }else{
            throw new IllegalArgumentException("Trigger non valido: " + triggerType);
        }
    }

}
