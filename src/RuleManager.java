import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.time.LocalTime;
import java.util.ArrayList;

public class RuleManager {
    private final ObservableList<Rule> rules;
    private static RuleManager instance;
    private Action action;

    private RuleManager(){
        rules = FXCollections.observableArrayList();
    }

    public static RuleManager getInstance(){
        if (instance==null){
            instance = new RuleManager();
        }
        return instance;
    }

    public boolean check() {
        LocalTime currentTime = LocalTime.now();
        boolean triggerActivated = false;
        for (Rule rule : rules) {
            if (rule.getTrigger() instanceof TriggerTime) {
                TriggerTime triggerTime = (TriggerTime) rule.getTrigger();
                if (triggerTime.isTimeToTrigger(currentTime)) {
                    if(rule.getAction() instanceof DisplayMessage) {
                        DisplayMessage displayMessage = (DisplayMessage) rule.getAction();
                        displayMessage.execute();
                    } else if(rule.getAction() instanceof AlarmClock) {
                             AlarmClock alarmClock = (AlarmClock) rule.getAction();
                             alarmClock.execute();
                    }
                    triggerActivated = true;
                }
            }
        }
        return triggerActivated;
    }


    public void addRule(String nameRule, String nameAction, String nameTrigger, String content, LocalTime time){
        this.rules.add(new Rule(nameRule, nameAction, nameTrigger, content, time));
        Rule rule = new Rule(nameRule, nameAction, nameTrigger, content, time);
    }



    public ObservableList<Rule> getRules(){ return this.rules;}
}
