import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class FactoryTriggerTest {

    //Test case: createConcreteTrigger restituisce un'istanza di TriggerTime con un nome valido e orario valido
    @Test
    void createConcreteTrigger_validTriggerTime_shouldReturnTriggerTimeInstance() {
        //Creazione di un'istanza di FactoryTrigger e TriggerTime con un orario specifico
        FactoryTrigger factoryTrigger = (FactoryTrigger) new FactoryTrigger().createConcreteTrigger("TriggerTime", LocalTime.of(12, 30));

        //Chiamata al metodo createConcreteTrigger con un nome e un orario validi
        Trigger trigger = factoryTrigger.createConcreteTrigger("TriggerTime", LocalTime.of(12, 30));

        //Verifica che l'istanza non sia nulla, che sia un'istanza di TriggerTime
        //e che l'orario dell'istanza sia quello atteso
        assertNotNull(trigger);
        assertTrue(trigger instanceof TriggerTime);
        assertEquals(LocalTime.of(12, 30), ((TriggerTime) trigger).getTime());
    }

    //Test case: createConcreteTrigger dovrebbe lanciare un'eccezione per un nome di trigger non valido
    @Test
    void createConcreteTrigger_invalidTriggerName_shouldThrowException() {
        //Creazione di un'istanza di FactoryTrigger con un nome di trigger valido e orario specifico
        FactoryTrigger factoryTrigger = (FactoryTrigger) new FactoryTrigger().createConcreteTrigger("TriggerTime", LocalTime.of(12, 30));

        //Verifica che chiamare createConcreteTrigger con un nome di trigger non valido
        //lanci un'eccezione di tipo IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            factoryTrigger.createConcreteTrigger("InvalidTriggerName", LocalTime.of(12, 30));
        });
    }

    //Test case: createConcreteTrigger dovrebbe lanciare un'eccezione per un nome di trigger nullo
    @Test
    void createConcreteTrigger_nullTriggerName_shouldThrowException() {
        //Creazione di un'istanza di FactoryTrigger con un nome di trigger valido e orario specifico
        FactoryTrigger factoryTrigger = (FactoryTrigger) new FactoryTrigger().createConcreteTrigger("TriggerTime", LocalTime.of(12, 30));

        //Verifica che chiamare createConcreteTrigger con un nome di trigger nullo
        //lanci un'eccezione di tipo IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            factoryTrigger.createConcreteTrigger(null, LocalTime.of(12, 30));
        });
    }
}



