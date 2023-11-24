import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalTime;

public class FactoryTrigger {
    //Variabili d'istanza private per memorizzare il nome del trigger e l'orario
    private String nameTrigger;
    private LocalTime time;

    //Costruttore della classe FactoryTrigger che accetta il nome del trigger e l'orario come parametri
    public FactoryTrigger(String nameTrigger, LocalTime time) {
        //Inizializza le variabili d'istanza con i valori forniti
        this.nameTrigger = nameTrigger;
        this.time = time;
    }
    //Metodo per creare un'istanza di una classe che implementa l'interfaccia Trigger
    //in base al nome del trigger fornito come parametro
    public Trigger createConcreteTrigger(String nameTrigger, LocalTime time){
        //Verifica il tipo di trigger e restituisce un'istanza corrispondente
        if(nameTrigger.equals("TriggerTime")){
            return new TriggerTime(time);
        }else{
            //Se il nome del trigger non corrisponde a nessuno dei casi precedenti, lancia un'eccezione
            throw new IllegalArgumentException("Trigger non valido: " + nameTrigger);
        }
    }

}
