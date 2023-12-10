import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TriggerTimeTest {

    //Metodo che testa se isTimeToTrigger restituisce true quando l'orario corrente è dopo l'orario di trigger
    @Test
    void isTimeToTrigger_ShouldReturnTrue_WhenCurrentTimeIsAfterTriggerTime() {
        //Ottieni un orario di trigger che è passato di un'ora rispetto all'orario corrente
        LocalTime triggerTime = LocalTime.now().minusHours(1);
        //Crea un'istanza della classe TriggerTime con l'orario di trigger
        TriggerTime trigger = new TriggerTime(triggerTime);

        //Invoca il metodo isTimeToTrigger passando l'orario corrente e salva il risultato
        boolean result = trigger.isTimeToTrigger(LocalTime.now());

        //Verifica che il risultato sia true
        assertTrue(result);
    }

    //Metodo che testa se isTimeToTrigger restituisce false quando l'orario corrente è prima dell'orario di trigger
    @Test
    void isTimeToTrigger_ShouldReturnFalse_WhenCurrentTimeIsBeforeTriggerTime() {
        //Ottieni un orario di trigger che è avanzato di un'ora rispetto all'orario corrente
        LocalTime triggerTime = LocalTime.now().plusHours(1);
        //Crea un'istanza della classe TriggerTime con l'orario di trigger
        TriggerTime trigger = new TriggerTime(triggerTime);

        //Invoca il metodo isTimeToTrigger passando l'orario corrente e salva il risultato
        boolean result = trigger.isTimeToTrigger(LocalTime.now());

        //Verifica che il risultato sia false
        assertFalse(result);
    }

    //Metodo che testa se execute imposta isTriggered a true dopo l'orario di trigger
    @Test
    void execute_ShouldSetIsTriggeredToTrueAfterTriggerTime() throws InterruptedException {
        //Ottieni un orario di trigger che è avanzato di 2 secondi rispetto all'orario corrente
        LocalTime triggerTime = LocalTime.now().plusSeconds(2);
        //Crea un'istanza della classe TriggerTime con l'orario di trigger
        TriggerTime trigger = new TriggerTime(triggerTime);

        //Crea un nuovo thread che esegue il metodo execute della classe TriggerTime
        Thread thread = new Thread(trigger::execute);
        thread.start();

        //Attendere 3 secondi (tempo sufficiente per l'esecuzione del thread)
        Thread.sleep(3000);

        //Verifica che isTriggered sia stato impostato a true dopo l'orario di trigger
        assertTrue(trigger.getTime().isBefore(LocalTime.now()));
    }

    //Metodo che testa se getTime restituisce l'orario di trigger
    @Test
    void getTime_ShouldReturnTriggerTime() {
        //Ottieni un orario di trigger che è avanzato di un'ora rispetto all'orario corrente
        LocalTime triggerTime = LocalTime.now().plusHours(1);
        //Crea un'istanza della classe TriggerTime con l'orario di trigger
        TriggerTime trigger = new TriggerTime(triggerTime);

        //Invoca il metodo getTime e salva il risultato
        LocalTime result = trigger.getTime();

        //Verifica che il risultato sia uguale all'orario di trigger
        assertEquals(triggerTime, result);
    }
}
