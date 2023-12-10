import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class FactoryTriggerTest {

    //Metodo che testa la creazione di un trigger di tipo TriggerTime con successo
    @Test
    void testCreateConcreteTrigger_TriggerTime_Success() {
        FactoryTrigger factory = new FactoryTrigger();
        //Impostiamo l'orario del trigger alle 12:30
        LocalTime triggerTime = LocalTime.of(12, 30);

        //Creazione di un TriggerTime usando la factory
        Trigger trigger = factory.createConcreteTrigger("TriggerTime", triggerTime, 0);

        //Verifica che il trigger sia stato creato con successo
        assertNotNull(trigger);
        //Verifica che il trigger sia un'istanza di TriggerTime
        assertTrue(trigger instanceof TriggerTime);
        //Verifica che l'orario del trigger sia quello atteso
        assertEquals(triggerTime, ((TriggerTime) trigger).getTime());
    }

    //Metodo che testa la creazione di un trigger di tipo TriggerDayOfWeek con successo
    @Test
    void testCreateConcreteTrigger_TriggerDayOfWeek_Success() {
        FactoryTrigger factory = new FactoryTrigger();

        //Creazione di un TriggerDayOfWeek usando la factory con il giorno della settimana 3 (Mercoledì)
        Trigger trigger = factory.createConcreteTrigger("TriggerDayOfWeek", LocalTime.now(), 3);

        //Verifica che il trigger sia stato creato con successo
        assertNotNull(trigger);
        //Verifica che il trigger sia un'istanza di TriggerDayOfWeek
        assertTrue(trigger instanceof TriggerDayOfWeek);
        //Verifica che il giorno della settimana del trigger sia quello atteso
        assertEquals(3, ((TriggerDayOfWeek) trigger).getDayOfWeek().getValue());
    }

    //Metodo che testa la creazione di un trigger di tipo TriggerDayOfMonth con successo
    @Test
    void testCreateConcreteTrigger_TriggerDayOfMonth_Success() {
        FactoryTrigger factory = new FactoryTrigger();
        //Settiamo l'orario del trigger alle 15:45
        LocalTime triggerTime = LocalTime.of(15, 45);

        //Creiamo un TriggerDayOfMonth usando la factory con il giorno del mese 10 (Ottobre)
        Trigger trigger = factory.createConcreteTrigger("TriggerDayOfMonth", triggerTime, 10);

        //Verifica che il trigger sia stato creato con successo
        assertNotNull(trigger);
        //Verifica che il trigger sia un'istanza di TriggerDayOfMonth
        assertTrue(trigger instanceof TriggerDayOfMonth);
        //Verifica che il giorno del mese del trigger sia quello atteso
        assertEquals(10, ((TriggerDayOfMonth) trigger).getDayOfMonth().getDayOfMonth());
    }

    //Metodo che testa la gestione di un nome di trigger non valido
    @Test
    void testCreateConcreteTrigger_InvalidTriggerName() {
        FactoryTrigger factory = new FactoryTrigger();

        //Verifica che venga lanciata un'eccezione quando si tenta di creare un nome non valido
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> factory.createConcreteTrigger("InvalidTrigger", LocalTime.now(), 0));

        //Verifica che il messaggio di errore dell'eccezione sia quello atteso
        assertEquals("Trigger non valido: InvalidTrigger", exception.getMessage());
    }

    //Metodo che testa la gestione ddi un nome di trigger nullo
    @Test
    void testCreateConcreteTrigger_NullTriggerName() {
        FactoryTrigger factory = new FactoryTrigger();

        //Verifica che venga lanciata un'eccezione quando si tenta di creare un trigger con un nome nullo
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> factory.createConcreteTrigger(null, LocalTime.now(), 0));

        //Verifica che il messaggio di errore dell'eccezione sia quello atteso
        assertEquals("Il nome del trigger non può essere nullo", exception.getMessage());
    }
}

