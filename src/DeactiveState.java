public class DeactiveState extends Context{
    public DeactiveState(Rule rule) {
        super(rule);
    }

    @Override
    public void deactivate(){}

    @Override
    public void activate(){
        rule.changeState(new ActiveState(rule));
    } // cambia l'oggetto State di Rule da ActiveState a DeactiveState
}