import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TriggerDayOfWeekTest {

    //Metodo che testa se il costruttore inizializza correttamente l'istanza con un giorno valido
    @Test
    void testConstructor_ValidDayOfWeek() {
        TriggerDayOfWeek trigger = new TriggerDayOfWeek(3); //3 -> Mercoledì
        assertEquals(DayOfWeek.WEDNESDAY, trigger.getDayOfWeek());
        assertFalse(trigger.isTriggered());
    }

    //Metodo di test che permette di verificare che il costruttore lanci un'eccezione per un giorno valido
    @Test
    void testConstructor_InvalidDayOfWeek() {
        assertThrows(IllegalArgumentException.class, () -> new TriggerDayOfWeek(8));
    }

    //Metodo di test che verifica se isTimeToTrigger restituisce false prima del giorno di attivazione
    @Test
    void testIsTimeToTrigger_BeforeTriggerTime() {
        TriggerDayOfWeek trigger = new TriggerDayOfWeek(4); //4 -> Giovedì
        LocalDateTime currentDateTime = LocalDateTime.of(2023, 1, 1, 8, 0); // Una data a caso
        assertFalse(trigger.isTimeToTrigger(currentDateTime));
    }

    //Metodo di test che verifica se isTimeToTrigger restituisce true all'orario di attivazione
    @Test
    void testIsTimeToTrigger_AtTriggerTime() {
        TriggerDayOfWeek trigger = new TriggerDayOfWeek(4); //4 -> Giovedì
        LocalDateTime currentDateTime = LocalDateTime.of(2023, 12, 7, 11, 0); // Una data a caso
        assertTrue(trigger.isTimeToTrigger(currentDateTime));
    }

    //Metodo di test che verifica se isTimeToTrigger restituisce false dopo il giorno l'attivazione
    @Test
    void testIsTimeToTrigger_AfterTriggerTime() {
        TriggerDayOfWeek trigger = new TriggerDayOfWeek(6); //6 -> Sabato
        LocalDateTime currentDateTime = LocalDateTime.of(2023, 1, 1, 18, 0); // Una data a caso
        assertFalse(trigger.isTimeToTrigger(currentDateTime));
    }

    //Metodo di test che verifica se execute attiva correttamente il trigger
    @Test
    void testExecute_TriggerSuccess() throws InterruptedException {
        TriggerDayOfWeek trigger = new TriggerDayOfWeek(4); //4 -> Giovedì

        // Esegui il trigger in un thread separato
        Thread triggerThread = new Thread(trigger::execute);
        triggerThread.start();

        // Attendi un po' per dare al thread una possibilità di attivare il trigger
        Thread.sleep(1000);

        // Interrompi il thread dopo un secondo
        triggerThread.interrupt();

        // Attendi che il thread di trigger termini
        triggerThread.join(2000); // Imposta un timeout ragionevole per evitare un blocco infinito

        // Verifica lo stato del trigger dopo che il thread ha terminato
        assertTrue(trigger.isTriggered());
    }

    //Metodo di test che verifica se execute gestisce correttamente l'interruzione del thread
    @Test
    void testExecute_TriggerInterrupted() throws InterruptedException {
        TriggerDayOfWeek trigger = new TriggerDayOfWeek(3); //3 -> Mercoledì

        // Esegui il trigger in un thread separato
        Thread triggerThread = new Thread(trigger::execute);
        triggerThread.start();

        // Attendi un po' per dare al thread una possibilità di attivare il trigger
        Thread.sleep(1000);

        // Interrompi il thread immediatamente dopo l'avvio
        triggerThread.interrupt();

        // Attendi che il thread di trigger termini
        triggerThread.join(2000); // Imposta un timeout ragionevole per evitare un blocco infinito

        // Verifica che il trigger non sia stato attivato a causa dell'interruzione
        assertFalse(trigger.isTriggered());
    }
}
