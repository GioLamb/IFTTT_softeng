public class ActiveState extends Context{
    public ActiveState(Rule rule) {
        super(rule);
    }

    @Override
    public void activate(){}
    @Override
    public void deactivate(){
        rule.changeState(new DeactiveState(rule));
    } // cambia l'oggetto State di Rule da ActiveState a DeactiveState

}
