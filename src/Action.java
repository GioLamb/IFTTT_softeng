public interface Action {
    String getName();
    String getContent1();
    String getContent2();
    void execute();
    void onActionClose();
}


