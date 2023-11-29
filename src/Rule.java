import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalTime;

public class Rule{
        // Variabili d'istanza private per memorizzare un'azione, un trigger e informazioni sulla regola
        private Action action;
        private Trigger trigger;
        private StringProperty nameRule = new SimpleStringProperty();
        private StringProperty nameTrigger = new SimpleStringProperty();
        private StringProperty nameAction = new SimpleStringProperty();
        private StringProperty actionContent = new SimpleStringProperty();
        private StringProperty triggerContent = new SimpleStringProperty();
        private BooleanProperty state=new SimpleBooleanProperty(true);

        //Costruttore della classe Rule che accetta il nome della regola, il nome dell'azione,
        //il nome del trigger, il contenuto dell'azione e l'orario del trigger come parametri
        public Rule(String nameRule, String nameAction, String nameTrigger, String content, LocalTime time) {
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
}
