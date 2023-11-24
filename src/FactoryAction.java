public class FactoryAction{
    private String nameAction;
    private String content;

    public FactoryAction(String nameAction, String content){
        this.nameAction = nameAction;
        this.content = content;
    }

    public Action FactoryAction(String actionType, String content) throws IllegalArgumentException{
        if(actionType.equals("Promemoria")){
            return new displayMessage(content);
        }else if(actionType.equals("Sveglia")){
            return new AlarmClock(content);
        }else{
            throw new IllegalArgumentException("Azione non valida: " + actionType);
        }
    }
}