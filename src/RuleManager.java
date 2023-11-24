import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.time.LocalTime;
import java.util.ArrayList;

public class RuleManager {
    private ObservableList<Rule> rules;
    private static RuleManager instance;

    private RuleManager(){
        rules = FXCollections.observableArrayList();
    }

    public static RuleManager getInstance(){
        if (instance==null){
            instance = new RuleManager();
        }
        return instance;
    }

    public void addRule(String nameRule, String nameAction, String nameTrigger, String content, LocalTime time){
        this.rules.add(new Rule(nameRule, nameAction, nameTrigger, content, time));
    }

    public ObservableList<Rule> getRules(){
        return this.rules;
    }
}
