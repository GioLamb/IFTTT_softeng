import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class FactoryTrigger{
    //Variabili d'istanza private per memorizzare il nome del trigger e l'orario

    public FactoryTrigger(){};

    //Metodo per creare un'istanza di una classe che implementa l'interfaccia Trigger
    //in base al nome del trigger fornito come parametro
    public Trigger createConcreteTrigger(String nameTrigger, LocalTime time, Integer content3){
        //Verifica il tipo di trigger e restituisce un'istanza corrispondente
        if (nameTrigger != null) {
            // Verifica il tipo di trigger e restituisce un'istanza corrispondente
            if (nameTrigger.equalsIgnoreCase("TriggerTime")) {
                return new TriggerTime(time);
            } else if(nameTrigger.equalsIgnoreCase("TriggerDayOfWeek")) {
                return new TriggerDayOfWeek(content3);
            } else if(nameTrigger.equalsIgnoreCase("TriggerDayOfMonth")) {
                LocalDateTime dateTime = LocalDate.now().atTime(time.withHour(0).withMinute(0).withSecond(0));
                dateTime = dateTime.withDayOfMonth(content3);
                return new TriggerDayOfMonth(dateTime);
            }
                // Se il nome del trigger non corrisponde a nessuno dei casi precedenti, lancia un'eccezione
                throw new IllegalArgumentException("Trigger non valido: " + nameTrigger);
            } else {
            // Se nameTrigger è nullo, lancia un'eccezione
            throw new IllegalArgumentException("Il nome del trigger non può essere nullo");
        }
    }
}
