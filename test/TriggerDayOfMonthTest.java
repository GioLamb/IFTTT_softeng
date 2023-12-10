import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TriggerDayOfMonthTest {


    //Metodo che testa execute quando il trigger è già stato attivato in passato
    @Test
    void testExecute_TriggerAlreadyActivated() throws InterruptedException {
        // Imposta un giorno passato rispetto all'istante corrente
        LocalDateTime pastDateTime = LocalDateTime.now().minusDays(1);
        TriggerDayOfMonth trigger = new TriggerDayOfMonth(pastDateTime);

        // Avvia l'esecuzione del trigger in un thread separato
        Thread thread = new Thread(trigger::execute);
        thread.start();

        // Attendi che il thread termini
        thread.join(5000); // Aspetta al massimo 5 secondi

        // Verifica che il trigger non sia stato attivato (già attivato nel passato)
        assertFalse(trigger.isTriggered());
    }

    //Metodo che testa il metodo isTimeToTrigger con una data futura
    @Test
    void testIsTimeToTrigger_FutureDate() {
        // Imposta un giorno futuro rispetto all'istante corrente
        LocalDateTime futureDateTime = LocalDateTime.now().plusDays(1);
        TriggerDayOfMonth trigger = new TriggerDayOfMonth(futureDateTime);

        // Verifica che il trigger non sia ancora attivato
        assertFalse(trigger.isTriggered());

        // Verifica che il metodo isTimeToTrigger restituisca true per la data futura
        assertTrue(trigger.isTimeToTrigger(futureDateTime.plusHours(1)));
    }

    //Metodo che testa il metodo isTimeToTrigger con una data passata
    @Test
    void testIsTimeToTrigger_PastDate() {
        // Imposta un giorno passato rispetto all'istante corrente
        LocalDateTime pastDateTime = LocalDateTime.now().minusDays(1);
        TriggerDayOfMonth trigger = new TriggerDayOfMonth(pastDateTime);

        // Verifica che il trigger non sia ancora attivato
        assertFalse(trigger.isTriggered());

        // Verifica che il metodo isTimeToTrigger restituisca false per la data passata
        assertFalse(trigger.isTimeToTrigger(pastDateTime.minusHours(1)));
    }
}
