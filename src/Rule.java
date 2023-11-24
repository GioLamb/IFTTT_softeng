import java.time.LocalTime;

public class Rule{
        private Action action;
        private Trigger trigger;
        private String nameRule;
        private String nameTrigger;
        private String nameAction;

        // Costruttore di Rule, che riceve un'istanza di Action e Trigger
        public Rule(String nameRule, String nameAction, String nameTrigger, String content, LocalTime time) {
            this.nameRule = nameRule;
            this.nameAction = nameAction;
            this.nameTrigger = nameTrigger;
            this.action = (Action) new FactoryAction(nameAction, content);
            this.trigger = (Trigger) new FactoryTrigger(nameTrigger, time);
        }
}
