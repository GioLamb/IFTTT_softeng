public class FactoryAction {
    // Variabili di istanza private per memorizzare il nome dell'azione e il suo contenuto
    // Costruttore della classe che accetta il nome dell'azione e il contenuto come parametri
    public FactoryAction(){}
    // Metodo per creare un'istanza di una classe che implementa l'interfaccia Action
    // in base al nome dell'azione fornito come parametro
    public Action createConcreteAction(String nameAction, String content, String content2) throws IllegalArgumentException {
        // Verifica il tipo di azione e restituisce un'istanza corrispondente
        // Se il nome dell'azione non corrisponde a nessuno dei casi precedenti, lancia un'eccezione
        return switch (nameAction) {
            case "Promemoria" -> new DisplayMessage(content); // Crea e restituisce un oggetto DisplayMessage

            case "Sveglia" -> new AlarmClock(content); // Crea e restituisce un oggetto AlarmClock

            case "Copia un file" -> new ActionCopy(content, content2); // Crea e restituisce un oggetto ActionCopy

            case "Sposta un file" ->
                    new MoveFileAction(content, content2); // Crea e restituisce un oggetto MoveFileAction

            case "Elimina un file" -> new DeleteFileAction(content); // Crea e restituisce un oggetto DeleteFileAction

            case "Scrittura su File" ->
                    new WriteToFileAction(content, content2); //Crea e restituisce un oggetto WriteToFileAction

            default -> throw new IllegalArgumentException("Azione non valida: " + nameAction);
        };
    }
}
