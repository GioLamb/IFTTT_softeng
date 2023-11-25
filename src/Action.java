public interface Action {
    public String getName();
    public String getContent();
    public void execute();
    public void onActionClose();
}


