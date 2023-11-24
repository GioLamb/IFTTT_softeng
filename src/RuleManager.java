import java.time.LocalTime;

public class RuleManager {
    ArrayList<Rule> rules;
    private static RuleManager instance;
    private RuleManager RuleManager(){
        if (this.instance==null){
            this.instance = new RuleManager();
            this.rules = new ArrayList<Rule>();
        }
        return this.instance;
    }

    public RuleManager getInstance(){
        if (this.instance==null){
            return RuleManager();
        }
        return this.instance;
    }

    public void addRule(String nameRule, String nameAction, String nameTrigger, String content, LocalTime time){
        rules.add(new Rule(nameRule, nameAction, nameTrigger, content, time));
    }
}
