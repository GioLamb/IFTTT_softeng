public interface Action {
    public String getName();
    public String getContent1();
    public String getContent2();
    public void execute();
    public void onActionClose();
    // Nuovo metodo specifico per l'azione di scrittura su file
    /*default void writeToFile(String content, String filePath) {
        throw new UnsupportedOperationException("Questo metodo non è supportato per questa azione.");
    }*/
}


