import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Rule {
    // Variabili d'istanza private per memorizzare un'azione, un trigger e informazioni sulla regola
    private Action action;
    private Trigger trigger;
    private StringProperty nameRule = new SimpleStringProperty();
    private StringProperty nameTrigger = new SimpleStringProperty();
    private StringProperty nameAction = new SimpleStringProperty();
    private StringProperty actionContent = new SimpleStringProperty();
    private StringProperty triggerContent = new SimpleStringProperty();
    private BooleanProperty stateValue = new SimpleBooleanProperty();
    private State state;
    private Boolean oneTime;
    private Boolean recurrent;
    private int sleepDays;
    private int sleepHours;
    private int sleepMinutes;
    private LocalDateTime now;
    private LocalDateTime nowPlusSleep;
    private Boolean repeat;

    private String content2;
    private Integer content3;
    private StringProperty nowPlusSleepFormat = new SimpleStringProperty();

    //Costruttore della classe Rule che accetta il nome della regola, il nome dell'azione,
    //il nome del trigger, il contenuto dell'azione e l'orario del trigger come parametri
    public Rule(String nameRule, String nameAction, String nameTrigger, String content, String content2, Integer content3, LocalTime time, Boolean oneTime, int sleepDays, int sleepHours, int sleepMinutes, Boolean recurrent, Boolean state, Boolean repeat, LocalDateTime nowPlusSleep) {
        //Inizializza le variabili d'istanza con i valori forniti
        this.nameRule.set(nameRule);
        this.nameAction.set(nameAction);
        this.nameTrigger.set(nameTrigger);
        actionContent.set(content);
        triggerContent.set(time.toString());
        //Utilizza la FactoryAction per creare un'istanza dell'azione in base al nome e al contenuto
        this.action = new FactoryAction().createConcreteAction(nameAction, content, content2);
        //Utilizza la FactoryTrigger per creare un'istanza del trigger in base al nome e all'orario
        this.trigger = new FactoryTrigger().createConcreteTrigger(nameTrigger, time, content3);
        this.oneTime = oneTime;
        this.sleepDays = sleepDays;
        this.sleepHours = sleepHours;
        this.sleepMinutes = sleepMinutes;
        this.recurrent = recurrent;
        this.repeat = repeat;
        this.content2 = content2;
        this.content3 = content3;
        if(state){
            this.state = new ActiveState(this);
        }
        else{
            this.state = new DeactiveState(this);
        }
        this.nowPlusSleep = nowPlusSleep;
        setNowPlusSleep(nowPlusSleep);
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
    public String toString() {
        return "" + getNameRule().get() + "," + getNameAction().get() + "," + getAction() + "," + getNameTrigger().get() + "," + getTrigger();
    }

    public StringProperty getTriggerContent() {
        return triggerContent;
    }

    public Integer getContent3() {
        return content3;
    }

    public StringProperty getActionContent() {
        return actionContent;
    }

    public String getActionContent2() {
        return content2;
    }

    public State getState() {
        return state;
    }

    public BooleanProperty getStateValue() {
        return stateValue;
    }

    public void changeState(State state){
        this.state = state;
        if (state instanceof ActiveState){
            stateValue.set(true);
        }
        else{
            stateValue.set(false);
        }
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        nowPlusSleepFormat.set(nowPlusSleep.format(formatter));
    }
    public StringProperty getNowPlusSleepFormat(){
        return nowPlusSleepFormat;
    }
}