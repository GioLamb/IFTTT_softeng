import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class RuleTest {

    Rule rule1 = new Rule("Test","Promemoria","TriggerTime","abracadabra",LocalTime.of(17,32));
    Rule rule2 = new Rule("Test","Sveglia","TriggerTime","C:\\Users\\Giovanni\\Downloads\\Effetto Sonoro Campanello Casa.mp3",LocalTime.of(17,32));

    @Test
    void getContentAction() {
        assertEquals("abracadabra", rule1.getContentAction());
        assertEquals("C:\\Users\\Giovanni\\Downloads\\Effetto Sonoro Campanello Casa.mp3", rule2.getContentAction());
    }

    @Test
    void getContentTrigger() {
        assertEquals("17:32", rule1.getContentTrigger());
        assertEquals("17:32", rule2.getContentTrigger());
    }

    @Test
    void getNameTrigger() {
        assertEquals("TriggerTime", rule1.getNameTrigger());
        assertEquals("TriggerTime", rule2.getNameTrigger());
    }

    @Test
    void getNameRule() {
        assertEquals("Test", rule1.getNameRule());
        assertEquals("Test", rule2.getNameRule());
    }

    @Test
    void getNameAction() {
        assertEquals("Promemoria", rule1.getNameAction());
        assertEquals("Sveglia", rule2.getNameAction());
    }

    @Test
    void testToString() {
        assertEquals("Test,Promemoria,abracadabra,TriggerTime,17:32", rule1.toString());
        assertEquals("Test,Sveglia,C:\\Users\\Giovanni\\Downloads\\Effetto Sonoro Campanello Casa.mp3,TriggerTime,17:32", rule2.toString());
    }
}