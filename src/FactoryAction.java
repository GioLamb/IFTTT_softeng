public class FactoryAction {
    // Variabili di istanza private per memorizzare il nome dell'azione e il suo contenuto
    private final String nameAction;
    private final String content;
 
    // Costruttore della classe che accetta il nome dell'azione e il contenuto come parametri
    public FactoryAction(String nameAction, String content) {
        // Inizializza le variabili di istanza con i valori forniti
        this.nameAction = nameAction;
        this.content = content;
    }
 
    // Metodo per creare un'istanza di una classe che implementa l'interfaccia Action
    // in base al nome dell'azione fornito come parametro
    public Action createConcreteAction(String nameAction, String content) throws IllegalArgumentException {
        // Verifica il tipo di azione e restituisce un'istanza corrispondente
        if (nameAction.equals("Promemoria")) {
            return new DisplayMessage(content); // Crea e restituisce un oggetto DisplayMessage
        } else if (nameAction.equals("Sveglia")) {
            return new AlarmClock(content); // Crea e restituisce un oggetto AlarmClock
        } else {
            // Se il nome dell'azione non corrisponde a nessuno dei casi precedenti, lancia un'eccezione
            throw new IllegalArgumentException("Azione non valida: " + nameAction);
        }
    }

    public String getContentAction(){
        return content;
    }
}
