import java.time.LocalTime;

public class Rule{
        // Variabili d'istanza private per memorizzare un'azione, un trigger e informazioni sulla regola
        private FactoryAction action;
        private FactoryTrigger trigger;
        private String nameRule;
        private String nameTrigger;
        private String nameAction;

        //Costruttore della classe Rule che accetta il nome della regola, il nome dell'azione,
        //il nome del trigger, il contenuto dell'azione e l'orario del trigger come parametri
        public Rule(String nameRule, String nameAction, String nameTrigger, String content, LocalTime time) {
            //Inizializza le variabili d'istanza con i valori forniti
            this.nameRule = nameRule;
            this.nameAction = nameAction;
            this.nameTrigger = nameTrigger;

            //Utilizza la FactoryAction per creare un'istanza dell'azione in base al nome e al contenuto
            this.action = new FactoryAction(nameAction, content);

            //Utilizza la FactoryTrigger per creare un'istanza del trigger in base al nome e all'orario
            this.trigger = new FactoryTrigger(nameTrigger, time);
        }

    public String getAction() {
        return action.getContentAction();
    }

    public String getTrigger() {
        return trigger.getContentTrigger();
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
            return ""+getNameRule()+","+getNameAction()+","+getAction()+
                    ","+getNameTrigger()+","+getTrigger();
    }
}
