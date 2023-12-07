import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class FactoryTriggerTest {

    @Test
    void testCreateConcreteTrigger_TriggerTime_Success() {
        FactoryTrigger factory = new FactoryTrigger();
        LocalTime triggerTime = LocalTime.of(12, 30);

        Trigger trigger = factory.createConcreteTrigger("TriggerTime", triggerTime, 0);

        assertNotNull(trigger);
        assertTrue(trigger instanceof TriggerTime);
        assertEquals(triggerTime, ((TriggerTime) trigger).getTime());
    }

    @Test
    void testCreateConcreteTrigger_TriggerDayOfWeek_Success() {
        FactoryTrigger factory = new FactoryTrigger();

        Trigger trigger = factory.createConcreteTrigger("TriggerDayOfWeek", LocalTime.now(), 3);

        assertNotNull(trigger);
        assertTrue(trigger instanceof TriggerDayOfWeek);
        assertEquals(3, ((TriggerDayOfWeek) trigger).getDayOfWeek().getValue());
    }

    @Test
    void testCreateConcreteTrigger_TriggerDayOfMonth_Success() {
        FactoryTrigger factory = new FactoryTrigger();
        LocalTime triggerTime = LocalTime.of(15, 45);

        Trigger trigger = factory.createConcreteTrigger("TriggerDayOfMonth", triggerTime, 10);

        assertNotNull(trigger);
        assertTrue(trigger instanceof TriggerDayOfMonth);
        assertEquals(10, ((TriggerDayOfMonth) trigger).getDayOfMonth().getDayOfMonth());
    }

    @Test
    void testCreateConcreteTrigger_InvalidTriggerName() {
        FactoryTrigger factory = new FactoryTrigger();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> factory.createConcreteTrigger("InvalidTrigger", LocalTime.now(), 0));

        assertEquals("Trigger non valido: InvalidTrigger", exception.getMessage());
    }

    @Test
    void testCreateConcreteTrigger_NullTriggerName() {
        FactoryTrigger factory = new FactoryTrigger();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> factory.createConcreteTrigger(null, LocalTime.now(), 0));

        assertEquals("Il nome del trigger non può essere nullo", exception.getMessage());
    }
}

