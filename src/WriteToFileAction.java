/*import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFileAction implements Action {
    private FileWriter fileWriter;
    private String filePath;
    private String userInput;
    public WriteToFileAction(String filePath, String userInput) {
        this.filePath = filePath;
        this.userInput = userInput;
    }
    @Override
    public String getName() {
        return "Scrittura su File";
    }

    @Override
    public String getContent() {
        // Restituisci informazioni aggiuntive sull'azione, se necessario
        return "Azione di scrittura su file";
    }

    @Override
    public void execute() {
        try {
            // Inizializzazione del FileWriter nel tuo metodo writeToFile
            fileWriter = new FileWriter(filePath);
            writeToFile(userInput, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActionClose() {
        // Chiudi risorse o connessioni aperte, se necessario
        if (fileWriter != null) {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Metodo specifico per l'azione di scrittura su file
    @Override
    public void writeToFile(String content, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
            System.out.println("Contenuto scritto con successo nel file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
 */
