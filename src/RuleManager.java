import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalTime;

public class RuleManager{
    private ObservableList<Rule> rules;
    private static RuleManager instance;
    private final Check checkRule = new Check();

    private RuleManager(){
        rules = FXCollections.observableArrayList();
        check(); // creata la classe RuleManager avviamo il thread per il controllo delle regole
    }

    public static RuleManager getInstance(){
        if (instance==null){
            instance = new RuleManager();
        }
        return instance;
    }


    public void addRule(String nameRule, String nameAction, String nameTrigger, String content, LocalTime time, Boolean oneTime, int sleepDays, int sleepHours, int sleepMinutes, Boolean recurrent, Boolean state){
        this.rules.add(new Rule(nameRule, nameAction, nameTrigger, content, time, oneTime, sleepDays, sleepHours, sleepMinutes, recurrent, state));
    }

    public void removeRule(Rule rule){ rules.remove(rule); }; //Metodo utilizzato per la rimozione di una regola (rule) dalla lista rules di regole.

    public ObservableList<Rule> getRules(){ return this.rules;}

    public void setRules(ObservableList<Rule> rules){this.rules=rules;}

    public void check(){
        checkRule.start(); // eseguiamo il trigger
    }

    public Check getCheck(){
        return checkRule;
    }
}
