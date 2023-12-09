import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TriggerDayOfWeekTest {

    @Test
    void testConstructor_ValidDayOfWeek() {
        // Verifica che il costruttore inizializzi correttamente l'istanza con un giorno valido
        TriggerDayOfWeek trigger = new TriggerDayOfWeek(3); // Mercoledì
        assertEquals(DayOfWeek.WEDNESDAY, trigger.getDayOfWeek());
        assertFalse(trigger.isTriggered());
    }

    @Test
    void testConstructor_InvalidDayOfWeek() {
        // Verifica che il costruttore lanci un'eccezione per un giorno non valido
        assertThrows(IllegalArgumentException.class, () -> new TriggerDayOfWeek(8));
    }

    @Test
    void testIsTimeToTrigger_BeforeTriggerTime() {
        // Verifica che isTimeToTrigger restituisca false prima dell'orario di attivazione
        TriggerDayOfWeek trigger = new TriggerDayOfWeek(4); // Giovedì
        LocalDateTime currentDateTime = LocalDateTime.of(2023, 1, 1, 8, 0); // Una data a caso
        assertFalse(trigger.isTimeToTrigger(currentDateTime));
    }

    @Test
    void testIsTimeToTrigger_AtTriggerTime() {
        // Verifica che isTimeToTrigger restituisca true all'orario di attivazione
        TriggerDayOfWeek trigger = new TriggerDayOfWeek(4); // Venerdì
        LocalDateTime currentDateTime = LocalDateTime.of(2023, 12, 7, 11, 0); // Una data a caso
        assertTrue(trigger.isTimeToTrigger(currentDateTime));
    }

    @Test
    void testIsTimeToTrigger_AfterTriggerTime() {
        // Verifica che isTimeToTrigger restituisca false dopo l'orario di attivazione
        TriggerDayOfWeek trigger = new TriggerDayOfWeek(6); // Sabato
        LocalDateTime currentDateTime = LocalDateTime.of(2023, 1, 1, 18, 0); // Una data a caso
        assertFalse(trigger.isTimeToTrigger(currentDateTime));
    }

    @Test
    void testExecute_TriggerSuccess() throws InterruptedException {
        // Verifica che il metodo execute attivi correttamente il trigger
        TriggerDayOfWeek trigger = new TriggerDayOfWeek(LocalDateTime.now().getDayOfWeek().getValue()); //

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

    @Test
    void testExecute_TriggerInterrupted() throws InterruptedException {
        // Verifica che il metodo execute gestisca correttamente l'interruzione del thread
        TriggerDayOfWeek trigger = new TriggerDayOfWeek(3); // Mercoledì

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
