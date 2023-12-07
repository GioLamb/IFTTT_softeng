public class FactoryAction {
    // Variabili di istanza private per memorizzare il nome dell'azione e il suo contenuto
    // Costruttore della classe che accetta il nome dell'azione e il contenuto come parametri
    public FactoryAction(){};
    // Metodo per creare un'istanza di una classe che implementa l'interfaccia Action
    // in base al nome dell'azione fornito come parametro
    public Action createConcreteAction(String nameAction, String content, String content2) throws IllegalArgumentException {
        // Verifica il tipo di azione e restituisce un'istanza corrispondente
        if (nameAction.equals("Promemoria")) {
            return new DisplayMessage(content); // Crea e restituisce un oggetto DisplayMessage
        } else if (nameAction.equals("Sveglia")) {
            return new AlarmClock(content); // Crea e restituisce un oggetto AlarmClock
        } else if (nameAction.equals("Copia un file")) {
            return new ActionCopy(content, content2); // Crea e restituisce un oggetto ActionCopy
        } else if (nameAction.equals("Sposta un file")) {
            return new MoveFileAction(content, content2); // Crea e restituisce un oggetto MoveFileAction
        } else if (nameAction.equals("Elimina un file")) {
            return new DeleteFileAction(content, content2); // Crea e restituisce un oggetto DeleteFileAction
        }
        /*else if (nameAction.equals("Scrittura su File")){
            return new WriteToFileAction(content, content);
        }*/
        else {
            // Se il nome dell'azione non corrisponde a nessuno dei casi precedenti, lancia un'eccezione
            throw new IllegalArgumentException("Azione non valida: " + nameAction);
        }
    }
}
