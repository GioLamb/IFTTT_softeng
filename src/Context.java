public abstract class Context implements State{
    protected Rule rule;
    public Context( Rule rule){
        this.rule = rule;

    }

    @Override
    public abstract void activate();

    @Override
    public abstract void deactivate();
}
