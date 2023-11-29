import java.time.LocalTime;
import java.util.Objects;

public class Rule{
        // Variabili d'istanza private per memorizzare un'azione, un trigger e informazioni sulla regola
        private Action action;
        private Trigger trigger;
        private String nameRule;
        private String nameTrigger;
        private String nameAction;
        private String actionContent;
        private String triggerContent;
        private Boolean state=true;

        //Costruttore della classe Rule che accetta il nome della regola, il nome dell'azione,
        //il nome del trigger, il contenuto dell'azione e l'orario del trigger come parametri
        public Rule(String nameRule, String nameAction, String nameTrigger, String content, LocalTime time) {
            //Inizializza le variabili d'istanza con i valori forniti
            this.nameRule = nameRule;
            this.nameAction = nameAction;
            this.nameTrigger = nameTrigger;
            actionContent = content;
            triggerContent=time.toString();
            //Utilizza la FactoryAction per creare un'istanza dell'azione in base al nome e al contenuto
            this.action = new FactoryAction().createConcreteAction(nameAction, content);
            //Utilizza la FactoryTrigger per creare un'istanza del trigger in base al nome e all'orario
            this.trigger = new FactoryTrigger().createConcreteTrigger(nameTrigger, time);
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
        return Objects.equals(nameRule, rule.nameRule) &&
                Objects.equals(nameAction, rule.nameAction) &&
                Objects.equals(nameTrigger, rule.nameTrigger) &&
                Objects.equals(triggerContent, rule.triggerContent) &&
                Objects.equals(actionContent, rule.actionContent) &&
                Objects.equals(state, rule.state);
    }


    public Action getAction() {
        return action;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public String getNameTrigger() {
        return nameTrigger;
    }

    public String getNameRule() {
        return nameRule;
    }

    public String getNameAction() {
        return nameAction;
    }

    @Override
    public String toString(){
            return ""+getNameRule()+","+getNameAction()+","+getAction()+","+getNameTrigger()+","+getTrigger();
    }

    public String getTriggerContent() {
        return triggerContent;
    }

    public String getActionContent() {
        return actionContent;
    }

    public Boolean getState() {
        return state;
    }

    public Boolean setState(Boolean state) {
        return this.state=state;
    }
}
