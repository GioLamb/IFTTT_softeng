import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Rule{
        // Variabili d'istanza private per memorizzare un'azione, un trigger e informazioni sulla regola
        private Action action;
        private Trigger trigger;
        private StringProperty nameRule = new SimpleStringProperty();
        private StringProperty nameTrigger = new SimpleStringProperty();
        private StringProperty nameAction = new SimpleStringProperty();
        private StringProperty actionContent = new SimpleStringProperty();
        private StringProperty triggerContent = new SimpleStringProperty();
        private BooleanProperty state=new SimpleBooleanProperty();
        private Boolean oneTime;
        private Boolean recurrent;
        private int sleepDays;
        private int sleepHours;
        private int sleepMinutes;
        private LocalDateTime now;
        private LocalDateTime nowPlusSleep;
        private Boolean repeat;

        //Costruttore della classe Rule che accetta il nome della regola, il nome dell'azione,
        //il nome del trigger, il contenuto dell'azione e l'orario del trigger come parametri
        public Rule(String nameRule, String nameAction, String nameTrigger, String content, LocalTime time, Boolean oneTime, int sleepDays, int sleepHours, int sleepMinutes, Boolean recurrent, Boolean state, Boolean repeat, LocalDateTime nowPlusSleep) {
            //Inizializza le variabili d'istanza con i valori forniti
            this.nameRule.set(nameRule);
            this.nameAction.set(nameAction);
            this.nameTrigger.set(nameTrigger);
            actionContent.set(content);
            triggerContent.set(time.toString());
            //Utilizza la FactoryAction per creare un'istanza dell'azione in base al nome e al contenuto
            this.action = new FactoryAction().createConcreteAction(nameAction, content);
            //Utilizza la FactoryTrigger per creare un'istanza del trigger in base al nome e all'orario
            this.trigger = new FactoryTrigger().createConcreteTrigger(nameTrigger, time);
            this.oneTime = oneTime;
            this.sleepDays = sleepDays;
            this.sleepHours = sleepHours;
            this.sleepMinutes = sleepMinutes;
            this.recurrent = recurrent;
            this.repeat = repeat;
            setState(state);
            this.nowPlusSleep = nowPlusSleep;
        }

    //Sovrascrive il metodo equals della classe Object
    @Override
    public boolean equals(Object o) {
        //Verifica se l'oggetto corrente è lo stesso di 'o' (stesso riferimento)
        if (this == o) return true;
        //Verifica se 'o' è null o appartiene a una classe diversa da Rule
        if (o == null || getClass() != o.getClass()) return false;
        //Casting di 'o' a Rule per ottenere un oggetto Rule
        Rule rule = (Rule) o;
        //Confronta gli attributi delle due regole per determinare l'uguaglianza
        return Objects.equals(this.nameRule, rule.nameRule) &&
                Objects.equals(this.nameAction, rule.nameAction) &&
                Objects.equals(this.nameTrigger, rule.nameTrigger) &&
                Objects.equals(this.triggerContent, rule.triggerContent) &&
                Objects.equals(this.actionContent, rule.actionContent) &&
                Objects.equals(this.state, rule.state);
    }


    public Action getAction() {
        return action;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public StringProperty getNameTrigger() {
        return nameTrigger;
    }

    public StringProperty getNameRule() {
        return nameRule;
    }

    public StringProperty getNameAction() {
        return nameAction;
    }

    @Override
    public String toString(){
            return ""+getNameRule().get()+","+getNameAction().get()+","+getAction()+","+getNameTrigger().get()+","+getTrigger();
    }

    public StringProperty getTriggerContent() {
        return triggerContent;
    }

    public StringProperty getActionContent() {
        return actionContent;
    }

    public BooleanProperty getState() {
        return state;
    }

    public void setState(Boolean state) {
            this.state.set(state);
    }
    public Boolean getOneTime(){
        return this.oneTime;
    }

    public Boolean setOneTime(Boolean oneTime){
        return this.oneTime  = oneTime;
    }
    public Boolean getRecurrent(){
        return this.recurrent;
    }

    public Boolean setRecurrent(Boolean recurrent){
        return this.recurrent = recurrent;
    }

    public Boolean getRepeat(){
        return this.repeat;
    }

    public Boolean setRepeat(Boolean active){
        return this.repeat= active;
    }

    public int getSleepDays(){
        return this.sleepDays;
    }
    public int getSleepHours(){
        return this.sleepHours;
    }
    public int getSleepMinutes(){
        return this.sleepMinutes;
    }

    public LocalDateTime getNow(){
        return this.now;
    }

    public LocalDateTime setNow (LocalDateTime time){
        return this.now = time;
    }
    public LocalDateTime getNowPlusSleep(){
        return this.nowPlusSleep;
    }
    public void setNowPlusSleep (LocalDateTime time){
        nowPlusSleep = time;
    }
    public StringProperty getNowPlusSleepFormat(){
            StringProperty timeFormat = new SimpleStringProperty();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
            timeFormat.set(nowPlusSleep.format(formatter));
            return timeFormat;
    }
}
